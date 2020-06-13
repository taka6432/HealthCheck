package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.BusinessDate;

public interface RestService {

	// 登録件数取得
	// int getCount();
	// 種類別の日付名の数を取得
	// int countDateName(String dateName);

	// 1件登録
	void insertDate(BusinessDate businessDate);

	// 全件取得
	List<BusinessDate> getAll();

	// 1件取得
	BusinessDate selectOne(int id);

}