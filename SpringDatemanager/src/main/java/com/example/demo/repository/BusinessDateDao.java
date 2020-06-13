package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.entity.BusinessDate;

public interface BusinessDateDao {
	// 登録済みデータ数取得
	int getCount() throws DataAccessException; // DB関連の例外をキャッチするもの

	// 入力した日付名の数をカウントする。
	int countDateName(String dateName) throws DataAccessException;

	// １件取得（詳細画面用）
	BusinessDate selectOne(int id) throws DataAccessException;

	// 1件登録
	void insertDate(BusinessDate businessDate) throws DataAccessException;

	// 編集用
	void updateOne(BusinessDate businessDate) throws DataAccessException;

	// 一覧取得
	List<BusinessDate> getAll() throws DataAccessException;

	// 1件削除
	void deleteOne(int id) throws DataAccessException;

}
