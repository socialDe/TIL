## Java, R Programming 연동(Rserve) in Mac



### Rserve 설치

Java, Eclipse, R, Rstudio가 모두 설치된 상황이다.

Rserve 패키지를 설치하기 위해 콘솔에 다음과 같이 실행한다.

```R
#패키지 설치
install.packages("Rserve")

#패키지 업데이트
install.packages('Rserve',,"http://rforge.net/",type="source")
```

기본적으로 Rserve를 설치한 뒤 연동이 되어야하지만, 아래와 같은 오류메시지를 계속 받았다.

> Error in run.Rserve() : long vectors not supported yet: qap_encode.c:36

해결 방법을 찾던 중 Mac 환경에서 R을 최신 버전으로 설치해 오류가 있을 가능성이 있다는 글을 보고 업데이트를 한 결과 정상적으로 연동되었다.



### Rserve 시작

```R
library(Rserve)
Rserve(args = "--no-save")

#백그라운드에서 Rserve 실행
Rserve::Rserve(args = "--no-save")
# 소켓 에러 발생할 경우 프로세스 찾아서 종료
```

위와 같이 Rserve를 실행시키면, 연동 준비는 끝이 난다. 이제 Eclipse에서 Rserve로 접근해주면 된다.



백그라운드에서 Rserve를 동작시키면, Rstudio에서 끄거나 재실행시키는 것이 불편하다.

심지어 Rstudio를 종료하더라도, 백그라운드에서 Rserve는 계속 돌아가고 있다.



이 때는 Terminal을 이용해 프로세스를 관리해준다.

```bash
# 6311 포트에 있는 프로세스 확인
lsof -i :6311

# 프로세스 강제 종료
sudo kill -9 프로세스번호(PID)
```









### R Setting

![스크린샷 2020-10-09 오후 3.11.51](/Users/jaeuk/Library/Application%20Support/typora-user-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-09%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%203.11.51.png)

R에서 사용할 데이터를 간단하게 준비했다. (txt, utf-8)



```R
stavg <- function(){
  library(dplyr)
  library(reshape2)
  
  #데이터 입력
  st <- read.csv("/Users/jaeuk/Desktop/Dev/RPractice/day04/st.txt", fileEncoding = "utf-8")
  
  #데이터 가공(합, 평균)
  st <- melt(st, id.vars="NAME")
  st <- st %>% group_by(NAME) %>% summarise(TOT=sum(value),
                                            AVG=mean(value))
  #데이터프레임 형변환
  result <- as.data.frame(st)
  
  return (result) 
}
```

그리고 이와 같이 행별 점수 합계, 평균을 만들어 반환하는 함수 R 스크립트(stavg.R)를 작성했다.



### Eclipse Settings

```Java
package r;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RTest4 {

	public static void main(String[] args) throws REXPMismatchException {
		RConnection rconn = null;
		try {
			rconn = new RConnection();
			rconn.setStringEncoding("utf8");
			rconn.eval("source('/Users/jaeuk/Desktop/Dev/RPractice/day04/stavg.R',encoding='UTF-8')");
			REXP rexp = rconn.eval("stavg()");
			RList rlist =rexp.asList();
			String names[] = rlist.at("NAME").asStrings();
			double tots[] = rlist.at("TOT").asDoubles();
			double avgs[] = rlist.at("AVG").asDoubles();
			
			for(int i =0; i<names.length;i++) {
				System.out.println(names[i]+ " "+ tots[i]+" "+ avgs[i]);
			}
			
			
		} catch (RserveException e) {
			e.printStackTrace();
		}
		System.out.println("Connection Complete !");
		
		rconn.close();
	}

}
```

위와 같이 `RConnection`, `REXP`, `RList` 객체를 이용해 값을 받아오고, 처리한다.



결과 Console

![스크린샷 2020-10-09 오후 3.15.48](/Users/jaeuk/Library/Application%20Support/typora-user-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-09%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%203.15.48.png)