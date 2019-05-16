
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