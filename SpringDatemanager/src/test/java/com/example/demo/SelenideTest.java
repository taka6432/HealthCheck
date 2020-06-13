package com.example.demo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.Test;

public class SelenideTest {

	@Test
	public void test() {
//	 selenideで行うUIテスト三箇条
//	 1.ページを開く
//	 2.$(find element).アクションを起こす();
//	 3.$(find element).状態をチェックする();
//	 Googleトップページ
//	 "selenide"を検索,$()で要素を取得
		open("https://www.google.co.jp/");// ページを開く
		$("input[type=text]").val("selenide").pressEnter();

		// 検索ページ
		// Selenideの公式ページをクリック
		$(byText("Selenide: concise UI tests in Java")).click();

		// Selenide公式ページ
		// 「What is Selenide?」という文言があることを確認
		$("body").shouldHave(text("Quick start"));

	}
}
