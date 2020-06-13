package com.example.demo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.BusinessDate;
import com.example.demo.form.DateForm;
import com.example.demo.service.BusinessDateService;

@RunWith(SpringRunner.class) // DIを利用してテストする時に必要
@SpringBootTest // springのDI機能を動かすためRunWithに追記する→結合テスト
@AutoConfigureMockMvc // MockMvc mvcの初期化処理の記述を不要にする
public class HomeControllerTest {

	// 依存しているサービスクラスをMock化→DB上のデータのやりとりを介さず、serviceクラスをテスト出来る
	@MockBean
	BusinessDateService mockDateService;

	@Autowired // テスト対象のクラスをDIコンテナに登録→今回はMockMvcインスタンスが対象
	private MockMvc mockMvc;// MockMvcを利用して仮装のリクエストを発生させテストを実行する

	@Test
	public void ホーム画面index_bootが表示される() throws Exception {

		// mockMvcのperformを使用しHTTPリクエストを実施,andExpectでレスポンスのテストを実行
		// status().isOk()はレスポンス成功を指す,view().name()でgetが正しく作動し/businessdateでviewを返すかテストしてる
		mockMvc.perform(get("/businessdate")).andExpect(status().isOk()).andExpect(view().name("index_boot"));
	}

	@Test
	public void 一覧画面list_bootが表示される() throws Exception {

		// Mock化したBusinessDateServiceのgetAll()の戻り値を設定
		List<BusinessDate> dateList = new ArrayList<BusinessDate>();
		dateList.add(createBusinessDate(1, "19990101", "テスト日付", 1, 0, 0));
		when(mockDateService.getAll()).thenReturn(dateList);// 特定のモックインスタンスが呼ばれた時(when),指定した値を返却する(thenReturn)

		// viewで使う変数を正しくmodelに詰められているかmodelの状態をテストしている
		// 変数をviewに渡しているかどうかはmodel().attribute()を使用する
		mockMvc.perform(get("/businessdate/datelist")).andExpect(status().isOk()).andExpect(view().name("list_boot"))
				.andExpect(model().attribute("dateList", dateList));
	}

	@Test
	public void 計算シミュレーション結果が表示される() throws Exception {

		// Mock化したBisinessDateServiceのgetCount()の戻り値を999に設定
		when(mockDateService.getCount()).thenReturn(999);

		// テスト用のFormクラスをインスタンス化
		DateForm dateForm = createDateForm(1, "19990101", "テスト日付", 1, 0, 0);

		// postリクエスト、テスト用の計算結果IDが999、計算結果が20000101を検証
		mockMvc.perform(post("/businessdate").param("calc", "calc").flashAttr("dateForm", dateForm))
				.andExpect(status().isOk()).andExpect(view().name("index_boot"))
				.andExpect(content().string(containsString("999")))
				.andExpect(content().string(containsString("20000101")))
				.andExpect(model().attribute("dateForm", dateForm));

	}

	@Test
	public void 計算シミュレーション時に不適切な入力でエラーが表示される() throws Exception {

		// テスト用のFormクラスをインスタンス化（base_dateに不適切な入力値を設定）
		DateForm dateForm = createDateForm(1, "Invalid input", "テスト日付", 1, 0, 0);

		// postリクエスト。バリデーションエラーの発生、エラーメッセージが適切か検証
		// リクエスト時にパラメータに値を入れる為にparam(パラメーター名,値),flashAttr(パラメーター名,オブジェクト)を使う
		mockMvc.perform(post("/businessdate").param("calc", "calc").flashAttr("dateForm", dateForm))
				.andExpect(status().isOk()).andExpect(view().name("index_boot")).andExpect(model().hasErrors())
//				.andExpect(content().string(containsString("yyyyMMdd形式で入力してください。")))
				.andExpect(model().attribute("dateForm", dateForm));

	}

	@Test
	public void 戻るボタンでホーム画面が表示される() throws Exception {
		mockMvc.perform(post("/businessdate").param("back", "back")).andExpect(status().isOk())
				.andExpect(view().name("index_boot"));
	}

	@Test
	public void データベースへ登録完了しホーム画面へ戻る() throws Exception {

		// テスト用のFormクラスをインスタンス化
		DateForm dateForm = createDateForm(1, "19990101", "テスト日付", 1, 0, 0);
		// テスト用のEntityクラスをインスタンス化（idはAutoincrementのためゼロで良い）
		BusinessDate businessDate = createBusinessDate(0, "19990101", "テスト日付", 1, 0, 0);
		// 302→応答された時にブラウザが新しいurlにアクセスする必要があることを意味する
		mockMvc.perform(post("/businessdate").param("insert", "insert").flashAttr("dateForm", dateForm))
				.andExpect(status().is(302)).andExpect(model().hasNoErrors()).andExpect(redirectedUrl("businessdate"));

		// BusinessDateServiceクラスのinsertDateメソッドが1回呼び出されたか検証
//		verify(mockDateService, times(1)).insertDate(businessDate);

	}

	@Test
	public void 詳細画面でURLパス指定した登録データが表示される() throws Exception {

		// テスト用のFormクラスをインスタンス化
		BusinessDate businessDate = createBusinessDate(1, "19990101", "テスト日付", 1, 0, 0);
		// id=1のデータの取得を再現。
		int testId = 1;
		// Mock化したBisinessDateServiceのselectOne()の戻り値を設定
		when(mockDateService.selectOne(testId)).thenReturn(businessDate);

		mockMvc.perform(get("/businessdate/dateDetail/" + testId)).andExpect(status().isOk())
				.andExpect(content().string(containsString("テスト日付"))).andExpect(view().name("dateDetail_boot"));

		// BusinessDateServiceクラスのselectOneメソッドが1回呼び出されたか検証
		verify(mockDateService, times(1)).selectOne(testId);

	}

	@Test
	public void 編集ボタンで編集完了メッセージとともに一覧画面が表示される() throws Exception {
		// テスト用のFormクラスをインスタンス化
		DateForm dateForm = createDateForm(1, "19990101", "テスト日付", 1, 0, 0);
		// テスト用のEntityクラスをインスタンス化
		BusinessDate businessDate = createBusinessDate(1, "19990101", "テスト日付", 1, 0, 0);
		// テスト用のEntityクラスのリストをインスタンス化
		List<BusinessDate> dateList = new ArrayList<BusinessDate>();
		dateList.add(businessDate);

		// Mock化したBisinessDateServiceのupdateOne()が実行されることを設定
		doNothing().when(mockDateService).updateOne(businessDate);
		// Mock化したBisinessDateServiceのgetAll()の戻り値を設定
		when(mockDateService.getAll()).thenReturn(dateList);

		mockMvc.perform(post("/businessdate/datelist").param("update", "update").flashAttr("dateForm", dateForm))
				.andExpect(status().isOk()).andExpect(content().string(containsString("編集しました")))
				.andExpect(view().name("list_boot"));

		// BusinessDateServiceクラスのupdataOne、getAllメソッドが各1回呼び出されたか検証
//		verify(mockDateService, times(1)).updateOne(businessDate);
		verify(mockDateService, times(1)).getAll();

	}

	@Test
	public void 削除ボタンで削除完了メッセージとともに一覧画面が表示される() throws Exception {

		// テスト用のFormクラスをインスタンス化
		DateForm dateForm = createDateForm(1, "19990101", "テスト日付", 1, 0, 0);
		// テスト用のEntityクラスをインスタンス化
		BusinessDate businessDate = createBusinessDate(1, "19990101", "テスト日付", 1, 0, 0);
		// テスト用のEntityクラスのリストをインスタンス化
		List<BusinessDate> dateList = new ArrayList<BusinessDate>();
		dateList.add(businessDate);

		// Mock化したBisinessDateServiceのdeleteOne()が実行されることを設定
		doNothing().when(mockDateService).deleteOne(businessDate.getId());
		// Mock化したBisinessDateServiceのgetAll()の戻り値を設定
		when(mockDateService.getAll()).thenReturn(dateList);

		mockMvc.perform(post("/businessdate/datelist").param("delete", "delete").flashAttr("dateForm", dateForm))
				.andExpect(status().isOk()).andExpect(content().string(containsString("削除しました")))
				.andExpect(view().name("list_boot"));

		// BusinessDateServiceクラスのdelieteOneとgetAllメソッドが各1回呼び出されたか検証
		verify(mockDateService, times(1)).deleteOne(dateForm.getId());
		verify(mockDateService, times(1)).getAll();

	}

	// テスト用のEntityクラスの作成メソッド
	public BusinessDate createBusinessDate(int id, String base_date, String date_name, int diff_year, int diff_month,
			int diff_day) {
		BusinessDate businessDate = new BusinessDate();
		businessDate.setId(id);
		businessDate.setBase_date(base_date);
		businessDate.setDate_name(date_name);
		businessDate.setDiff_year(diff_year);
		businessDate.setDiff_month(diff_month);
		businessDate.setDiff_month(diff_day);

		return businessDate;
	}

	// テスト用のFormクラスの作成メソッド
	public DateForm createDateForm(int id, String base_date, String date_name, int diff_year, int diff_month,
			int diff_day) {
		DateForm dateForm = new DateForm();
		dateForm.setId(id);
		dateForm.setBase_date(base_date);
		dateForm.setDate_name(date_name);
		dateForm.setDiff_year(diff_year);
		dateForm.setDiff_month(diff_month);
		dateForm.setDiff_month(diff_day);
//		 dateForm.setCalc_date(calc_date);

		return dateForm;
	}

}