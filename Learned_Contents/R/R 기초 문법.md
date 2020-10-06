## R 기초 문법

R: 통계 분석에 사용하는 프로그래밍 언어.



### R의 장단점



**장점**

> * 프로그래밍에 익숙하지 않아도 설치와 환경 구축이 손쉬움
> * 다른 프로그래밍 언어에 비해 한글 처리가 쉬움

**단점**

> * 범용 프로그래밍 언어(C, Java, Python 등)에 비해 처리 속도가 느림
> * 데이터 분석에만 특화되어 있다 보니 대규모 IT 서비스 개발에 접목하기 어려움



* **참고자료**

> * R 다운로드: https://www.r-project.org/
> * R Studio 다운로드: https://rstudio.com/
> * 전자책(R을 이용한 데이터 처리 & 분석 실무 - 더북):
>    https://thebook.io/006723/
> * 처음 시작하는 R데이터 분석 -한빛미디어, 저자 깃허브: 
>   https://github.com/newstars/HelloR





### **R 변수**

> * 스칼라, 펙터 : 1차원 변수
> * 벡터 : Java에서의 배열
> * 리스트: k,v
> * 행렬: 동일 타입의 matrix
> * 배열: 다차원 행렬
> * 데이터 프레임 : 다중(데이터타입) 행렬





#### **스칼라**

스칼라란 단일 차원의 값을 뜻하는 것으로 숫자 1, 2, 3, …을 예로 들 수 있다. 반면 좌표 평면 위에 있는 점인 (1, 2)는 2차원 값이므로 스칼라에 해당하지 않는다.





#### **NA: 상수**

R과 다른 언어의 가장 큰 차이 중 하나가 바로 NANot Available 상수다. NA는 데이터 값이 없음을 뜻한다. 예를 들어, 4명의 시험 점수가 있을 때 3명의 점수는 각각 80, 90, 75지만 4번째 사람의 점수를 모를 경우 NA를 이용해 4번째 사람의 점수를 표현한다.





#### **NULL과 NA의 차이**

NA는 결측치, 즉 값이 빠져 있는 경우를 뜻한다. 결측치가 존재하는 이유로는 데이터 입력 중 실수로 값을 입력하지 않은 경우, 값을 어떤 이유로든 관찰되지 못한 경우(예를 들어, 인구 조사에서 특정 가구가 소득을 기재하지 않은 경우), 마지막으로 해당 항목에 적절한 값이 없어서 값이 입력되지 않은 경우(예를 들어, 약품의 냄새를 기록하고 있는 칸에서 특정 약품은 향이 없는 경우)를 들 수 있다.반면 NULL은 프로그래밍의 편의를 위해 미정(undefined) 값을 표현하는 데 사용하는 개념이다. 

is_even이라는 변수에 a 변수의 값이 짝수면 TRUE, 홀수면 FALSE를 저장하는 다음 예를 보자.

is_even <- NULL if (a 가 짝수면) {   is_even <- TRUE } else {   is_even <- FALSE }

위 코드에서 if 조건문이 실행되기 전에는 is_even에 어떤 값을 줘야 할지 알 수 없어 NULL로 초기화했다. 그리고 if 문을 지나면서 is_even에 적절한 값이 할당되었다. 이처럼 NULL은 변숫값이 아직 미정인 상태를 표현하는 목적으로 사용한다.





#### **팩터**

팩터Factor는 범주형Categorical 데이터(자료)를 표현하기 위한 데이터 타입이다.범주형 데이터란 데이터가 사전에 정해진 특정 유형으로만 분류되는 경우를 뜻한다. 예를 들어, 방의 크기를 대, 중, 소로 나누어 기재하고 있을 때 특정 방의 크기를 ‘대’라고 적는다면 이 값은 범주형 데이터다. 이와 같이 범주형 변수가 담을 수 있는 값의 목록(이 예에서는 대, 중, 소)을 레벨(수준)level이라고 한다. 따라서 범주형 데이터를 저장하는 데이터 타입인 팩터에는 관측된 값뿐만 아니라 관측 가능한 값의 레벨도 나열해야 한다.

범주형 데이터는 또 다시 명목형Nominal과 순서형Ordinal으로 구분된다. 명목형 데이터는 값들 간에 크기 비교가 불가능한 경우를 뜻한다. 예를 들어, 정치적 성향을 좌파, 우파로 구분하여 저장한 데이터는 명목형이다. 반면 순서형 데이터는 대, 중, 소와 같이 값에 순서를 둘 수 있는 경우를 말한다.범주형 데이터에 상반하는 개념에는 수치형(Numerical) 데이터가 있다. 수치형 데이터는 값을 측정하거나 개수를 세는 경우와 같이 숫자로 나온 값을 의미한다. 예를 들어, 방의 크기를 30㎡, 28㎡와 같이 기록하는 경우나 학생들의 성적이 이에 해당한다.





#### **벡터**

벡터Vector는 다른 프로그래밍 언어에서 흔히 접하는 배열의 개념으로, 한 가지 스칼라 데이터 타입의 데이터를 저장할 수 있다. 예를 들어, 숫자만 저장하는 배열, 문자열만 저장하는 배열이 벡터에 해당한다.R의 벡터는 슬라이스Slice를 제공한다. 슬라이스란 배열의 일부를 잘라낸 뒤 이를 또 다시 배열처럼 다루는 개념을 뜻한다.또한, 벡터의 각 셀에는 이름을 부여할 수 있다. 따라서 벡터에 저장된 요소들을 색인을 사용하여 접근하는 것뿐 아니라 이름을 사용해서도 접근할 수 있다. 이런 특징을 사용하면 데이터를 좀 더 의미 있는 형태로 저장할 수 있다.





**리스트**

자료 구조 책에서 리스트List는 배열과 비교할 때 데이터를 중간 중간에 삽입하는 데 유리한 구조로 설명한다. 물론 그러한 장점은 동일하지만 R에서 리스트는 데이터를 접근한다는 관점에서 다른 언어의 해시 테이블 또는 딕셔너리로 종종 설명된다. 즉, 리스트는 ‘(키, 값)’ 형태의 데이터를 담는 연관 배열Associative Array이다.

또 다른 리스트의 특징은 벡터와 달리 값이 서로 다른 데이터 타입을 담을 수 있다는 점이다. 따라서 “이름”이라는 키에 “홍길동”이라는 문자열 값을 저장하고, “성적”이라는 키에 95라는 숫자 값을 저장할 수 있다.





**데이터프레임**

데이터 프레임Data Frame은 처리할 데이터를 마치 엑셀의 스프레드시트와 같이 표 형태로 정리한 모습을 하고 있다. 데이터 프레임의 각 열에는 관측값의 이름이 저장되고, 각 행에는 매 관측 단위마다 실제 얻어진 값이 저장된다.

```R
d1 <- data.frame(name=c("kim","lee","seo"),
                   ko=c(90,80,98),
                   en=c(100,78,92),
                   ma=c(99,68,88),
                   stringsAsFactors = F)
result <- d1[,c("ko"),drop=F]
```


drop: -> 뽑아낼 때 데이터프레임 구조를 유지한다(F), 벡터로 뽑아낸다(T)
stringsAsFactors: 데이터프레임을 생성할 때, 문자열을 팩터로 저장할 것인지 결정하는 옵션(Default = T)





### 데이터 저장하고 불러오기



#### 엑셀 파일

on console

```R
install.packages("readxl")
```

엑셀 파일을 불러오기 위해서는 readxl 패키지를 먼저 설치해야 한다.



**워크스페이스에 엑셀 파일이 있는 경우**

```R
library(readxl)
ex1 <- read_excel("data_ex.xls")
View(ex1)
```

**워크스페이스 외부에 파일이 있는 경우**

```R
library(readxl)ex1 <- read_excel("C:/Users/i/Downloads/HelloR-master/HelloR-master/Data/data_ex.xls")
View(ex1)
```

워크스페이스 외부의 파일을 읽어들일 때는 절대 경로를 명시해주어야 한다.





#### CSV, TXT 파일

CSV, TXT 파일로 저장하면 R 데이터 분석뿐만 아니라 여러 상황에서 쓸 수 있고, 다양한 프로그램에서도 사용할 수 있다.

CSV파일이나 TXT 파일로 저장할 때는 write.csv(), write.table() 함수를 사용하고,
불러올 때는 read.csv(), read.table() 함수를 사용한다.



```R
ex1 <- read.table("data_exx.TXT", encoding = "UTF-8", header=TRUE)> View(ex1)
```

**주의!**

* encoding 옵션을 설정해줘야 한글이 깨지지 않는다.

* 헤더 옵션을 걸지 않으면 헤더가 데이터로 들어온다.

  

```R
ex1 <- read.table("data_exx.TXT", fileEncoding = "UTF-8", 
                  header = TRUE, skip=1)
colnames(ex1)<-c("ID","NAME","AGE")
View(ex1)skip
```

 skip옵션을 걸어서 헤더를 가져오지 않고, 별도 생성해줄 수 있다.
(헤더가 한글로 만들어져 깨지는 경우 이용)

```R
 ex1 <- read.table("mydata2.txt", fileEncoding = "UTF-8", 
                   header=TRUE,sep = ",",stringsAsFactors = FALSE)
str(ex1)
```

,(comma)로 구분자를 두어 sep옵션으로 데이터를 나눌 수 있다.




#### RDA 파일 

*.rda 파일은 R 전용 파일이다. 

저장할 때는 save(데이터 세트, file="파일명") 함수를 사용하고, 
불러올 때는 load("경로/파일명")함수를 사용한다.

```R
s1 <- read.csv("shop.txt",
                header = F,
                stringsAsFactors = F,
                encoding = "UTF-8"
)
colnames(ws1) <- c("ID", "NAME", "AGE", "TEMP", "PRICE","QT")
tt <- ws1$PRICE*ws1$QT
ws1$TOTAL <-tt
write.csv(
  ws1,
  file="shoptotal.csv",
  row.names = T
)
save(ws1, file="shoptotal.rda")
sht <- load("shoptotal.rda")
```

위 예시는 1) csv 파일을 불러와 가공한 뒤, 2) 다시 csv파일과 rda파일로 나눠 저장하고, 3) 저장한 rda 파일을 다시 불러왔다.







### R 연산자

프로그래밍 언어(Java, Javascript)등을 이미 공부하고 왔기 때문에, 헷갈릴 수 있는 연산자만 골라 정리한다.



#### 산술 연산

| 산술 연산자 |  기능  |
| :---------: | :----: |
|     %/%     |   몫   |
|     %%      | 나머지 |
|  ** 또는 ^  |  제곱  |



#### 논리 연산

| 논리 연산자 |    기능     |
| :---------: | :---------: |
|      &      | 그리고(and) |
|     \|      |  또는(or)   |





### 분석을 위한 데이터 기본 정리

#### 데이터 파악 함수(View, str, dim, ls)

| 데이터 파악 함수  | 설명                                                         |
| ----------------- | ------------------------------------------------------------ |
| View(데이터 세트) | View 창을 통해 데이터 세트의 데이터 확인                     |
| str(데이터 세트)  | 데이터 세트에 있는 변수의 속성 확인                          |
| dim(데이터 세트)  | 데이터 세트의 데이터 프레임 확인<br />(행과 열의 수)         |
| ls(데이터 세트)   | 데이터 세트의 변수 항목을 리스트로 만듦<br />(열 제목을 확인) |





#### 변수명 변경하기

변수명 변경은  dplyr 패키지의 rename() 함수를 이용한다.

```R
install.packages("dplyr") # 패키지 다운로드
library(dplyr) # 패키지 로드
sh <- read.csv("shop2.txt",
                header = T,
                stringsAsFactors = F,
                encoding = "UTF-8"
) # 데이터 세트 로드 및 저장
sh<-rename(sh, ID=TX_ID, NAME=TX_NM, 
       AGE=TX_A, TEMP=TXT, PRICE=TX_P, QT=TX_Q) # 변수명 변경

```





#### 파생 변수 생성하기

보유한 데이터를 기반으로 연산 작업 등을 이용해 새로운 결과를 얻고 그 결과로 새로운 변수를 생성하는 것을 파생 변수 생성이라고 한다.



```R
sh$AGE_HL <- ifelse(sh$AGE >=30, "H" ,
                    ifelse(sh$AGE>=25, "M",
                           ifelse(sh$AGE>20, "L", "F")
                           )
                    )
sh$GRADE <- ifelse(sh$PRICE * sh$QT<=10000, "B" ,
                    ifelse(sh$PRICE * sh$QT<=30000, "S",
                           ifelse(sh$PRICE * sh$QT<=100000, "G", "F")
                    )
)
```



위와 같이 `데이터 세트$새 변수명` 으로 파생 변수를 생성할 수 있다.

`ifelse(조건, 참일 경우, 거짓일 경우)`는 Java에서의 삼항연산자와 유사하다.





### 데이터 전처리(데이터 추출 ~ 정제)

데이터 전처리 과정에서 주로 사용하는 `데이터 추출, 정렬, 요약, 결합` 함수는 앞서 사용한 dplyr 패키지에 속해 있다.



#### 필요한 데이터 추출하기

|   함수   |             설명              |
| :------: | :---------------------------: |
| select() | 원하는 데이터의 변수 선택(열) |
| filter() |  조건에 맞는 데이터 추출(행)  |



```R
#sh 데이터 세트의 ID, AGE 열만 가져오기
sh2 <- sh %>% select(ID,AGE) 

#sh 데이터 세트에 조건을 주고 해당 데이터 가져오기
sh3 <- sh %>% filter(GRADE=='G' & AGE_HL == 'M' & TEMP != 'NA')
```



#### 데이터 정렬하기(arrange)

변수를 크기순으로 정렬하여 새로운 데이터를 만들거나 조회할 때는 `arrange()` 함수를 사용한다. 



| 데이터 정렬 방식 |    정렬 함수    |              설명               |
| :--------------: | :-------------: | :-----------------------------: |
|     오름차순     |    arrange()    | 지정한 변수를 오름차순으로 정렬 |
|     내림차순     | arrange(desc()) | 지정한 변수를 내림차순으로 정렬 |



```R
#AGE 변수로 우선적으로 내림차순 정렬, 이후 MM 변수로 오름차순 정렬
sh4 <- sh %>% arrange(desc(AGE), MM)
```

이와 같이 여러 변수에 정렬을 중첩해 지시할 수도 있다.





#### 데이터 요약하기

데이터를 특정 집단별로 나누어 요약하는 형태로 데이터를 가공할 수 있다. 이러한 데이터 요약에는 `group_by()` 함수와 `summarise()` 함수를 이용한다.

또, 어떤 데이터로 요약할 지에 따라 아래 함수를 사용할 수 있다.

|    함수    |     기능      |
| :--------: | :-----------: |
|    n()     |     빈도      |
|   min()    |    최댓값     |
|   max()    |    최솟값     |
| quantile() | 변수의 분위수 |
|   sum()    |     합계      |
|   mean()   |     평균      |



```R
#TOT 변수에 PRICE의 합, AGES 변수에 AGE의 평균 담아 smr에 저장
smr <- sh %>% summarise(TOT = sum(PRICE), AGES = mean(AGE))

#NAME별 TOTAVG 파생변수 만들어 smr2에 저장
smr2 <- sh %>% group_by(NAME) %>%
summarise(TOTAVG =mean(PRICE * QT))

#smr2를 데이터 프레임으로 형변환
as.data.frame(smr2)
```

데이터 요약 함수를 사용하면 list형 변수로 들어온다. 따라서 데이터 프레임으로 사용하기 위해서는 형변환을 해주어야 한다.





#### 데이터 결합하기

데이터 결합 방식으로는 세로 결합과 가로 결합이 존재한다.

세로 결합은 동일한 열(변수)를 가진 2개 이상의 데이터 프레임을 결합하는 것이다.

bind_rows() 함수를 사용해 세로 결합을 수행할 수 있다.



가로 결합은 서로 다른 열을 1개 이상 가지고 있는 경우 2개 이상의 데이터 프레임을 결합하는 것이다.



| 가로 결합 방식 함수                                  | 설명                                                         |
| ---------------------------------------------------- | ------------------------------------------------------------ |
| left_join(데이터 세트 1, 데이터 세트 2, by="변수명") | 지정한 변수와 데이터 세트 1을 기준으로 데이터 세트 2에 있는 나머지 변수를 결합 |
| inner_join(데이터 세트 1, 데이터 세트 2, by="변수명) | 데이터 세트 1과 데이터 세트 2에서 기준으로 지정한 변수 값이 동일할 때만 결합 |
| full_join(데이터 세트 1, 데이터 세트 2, by="변수명)  | 데이터 세트1과 데이터 세트 2에서 기준으로 지정한 변수값 전체를 결합 |

가로 결합 방식은 SQL에서의 join과 크게 다르지 않다. 다만 인자의 순서에 주의할 필요가 있다.





```R
# 라이브러리 load
library(readxl)
library(descr)

# 데이터 load
y16 <- read_excel("y16.xlsx")
y17 <- read_excel("y17.xlsx")

# 가로결합
j1 <- left_join(y16,y17, by = "ID")
j2 <- right_join(y16,y17, by = "ID")
j3 <- inner_join(y16,y17,by="ID")

# 복사 후 데이터 프레임으로 형변환
j5 <- j3
j5<-as.data.frame(j5)

# 가로결합(full_join)
j4 <- full_join(y16,y17,by="ID")
j4 <- as.data.frame(j4)

# 파생변수 생성, NA값 제거
j4$SUM_AMT <- rowSums(j4 %>% select(AMT16, AMT17), na.rm = T)
j4$SUM_CNT <- rowSums(j4 %>% select(Y16_CNT, Y17_CNT), na.rm = T)
```



가로 결합을 수행할 때는 NA(결측값)이 있을 수 있음을 고려해야 한다. 결측값 처리가 필요할 경우 위처럼 `na.rm = T` 옵션을 추가하여 수행할 수 있다.





### 기초 통계 분석 함수

기초적인 기술통계량 함수와 함수 사용법을 살펴본다.



낯설 만한 함수

| 기술 통계 함수 | 기능     |
| -------------- | -------- |
| quantile()     | 분위수   |
| var()          | 분산     |
| sd()           | 표준편차 |
| kurtosis()     | 첨도     |
| skew()         | 왜도     |



기술 통계량 함수는 `summary()` 함수와 `describe()` 함수를 이용하면 한 번에 확인할 수 있다. 다만 `describe()` 함수는 `psych` 패키지에 내장된 함수이므로 해당 패키지를 설치하고 로드한 뒤 사용해야 한다.

```R
# 라이브러리 load
library(readxl)
library(psych)
library(descr)

# 데이터 load
y16 <- read_excel("y16.xlsx")
y17 <- read_excel("y17.xlsx")

# 가로결합 및 형변환
j4 <- full_join(y16,y17,by="ID")
j4 <- as.data.frame(j4)

# 파생변수 생성, NA값 제거
j4$SUM_AMT <- rowSums(j4 %>% select(AMT16, AMT17), na.rm = T)
j4$SUM_CNT <- rowSums(j4 %>% select(Y16_CNT, Y17_CNT), na.rm = T)

# 기술통계 활용 예시
j4<- j4 %>% group_by(AREA) %>% summarise(AMT_AVG = mean(SUM_AMT, na.rm = T))
j4<- j4 %>% group_by(AREA) %>% summarise(CNT_AVG = mean(SUM_CNT, na.rm = T))
```





#### 빈도 분석 및 그래프

빈도 분석은 주로 `freq()` 함수를 이용한다. 이 함수는 `descr` 패키지에 포함되어 있다.

`freq(변수, plot 여부)` 함수는 default로 빈도 막대그래프를 생성하는데, plot=F 옵션으로 막대그래프를 생성하지 않을 수도 있다.

이외 그래프로 히스토그램을 그릴 때는 `hist()`, 박스 플롯 `boxplot()`, 막대 그래프 `barplot()` 등을 사용할 수 있다.



그래프 함수의 인자로는 데이터 변수가 들어가는데, `main = "성별(barplot)"`과 같이 main 옵션으로 그래프의 제목을 설정할 수 있다.



또, 히스토그램의 경우 막대그래프와 달리 연속형 변수를 취급하기 때문에, x축과 y축의 범위를 설정해야 하는 경우가 빈번하다.

```R
hist(exdata1$AGE, 
     xlim = c(0, 60), ylim = c(0, 5),
     main ="AGE 분포")
```

이 때에는 위와 같이 x, y 값의 범위를 설정할 수 있다.



막대 그래프는  `xlab=""`, `ylab=""`으로 x, y 축 이름을 설정하고, `names =c("",""...)`으로 항목값을 설정할 수 있다.



또, 막대 그래프와 box plot 모두 `col=c("",""...)`옵션으로 각 막대나 박스의 색상을 변경할 수 있다.





### R 기초 실습

mid_exam.xlsx

![스크린샷 2020-10-06 오후 11.14.13]($md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-06%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2011.14.13.png)

final_exam.xlsx

![스크린샷 2020-10-06 오후 11.14.35]($md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-06%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2011.14.35.png)





``` R
library(readxl)
library(dplyr)
#처음 시작하는 R 데이터 분석 p150~p151 

# Q01
# 중간고사 성적 파일(mid_exam.xlsx)을 mid_exam에 저장
# 수학점수를 MATH_MID, 영어점수를 ENG_MID로 변경
mid_exam <- read_excel("mid_exam.xlsx")
mid_exam <- as.data.frame(mid_exam)
mid_exam <- rename(mid_exam,MATH_MID=MATH, ENG_MID=ENG)

# Q02
# 기말고사 성적 파일(final_exam.xlsx)을 final_exam에 저장
# 수학점수를 MATH_FINAL, 영어점수를 ENG_FINAL로 변경
final_exam <- read_excel("final_exam.xlsx")
final_exam <- as.data.frame(final_exam)
final_exam <- rename(final_exam,MATH_FINAL=MATH, ENG_FINAL=ENG)

# Q03
# 중간고사와 기말고사 점수가 모두 있는 데이터를 total_exam에 결합합 
total_exam <- inner_join(mid_exam, final_exam, by = "ID")

# Q04
# total_exam을 활용해 수학 점수 평균을 MATH_AVG, 영어 점수 평균을을 ENG_AVG에 구함
total_exam$MATH_AVG <- 
rowMeans(total_exam %>% select(MATH_MID, MATH_FINAL))
total_exam$ENG_AVG <- 
rowMeans(total_exam %>% select(ENG_MID, ENG_FINAL))


# Q05
# 성적이 모두 입력된 9명 학생의 평균을 TOTAL_AVG에 구함
total_exam$TOTAL_AVG <- rowMeans(total_exam %>% select(MATH_AVG,ENG_AVG))


# Q06
# 수학 점수와 영어 점수의 전체 평균을 구해본다
TOTAL_MATH_AVG <- mean(total_exam$MATH_AVG)
TOTAL_ENG_AVG <- mean(total_exam$ENG_AVG)

# Q07
# 중간고사 수학 80점 이상, 중간고사 영어 90점 이상인 학생을 선별
total_exam %>% filter(MATH_MID>=80 & ENG_MID >=90)

# Q08
# 수학 점수 평균과 영어 점수 평균에 대한 상자 그림
boxplot(total_exam %>% select(MATH_AVG,ENG_AVG))
```

![스크린샷 2020-10-06 오후 11.10.08]($md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-06%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2011.10.08.png)