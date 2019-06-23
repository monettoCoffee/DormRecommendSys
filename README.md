# DormRecommendSys Description
We try to achieve automatic dormitory partition through algorithm.
<br>DRS contains router module, service module, and calculate module.
<br>For learning purposes,
<br>The core algorithm part by ourselves.
<br>For specific design details, please refer to /document.
<br>
<br>我们尝试通过某些算法实现自动划分寝室的目的。
<br>DRS 包括 路由层, 服务层, 计算层, 三个子项目, 我们既可以使用单节点的方式, 也可以分别部署。
<br>出于学习的目的, 核心算法模块我们自己来探索与实现。
<br>关于工程详细的设计细节, 可以参考document文件夹下的资料。


# The Deploy of DRS
Use IntelliJ IDEA Community Edition to open project, and config environment Python3, Java8. 
<br>You can scan environment directory get the requires,
<br>use the python script "deploy.py" to boot DRS, 
<br>also can deploy it in different node.
<br>DRS-Service is written in Java SpringBoot.
<br>DRS-Calculate is writen in Python Flask.
<br>
<br>直接使用IntelliJ IDEA Community打开，并配置环境(Python3，Java8)。
<br>部署前请到environment目录查看环境依赖。
<br>你可以使用Python脚本"deploy.py"去启动DRS系统。
<br>也可以手动/自动部署在不同的节点上。
<br>DRS-Server后端模块由 Java Spring 编写。
<br>DRS-Calculate计算模块由 Python Flask 编写。
