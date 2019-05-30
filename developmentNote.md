
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
  `CODE` char(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DATE` char(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TCLOSE` float NOT NULL,
  `HIGH` float NOT NULL,
  `LOW` float NOT NULL,
  `TOPEN` float NOT NULL,
  `CHG` float NOT NULL,
  `PCHG` float NOT NULL,
  `TURNOVERRATE` float NOT NULL,
  `VOTURNOVER` bigint(20) unsigned NOT NULL,
  `VATURNOVER` double NOT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `PRICE_HISTORY_MONTH` (
  `pk` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `CODE` char(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DATE` char(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TCLOSE` float NOT NULL,
  `HIGH` float NOT NULL,
  `LOW` float NOT NULL,
  `TOPEN` float NOT NULL,
  `CHG` float NOT NULL,
  `PCHG` float NOT NULL,
  `TURNOVERRATE` float NOT NULL,
  `VOTURNOVER` bigint(20) unsigned NOT NULL,
  `VATURNOVER` double NOT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

```

## 那些不存在的股票代码
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000614.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000624.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000634.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000640.csv: 108

## Bug List
这种读取出来的2019-05-19的数据，在写入数据库的时候变成了2019-05-16,可能和时间地区有关
2019-05-17,'600051,宁波联合,6.3,6.59,6.27,6.56,6.55,-0.25,-3.8168,1.1525,3582877,22993286.0
java代码
```java
					priceHistory.setDate(sdf.parse(rowData[0]));
					priceHistory.setCode(removeQuoteForStockCode(rowData[1]));
```
数据库定义date类型
```sql
`DATE` date NOT NULL,
```
解决：和数据库时区设置有关系，直接修改成字符类型，不用Date类型了。


## 一些问题
文件下载到000913，但是都没写数据库，前面股票都需要重写数据库
从000914-998文件和数据都有
中小板文件和数据都有
创业板文件和数据都有
这个异常因为涨跌幅和涨跌额，换手率为None导致，删除改股票代码重
300739只有两条写入数据库,很多数据都只写入了两条


000012
000014
000049
000538
000553
600651
600652