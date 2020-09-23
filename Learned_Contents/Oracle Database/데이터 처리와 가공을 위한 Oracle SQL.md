## 데이터 처리와 가공을 위한 Oracle SQL

### 1. 문자 데이터를 가공하는 문자 함수

#### 1.1. 대, 소문자 변경 함수

* UPPER(문자열)
  * 괄호 안 문자 데이터를 모두 대문자로 변환하여 반환
* LOWER(문자열)
  * 괄호 안 문자 데이터를 모두 소문자로 변환하여 반환
* INITCAP(문자열)
  * 괄호 안 문자 데이터의 첫 글자는 대문자, 나머지는 소문자 반환

##### * 활용 예시

```sql
SELECT ENAME, UPPER(ENAME), LOWER(ENAME), INITCAP(ENAME) 
	FROM EMP;
```

#### 1.2. 문자열 길이를 구하는 LENGTH 함수

* LENGTH(문자열)
  * 특정 문자열의 길이를 구할 때 LENGTH 함수를 사용
  * LENGTH 함수를 WHERE 절에 사용해서 선별하는 것도 가능

##### * 활용 예시

```sql
SELECT ENAME, LENGTH(ENAME)
	FROM EMP;
	
SELECT ENAME, LENGTH(ENAME)
	FROM EMP
	WHERE LENGTH(ENAME) >=5;
```

#### 1.3. 문자열 일부를 추출하는 SUBSTR 함수

* SUBSTR(문자열 데이터, 시작 위치, 추출 길이)
  * 문자열 데이터의 시작 위치부터 추출 길이만큼 추출. 
    시작 위치가 음수일 경우, 마지막 위치부터 거슬러 올라간 위치에서 시작
* SUBSTR(문자열 데이터, 시작 위치)
  * 문자열 데이터의 시작 위치부터 문자열 데이터 끝까지 추출
    시작 위치가 음수일 경우, 마지막 위치부터 거슬러 올라간 위치에서 끝까지 추출

> **!!! 위치를 지정하는 인덱스는 0이 아닌 1부터 시작함.**

##### * 활용 예시

```sql
SELECT JOB, SUBSTR(JOB, 1, 2), SUBSTR(JOB, 3, 2), SUBSTR(JOB, 5)
	FROM EMP;
```



#### 1.4. 문자열 데이터 안에서 특정 문자 위치를 찾는 INSTR 함수

* INSTR([대상 문자열 데이터(필수)]
  			[위치를 찾으려는 부분 문자(필수)]
  			[위치 찾기를 시작할 대상 문자열 데이터 위치(선택, 기본값 1)]
  			[시작 위치에서 찾으려는 문자가 몇 번째인지 지정(선택, 기본값 1)])

##### * 활용 예시

```sql
SELECT INSTR('HELLO, ORACLE!', 'L') AS INSTR_1,
	INSTR('HELLO, ORACLE!', 'L', 5) AS INSTR_2,
	INSTR('HELLO, ORACLE!', 'L', 2, 2) AS INSTR_3
	FROM DUAL;
```



#### 1.5. 특정 문자를 다른 문자로 바꾸는 REPLACE 함수

* REPLACE([문자열 데이터 또는 열 이름(필수)], [찾는 문자(필수)], [대체할 문자(선택)])
  * 만약 대체할 문자를 입력하지 않는다면 찾는 문자로 지정한 문자는 문자열 데이터에서 삭제

```sql
SELECT '010-1234-5678' AS REPLACE_BEFORE,
		REPLACE('010-1234-5678', '-', ' ') AS REPLACE_1,
		REPLACE('010-1234-5678', '-') AS REPLACE_2
	FROM DUAL;
```



#### 1.6. 데이터의 빈 공간을 특정 문자로 채우는 LPAD, RPAD 함수

> LPAD와 RPAD는 각각 Left Padding, Right Padding을 뜻함.
>
> 데이터와 자릿수를 지정한 후 데이터 길이가 지정한 자릿수보다 작을 경우에 나머지 공간을 특정 문자로 채우는 함수. 
>
> LPAD는 남은 빈 공간을 왼쪽에 채우고, RPAD는 오른쪽에 채움.
>
> 만약 빈 공간에 채울 문자를 지정하지 않으면 LPAD와 RPAD는 함수의 빈ㄴ공간의 자릿수만큼 공백 문자로 띄움.

* LPAD([문자열 데이터 또는 열이름(필수)], [데이터의 자릿수(필수)], [빈 공간에 채울 문자(선택)])
* RPAD([문자열 데이터 또는 열이름(필수)], [데이터의 자릿수(필수)], [빈 공간에 채울 문자(선택)])

##### * 활용 예시

```sql
SELECT 'Oracle',
		LPAD('Oracle', 10, '#') AS LPAD_1,
		RPAD('Oracle', 10, '*') AS RPAD_1,
		LPAD('Oracle', 10) AS LPAD_2,
		RPAD('Oracle', 10) AS RPAD_2
	FROM DUAL;
```



#### 1.7. 두 문자열 데이터를 합치는 CONCAT 함수

* CONCAT(문자열1, 문자열2)

##### * 활용 예시

두 문자열 사이에 콜론(:) 넣고 연결하기

```sql
SELECT CONCAT(EMPNO, ENAME),
		CONCAT(EMPNO, CONCAT(':',ENAME))
	FROM EMP
	WHERE ENAME = 'SCOTT';
```



#### 1.8. 특정 문자를 지우는 TRIM, LTRIM, RTRIM 함수

* TRIM([삭제 옵션(선택)] [삭제할 문자(선택)] FROM [원본 문자열 데이터(필수)])
  * 삭제할 문자를 지정하지 않으면 기본적으로 공백 제거

> 삭제 옵션
>
> * LEADING : 왼쪽에 있는 글자 제거
> * TRAILING : 오른쪽에 있는 글자 제거
> * BOTH : 양쪽 글자 제거
>
> LTRIM이나 RTRIM을 사용하면 삭제 옵션을 외우지 않고 활용 가능.

### 2. 숫자 데이터를 연산하고 수치를 조정하는 숫자 함수

#### 2.1. 반올림, 올림, 버림 함수

| 함수  |                       설명                       |
| :---: | :----------------------------------------------: |
| ROUND |  지정된 숫자의 특정 위치에서 반올림한 값을 반환  |
| TRUNC |   지정된 숫자의 특정 위치에서 버림한 값을 반환   |
| CEIL  | 지정된 숫자보다 큰 정수 중 가장 작은 정수를 반환 |
|  MOD  | 지정된 숫자보다 작은 정수 중 가장 큰 정수를 반환 |

* ROUND([숫자(필수)], [반올림 위치(선택)])
* TRUNC([숫자(필수)], [버림 위치(선택)])
* CEIL([숫자(필수)])
* FLOOR([숫자(필수)])

#### 2.2. 숫자를 나눈 나머지 값을 구하는 MOD 함수

* MOD([나눗셈 될 숫자(필수)], [나눌 숫자(필수)])

#### 3. 날짜 데이터를 다루는 형 변환 함수



#### 4. 자료형을 변환하는 형 변환 함수



#### 5. NULL 처리 함수



#### 6. 상황에 따라 다른 데이터를 반환하는 DECODE 함수와 CASE문



