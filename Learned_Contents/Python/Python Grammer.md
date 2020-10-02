## Python Grammer

본 문서는 파이썬 언어에 익숙해지기 위해 기초적인 문법 중 낯선 내용을 담는다. 

참고: 점프 투 파이썬 wikidocs (https://wikidocs.net/book/1)

### `**` 연산자

x의 y제곱 값을 계산하는 연산자이다.

```python
>>> a = 3
>>> b = 4
>>> a ** b
81
```



### `//` 연산자

`/` 연산자를 사용하여 7나누기 4를 하면 그 결과는 1.75가 된다.

```python
>>> 7 / 4
1.75
```

`//` 연산자는 나눗셈 후 몫을 반환한다. 

```python
>>> 7 // 4
1
```

1.75에서 몫에 해당되는 정수값 1만 돌려주는 것을 확인할 수 있다.



### 여러 줄인 문자를 변수에 대입하고 싶을 때 """ or '''

1. 줄을 바꾸기 위한 이스케이프 코드 `\n`을 삽입하기
2. 연속된 작은 따옴표 3개(`'''`) 또는 큰 따옴표 3개(`"""`) 사용하기

```python
>>> multiline ='''
	Life is too short
	You need python
'''
```



### 문자열 곱하기

```python
>>> a = "python"
>>> a * 2
'pythonpython'
```

문자열을 갖고 있는 변수에 상수를 곱하면 문장을 반복하는 연산이 수행된다.

```python
>>> print("=" * 50)
>>> print("My Program")
>>> print("=" * 50)

==================================================
My Program
==================================================
```



### 문자열 길이 구하기

문자열의 길이는 len 함수를 사용하여 구할 수 있다.

```python
>>> a = "Life is too short"
>>> len(a)
17
```



### 문자열 슬라이싱

```python
>>> a = "Life is too short, You need Python"
>>> a[0:4]
'Life'
```

슬라이싱 기법으로 a[시작 번호: 끝 번호]를 지정할 때 끝 번호는 포함되지 않는다.



```python
>>>a[19:]
'You need Python'
```

a[시작 번호: 끝 번호]에서 끝 번호를 생략하면 시작 번호부터 그 문자열의 끝까지 뽑아낸다.



```python
>>>a[:17]
'Life is too short'
```

a[시작 번호: 끝 번호]에서 시작 번호를 생략하면 문자열의 처음부터 끝 번호까지 뽑아낸다.



```python
>>>a[:]
'Life is too short, You need Python'
```

a[시작 번호: 끝 번호]에서 시작 번호와 끝 번호 모두 생략하면 문자열의 처음부터 끝까지 뽑아낸다.



**`Pithon`이라는 문자열을 `Python`으로 바꾸려면?**

```python
>>> a = "Pithon"
>>> a[1]
'i'
>>> a[1] = 'y'
```

가장 먼저 위와 같이 문자열의 요솟값 변경을 시도하겠으나, 이는 오류가 발생한다. 문자열의 요솟값은 바꿀 수 있는 값이 아니다. 그래서 immutable한 자료형이라고도 부른다. 이럴 때는 슬라이싱 기법을 사용하자.

```python
>>> a = "Pithon"
>>> a[:1]
'P'
>>> a[2:]
'thon'
>>> a[:1] + 'y' + a[2:]
'Python'
```



### 문자열 포매팅

1. 숫자 바로 대입

   ```python
   >>> "I eat %d apples." % 3
   'I eat 3 apples.'
   ```

2. 문자열 바로 대입

   ```python
   >>> "I eat %s apples." % "five"
   'I eat five apples.'
   ```

3. 숫자 값을 나타내는 변수로 대입

   ```python
   >>> number = 3
   >>> "I eat %d apples." % number
   'I eat 3 apples.'
   ```

4. 2개 이상의 값 넣기

   ```python
   >>> number = 10
   >>> day = "three"
   >>> "I ate %d apples. so I was sick for %s days." % (number, day)
   'I ate 10 apples. so I was sick for three days.'
   ```

   2개 이상의 값을 넣으려면 마지막 % 다음 괄호 안에 콤마(,)로 구분하여 각각의 값을 넣어주면 된다.

문자열 포맷 코드

| 코드 | 설명                      |
| ---- | ------------------------- |
| %s   | 문자열(String)            |
| %c   | 문자 1개(character)       |
| %d   | 정수(Integer)             |
| %f   | 부동소수(floating-point)  |
| %o   | 8진수                     |
| %x   | 16진수                    |
| %%   | Literal % (문자 `%` 자체) |

여기서 %s 코드는 어떤 형태의 값이든 변환해 넣을 수 있다.

```python
>>> "I have %s apples" % 3
'I have 3 apples'
>>> "rate is %s" % 3.234
'rate is 3.234'
```

3을 문자열 안에 삽입하려면 %d를 사용하고, 3.234를 삽입하려면 %f을 사용해야 한다. 하지만 %s를 사용하면 이를 생각하지 않아도 된다. 왜냐하면 %s는 자동으로 % 뒤에 있는 값을 문자열로 바꾸기 때문이다.



포맷 코드와 숫자를 함께 사용하면 더 유용하게 사용할 수 있다.

1. 정렬과 공백

   ```python
   >>> "%10s" % "hi"
   '        hi'
   ```

   `%10s`는 전체 길이가 10인 문자열 공간에서 대입되는 값을 오른쪽으로 정렬하고 그 앞의 나머지는 공백으로 남겨 두라는 의미이다. 반대쪽인 왼쪽 정렬은 `%-10s`이다.

   ```python
   >>> "%-10sjane." % 'hi'
   'hi        jane.'
   ```

   

2. 소수점 표현하기

```python
>>> "%0.4f" % 3.42134234
'3.4213'
```

3.42134234를 소수점 네 번째 자리만 나타내고 싶은 경우에는 위와 같이 사용한다. 즉 여기서 '.'의 의미는 소수점 포인트를 말하고 그 뒤의 숫자 4는 소수점 뒤에 나올 숫자의 개수를 말한다. 

```python
>>> "%10.4f" % 3.42134234
'    3.4213'
```

위 예는 숫자 3.42134234를 소수점 네 번째 자리까지만 표시하고 전체 길이가 10개인 문자열 공간에서 오른쪽으로 정렬하는 예를 보여준다.

### format 함수를 사용한 포매팅

문자열의 format함수를 사용하면 좀 더 발전된 스타일로 문자열 포맷을 지정할 수 있다. 앞에서 살펴본 문자열 포매팅 예제를 format 함수를 사용해서 바꾸면 다음과 같다.

1. 숫자 바로 대입하기

   ```python
   >>> "I eat {0} apples".format(3)
   'I eat 3 apples'
   ```

2. 문자열 바로 대입하기

   ```python
   >>> "I eat {0} apples".format("five")
   'I eat five apples'
   ```

3. 숫자 값을 가진 변수로 대입하기

   ```python
   >>> number = 3
   >>> "I eat {0} apples".format(number)
   'I eat 3 apples'
   ```

4. 2개 이상의 값 넣기

   ```python
   >>> number = 10
   >>> day = "three"
   >>> "I ate {0} apples. so I was sick for {1} days.".format(number, day)
   'I ate 10 apples. so I was sick for three days.'
   ```

5. 이름으로 넣기

   ```python
   >>> "I ate {number} apples. so I was sick for {day} days.".format(number=10, day=3)
   'I ate 10 apples. so I was sick for 3 days.'
   ```

6. 인덱스와 이름을 혼용해서 넣기

   ```python
   >>> "I ate {0} apples. so I was sick for {day} days.".format(10, day=3)
   'I ate 10 apples. so I was sick for 3 days.'
   ```

7. 왼쪽 정렬

   ```python
   >>> "{0:<10}".format("hi")
   'hi        '
   ```

8. 오른쪽 정렬

   ```python
   >>> "{0:>10}".format("hi")
   '        hi'
   ```

9. 가운데 정렬

   ```python
   >>> "{0:^10}".format("hi")
   '    hi    '
   ```

10. 공백 채우기

    ```python
    >>> "{0:=^10}".format("hi")
    '====hi===='
    >>> "{0:!<10}".format("hi")
    'hi!!!!!!!!'
    ```

11. 소수점 표현하기

    ```python
    >>> y = 3.42134234
    >>> "{0:0.4f}".format(y)
    '3.4213'
    ```

12. f문자열 포매팅

    ```python
    >>> name = '홍길동'
    >>> age = 30
    >>> f'나의 이름은 {name}입니다. 나이는 {age}입니다.'
    '나의 이름은 홍길동입니다. 나이는 30입니다.'
    ```

### 문자열 관련 함수들

