
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
CREATE TABLE stock.stock_priceinfo (
	pk BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`date` DATE NOT NULL,
	endPrice FLOAT NOT NULL,
	highPrice FLOAT NOT NULL,
	lowPrice FLOAT NOT NULL,
	startPrice FLOAT NOT NULL,
	deltaPrice FLOAT NOT NULL,
	deltaRate FLOAT NOT NULL,
	exchangeRate FLOAT NOT NULL,
	turnover INTEGER UNSIGNED NOT NULL,
	amount FLOAT NOT NULL,
	MC FLOAT NOT NULL,
	FAMC FLOAT NOT NULL,
	CONSTRAINT stock_priceinfo_PK PRIMARY KEY (pk)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_bin
AUTO_INCREMENT=1;
```

##


/Users/zdm/eclipse-workspace/StockProject/csvfiles/000611.csv: 3799
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000612.csv: 7363
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000613.csv: 2617
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000614.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000615.csv: 871
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000616.csv: 871
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000617.csv: 7883
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000618.csv: 870
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000619.csv: 871
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000620.csv: 871
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000621.csv: 6810
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000622.csv: 5514
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000623.csv: 871
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000624.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000625.csv: 870
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000626.csv: 6175
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000627.csv: 16746
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000628.csv: 14370
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000629.csv: 17501
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000630.csv: 10927
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000631.csv: 871
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000632.csv: 869
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000633.csv: 9739
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000634.csv: 108
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000635.csv: 871
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000636.csv: 17934
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000637.csv: 872
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000638.csv: 3246
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000639.csv: 9739
/Users/zdm/eclipse-workspace/StockProject/csvfiles/000640.csv: 108