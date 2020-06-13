--datetableがあれば削除
DROP TABLE IF EXISTS datetable;

--datetableがなければ新しく作成
CREATE TABLE IF NOT EXISTS datetable(
id INT AUTO_INCREMENT,
date_name VARCHAR(5) NOT NULL,
base_date VARCHAR(10) NOT NULL,
diff_year INTEGER NOT NULL,
diff_month INTEGER NOT NULL,
diff_day INTEGER NOT NULL,
PRIMARY KEY(id)
);