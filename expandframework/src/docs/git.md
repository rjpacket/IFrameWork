### 1. 如何删除一个commit到本地的分支

#### 1.1 使用git reset

> git log

首先查看提交日志，找到commitId，

> git reset --hard <commitId>

如果是本地分支到这里，分支就已经改变了，后提交的分支就都没有了。如果是远程分支，还需要执行命令行

> git push origin HEAD --force

#### 1.2 使用git rebase

git-rebase操作起来要比git-reset高级的多，例如如果你有三次提交commit A，commit B，commit C，只想删除B操作，如果使用reset的方式，C的提交记录也会消失。

使用git rebase步骤如下：

> git log

> git rebase -i <pre-commitId>

这里的pre-commitId指的是你想删除的commit的前一次提交commitId。执行完这个会进入文本编辑页面，你需要修改记录文件里面的pick为drop，也就是放弃这个commit。然后保存并退出。

如果是远程的分支同样执行

> git push origin HEAD --force

需要特别留意的是，可能会发生冲突。

