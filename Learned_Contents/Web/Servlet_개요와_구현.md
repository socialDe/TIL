# Servlet 개요와 구현

## 1. Servlet 개요

* 서버에서 웹페이지 등을 동적으로 생성하거나 데이터 처리를 수행하기 위해 자바로 작성된 프로그램

### Java Servlet

* Java EE(Java Platform Enterprise Edition)의 한 기능으로, 서버측 기능을 확장시킨 Java 프로그램

<br>

<br>



## 2. Servlet 구현

* Servlet 클래스를 구현할 때는 구현하려는 기능과 관계없이 HttpServleet 클래스를 상속해야 함.

<br>

Servlet의 상속 구조

<img width="626" alt="스크린샷 2021-03-04 오후 9 32 41" src="https://user-images.githubusercontent.com/46706670/109974592-11338c00-7d3d-11eb-93bf-91dc205049d3.png">



Servlet 클래스는 javax.servlet.Servlet, javax.servlet.GenericServlet, javax.servlet.http.HttpServelt 중 하나를 상속해야 하며, **웹 서버에서 수행되는 Servlet의 경우 주로 HttpServlet을 상속하여 구현**함

main() 메서드는 구현하지 않고, 아래와 같은 HttpServletz클래스의 메서드들을 기능에 따라 **<u>선택적으로 오버라이딩</u>**하여 구현



```java
@Override
	public void init(ServletConfig config) throws ServletException {
		// Servlet 클래스의 객체가 생성된 후에 호출되는 메서드 
	}
	
	@Override
	public void destroy() {
		// Servlet 객체가 메모리에서 해제될 때 호출되는 메서드 
	}
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// 요청 방식에 관계없이 브라우저로부터 요청이 전달되면 호출되는 메서드
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 브라우저로부터 GET 방식으로 요청이 전달되면 호출되는 메서드
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 브라우저로부터 POST 방식으로 요청이 전달되면 호출되는 메서드
	}
```



### 2. 1. Servlet 생성

<img width="599" alt="스크린샷 2021-03-04 오후 9 43 50" src="https://user-images.githubusercontent.com/46706670/109974603-142e7c80-7d3d-11eb-9c32-5f95b2f8ab1e.png">



생성할 Class Name을 작성하고 Next 클릭



<img width="595" alt="스크린샷 2021-03-04 오후 9 44 09" src="https://user-images.githubusercontent.com/46706670/109974662-21e40200-7d3d-11eb-8f64-fe2a36cd9c4c.png">



URL mappings를 직접 추가해주거나, 수정할 수 있다. 이렇게 작성한 Servlet은 매핑된 URL로 요청이 오면 POST, GET 요청에 따라 Servlet에 작성한 코드를 작동시킨다. 

<br>

> http://서버주소:포트번호/contextPath명/Servlet클래스의URL mapping명

<br>

생성된 Servlet 소스 코드

<img width="746" alt="스크린샷 2021-03-04 오후 9 49 22" src="https://user-images.githubusercontent.com/46706670/109974827-4e981980-7d3d-11eb-81aa-edb67015a3b8.png">

생성된 Servlet은 이와 같이 `@WebServlet` Annotation으로 URL mapping이 되어 생성된다. 직접 코드로 URL mapping을 추가하거나 삭제할 수도 있다.



### 2. 2 Servlet의 등록과 매핑

대부분의 웹 자원들 즉, HTML 파일, 이미지 파일, 동영상 파일, CSS 파일, 자바스크립트 파일 등은 **파일의 확장자로 파일의 종류를 구분**하는데, **Servlet의 경우에는 불가능**함. **Servlet 소스는 Java 프로그램으로 구현**되기 때문에 컴파일을 통해 `.class`라는 확장자를 갖는 수행 파일이 되는데 웹에서 .class라는 수행 파일을 갖는 파일의 요청은 **Servlet보다 이전 기술인 Applet에서 이미 사용**되고 있음

그래서 Servlet 클래스 파일은 서버에서 Servlet 프로그램으로 인식되어 처리되도록 등록과 매핑이라는 설정을 `web.xml`이라는 디스크립터 파일에 작성해야 함. web.xm은 웹 애플리케이션에 대한 다양한 정보를 설정하는 파일로 WEB-INF 폴더에 만들어야 함. **생성되는 Servlet마다 등록하는 기능의 <servlet> 태그와 매핑하는 기능의 <servlet-mapping> 태그를 정의**해야 함.

<u>그러나 Servlet 3.0부터 위와 같이 web.xml이라는 디스크립터 파일에 Servlet을 등록하고 매핑하는 태그를 작성하는 대신 Servlet 소스 안에 Java의 Annotation 구문으로 선언하는 방법이 추가됌.</u>



### 2. 3 Servlet의 수행

* Java EE 기술에서 `Servlet`과 `JSP`는 웹 컨테이너가 관리하고 수행하는 Web 컴포넌트로, 다양한 Web 컴포넌트와 정적 리소스들 그리고 추가 라이브러리들이 모여 하나의 웹 애플리케이션을 구성함.

* 용어
  * Eclipse - Dynamic Web Project 폴더
  * Tomcat - 컨텍스트
  * 개발자 - 웹 애플리케이션

<br>

#### 2. 3. 1 첫 번째 호출과 두 번째 호출

Servlet 수행 흐름

<img width="1315" alt="스크린샷 2021-03-04 오후 10 25 32" src="https://user-images.githubusercontent.com/46706670/109975050-8c953d80-7d3d-11eb-865f-4cf80ed81d44.png">





* 웹 컨테이너는 클라이언트에서 전송된 요청 정보를 가지고 `HttpServletRequset`, `HttpServletResponse`객체를 생성하고, 요청된 `Servlet 객체`가 생성된 상태인지 확인
* `Servlet 객체`가 생성되어 있는 상태라면 바로 수행 명령
* 생성된 상태가 아니라면 클래스 파일을 찾아서 로딩한 후 객체를 생성하여 수행 명령
* 생성된 Servlet 객체는 서버를 종료할 때까지 or 웹 애플리케이션이 리로드될 때까지 객체 상태를 계속 유지

<br>

#### 2. 3. 2 여러 브라우저가 하나의 Servlet을 동시에 요청할 때

* 웹 컨테이너가 Servlet 수행을 처리할 때는 다중 스레드 방식을 사용
* 각 요청마다 스레드를 구동시켜 병렬로 처리
* 각 요청마다 Servlet 클래스의 객체를 생성하여 수행하는 것이 아닌 하나의 Servlet 객체를 공유하여 여러 스레드가 병렬로 처리하는 방식
* 결과적으로 여러 개의 요청이 동시에 전달되더라도 적은 메모리 사용으로 수행 성능을 높일 수 있음

<br>

#### 2. 3. 1과 2. 3. 2 검증

##### 2. 3. 1 수행 흐름

FlowServlet

```java
@WebServlet("/flow")
public class FlowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init() method 호출");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("destroy() method 호출");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("service() method 호출 abc");
	}
}
```

<br>

처음 브라우저에서 해당 웹을 접근하면 "init메서드 호출"이 출력, 새로고침으로 해당 웹에서 여러번 요청하면 "service 메서드 호출"이 출력됌.

또, 코드를 약간 수정해서 자동 컴파일로 reload되는 경우 "destroy 메서드 호출"과 "init 메서드 호출"을 다시 출력함

<br>

##### 2. 3. 2 멤버 변수와 지역 변수

MemberLocalServlet

```java
@WebServlet("/memberLocal")
public class MemberLocalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int member_v = 0;	// 멤버 변수 -> heap

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// write code
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				int local_v = 0;	// 지역변수 -> Stack
				
				member_v++;
				local_v++;
				
				out.println("<h2>member_v : "+ member_v + "<h2>");
				out.println("<h2>local_v : "+ local_v + "<h2>");
				out.close();
	}
}

```

<br>

새로고침으로 해당 웹에서 여러번 요청하면 member_v는 1로 고정되어 있고, local_v 는 요청시마다 증가함.



### 2. 4 Servlet의 요청과 응답

#### 2.4.1 HttpServletRequest

* Servlet에서 요청 헤더에 포함된 요청 정보를 추출하려는 경우 사용
* ex. 요청을 보내온 클라이언트에 대한 정보, 요청 정보

####  2.4.2 HttpServletResponse

* 응답하려는 콘텐츠를 담고 전송하기 위해 사용
* ex) HTML, XML, JSON, 이미지...

<br>

[HTTP 요청/응답 method 참고](http://www.devkuma.com/books/pages/1190)



