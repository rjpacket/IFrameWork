### 1. 如何删除一个commit到本地的分支

#### 1.1 使用git reset

> git log

首先查看提交日志，找到commitId，

> git reset --hard <commitId>

如果是本地分支到这里，分支就已经改变了，后提交的分支就都没有了。如果是远程分支，还需要执行命令行

> git push origin HEAD --force

#### 1.2 使用git rebase

git-rebase操作起来要比git-reset高级的多，例如如果你有三次提交commit 1，commit 2，commit 3，只想删除commit 2操作，如果使用reset的方式，commit 3的提交记录也会消失。

使用git rebase步骤如下：

> git log

> git rebase -i <pre-commitId>

这里的pre-commitId指的是你想删除的commit的前一次提交commitId。执行完这个会进入文本编辑页面，你需要修改记录文件里面的pick为drop，也就是放弃这个commit。然后保存并退出。

如果是远程的分支同样执行

> git push origin HEAD --force

需要特别留意的是，可能会发生冲突。发生冲突先解决冲突，然后点击Continue rebase，会依次执行合并后续的commit，一直到最新的commit，这是执行命令

> git rebase --continue

执行完之后，刷新看到commit 2的提交内容已经没有了。但是这个操作看起来风险很大，如果要去掉中间某一个commit的代码，最好还是使用revert，简单暴力，缺点就是提交记录会保留着，不好看。


### 2. 如何恢复已经删除的分支呢？

