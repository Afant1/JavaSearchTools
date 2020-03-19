# JavaSearchTools 辅助人工审计，寻找java反序列化危险类的工具。

## Usage:java -jar SearchClassInJar.jar Path

第一步：首先用jd-gui反编译jar包还原代码,存储路径：C:\\Users\\afanti\\Desktop\\ctf-tools\\jd-gui\\weblogic\\

第二步：java -jar SearchClassInJar.jar C:\\Users\\afanti\\Desktop\\ctf-tools\\jd-gui\\weblogic\\

![](http://img2020.cnblogs.com/blog/1298490/202003/1298490-20200319105834907-1284701098.png)

> Demo是寻找xml库和实现序列化接口的正则，可在SearchReadObject，SearchEvilPackage函数修改正则。

文章发表在安全客：https://www.anquanke.com/post/id/199703
