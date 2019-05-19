
#开发笔记

## 文件下载
http://quotes.money.163.com/service/chddata.html?code=1000611&start=19961008&end=20190513&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP

## 文件下载性能测试
### 单文件下载测试结果
```
StopWatch '': running time (millis) = 5920
-----------------------------------------
ms     %     Task name
-----------------------------------------
02454  041%  Method downloadFile
01162  020%  Method downloadWithNIO
02304  039%  Method downloadWithAsyncHttpClient
```

60个文件下性能测试
```
StopWatch '': running time (millis) = 77566
-----------------------------------------
ms     %     Task name
-----------------------------------------
29593  038%  Method downloadFile
14587  019%  Method downloadWithNIO
33386  043%  Method downloadWithAsyncHttpClient
```
从结论上来看最快的是NIO这是为什么？能给一个解释吗？最慢的反而是异步。
这里大概也能计算出60个文件的下载和写入需要14秒，那么3000只股票需要12分钟。那还能接受。

位置一换NIO的优势就没有了。还跟顺序有关系？
StopWatch '': running time (millis) = 92860
-----------------------------------------
ms     %     Task name
-----------------------------------------
30976  033%  Method downloadWithNIO
35042  038%  Method downloadWithAsyncHttpClient
26842  029%  Method downloadFile

单独测试,以下测试了30个文件的读入
IO
```
StopWatch '': running time (millis) = 11621
-----------------------------------------
ms     %     Task name
-----------------------------------------
11621  100%  Method downloadFile
```

NIO
```
StopWatch '': running time (millis) = 12710
-----------------------------------------
ms     %     Task name
-----------------------------------------
12710  100%  Method downloadWithNIO
```

AsyncHttpClient
```
StopWatch '': running time (millis) = 38509
-----------------------------------------
ms     %     Task name
-----------------------------------------
38509  100%  Method downloadWithAsyncHttpClient
```

## 文件移动
移动download文件夹下所有的csv文件到指定目录
```bash
mv  /Users/zdm/Downloads/*.csv /Users/zdm/eclipse-workspace/StockProject/csvfiles
```
移动文件到指定目录除开包含()的文件
```bash
shopt -s extglob
mv -f /Users/zdm/Downloads/!(*[()]*).csv /Users/zdm/eclipse-workspace/StockProject/csvfiles
```

## 文件导入

## 数据库定义
```sql
CREATE TABLE `PRICE_HISTORY` (
  `pk` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `CODE` char(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DATE` date NOT NULL,
  `TCLOSE` float NOT NULL,
  `HIGH` float NOT NULL,
  `LOW` float NOT NULL,
  `TOPEN` float NOT NULL,
  `CHG` float NOT NULL,
  `PCHG` float NOT NULL,
  `TURNOVERRATE` float NOT NULL,
  `VOTURNOVER` int(10) unsigned NOT NULL,
  `VATURNOVER` float NOT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB AUTO_INCREMENT=849 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```

## 那些不存在的股票代码
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000614.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000624.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000634.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000640.csv: 108


```
Date is : 1997-04-10Stock is : 3.7295
java.lang.NumberFormatException: For input string: "None"
	at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:2043)
	at sun.misc.FloatingDecimal.parseFloat(FloatingDecimal.java:122)
	at java.lang.Float.parseFloat(Float.java:451)
	at com.lin.stock.jobs.DataLoadJob.fullDownloadFor1Market(DataLoadJob.java:64)
	at com.lin.stock.jobs.DataLoadJob.fullLoad(DataLoadJob.java:35)
	at com.lin.stock.jobs.app.DataLoadApp.fullDataLoad(DataLoadApp.java:39)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.springframework.test.context.junit4.statements.RunBeforeTestExecutionCallbacks.evaluate(RunBeforeTestExecutionCallbacks.java:74)
	at org.springframework.test.context.junit4.statements.RunAfterTestExecutionCallbacks.evaluate(RunAfterTestExecutionCallbacks.java:84)
	at org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks.evaluate(RunBeforeTestMethodCallbacks.java:75)
	at org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks.evaluate(RunAfterTestMethodCallbacks.java:86)
	at org.springframework.test.context.junit4.statements.SpringRepeat.evaluate(SpringRepeat.java:84)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:251)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:97)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:190)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:89)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:41)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:541)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:763)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:463)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:209)
```

