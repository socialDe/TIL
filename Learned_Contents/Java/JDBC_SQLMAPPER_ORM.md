# JDBC, SQLMAPPER, ORM

<br>

세 기술의 공통점은 바로 Persistence이다.

> Persistence(영속성): 데이터를 생성한 프로그램의 실행이 종료되더라도 사라지지 않는 데이터의 특성, 영구히 저장되는 특성

애플리케이션을 실행할 때 프로세스로 휘발성 메모리인 LAM 위에 올려 실행하게 된다. 그런데 만약 프로세스가 예기치 못하게 종료되더라도, 프로세스 실행 중에 들어온 데이터 중 어떤 데이터(유저 데이터 등)는 영구히 보존해야 할 필요성이 있다. 때문에 우리는 영속성 특징을 갖는 DB에 데이터를 저장하고자 하는데, JDBC, SQLMAPPER, ORM 모두 여기에 관련 있다. 

<br>

<img width="308" alt="스크린샷 2021-05-06 오후 3 42 05" src="https://user-images.githubusercontent.com/46706670/117297975-cf949e00-aeb1-11eb-9e5d-75c12fd5185b.png">

일반적으로 위 사진처럼 Layered Architecture를 통해 DB 접근이 일어나게 되는데, JDBC와 SQLMapper, ORM 모두 Domain Model 단계에서 들어있는 정보를 DB와 연동하기 위해 필요한 과정들, 즉 Persistence 단계와 관련되어 있다.

<br><br><br>

<img width="1282" alt="스크린샷 2021-05-06 오후 9 05 18" src="https://user-images.githubusercontent.com/46706670/117298499-64979700-aeb2-11eb-8b91-c4d753f6f048.png">

현재 주로 이용하는 기술 중 JDBC, SQLMapper, ORM에 해당하는 각 기술들은 위와 같다.

<br><br><br>



## 기술들의 역사

<br>

### JDBC API

1990년대 중반 아래와 같은 흐름 속에 1997년 JDBC API가 등장한다.

- 인터넷 보급, DB 산업 성장
- 온라인 비즈니스의 투자 증가
- DB Connector에 대한 니즈
- Java 진영의 DB연결 표준 인터페이스

<br><br>

![jdbc-architecture-e1530594694439](https://user-images.githubusercontent.com/46706670/117298510-67928780-aeb2-11eb-8341-a72f82e763f9.jpg)

JDBC는 여러 DB 제품군에 따른 Driver를 만들어 우리가 사용하는 API를 변경하지 않고도 DriverManager만 바꾸면 어떤 DB든 사용할 수 있도록 도와주는 기술이다.

<br>

<img width="1261" alt="스크린샷 2021-05-06 오후 3 57 46" src="https://user-images.githubusercontent.com/46706670/117297980-d1f6f800-aeb1-11eb-8a9e-b124ef69c9cb.png">

1. DriverManager를 이용해서 Connection 인스턴스를 얻는다.
2. Connection을 통해서 Statement를 얻는다.
3. Statement를 이용해 ResultSet을 얻는다.

<br>

그러나 JDBC를 이용하며 불편사항이 다시 등장한다.

* 중복 코드가 많다.
* Connection 관리를 계속 해줘야 한다.

이와 같은 불편에 의해 SQLMAPPER가 등장한다.

<br><br>

### SQLMAPPER

<br>

#### Spring JDBC

<img width="1288" alt="스크린샷 2021-05-06 오후 4 06 59" src="https://user-images.githubusercontent.com/46706670/117297985-d3282500-aeb1-11eb-8735-b20783d48076.png">



```java
public class CrewDAO{
  private JdbcTemplate jdbcTemplate;
  
  @Autowired
  public void setDataSource(DataSource dataSource){
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }
  
  public List<Crew> getCrews(){
    return jdbcTemplate.query("select * from crews", new RowMapper<Crew>(){
      @Override
      public Crew mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Crew(rs.getInt("id"), rs.getString("name"));
      }
    });
  }
}
```

JDBC API만을 사용할 때보다, Connection에 대한 Configuration을 JdbcTemplate이라는 클래스에 담아 Spring을 통해 주입받는다. 또, 메소드에 쿼리를 매핑해두고 RowMapper를 통해 쿼리 메소드의 결과를 통해 DB의 각 row마다 하나의 인스턴스화를 지원해준다. 추상화가 많이 이뤄져 이전보다 편하게 사용하고 있는 모습을 볼 수 있다.

<br><br>

|                  동작                  | Spring | 애플리케이션 개발자 |
| :------------------------------------: | :----: | :-----------------: |
|           연결 파라미터 정의           |        |          O          |
|               연결 오픈                |   O    |                     |
|               SQL문 지정               |        |          O          |
|    파라미터 선언과 파라미터 값 제공    |        |          O          |
|         statement 준비와 실행          |   O    |                     |
| (존재한다면) 결과를 반복하는 루프 설정 |   O    |                     |
|     각 iteration에 대한 작업 수행      |        |          O          |
|             모든 예외 처리             |   O    |                     |
|             트랜잭션 제어              |   O    |                     |
|    연결, statement, resultSet 닫기     |   O    |                     |

개발자는 어떤 DB를 사용할 것인지에 대해 연결 파라미터를 넣어주면 Spring이 알아서 연결을 열어준다. 또, 개발자가 원하는 쿼리를 설정하고 해당 쿼리에 들어가는 파라미터를 넣어주면 Spring이 해당 statement를 알아서 수행해주며, 결과 resultset에 대한 루프, 예외처리, 트랜잭션, 종료까지 많은 부분을 대신해주며 기존 방식에서의 불편을 덜어줬다.

<br><br>

#### Mybatis

Mybatis는 기존 JDBC의 문제점을 Spring JDBC와 약간 다르게 해석했다. Java 코드에서 SQL을 쓰는 것 자체를 분리하는 것을 목표로 잡았다. Query를 Java에서 XML로 이동하는 것이다.

- 복잡한 JDBC 코드 X
- ResultSet과 같이 결과값을 매핑하는 객체 X
- 간단한 설정!
- 관심사 분리!

![DataAccessMyBatis3RelationshipOfComponents](https://user-images.githubusercontent.com/46706670/117298505-66615a80-aeb2-11eb-941e-167e459d03d0.png)

<br>

```java
public interface CrewDAO{
  public List<Crew> selectAll();	// 모든 행 가져오기
  public Crew selectById(int id);	// 특정한 행 가져오기
  public int insert(Crew crew);	// 삽입
  public void update(Crew crew);	// 업데이트
  public void delete(int id);	// 삭제
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org/DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.dao.CrewDao">
	<resultMap id="result" type="Crew">
    <result property="id" column="id"/>
    <result property="name" column="name"/>
  </resultMap>
  
  <select id="selectAll" resultType="result">
		SELECT * FROM crews;
  </select>
  
  <select id="selectById" parameterType="int" resultType="result">
		SELECT * FROM crews WHERE id = #{id};
  </select>
  
  <insert id="insert" parameterType="Crew" useGeneratorKeys="true" keyProperty="list_id">
    INSERT INTO crews(name) VALUES(#{name});
  </insert>
  
  <update id="update" parameterType="Crew">
    UPDATE crews SET name = #{name} WHERE id = #{id};
  </update>
  
  <delete id="delete" parameterType="int">
    DELETE from crews WHERE id =#{id}
  </delete>
</mapper>

```

<br>

SQL statements를 xml에 따로 지정하고 쿼리를 메소드와 매핑시킴으로써 Java 코드와 SQL 구문들을 완전히 분리한다. DAO는 interface로 Spring JDBC보다 훨씬 간결해졌다. 실제 사용 코드는 아래와 같다.

```java
public class Main{
  public static void main(String[] args){
    // Mybatis 세션 연결
    SqlSession sqlSession = MtBatisconnectionFactory.getSqlSessionFactory()
      .openSession(true);
    
    // Mapper 연결
    CrewDao crewDao = sqlSession.getMapper(CrewDAO.class);
    
    // Select
    List<Crew> crews = crewDao.selectAll();
    for(int i = 0; i < crews.size(); i++){
      System.out.println("크루 이름: "+crews.get(i).getName());
    }
    // Insert
    Crew kouz = new Crew();
    kouz.setName("코즈");
    crewDao.insert(kouz);
    
    // Update
    kouz.setName("코우우우즈");
    crewDao.update(kouz);
    
    // Delete
    crewDao.delete(kouz.getId());
  }
}
```

실제 사용하는 코드를 보면 쿼리를 몰라도 DB 연결 작업을 수행할 수 있도록 코드 상에서 매우 가벼워진 모습을 보인다.

<br><br><br>

## ORM(Object-Relational Mapping)

<br>

#### JPA(Java Persistence API) / Hibernate

객체지향적으로 구현되어 있는 구조에서 관계형데이터베이스와 연결하는 것을 간편하게 하기 위해 등장한 기술이다.

```java
public class Crew{
  private int id;
  private String name;
}
```

<br>

```java
public class Crew{
  private int id;
  private String name;
  private String nickName;
}
```

만일 위와 같은 코드에서 아래 코드로 구조가 변경된다면, SQL구문도 모두 이에 맞춰 변경이 필요하다. Insert, Update....

<br><br>

```java
String sql = "INSERT INTO CREW(CREW_ID, NAME, NICKNAME) VALUES(?,?,?)";
...
  pstmt.setString(3, crew.getNickName());
...
  
...
  SELECT CREW_ID, NAME, NICKNAME FROM CREW WHERE CREW_ID = ?
...
```

이런 문제는 SQLMAPPER를 사용한다고 해결되지 않고, 여전히 변경 작업을 수행해줘야 한다.

<br>

```java
public class Crew{
  private int id;
  private String name;
  private String nickName;
  private Team team;
}
```

그런데 만약, 다시 이와 같은 구조로 변경 이슈가 또 한 번 발생한다면 어떨까? 객체를 갖는 멤버변수로 갖는 구조는 RDB상에서 그대로 표현되지 않고 team Id를 외래키로 지정하는 형태로 갖기 때문에 점점 작업해줄 것이 많아지고 복잡해진다.

<br>

> "물리적으로 SQL과 JDBC API를 데이터 접근 계층에 숨기는데 성공했을지는 몰라도, 논리적으로는 엔티티와 아주 강한 의존 관계를 가지고 있다." - 김영한님의 Spring

<br>

소프트웨어는 객체지향의 흐름을 타고 있고, DB는 관계형데이터베이스를 주로 사용하는데, 관계형데이터베이스에서는 객체지향에서의 개념인 연관관계(객체 참조), 상속을 그대로 표현하는 것이 어렵고 불편한 문제가 있었다. -> **패러다임의 불일치** -> **개발자들의 SQL 의존적인 구현 현상 발생**

<br>

이를 해결하기 위해  JPA가 등장한다.

<br><br>

<img width="1270" alt="스크린샷 2021-05-06 오후 5 19 31" src="https://user-images.githubusercontent.com/46706670/117297999-d58a7f00-aeb1-11eb-90e9-67c113e2001a.png">

<br>

JPA는 표준 인터페이스가 있고, 이를 구현하는 실제 서비스가 여러가지 존재한다. 그 중 하나가 Hibernate이고, 그 외 다양하게 존재한다. JPA도 JDBC API가 여러 Driver를 변경할 수 있던 것처럼 마찬가지로 추상화를 통해 손쉽게 기술 변경이 가능하다.

<br>

이들의 핵심 모델은 **<u>EntityManager</u>**와 <u>**영속성 컨텍스트**</u>이다.

<img width="1302" alt="스크린샷 2021-05-06 오후 5 44 38" src="https://user-images.githubusercontent.com/46706670/117298285-27330980-aeb2-11eb-9d85-56c7015701f8.png">

<br>

EntityManager는 말 그대로 Entity를 관리해주는 역할을 하는데, Hibernate, JPA 측에서는 Entity의 Context를 4개로 분리한다.(Detached, Managed, Removed, Transient) Entity는 가장 먼저 persist(entity)를 통해 PersistenceContext에 올라오고 해당 entity는 EntityManger에 의해 관리를 받는 상태가 된다. 그 상태에서 flush()가 발생하면 DB에 접근하며 그 시점에 SQL이 생성되어 동작된다. 즉 쿼리를 개발자가 직접 관리하지 않고 EntityManager에 맡기는 것이다. 

<br><br>

<img width="1267" alt="스크린샷 2021-05-06 오후 5 52 43" src="https://user-images.githubusercontent.com/46706670/117298288-28643680-aeb2-11eb-94d7-098fe988be20.png">

> (이 부분에서는 이해가 잘 가지않아, 추후 조금 더 공부해보고 내용을 보완하겠습니다.)

Lazy Loading: 사용 상황에 알맞게 Proxy 객체를 사용해 필요한 정보만 가져오기 위한 Concept

Dirty Checking: 해당 객체의 변경 사항만 관리하는 Concept

Caching: DB의 Connection을 최소화해 효율적으로 사용할 수 있도록 가능하면 Cache를 사용하는 Concept

<br><br>

#### Spring Data JPA

- Spring Data 진영에서 만든 JPA
- Core Concpet로 Repository를 제안

<br>

EntityManager가 복잡하기 때문에 Spring에서 한번 더 추상화를 해 개발자가 보다 편리하게 이를 이용할 수 있도록 제공한다.

<img width="994" alt="스크린샷 2021-05-06 오후 6 17 49" src="https://user-images.githubusercontent.com/46706670/117298292-2ac69080-aeb2-11eb-98dc-9e91da494314.png">

위 사진처럼 interface 형태로 Repository를 제공해 개발자는 이를 가져다 사용하는데, 이를 구현한 클래스 내부 구조를 살펴보면 아래와 같다.

<br><br>

```java
package org.springframework.data.jpa.repository.support;

import ...

public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID>{
  
  private final EntityManager em;
  
  public Optional<T> findById(Id id){
    
    Assert.notNull(id, ID_MUST_NOT_BE_NULL);
    
    Class<T> domainType = getDomainClass();
    
    if(metadata == null){
      return Optional.ofNullable(em.find(domainType, id));
    }
    
    LockModeType type = metadata.getLockModeType();
    Map<String, Object> hints = getQueryHints().withFetchGraphs(em).asMap();
    
    return Optional.ofNullable(type == null? em.find(domainType, id, hints) : em.find(domainType, id, type, hints));
  }
...
```

실제로 JpaRepository 클래스들을 들어가보면 EntityManager를 멤버변수로 갖고 있음을 볼 수 있다. 개발자는 추상화된 인터페이스를 가지고 보다 편리하게 EntityManager를 운용할 수 있다.

<br><br>



<img width="1169" alt="스크린샷 2021-05-06 오후 6 34 35" src="https://user-images.githubusercontent.com/46706670/117298300-2bf7bd80-aeb2-11eb-8bf8-bb43a50cd242.png">

개발자가 Spring Data JPA를 이용한다면 위와 같은 구조로 DB에 접근하는 것이다.

<br><br>

## Spring Data JDBC

* Core Concept: Simple

객체지향으로 설계된 프로그램에서 DB에 접근하다보니 너무 많은 기술들이 사용됐다. 이에 DDD 구조로 한꺼번에 Entity를 주고 받는 방식으로 DB에 접근하는 방법을 고안해 냈다.

>  "Spring Data JDBC a SImple, Limited Opinionated ORM" - Spring Data JDBC Officials

<img width="667" alt="스크린샷 2021-05-06 오후 6 45 49" src="https://user-images.githubusercontent.com/46706670/117298491-62cdd380-aeb2-11eb-80e3-577c061f2917.png">

Spring Data JDBC는 위 사진처럼 Hibernate와 같은 기술을 사용하지 않고 JDBC API를 그대로 직접 구현해서 사용하는 방식으로 동작된다. 물론 Spring Data 이므로 Repository 개념은 그대로 갖고 있다.

<br><br><br>

# 출처

* [코즈의 JDBC, SQLMAPPER, ORM - 우아한Tech](https://www.youtube.com/watch?v=mezbxKGu68Y&t=1s)