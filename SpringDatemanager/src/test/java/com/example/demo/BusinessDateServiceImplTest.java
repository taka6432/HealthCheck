package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.BusinessDate;
import com.example.demo.repository.BusinessDateDao;
import com.example.demo.service.BusinessDateService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BusinessDateServiceImplTest {

	// 依存しているDaoをモック化
	@MockBean
	BusinessDateDao mockDao;

	@Autowired
	BusinessDateService businessDateService;

	@Test
	public void 一件登録が機能する() throws Exception {
		// テスト用の空のEntityクラス用意
		BusinessDate businessDate = new BusinessDate();

		// Daoのvoidメソッドが実行されることを設定（void）
		doNothing().when(mockDao).insertDate(businessDate);

		// ServiceクラスのinsertDateメソッド実行
		businessDateService.insertDate(businessDate);

		// DaoのinsertDateメソッドが内部で1回実行されることを検証
		verify(mockDao, times(1)).insertDate(businessDate);
	}

	@Test
	public void 登録件数が取得できる() throws Exception {
		// DaoのgetCountメソッド戻り値に999を設定
		when(mockDao.getCount()).thenReturn(999);

		// actual設定
		int actualCount = businessDateService.getCount();

		// DaoのgetCountメソッドが内部で1回実行されることを検証
		verify(mockDao, times(1)).getCount();

		// actualとexpectedを比較検証
		assertThat(actualCount, is(999));
	}

	@Test
	public void IDで1件取得できる() throws Exception {
		// テスト用の空のEntityクラス用意
		BusinessDate businessDate = new BusinessDate();

		// Daoの戻り値設定
		when(mockDao.selectOne(999)).thenReturn(businessDate);

		// actual設定
		BusinessDate actualEntity = businessDateService.selectOne(999);

		// DaoのgetCountメソッドが内部で1回実行されることを検証
		verify(mockDao, times(1)).selectOne(999);

		// actualとexpectedを比較検証
		assertThat(actualEntity, is(businessDate));
	}

	@Test
	public void IDで1件削除できる() throws Exception {

		// Daoのvoidメソッドが実行されることを設定（void）
		doNothing().when(mockDao).deleteOne(999);

		// Serviceクラスのメソッド実行
		businessDateService.deleteOne(999);

		// Daoのメソッドが内部で1回実行されることを検証
		verify(mockDao, times(1)).deleteOne(999);
	}

	@Test
	public void 編集ができる() throws Exception {
		// テスト用の空のEntityクラス用意
		BusinessDate businessDate = new BusinessDate();

		// Daoのvoidメソッドが実行されることを設定（void）
		doNothing().when(mockDao).updateOne(businessDate);

		// Serviceクラスのメソッド実行
		businessDateService.updateOne(businessDate);

		// Daoのメソッドが内部で1回実行されることを検証
		verify(mockDao, times(1)).updateOne(businessDate);
	}

	@Test
	public void 全件取得ができる() throws Exception {
		// テスト用のEntityクラスのリスト作成
		List<BusinessDate> dateList = new ArrayList<>();
		BusinessDate businessDate1 = new BusinessDate();
		BusinessDate businessDate2 = new BusinessDate();
		dateList.add(businessDate1);
		dateList.add(businessDate2);

		// Daoの戻り値設定
		when(mockDao.getAll()).thenReturn(dateList);

		// actual設定
		List<BusinessDate> actualList = businessDateService.getAll();

		// Daoのメソッドが内部で1回実行されることを検証
		verify(mockDao, times(1)).getAll();

		// actualとexpectedを比較検証
		assertThat(actualList, is(dateList));

	}

}