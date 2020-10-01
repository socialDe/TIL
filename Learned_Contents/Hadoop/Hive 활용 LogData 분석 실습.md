

## Hive 활용 LogData 분석 실습

LogData 생성과 관리, Hive를 활용한 데이터 핸들링 방법을 학습하기 위해 하루간 실습을 진행했다.

### 목차

> 1. 기획 내용
> 2. 빅데이터 수집 시스템 구축
> 3. 빅데이터 저장 시스템 구축
> 4. 통합 시스템 구축
> 5. 결과

### 1. 기획 내용

>  활용 기술 및 소프트웨어
>
> * 언어: Java(8), HTML, CSS, Javascript
>
> * 프레임워크&라이브러리: Spring, JQuery
>
> * 소프트웨어: Apache Tomcat(9), Maven, Hadoop(Hive), VirtualBox
>
> * OS: macOS Catalina 10.15.7,
>   CentOS 7(가상 환경) - Web, DB Server

차량 운전자의 생체정보를 기반으로 졸음운전 차량을 파악하고 위험 알람을 보내주는 운행 보조 시스템을 구현한다. 5분마다 운행 상태 데이터를 서버로 전송하는 차량 60대를 가상으로 생성하고, 운행 상태 데이터는 서버에서 로그로 저장한다. 서버는 Hive를 활용해 로그 데이터를 정형데이터로 가공하여 저장하고, 생체 이상이 있는 차량에 위험 알람을 전송하는 시나리오를 가정한다. 전체 시스템 관리자가 이용하는 웹을 간단하게 구현하고, 웹 상에 전송한 알람을 동적으로 생성시켜 확인할 수 있는 기능을 구현한다.

### 2. 빅데이터 수집 시스템 구축

차량의 운행 데이터(운전자ID, 운전자 성별, 운전자 연령, 운행 시간, 운전자 심박수)

각 차량에 비치된 안전벨트로부터 운전자의 생체 데이터(심박수)를 수집하는 것으로 가정한다. (2019 ICT 스마트섬유 공모전 수상작 아이디어 참조)

제대로 구현하기 위해서는 IoT 개발에 관련한 실습을 진행한 후 구현을 시도할 수 있겠으나, 본 실습에서는 LogData와 Hive 활용에 관한 실습이므로, 간단히 구현했다.
(별도 Java Project 생성 -> Server IP로 데이터 전송)

**CarStatusVO: 차량 데이터 객체**

```java
public class CarStatusVO {
	String id;
	String ownergender;
	int owenerage;
	int opertime;
	int vital;
	
	public CarStatusVO() {
		super();
	}
	public CarStatusVO(String id, String ownergender, int owenerage, int opertime, int vital) {
		super();
		this.id = id;
		this.ownergender = ownergender;
		this.owenerage = owenerage;
		this.opertime = opertime;
		this.vital = vital;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnergender() {
		return ownergender;
	}
	public void setOwnergender(String ownergender) {
		this.ownergender = ownergender;
	}
	public int getOwenerage() {
		return owenerage;
	}
	public void setOwenerage(int owenerage) {
		this.owenerage = owenerage;
	}
	public int getOpertime() {
		return opertime;
	}
	public void setOpertime(int opertime) {
		this.opertime = opertime;
	}
	public int getVital() {
		return vital;
	}
	public void setVital(int vital) {
		this.vital = vital;
	}
	@Override
	public String toString() {
		return "CarStatusVO [id=" + id + ", ownergender=" + ownergender + ", owenerage=" + owenerage + ", opertime="
				+ opertime + ", vital=" + vital + "]";
	}
```

**vehicleApp: 데이터 전송 Java Project**

```java
package vehicleApp;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class App {
	
	String url;
	public App() {
		
	}
	public void getData() {
		String id = "user";
		String ownergender;
		int ownerage;
		int opertime;
		int vital;
		Random r = new Random();
		opertime = 0;
		ownerage = r.nextInt(50)+20;
		
		// 60대 차량 데이터 생성
		for(int i=1; i<61;i++) {
			id+=i;
			vital= r.nextInt(40)+70;
			int ownergender_temp = r.nextInt(2);
			if(ownergender_temp ==1) {
				ownergender = "M";
			}else {
				ownergender = "F";
			}
			CarStatusVO cstatus = new CarStatusVO(id, ownergender, ownerage, opertime, vital);
			try {
				sendData(cstatus);
				System.out.println("Send Data ...."+vital);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		// 5분마다 데이터 전송
		while(true) {
			int opertime_temp =1;
			// 60대 반복 수행
			for(int i=1;i<61;i++) {
				int ownergender_temp = r.nextInt(2);
				if(ownergender_temp ==1) {
					ownergender = "M";
				}else {
					ownergender = "F";
				}
				opertime += 5 *  opertime_temp;
				vital = r.nextInt(40) + 70;
				opertime_temp++;
			}
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
	}
	public void sendData(CarStatusVO cs) throws Exception{
		url = "http://192.168.56.101/vehi2/carstatus.mc";
		url+= "?id="+cs.getId()+"&ownergender="+cs.getOwnergender()
		+"&ownerage="+cs.getOwenerage()+"&opertime="+cs.getOpertime()
		+"&vital="+cs.getVital();
		URL curl = new URL(url);
		HttpURLConnection con = 
		(HttpURLConnection)curl.openConnection();
		try {
			con.getInputStream();
			con.setReadTimeout(5000);
			con.setRequestMethod("POST");
			
		}catch(Exception e){
			
		}finally {
			con.disconnect();
		}
	}

	public static void main(String[] args) {
		App app = new App();
		app.getData();
	}

}
```



### 3. 빅데이터 저장 시스템 구축

1. Maven에 log4j Dependency 설정
   **pom.xml**(일부)

   ```xml
   		<!-- Logging -->
   		<dependency>
   			<groupId>org.slf4j</groupId>
   			<artifactId>slf4j-api</artifactId>
   			<version>${org.slf4j-version}</version>
   		</dependency>
   		<dependency>
   			<groupId>org.slf4j</groupId>
   			<artifactId>jcl-over-slf4j</artifactId>
   			<version>${org.slf4j-version}</version>
   			<scope>runtime</scope>
   		</dependency>
   		<dependency>
   			<groupId>org.slf4j</groupId>
   			<artifactId>slf4j-log4j12</artifactId>
   			<version>${org.slf4j-version}</version>
   		</dependency>
   		<dependency>
   			<groupId>log4j</groupId>
   			<artifactId>log4j</artifactId>
   			<version>1.2.17</version>
   		</dependency>
   
   ```

   

2. WEB-INF 내 config 폴더에 **log4j.properties** 생성

   ```xml
   ###############################################################################
   #
   #	log4j 
   #
   ###############################################################################
   
   log4j.logger.car = DEBUG, console, car
   
   # Console output... 
   log4j.appender.console= org.apache.log4j.ConsoleAppender 
   log4j.appender.console.layout = org.apache.log4j.PatternLayout 
   log4j.appender.console.layout.ConversionPattern = [%d] %-5p %L %m%n  
   
   # car
   log4j.appender.car.Threadhold=DEBUG
   log4j.appender.car = org.apache.log4j.DailyRollingFileAppender 
   log4j.appender.car.DatePattern = '.'yyyy-MM-dd
   log4j.appender.car.layout = org.apache.log4j.PatternLayout 
   log4j.appender.car.layout.ConversionPattern = %d{yyyy-MM-dd-HHmmss} - %m%n
   log4j.appender.car.File = /Users/jaeuk/Desktop/Dev/Hadoop/vehi2/logs/car.log 
   ```

   

3. **Java Resources/src/com.log/Loggers.java** 생성

   ```java
   package com.log;
   
   import org.apache.log4j.Logger;
   import org.aspectj.lang.JoinPoint;
   import org.aspectj.lang.annotation.Aspect;
   import org.aspectj.lang.annotation.Before;
   import org.springframework.stereotype.Service;
   import com.vo.CarStatusVO;
   
   @Service
   @Aspect
   public class Loggers {
   
   	private Logger car_log =   Logger.getLogger("car"); 
   
   
   	@Before("execution(* com.biz.CarBiz.*(..))")
   	public void loggingVital(JoinPoint jp) {
   		Object[] args = jp.getArgs();
   		CarStatusVO cstatus = (CarStatusVO)args[0];
   		car_log.debug(jp.getSignature().getName()+","
   				+cstatus.getId()+","+cstatus.getOwnergender()+","
   				+cstatus.getOwenerage()+","+cstatus.getVital()+","
   				+cstatus.getOpertime()+","+cstatus.getVital());
   	}
   }
   ```

4. 데이터 수신 Controller 생성, 데이터 전송 실행 & 로그 확인
   **Java Resources/src/com.controller/CarController.java**

   ```java
   package com.controller;
   
   import java.io.PrintWriter;
   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.PreparedStatement;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.util.ArrayList;
   
   import javax.servlet.http.HttpServletResponse;
   
   import org.json.simple.JSONArray;
   import org.json.simple.JSONObject;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.ResponseBody;
   
   import com.frame.Car;
   import com.vo.CarStatusVO;
   
   @Controller
   public class CarController {
   	String url = "jdbc:hive2://192.168.56.101:10000/default";
   	String userid = "root";
   	String password = "111111";
   	
   	public CarController() {
   		try {
   			Class.forName("org.apache.hive.jdbc.HiveDriver");
   		} catch (ClassNotFoundException e) {
   			e.printStackTrace();
   		}
   	}
   
   	@Autowired
   	Car<CarStatusVO> car;
   	 
   	@ResponseBody
   	@RequestMapping("/carstatus.mc")
   	public void carstatus(CarStatusVO cstatus) {
   		car.status(cstatus);
     }
   ```


   이후 **vehicleApp 실행**


   **생성된 로그 확인**

   ![스크린샷 2020-10-01 오후 7.47.20](md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%207.47.20.png)

5. Hive에 데이터 테이블 작성

   ```sql
   CREATE TABLE caroper(
   	date STRING,
     fn STRING,
     id STRING,
     ownergender STRING,
     ownerrage INT,
     opertime INT,
     vital INT
   )
   PARTITIONED BY (logdate STRING)
   ROW FORMAT DELIMITED
   	FIELDS TERMINATED BY ','
   	LINES TERMINATED BY '\n'
   	STORED AS TEXTFILE;
   ```

   

6. Hive에 Log파일 업로드

   **vehi2.sh 생성(load를 수행해줄 스크립트 파일)**

   ```bash
   #!/bin/sh
   
   date=`date`
   echo $date
   partitionName="${date:0:4}-${date:6:2}-${date:10:2}"
   echo $partitionName
   fileName="car.log.$partitionName"
   echo $fileName
   echo "Load the Data ?"
   read yn
   if [ $yn == "y" ]
   then
   echo "Start Load the Data ..."
   if [ -f /root/logs/$fileName ]
   then
   hive << EOF
   LOAD DATA LOCAL INPATH '/root/logs/$fileName' OVERWRITE INTO TABLE caroper  PARTITION (logdate="$partitionName");
   EOF
   echo "OK"
   echo "OK"
   else
   echo "File Not Found"
   "vehi2.sh" 28L, 510C
   
   ```

   **vehi2.sh 실행**

   ```bash
   $vehi2.sh
   ```

   

### 4. 빅데이터 분석 시스템 구축

1. 저장된 Hive Data Parsing하여 호출하는 Controller 셋팅

   **Java Resources/src/com.controller/CarController.java**

   ```java
   //코드 중 일부 발췌
   
   @ResponseBody
   	@RequestMapping("/getAlert.mc")
   	public void getAlert(HttpServletResponse res) throws Exception {
   		
   		Connection con = null;
   		JSONArray ja = new JSONArray();
   		
   		try {
   			con = DriverManager.getConnection(url, userid, password);
   			PreparedStatement pstmt = con.prepareStatement(
   					"SELECT * FROM caroper"
   					);
   			ResultSet rset = pstmt.executeQuery();
   			
   			//[{id:'user03', ownergender='M', ownerage=20 ,opertime=40, vital=60},{...}]
   			while(rset.next()) {
   				JSONObject data = new JSONObject();
   				System.out.println(rset.getString(2)+" "+rset.getString(3)+" "+rset.getInt(5)+" "+rset.getInt(6)+" "+rset.getInt(7));
   				data.put("id",rset.getString(2));
   				data.put("ownergender",rset.getString(3));
   				data.put("ownerage",rset.getInt(5));
   				data.put("opertime",rset.getInt(6));
   				data.put("vital",rset.getInt(7));
   				ja.add(data);
   			}
   		} catch (SQLException e) {
   			e.printStackTrace();
   		}finally {
   			con.close();
   		}
   		res.setCharacterEncoding("EUC-KR");
   		res.setContentType("application/json");
   		PrintWriter out=res.getWriter();
   		out.print(ja.toJSONString());
   		out.close();
   	}
   }
   ```

   

2. Hadoop & Hive 서버 실행

   ```bash
   # Hadoop 실행
   $ start-all.sh
   
   # Hive 실행
   $ hive --service hiveserver2
   ```

   

### 5. 통합 시스템 구축

1. 심박수 정상범위(90) 아래 -> 졸음 운전 경보 알람 메시지 생성(AJAX)

   **Web/view/main.jsp**(일부)

   ```javascript
   function displayAlert(data){
   	var alertString ='';
   	for(var i in data){
   		console.log(data[i]);
   		console.log('vi: '+data[i].vital);
   		 if(data[i].vital <90){ 
   			alertString+='<h5>'+data[i].id+' 차량의 심박수 '+data[i].vital+'로 위험 알람 발송합니다. </h5>';
   		}
   	}
   	$("#alert").append(alertString);
   	
   }
   
   function getAlert(){
   	$.ajax({
   		url:'getAlert.mc',
   		success:function(data){
   			alert(data);
   			displayAlert(data);
   		},
   		error:function(request,status,error){
   			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
   		}
   	});
   };
   ```



### 6. 결과

![스크린샷 2020-10-01 오후 7.59.18](md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-10-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%207.59.18.png)



### 7. 추가하면 좋을 내용

> * 운행 시간, 운전자 정보(성별, 나이)를 활용한 차트 생성
> * Linux 설정(가상환경 셋팅부터 리눅스, 소프트웨어 설치 등)
> * Bootstrap을 활용한 프론트엔드 보완

