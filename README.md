# EMREditor
#idea项目，使用spring boot开发，里面使用spring MVC,spring,mybatis框架
#数据库采用mysql数据库
#在进行表格操作是仍然还有很多的一些bug，在有合并单元格的地方进行删除行列操作是会出现单元格没对齐的情况，只需要将合并的单元格完全拆分即可解决

1、mysql8.0默认开始ssl连接认证，且自动创建ca.pem文件
	一般在mysql安装目录下 MySQL Server 8.0\Data 可以找到
2、将ca.pem复制出来，放到一个没有空格的路径中（带空格的路径执行会报错，目前没有找到解决办法），如：D:/ca.pem
3、需要将命令窗口切换的jdk的斌目录，如：C:\Program Files\Java\jdk1.8.0_191\bin
	执行keytool -importcert -alias mysqlca -file D:\ca.pem -keystore D:\icms.truststore命令即可在D盘根目录下生成一个store.truststore密钥文件
	执行命令后输入的密钥口令（如：123456）需要记住，在连接地址中需要写进去
4、将第3步生成的icms.truststore密钥文件放到项目的src目录下
5、在MySQL数据库连接地址后添加
	&useSSL=true&enabledTLSProtocols=TLSv1.2&verifyServerCertificate=true&trustCertificateKeyStoreUrl=classpath:store.truststore&trustCertificateKeyStorePassword=123456
