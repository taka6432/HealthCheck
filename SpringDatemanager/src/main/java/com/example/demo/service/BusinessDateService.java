package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.BusinessDate;

public interface BusinessDateService {
	// 現在指定されている登録件数を取得→引数はなし
	int getCount();

	// 種類別の日付名の数を取得
	int countDateName(String dateName);

	// 1件登録
	void insertDate(BusinessDate businessDate);

	// 一覧取得
	List<BusinessDate> getAll();

	// 1件取得（詳細画面用）→検索結果が１件だけで単一のオブジェクトで取得したい場合に使用
	BusinessDate selectOne(int id);

	// １件削除
	void deleteOne(int id);

	// 1件編集
	void updateOne(BusinessDate businessDate);

}
