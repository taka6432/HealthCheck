package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.BusinessDate;
import com.example.demo.repository.BusinessDateDao;

@Transactional
@Service
public class RestServiceImpl implements RestService {

	@Autowired
	// @Qualifier("BusinessDateDaoImpl")
	BusinessDateDao dao;

	// 1件登録
	@Override
	public void insertDate(BusinessDate businessDate) {
		// TODO 自動生成されたメソッド・スタブ
	}

	// 全件取得
	@Override
	public List<BusinessDate> getAll() {
		return dao.getAll();
	}

	// 1件取得
	@Override
	public BusinessDate selectOne(int id) {
		return dao.selectOne(id);
	}

}