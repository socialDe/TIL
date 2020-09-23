## Hadoop 완전 분산 시스템 구축

완전 분산 시스템 구축의 경우 가상 분산 시스템 구축의 경우와 SSH 설정 단계부터 차이가 있다. 따라서 본 정리 문서는 이전 단계를 생략하고 SSH 설정부터 다룬다.

### 목차

> * 하둡 다운로드(생략)
>
> * 호스트 파일 수정(생략)
>
> * 인코딩 방식 설정(생략)
>
> * 자바 설치(생략)
>
> 1. **SSH 설정**
> 2. **하둡 압축 해제**
> 3. **하둡 환경설정 수정**
> 4. **하둡 실행**
> 5. **가상 분산 시스템 전환**



![하둡_완전분산시스템](md-images/%ED%95%98%EB%91%A1_%EC%99%84%EC%A0%84%EB%B6%84%EC%82%B0%EC%8B%9C%EC%8A%A4%ED%85%9C.png)

먼저 VMware를 통해 3대의 Linux(RedHat CentOS 7)을 준비했으며, 각 이름은 mainserver, secondserver, dataserver이다. 아래 모든 과정은 secondserver와 dataserver를 가동시킨 상태를 유지하며, mainserver를 중심으로 진행한다.



### 1. SSH 설정

#### 1.1. SSH 설정

```bash
#공개키 생성
$ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa

#생성한 공개키로 authorized_key 파일 생성
$cat id_dsa.pub >> authorized_keys

#다른 컴퓨터로 authorized_key 파일 전송(공개키)
$ssh-copy-id -i /root/.ssh/id_dsa.pub root@secondserver
$ssh-copy-id -i /root/.ssh/id_dsa.pub root@dataserver
```

먼저 공개키를 생성한 뒤, 다른 서버(데이터 노드)로 공개키를 복사한다. 본 실습 환경에서는 데이터 노드가 secondserver와 dataserver이므로 두 개 서버에 모두 전송한다. 전송할 때 커넥션을 계속 유지할 것인지를 묻는데, 모두 yes를 입력해준다.

```bash
#secondserver 데이터노드로 SSH 접속 시도
$ssh secondserver
$exit
#dataserver 데이터노드로 SSH 접속 시도
$ssh dataserver
$exit
```

공개키를 모두 전송했다면 각 데이터노드로 SSH 접속을 시도해본다. 정상적으로 전송되고 연결이 된다면 암호를 묻지 않고 접속된다. 접속을 해제할 때는 exit;을 입력한다.

#### 1.2.Namenode에서 Datanode 데이터 전송 및 조작

```bash
#데이터(파일) 전송하기
$scp /usr/bin/java root@secondserver:/usr/bin/java

#secondserver 조작 command(jdk압축 해제)
$ssh root@secondserver tar xvf /root/jdk*
```

네임노드로 접속해 다른 데이터노드에 파일을 전송하거나 원격 조작이 가능하다. 본 실습에서는 데이터노드에 jdk, hadoop 설치 폴더를 전송하고 setup 시켰다.(커맨드는 중복되므로 상세 코드 생략)

 

### 2.하둡 압축 해제

```bash
#하둡 다운로드(링크에서 바로 다운로드)
$wget https://archive.apache.org/dist/hadoop/common/hadoop-1.2.1/hadoop-1.2.1.tar.gz


$tar cvfz hadoop.tar.gz ./hadoop-1.2.1/

```



### 3. 하둡 환경설정 수정

```bash
$vi core-site.xml
# core-site.xml 입력 start
<configuration>
<property>
<name>fs.default.name</name><value>hdfs://mainserver:9000</value>
</property>
<property>
<name>hadoop.tmp.dir</name>
<value>/usr/local/hadoop-1.2.1/tmp</value>
</property>
</configuration>
# core-site.xml 입력 end

$vi mapred-site.xml
# mapred-site.xml 입력 start
<configuration>
<property>
<name>mapred.job.tracker</name>
<value>localhost:9001</value>
</property>
</configuration>
# mapred-site.xml 입력 end

$vi masters
#secondserver만 작성하고 저장
$vi slaves
#secondserver 엔터, dataserver 작성하고 저장

$vi hdfs-site.xml
# hdfs-site.xml 입력 start
<configuration>
<property>
<name>dfs.replication</name>
<value>2</value>  #hdfs에 저장될 데이터 복제 개수 설정
</property>
<property>
<name>dfs.webhdfs.enabled</name>
<value>true</value>
</property>
<property>
<name>dfs.name.dir</name>
<value>/usr/local/hadoop-1.2.1/name</value>
</property>
<property>
<name>dfs.data.dir</name>
<value>/usr/local/hadoop-1.2.1/data</value>
</property>
</configuration>
# hdfs-site.xml 입력 end

$vi hadoop.env.sh
# line 9, 10에 내용 추가
# line 9: export JAVA_HOME=/usr/local/jdk1.8.0
# line 10: export HADOOP_HOME_WARN_SUPPRESS="TRUE"
```

완전 분산 시스템으로 하둡을 설정할 때, 달라지는 부분은 3가지이다. 

> 1) core-site.xml, mapred-site.xml의 localhost부분을 mainserver라는 hostname으로 변경해주는 것 
>
> 2) masters, slaves에 해당하는 hostname을 적어주는 것
>
> 3) hdfs-site.xml에  설정하는 데이터 복사본 수 



### 4. 하둡 실행

```bash
#하둡 초기화
$hadoop namenode -format

#하둡 실행
$start-all.sh

#확인
$jps

#하둡 정지
$stop-all.sh
```



#### * 하둡 실행 오류시

```bash
#하둡이 실행중이라면, 정지
$stop-all.sh

#/usr/local/hadoop 폴더 이동하여 초기화 시 생성된 폴더 삭제
$rm -rf name, data, tmp

#truble shooting : conf 내 setup, hostname, /etc/hosts, /etc/profile 등 확인하여 문제 해결

#다시 하둡 초기화
$hadoop namenode -format
```



### 5.가상 분산 시스템 전환

```bash
#하둡이 실행중이라면, 정지
$stop-all.sh

#/usr/local/하둡설치폴더 이동하여 초기화 시 생성된 폴더 삭제
$rm -rf name, data, tmp

$vi /etc/hosts
# mainserver를 제외하고 모든 hosts 삭제

#/usr/local/하둡설치폴더/conf 이동하여 masters, slave 수정
$vi masters
$vi slaves
# 두 문서 모두 localhost만 남겨놓고 내용 삭제

#다시 하둡 초기화
$hadoop namenode -format
```



만약 데이터노드로 사용하던 secondserver나 dataserver에서 단독으로 가상분산시스템으로 전환한다면, SSH 설정을 부가적으로 해주어야 한다. 이는 데이터노드로 사용하던 server들은 nameserver에서 공개키를 제공받아 사용했기 때문이다. 만약 이 부분을 생략할 경우 하둡을 실행시키거나 정지시킬 때마다 암호를 3번씩 입력해야 한다.