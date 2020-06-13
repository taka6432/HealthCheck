package com.example.demo.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class DateForm {
	// フォームにはないがシミュレーション用に残しているフィールド

	private int id;
	private String date_name;

	@NotBlank // バリデーションの一種→null、空文字、空白のみの場合エラーが出る
	// 指定の正規表現に合致するかのアノテーション
	@Pattern(regexp = "(19[0-9]{2}|20[0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])")
	private String base_date;

	// 計算式シミュレーション結果を格納する変数
	private String calc_date;
	private int diff_year;
	private int diff_month;
	private int diff_day;

	public DateForm() {
	}

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

	public String getCalc_date() {
		return calc_date;
	}

	public void setCalc_date(String calc_date) {
		this.calc_date = calc_date;
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
