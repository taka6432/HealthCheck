package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // このクラスがentityであるかの宣言
@Table(name = "datetable") // entityではクラス名がテーブルとマッピングするが、異なるテーブルとマッピングさせたい場合に使用
public class BusinessDate {
	@Id // プライマリーキーとなるプロパティかフィールドを指定している
	private int id;
	private String date_name;

	private String base_date;
	private int diff_year;
	private int diff_month;
	private int diff_day;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate_name() {
		return date_name;
	}

	public void setDate_name(String date_name) {
		this.date_name = date_name;
	}

	public String getBase_date() {
		return base_date;
	}

	public void setBase_date(String base_date) {
		this.base_date = base_date;
	}

	public int getDiff_year() {
		return diff_year;
	}

	public void setDiff_year(int diff_year) {
		this.diff_year = diff_year;
	}

	public int getDiff_month() {
		return diff_month;
	}

	public void setDiff_month(int diff_month) {
		this.diff_month = diff_month;
	}

	public int getDiff_day() {
		return diff_day;
	}

	public void setDiff_day(int diff_day) {
		this.diff_day = diff_day;
	}

}
