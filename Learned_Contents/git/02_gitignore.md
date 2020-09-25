## gitignore

> git으로 추적하지 않을 파일 또는 폴더를 정의

git으로 관리되고 있는 로컬 저장소에 `.gitignore`파일을 만든다.

```
*.xlsx				#특정 확장자 제외
secret.csv		#특정 파일
tests/				#특정 폴더
!tests/setting.txt				#! not
```

* 일반적으로 운영체제와 관련된 파일, 개발환경(IDE, text editor), 특정 프레임워크 혹은 언어에서 메인 소스코드가 아닌 경우

  * https://gitignore.io 에서 참고할 수도 있다.
  * Ex) `mac` `java-web` `eclipse` 

  

## 2020.09.25 추가

* 이미 추적 중인 파일 중 .gitignore를 반영하고 싶을 때

```bash
# cache 삭제
$ git rm -r --cached .
# 다시 추가 -> gitignore를 다시 반영하게 됌
$ git add .

$ git commit -m "Apply .gitignore"
$ git push
```

