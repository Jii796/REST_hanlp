## 通过git bash进行Pull Requests操作
- 前提工作：已经下载好Git
- 具体步骤如下：
1. 在文件夹下右键，选择“Git Bash Here”，进入如下页面;
![Git Bash Here](https://gitee.com/jiangjiahui666/jiangjiahui/raw/master/picture/GitBashHere.png)
![Git Bash Here](https://user-images.githubusercontent.com/83084341/183377169-b955dc02-8bc6-4369-a0ed-6eea7e18b230.png)

2. 之后，可以选择创建一个新的文件夹“hanlp”,然后进入该文件夹。


```
mkdir hanlp
cd hanlp
```
3. 初始化仓库

```
git init
```
![初始化本地仓库](https://user-images.githubusercontent.com/83084341/183377221-f646f1f2-7c8d-4ed6-86ff-a78b95f1bb4d.png)
4. 拉取远程仓库，这里没有直接使用clone，而是使用的如下命令。

```
git remote add origin 你的仓库地址
```


注意，这里的仓库地址建议使用ssh地址。
之后拉取远程仓库的内容至本地仓库

```
 git pull origin master
```
![拉取远程分支](https://user-images.githubusercontent.com/83084341/183377406-a61f2822-b8f4-4f4c-b7b5-e161b272b50a.png)
这个时候再次查看文件夹hanlp里面的内容，可以发现多出了上图文件夹内所示的内容：

5. 在这里通过`git branch`命令，查看当前分支，发现只有一个main分支，为了防止本地修改的时候出现文件丢失，可以新创建一个分支，比如新建一个名称为mergy的分支
![新建本地分支](https://user-images.githubusercontent.com/83084341/183377726-a2496be9-755c-4c08-9892-dc69ff03a41c.png)

```
git checkout -b mergy
```
在这里试着新建一个文本文件“新建文本文档.txt”
然后通过`git add .`、`git commit -m "你的提交信息"`提交本地修改至仓库。
在这里也可以通过`git add . `，`git commit`进行提交，该git commit命令将调用文本编辑器。输入modified my info第一行，其余内容保持不变。保存并退出编辑器，这就完成了本次提交。
![本地提交修改](https://user-images.githubusercontent.com/83084341/183378004-071fa2b7-ebbb-4bc3-992a-eda7930048fc.png)

之后通过
```
git checkout main
```
命令切换至main分支，发现该分支下并没有“新建文本文档.txt”这个文件，也就是说，在mergy分支下的任何修改都不会影响到main分支。

6. 之后再次将分支切换到mergy，然后修改相应的文件内容（这里可以直接将修改后的文件复制到了相应文件夹下），再通过`git add .`、`git commit -m "你的提交信息"`提交修改。

7. 最后将本地仓库的内容，提交至远程仓库,注意，你在哪个分支下修改的，就应该push哪个分支
```
git push -u origin mergy
```

8. 打开github查看仓库的内容，查看仓库的所有分支，发现多了一个mergy分支。表示你已经成功将本地内容提交至远程仓库。
然后点击上方的“Compare & pull requests”
![查看远程仓库内容](https://user-images.githubusercontent.com/83084341/183378313-db51442c-4bde-4b42-ad1e-80579029d39a.png)
开始创建一个新的Pull Requests,可以参照如下的图片顺序。
![创建Pull Request(1)](https://user-images.githubusercontent.com/83084341/183378545-dc3dde41-7107-42aa-8eec-701fc811d1d8.png)

![创建Pull Request(2)](https://user-images.githubusercontent.com/83084341/183378573-bd48d2cc-5213-42d1-bdd0-be6959788e85.png)

![创建Pull Request(3)](https://user-images.githubusercontent.com/83084341/183378620-c6aad0ee-6f1e-4bb1-8bc4-c4edb1d2ff7f.png)
最后，可以直接观察到修改前后的文件变化。
![创建Pull Request(4)](https://user-images.githubusercontent.com/83084341/183378675-94cd87d9-082e-4511-ba69-a591eaaee2d0.png)
