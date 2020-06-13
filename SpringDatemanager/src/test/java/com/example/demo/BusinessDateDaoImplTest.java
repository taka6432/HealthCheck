package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.BusinessDate;
import com.example.demo.repository.BusinessDateDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class BusinessDateDaoImplTest {

	@Autowired
	BusinessDateDao dao;

	@Test
	public void 登録件数を取得できる() throws Exception {
		// テスト用データ3件
		assertThat(dao.getCount(), is(3));
	}

	@Test(expected = DataAccessException.class)
	public void 存在しないIDによる１件取得でDataAccessExceptionが発生する() throws Exception {
		dao.selectOne(4);
	}

	@Test
	public void 新規登録ができる() throws Exception {

		// テスト用にEntityクラスを作成
		BusinessDate businessDate = new BusinessDate();
		businessDate.setBase_date("20200512");
		businessDate.setDate_name("test");
		businessDate.setDiff_year(0);
		businessDate.setDiff_month(0);
		businessDate.setDiff_day(1);

		// Entityクラス（テスト用）を登録し、idで取得（該当は４件目）
		dao.insertDate(businessDate);
		BusinessDate actual = dao.selectOne(4);

		assertThat(actual.getDate_name(), is("test"));
		assertThat(actual.getBase_date(), is("20200512"));
	}

	@Test
	public void 全件取得が取得できる() throws Exception {
		List<BusinessDate> dateList = new ArrayList<BusinessDate>();
		dateList = dao.getAll();
		// テスト用データ3件
		assertThat(dateList.size(), is(3));
	}

	@Test
	public void 登録された1件を更新できる() throws Exception {
		// テスト用にID=1のEntityクラスのデータを入れ替え。
		BusinessDate businessDate = new BusinessDate();
		businessDate.setBase_date("20200512");
		businessDate.setDate_name("test");
		businessDate.setDiff_year(0);
		businessDate.setDiff_month(0);
		businessDate.setDiff_day(1);
		businessDate.setId(1);

		dao.updateOne(businessDate);

		assertThat(dao.selectOne(1).getDate_name(), is("test"));
	}

	@Test
	public void 登録を１件削除できる() throws Exception {
		dao.deleteOne(1);

		// テスト用データ1件削除のため残り件数は2件
		assertThat(dao.getCount(), is(2));
	}

	@Test
	public void 存在しないIDによる１件削除ができない() throws Exception {

		// jdbcTemplate.update(SQL)で該当がなければ0が返却される仕様のため、
		// 削除実施しても件数が変わらないことを確認する（テスト用データ3件）。
		dao.deleteOne(999);
		assertThat(dao.getCount(), is(3));
	}

}