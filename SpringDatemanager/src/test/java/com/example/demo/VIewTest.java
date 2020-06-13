package com.example.demo;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.BeforeClass;
import org.junit.Test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

//Seleniedを使ったテスト
public class VIewTest {

	// テスト前の設定
	@BeforeClass
	public static void setUp() {
		// デフォルトChromeだが明示する。
		Configuration.browser = WebDriverRunner.CHROME;
		// タイムアウトの時間を5000ミリ秒にする(デフォルト:4000ミリ秒)
		Configuration.timeout = 5000;
		// ベースURL(デフォルト:http://localhost:8080だが明示する)
		Configuration.baseUrl = "http://localhost:8080";
	}

	@Test
	public void 空欄で計算実行を押すとエラーメッセージが表示される() {

		open("/businessdate");
		$("button[name=calc]").click();
//		$("body").shouldHave(text("基準日付はyyyyMMdd形式で入力してください。"));
//		$("body").shouldHave(text("基準日付を入力してください。"));
	}

	@Test
	public void 間違った形式のテキストを入力しエラーメッセージが表示される() {
		open("/businessdate");
		$("input[type=text]").val("2020/01/01");
		$("button[name=calc]").click();
//		$("body").shouldHave(text("基準日付はyyyyMMdd形式で入力してください。"));
	}

	@Test
	public void 計算シミュレーションで１年後の計算結果が表示される() {
		open("/businessdate");
		$("input[type=text]").val("20200101");
		$$("input[type=checkbox]").findBy(attribute("name", "diff_year")).setSelected(true);

		$("button[name=calc]").click();
		$("body").shouldHave(text("20210101"));
	}

	@Test
	public void 計算シミュレーションで１ヶ月後の計算結果が表示される() {
		open("/businessdate");
		$("input[type=text]").val("20200101");
		$$("input[type=checkbox]").findBy(attribute("name", "diff_month")).setSelected(true);

		$("button[name=calc]").click();
		$("body").shouldHave(text("20200201"));
	}

	@Test
	public void 計算シミュレーションで月末の計算結果が表示される() {

		open("/businessdate");
		$("input[type=text]").val("20200101");
		$$("input[type=checkbox]").findBy(attribute("name", "diff_day")).setSelected(true);

		$("button[name=calc]").click();
		$("body").shouldHave(text("20200131"));
	}

	@Test
	public void 計算後に登録ボタンを押すとフォームが空欄の初期画面に戻る() {

		// テストとして日付を入力
		open("/businessdate");
		$("input[type=text]").val("20200101");
		$$("input[type=checkbox]").findBy(attribute("name", "diff_day")).setSelected(true);

		// 登録ボタンクリック
		$("button[name=calc]").click();
		$$("input[type=submit]").findBy(attribute("name", "insert")).click();

		// 入力フォーム空欄の初期画面が表示されるか
		$("body").shouldHave(text("計算の基準となる日付を入力してください。"));
		$("input[type=text]").shouldBe(empty);
	}

	@Test
	public void 一覧を押すと登録日付一覧へ遷移し戻るボタンでホーム画面へ戻る() {
		// テストとして日付を入力
		open("/businessdate");
		$(byText("一覧")).click();

		// 一覧画面が表示されるか
		$("body").shouldHave(text("登録日付一覧"));

		// 戻るボタンでホーム画面へ戻るか。
		$$("input[type=submit]").findBy(attribute("name", "back")).click();
		$("body").shouldHave(text("計算の基準となる日付を入力してください。"));
	}

	@Test
	public void 詳細ボタンで詳細画面に遷移し戻るボタンで一覧画面へ戻る() {
		// テストとして日付を入力
		open("/businessdate/datelist");
		$(byText("詳細")).click();

		// 詳細画面が表示されるか
		$("body").shouldHave(text("登録日付詳細"));

		// 戻るボタンで一覧画面へ戻るか。
		$(byText("戻る")).click();
		$("body").shouldHave(text("登録日付一覧"));
	}

	@Test
	public void 詳細ボタンで一覧画面から詳細画面に遷移し削除ボタンでメッセージとともに一覧画面へ戻る() {
		// テストとして日付を入力
		open("/businessdate/datelist");
		$(byText("詳細")).click();

		// 詳細画面が表示されるか
		$("body").shouldHave(text("登録日付詳細"));

		// 削除ボタンクリックし一覧画面へ戻るか。
		// メッセージが表示されるか。
		$(byText("削除")).click();
		$("body").shouldHave(text("削除しました"));
		$("body").shouldHave(text("登録日付一覧"));
	}

	@Test
	public void 詳細ボタンで一覧画面から詳細画面に遷移し更新ボタンでメッセージとともに一覧画面へ戻る() {
		// テストとして日付を入力
		open("/businessdate/datelist");
		$(byText("詳細")).click();

		// 詳細画面が表示されるか
		$("body").shouldHave(text("登録日付詳細"));

		// 削除ボタンクリックし一覧画面へ戻るか。
		// メッセージが表示されるか。
		$(byText("更新")).click();
		$("body").shouldHave(text("編集しました"));
		$("body").shouldHave(text("登録日付一覧"));
	}

	@Test
	public void 詳細画面からホームボタンでホーム画面へ戻る() {
		// テストとして日付を入力
		open("/businessdate/datelist");
		$(byText("詳細")).click();

		// 詳細画面が表示されるか
		$("body").shouldHave(text("登録日付詳細"));

		// ホームボタンクリックし一覧画面へ戻るか。
		// メッセージが表示されるか。
		$(byText("ホーム")).click();
		$("body").shouldHave(text("計算の基準となる日付を入力してください。"));
	}

}
