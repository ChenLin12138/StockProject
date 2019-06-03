
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

股票表，暂时这样用着吧
```sql
CREATE TABLE stock.stock SELECT DISTINCT CODE FROM stock.PRICE_HISTORY group BY CODE
```

### 建立索引
PRICE_HISTORY是一个拥有900万条数据的表。
我们做一个这样的查询需要8.343秒的时间，我们想做查询优化。我的目标是做3星索引，意味着查询和返回数据都能在索引上完成。那么需要了解的是需求是什么，我需要返回什么样的字段。
添加索引后大约查询只需要1-2毫秒
```sql
SELECT * FROM stock.PRICE_HISTORY WHERE CODE = '600001'
AND DATE = '20091210'
```
创建索引的语句
```sql
CREATE UNIQUE INDEX PRICE_HISTORY_CODE_IDX USING BTREE ON stock.PRICE_HISTORY (CODE,`DATE`,TCLOSE,TOPEN);
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
Stock Code is : 000009 Count is 11 in totoal 27
Stock Code is : 000010 Count is 11 in totoal 20
Stock Code is : 000014 Count is 14 in totoal 27
Stock Code is : 000022 Count is 11 in totoal 25
Stock Code is : 000027 Count is 11 in totoal 25
Stock Code is : 000028 Count is 11 in totoal 26
Stock Code is : 000035 Count is 11 in totoal 21
Stock Code is : 000040 Count is 11 in totoal 24
Stock Code is : 000042 Count is 12 in totoal 24
Stock Code is : 000046 Count is 12 in totoal 24
Stock Code is : 000049 Count is 11 in totoal 24
Stock Code is : 000056 Count is 11 in totoal 22
Stock Code is : 000063 Count is 11 in totoal 21
Stock Code is : 000418 Count is 11 in totoal 22
Stock Code is : 000505 Count is 12 in totoal 23
Stock Code is : 000509 Count is 11 in totoal 25
Stock Code is : 000510 Count is 11 in totoal 26
Stock Code is : 000514 Count is 12 in totoal 25
Stock Code is : 000519 Count is 15 in totoal 25
Stock Code is : 000531 Count is 15 in totoal 25
Stock Code is : 000533 Count is 11 in totoal 25
Stock Code is : 000539 Count is 12 in totoal 25
Stock Code is : 000543 Count is 13 in totoal 25
Stock Code is : 000545 Count is 12 in totoal 21
Stock Code is : 000546 Count is 12 in totoal 23
Stock Code is : 000550 Count is 13 in totoal 25
Stock Code is : 000555 Count is 13 in totoal 22
Stock Code is : 000559 Count is 11 in totoal 25
Stock Code is : 000565 Count is 11 in totoal 24
Stock Code is : 000567 Count is 14 in totoal 25
Stock Code is : 000568 Count is 12 in totoal 25
Stock Code is : 000570 Count is 12 in totoal 24
Stock Code is : 000589 Count is 11 in totoal 23
Stock Code is : 000596 Count is 13 in totoal 21
Stock Code is : 000598 Count is 12 in totoal 23
Stock Code is : 000599 Count is 11 in totoal 23
Stock Code is : 000601 Count is 11 in totoal 22
Stock Code is : 000705 Count is 11 in totoal 22
Stock Code is : 000712 Count is 12 in totoal 22
Stock Code is : 000736 Count is 12 in totoal 20
Stock Code is : 000738 Count is 11 in totoal 22
Stock Code is : 000752 Count is 11 in totoal 22
Stock Code is : 000761 Count is 11 in totoal 21
Stock Code is : 000792 Count is 11 in totoal 21
Stock Code is : 000799 Count is 12 in totoal 21
Stock Code is : 000812 Count is 12 in totoal 21
Stock Code is : 000858 Count is 14 in totoal 21
Stock Code is : 000928 Count is 12 in totoal 19
Stock Code is : 000951 Count is 11 in totoal 18

