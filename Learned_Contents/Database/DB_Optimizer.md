# DB Optimizer는 무엇이고, 왜 필요한가?

서버의 70% 이상 처리 시간이 SQL 처리시간(DB에서 데이터를 조회하고 저장하는 작업)이라고 한다. (우아한Tech CU님에 의하면) 따라서, SQL 처리 시간을 단축하는 것이 성능 개선에 매우 중요하다.

<br><br><br>

## 쿼리처리 흐름

<img width="614" alt="스크린샷 2021-05-19 오후 3 19 44" src="https://user-images.githubusercontent.com/46706670/118930731-14740680-b981-11eb-8b37-dae61195377f.png">

출처: SQL LevelUp

* Parser: 사용자가 쿼리를 작성하면 가장 먼저 Parser라는 곳에 들어간다. Parser는 전달 받은 쿼리를 파싱하면서 문법적으로 오류가 없는지를 확인하고, DB가 이해할 수 있는 언어로 변환해 Optimizer로 전달한다.
* Optimizer: 받은 쿼리를 바탕으로 여러 실행계획을 세우고 각 실행계획의 비용 평가를 한다. 비용 평가를 위해 인덱스의 유무나 갖고 있는 데이터의 통계 정보를 활용한다. 통계 정보를 활용하기 위해 카탈로그 매니저를 참조하는데, 카탈로그 매니저에는 데이터 분산 또는 편향 정보 등의 통계 정보를 저장하고 있다. Optimizer는 실행계획의 비용 평가를 거쳐 각 플랜(실행계획) 중 가장 비용이 낮은 실행계획을 선택해 실행하고, 그 결과를 다시 사용자에게 보여준다.

<br><br>

Optimizer가 비용 평가를 하는 과정에서 만약, 카탈로그 매니저의 한정된 통계로 인해 최적화를 올바르게 못할 수도 있다. 이 때는 **<u>실행계획을 개발자가 직접 바꿔주는 방식</u>**으로 최적화해야 한다.

<br><br>

### 실행계획

실행계획은 SQL에서 요구한 사항을 처리하기 위한 절차와 방법이다.

동일한 SQL에 대해 결과를 내는 방법은 여러가지지만 각 비용은 서로 다르다.

현재 작성한 쿼리의 실행계획을 보기 위해서는 mySQL에서는 EXPLAIN + SQL문을 사용해 확인할 수 있다.

(Oracle의 경우 EXPLAIN plan FOR+SQL로 확인할 수 있다.)

```sql
SELECT hobby, max(respondent)
FROM survey_results_public
WHERE respondent < 100
GROUP BY hobby;
/*
respondent가 100미만인 행에서 hobby를 기준으로 그룹핑 후 그룹별 MAX값 가져오기
*/
```

<img width="125" alt="스크린샷 2021-05-19 오후 3 36 20" src="https://user-images.githubusercontent.com/46706670/118930743-18078d80-b981-11eb-891a-6c45c31df2b0.png">

위와 같은 쿼리를 실행해 결과가 다음과 같이 나온다고 할 때, **기본적인 상태에서 이 쿼리의 수행시간이 0.92초(약 1초)**가 나왔다. 소요 시간이 상당히 길다.  이 때의 실행 계획을 살펴보면 다음과 같다.

<img width="572" alt="스크린샷 2021-05-19 오후 3 41 19" src="https://user-images.githubusercontent.com/46706670/118930760-1ccc4180-b981-11eb-9104-909e28f3e4eb.png">

실행계획에서는 **Type, Rows, Extra 3개 컬럼의 내용이 중요**하다. 

<br>

**Type: ALL이라는 뜻은 Table Full Scan이라는 의미이며 이는 테이블의 row를 모두 접근(가장 비효율적인 방법)한다는 뜻이다. 물론 전체 행을 Select하는 경우처럼 Table Full Scan이 모든 경우에서 가장 비효율적인 것은 아니지만, 이 경우에서는 아니다.** 

<br>

> **Full Table Scan이 더 유리한 경우**
>
> - 테이블의 크기가 작을 때
> - 조건절이 없을 때(where, on)
> - 조건에 일치하는 레코드 수가 굉장히 많을 때

<br>

**Extra: Using temporary, Using file sort**

<br>

Using temporary : 쿼리를 처리하는 동안 중간결과를 담아두기 위한 임시테이블

Using file sort : 결과를 정렬 (이 경우에서는 딱히 정렬할 필요가 없다)

<br><br>

위의 경우, Index Scan을 고려하게 되는데 **인덱스를 구성하는 방법에 따라 2가지**를 고민해 볼 수 있다.

- **respondent, hobby 순으로 인덱싱**

  > create index respondent_hobby_index on survey_results_public (respondent, hobby)

  <br>

- **hobby, respondent 순으로 인덱싱**

  > create index respondent_hobby_index on survey_results_public (hobby, respondent)

결과적으로 두 방법 중 hobby, respondent 순의 방법으로 인덱싱을 구성하는 것이 훨씬 효율적이다.

<img width="533" alt="스크린샷 2021-05-20 오후 2 22 22" src="https://user-images.githubusercontent.com/46706670/118930825-31103e80-b981-11eb-9b3c-2bde3b6a9ca5.png">

<img width="605" alt="스크린샷 2021-05-20 오후 2 26 04" src="https://user-images.githubusercontent.com/46706670/118930831-32da0200-b981-11eb-816e-484dc6013b98.png">

<br>

그 결과, 실행계획은 위처럼 Scan Type이 Range(Index Range Scan)로 변경되었고 Rows도 2로 대폭 감소한 것을 볼 수 있다. 이를 실제로 실행해보면 실행 시간이 아래와 같이 대폭 향상되었다.

<br><br>

<img width="481" alt="스크린샷 2021-05-20 오후 2 26 29" src="https://user-images.githubusercontent.com/46706670/118930838-340b2f00-b981-11eb-9a0b-5a82de8df987.png">





출처: [[10분 테코톡]버디의 DB Optimizer - 우아한Tech(Youtube)](https://youtu.be/dP0MIgyrqlo)

