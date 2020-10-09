## R 필수 패키지와 함수



### `dplyr` 패키지

`dplyr` 패키지의 주요 함수는 아래와 같다.

|    함수명     |           기능            |
| :-----------: | :-----------------------: |
|   filter()    |    조건에 맞는 행 추출    |
|   arrange()   | 지정한 열을 기준으로 정렬 |
|   select()    |          열 추출          |
|   mutate()    |          열 추가          |
|  summarize()  |        데이터 요약        |
|  distinct()   |       중복 값 제거        |
|  group_by()   |     데이터 그룹 생성      |
|  sample_n()   |      n개의 샘플 추출      |
| sample_frac() |    n% 비율의 샘플 추출    |
|      %>%      | 파이프 연산자, 함수 연결  |

 

##### mutate( ) 함수로 열 추가하기

mutate( ) 함수는 데이터 세트에 열을 추가할 때 사용한다. 기존 열을 가공한 후 그 결과 값을 기존 열이나 새로운 열에 할당할 수 있다.

>  mutate(데이터 세트, 추가할 열 이름 = 조건 1, ...)



기존 mtcars 데이터 세트에 years라는 생산 연도 열을 추가하고 일괄적으로 1974를 추가

```R
library(dplyr)

# 데이터 확인 & 실습을 위한 데이터 복사
head(mtcars)
mtdatas = mtcars
head(mtdatas)

# 열 추가하기
head(mutate(mtdatas, years= "1974"))
```



##### distinct( ) 함수로 중복 값 제거하기

> distinct(데이터 세트, 중복 값 제거할 열 이름)





##### 데이터에서 샘플 추출하기

sample_n( ) 함수와 sample_frac( ) 함수는 데이터에서 샘플 데이터를 추출할 때 사용한다. 다만 두 함수는 샘플 데이터를 추출하는 기준이 다르다. sample_n( ) 함수는 개수를 기준으로 추출하고, sample_frac(  ) 함수는 비율을 기준으로 추출한다.



```R
# 샘플 추출하기
# mtdatas에서 10개의 데이터 추출
sample_n(mtdatas, 10)
```



![스크린샷 2020-10-08 오전 11.36.34](/Users/jaeuk/Library/Application%20Support/typora-user-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-08%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2011.36.34.png)



샘플 데이터는 무작위로 추출하므로, 추출할 때마다 다른 결과가 나온다,