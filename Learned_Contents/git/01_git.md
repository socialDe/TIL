# Git

>  Git은 분산형 버전관리 시스템(DVCS)중 하나이다.

## 1. Git 사전 준비

> git을 사용하기 전에 커밋을 남기는 사람에 대한 정보를 설정(최초)

```bash
$ git config --global user.name 'jaeuk9407'
$git config --global user.email 'jaeuk9407@gmail.com'
```

* 추후에 commit을 하면, 작성한 사람(author)로 저장된다.
* Email 정보는 github에 등록된 이메일로 설정을 하는 것을 추천
* 설정 내용을 확인하기 위해서는 아래의 명령어를 입력한다.

```bash
$git config --global -l
user.name=jaeuk9407
user.email=jaeuk9407@gmail.com
```

> Git bash 설치 [링크](#https://gitforwindows.org/ )

## 2. 기초 흐름

> 작업 -> add -> commit

### 2. 0. 저장소 설정

```bash
$git init
/Users/jaeuk/Desktop/test2/.git/ 안의 빈 깃 저장소를 다시 초기화했습니다
```

* Git 저장소를 만들게 되면 해당 디렉토리 네에 .git/폴더가 생성
* git bash애서는 (master)로 작업중인 브랜치가 표기된다.

### 2. 1. add

> 커밋을 위한 파일 목록(staging area)

```bash
$git add.				# 현재 디렉토리의 모든 파일 및 폴더
$git add a.txt #특정 파일
$git add md-images/ #특정 폴더
$git status
#master 브랜치에 있다.
현재 브랜치 master
아직 커밋이 없습니다
커밋할 변경 사항:
#unstage를 하기 위해서...명령어
#working directory 단계로 staging area에 있는 파일을 끌어내린다.
  (스테이지 해제하려면 "git rm --cached <파일>..."을 사용하십시오)
	새 파일:       a.txt
```

![img]($md-images/RqQG78Jp6PtX2nlg8WnY4I7xrqR5qPKEppVwOz8vHH5GiRqHMQzurMobDaCDGPYh2G2i1kEvCQmhvaojO_kQn2vRpxBCP2s7DfuJV7HF0936A40cTwQinPc5BwJ-_IhHTnXOOBCb.png)



### 2. 2. commit

> 버전을 기록(스냅샷)

```bash
$git commit -m '커밋메시지'
```

* 커밋 메시지는 현재 버전을 알 수 있도록 명확하게 작성한다
* 커밋 이력을 남기고 확인하기 위해서는 아래의 명령어를 입력한다.

```bash
$git log
$git log -1 # 최든 한개의 버전
$git log --oneline #한 줄로 간단하게 표현
$git log -1 --oneline
```



### 2. 3. status

> git에 대한 모든 정보는 status에서 확인할 수 있다.

```bash
$git status
```

* working directory
  * untracked - 깃이 관리하지 않고 있는 파일
    * 파일 생성(new file) 등
  * tracked - 이전 커밋에 포함된 적 있는 파일
    * modified - modified / deleted
    * unmodified - 수정 X (status에 안 뜸

## 3. 원격 저장소 활용하기

> 원격 저장소를 제공하는 서비스는 github, gitlab, bitbucket등이 있다.

### 3. 1. 원격 저장소 설정하기

```bash
$git remote add origin {URL}
```

* 깃아,원격(remote) 저장소로 추가해줘(add) origin이라는 이름으로 URL을
* 원격 저장소 삭제하기 위해서는 아래의 명령어를 사용한다.

```bash
$git remote rm origin 
```

### 3. 2. 원격 저장소 확인하기

```bash
$git remote -v
```

### 3. 3. push

```bash
$git push origin master
```

* origin 원격저장소의 master 브랜치로 push