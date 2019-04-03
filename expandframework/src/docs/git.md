### 1. 如何删除一个commit到本地的分支

#### 1.1 使用git reset

> git log

首先查看提交日志，找到commitId，

> git reset --hard <commitId>

如果是本地分支到这里，分支就已经改变了，后提交的分支就都没有了。如果是远程分支，还需要执行命令行

> git push origin HEAD --force

#### 1.2 使用git rebase