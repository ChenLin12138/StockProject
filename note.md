

## 文件下载
http://quotes.money.163.com/service/chddata.html?code=1000611&start=19961008&end=20190513&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP

## 文件移动
移动download文件夹下所有的csv文件到指定目录
```bash
mv  /Users/zdm/Downloads/*.csv /Users/zdm/idea-workspace/stock/stockhistory
```
移动文件到指定目录除开包含()的文件
```bash
shopt -s extglob
mv -f /Users/zdm/Downloads/!(*[()]*).csv /Users/zdm/idea-workspace/StockProject/csvfiles
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