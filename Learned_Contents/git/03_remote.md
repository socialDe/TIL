# 원격 저장소 활용하기

### 충돌 상황

#### * 원격 저장소의 이력과 로컬 저장소의 이력이 다른 상황



```bash
$git push -u origin master
To https://github.com/jaeuk9407/remote.git
 ! [rejected]        master -> master (fetch first)
error: 레퍼런스를 'https://github.com/jaeuk9407/remote.git'에 푸시하는데 실패했습니다
힌트: 리모트에 로컬에 없는 사항이 들어 있으므로 업데이트가
힌트: 거부되었습니다. 이 상황은 보통 또 다른 저장소에서 같은
힌트: 저장소로 푸시할 때 발생합니다.  푸시하기 전에
힌트: ('git pull ...' 등 명령으로) 리모트 변경 사항을 먼저
힌트: 포함해야 합니다.
힌트: 자세한 정보는 'git push --help'의 "Note about fast-forwards' 부분을
힌트: 참고하십시오.
```

* 원격 저장소의 이력작업을 받아오고 다시 push해라

```bash
$git pull origin master
```

자동으로 생성되는 vim 코드를 저장하고 나와서 다시 push 시도.

```bash
$ git status
현재 브랜치 master
브랜치가 'origin/master'보다 2개 커밋만큼 앞에 있습니다.
  (로컬에 있는 커밋을 제출하려면 "git push"를 사용하십시오)

커밋할 사항 없음, 작업 폴더 깨끗함
$ git push origin master
오브젝트 나열하는 중: 7, 완료.
오브젝트 개수 세는 중: 100% (7/7), 완료.
Delta compression using up to 12 threads
오브젝트 압축하는 중: 100% (4/4), 완료.
오브젝트 쓰는 중: 100% (5/5), 570 bytes | 570.00 KiB/s, 완료.
Total 5 (delta 1), reused 0 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (1/1), done.
To https://github.com/jaeuk9407/remote.git
   70847a1..75ffaa8  master -> master
```



#### * 서로 다른 환경에서 Github를 이용하는 경우

> 1) Computer A에서 commit후 push
>
> 2) Computer B에서 pull 후 add-commit-push(push 전까지 pull을 수행하면 순서 변동가능)
>
> 3) 에러가 발생하고 이유를 모르겠다면 repository의 commit 목록과 로컬에서 commit 목록을 비교해본다.

