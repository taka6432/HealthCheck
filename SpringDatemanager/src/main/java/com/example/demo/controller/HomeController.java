package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.BusinessDate;
import com.example.demo.form.DateForm;
import com.example.demo.service.BusinessDateService;

/**
 * 
 */

@RequestMapping("/businessdate")
@Controller
public class HomeController {
	/**
	 * コントローラー
	 *
	 */
	@Autowired // ??
	private BusinessDateService businessDateService;

	// 初回画面。
	@GetMapping() // @ModelAttribute→指定したクラスにリクエストパラメータ(クライアントから送られてきたデータ)をバインド(割り当てる)する。リクエストの度に@RequestMappingの前に呼ばれる。バインドされたクラスは自動的にModelに追加される
	public String getHome(@ModelAttribute DateForm dateForm, Model model) {
		System.out.println(businessDateService.getCount());

		return "index_boot";
	}

	// 一覧から「戻る」で初回画面に戻る。
	@PostMapping(params = "back") // params→Formで付与した属性
	public String postBack(@ModelAttribute DateForm dateForm, Model model) {
		return "index_boot";
	}

	// １件登録
	@PostMapping(params = "insert") // params→Formで付与した属性
	public String postInsert(@ModelAttribute @Validated DateForm dateForm, BindingResult bindingResult, Model model) {

		// バリデーションで間違いがあれば、getHomeメソッド呼び出し。
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult);
			return getHome(dateForm, model);
		}

		// Entityインスタンス作成。Formクラスの値をEntityクラスへ詰め込み。
		BusinessDate businessDate = new BusinessDate();

		businessDate.setBase_date(dateForm.getBase_date());
		businessDate.setDate_name(dateForm.getDate_name());
		businessDate.setDiff_year(dateForm.getDiff_year());
		businessDate.setDiff_month(dateForm.getDiff_month());
		businessDate.setDiff_day(dateForm.getDiff_day());

		businessDateService.insertDate(businessDate);

		// System.out.println(businessDate);
		return "redirect:businessdate";
	}

	// 一覧表示
	@PostMapping(value = "/datelist", params = "list") // params→Formで付与したname属性
	public String postIndex(Model model) {
		List<BusinessDate> dateList = businessDateService.getAll();
		model.addAttribute("dateList", dateList);
		return "list_boot";
	}

	@GetMapping("/datelist")
	public String getIndex(Model model) {
		List<BusinessDate> dateList = businessDateService.getAll();
		model.addAttribute("dateList", dateList);
		return "list_boot";
	}

	// 計算シミュレーションメソッド。「計算実行」ボタンで実行される。
	@PostMapping(params = "calc") // params=name属性とすることで細かくマッピングできる
	public String postSimlate(@ModelAttribute @Validated DateForm dateForm, BindingResult bindingResult, Model model) {
		// バリデーションで間違いがあれば、getHomeメソッド呼び出し。
		if (bindingResult.hasErrors()) {
			System.out.println(dateForm);
			return getHome(dateForm, model);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		// Formクラスにデータ設定
		if (dateForm.getDiff_year() == 1) {
			dateForm.setDate_name("来年");
			LocalDate calcLocalDate = LocalDate.parse(dateForm.getBase_date(), DateTimeFormatter.ofPattern("yyyyMMdd"))
					.plusYears(1);
			dateForm.setCalc_date(calcLocalDate.format(formatter));
		} else if (dateForm.getDiff_month() == 1) {
			dateForm.setDate_name("来月");
			LocalDate calcLocalDate = LocalDate.parse(dateForm.getBase_date(), DateTimeFormatter.ofPattern("yyyyMMdd"))
					.plusMonths(1);
			dateForm.setCalc_date(calcLocalDate.format(formatter));
		} else {
			dateForm.setDate_name("月末");
			LocalDate calcLocalDate = LocalDate.parse(dateForm.getBase_date(), DateTimeFormatter.ofPattern("yyyyMMdd"))
					.with(TemporalAdjusters.lastDayOfMonth());
			dateForm.setCalc_date(calcLocalDate.format(formatter));
		}

		// idとbase_date
		dateForm.setId(businessDateService.getCount() + 1);

		model.addAttribute("dateForm", dateForm);
		System.out.println(dateForm);
		return "index_boot";
	}

	// 詳細画面のGET用メソッド
	// @PathVariableでURLの指定箇所からパラメータを取得する。
	@GetMapping("/dateDetail/{id}")
	public String getDateDetail(@ModelAttribute DateForm dateForm, Model model, @PathVariable("id") int id) {

		BusinessDate businessDate = businessDateService.selectOne(id);
		System.out.println(businessDate);
		dateForm.setId(businessDate.getId());
		dateForm.setBase_date(businessDate.getBase_date());
		dateForm.setDate_name(businessDate.getDate_name());
		dateForm.setDiff_year(businessDate.getDiff_year());
		dateForm.setDiff_month(businessDate.getDiff_month());
		dateForm.setDiff_day(businessDate.getDiff_day());
		model.addAttribute("dateForm", dateForm);

		return "dateDetail_boot";
	}

	// １件編集用のPOSTメソッド
	@PostMapping(value = "/datelist", params = "update")
	public String postDateDetailUpdate(@ModelAttribute @Validated DateForm dateForm, Model model) {
		// Entityインスタンス作成。Formクラスの値をEntityクラスへ詰め込み。
		BusinessDate businessDate = new BusinessDate();
		businessDate.setId(dateForm.getId());
		businessDate.setDate_name(dateForm.getDate_name());
		businessDate.setBase_date(dateForm.getBase_date());
		businessDate.setDiff_year(dateForm.getDiff_year());
		businessDate.setDiff_month(dateForm.getDiff_month());
		businessDate.setDiff_day(dateForm.getDiff_day());
		businessDateService.updateOne(businessDate);

		// DBから1件編集後のリストを取り出し。
		List<BusinessDate> dateList = businessDateService.getAll();
		model.addAttribute("dateList", dateList);
		model.addAttribute("message", ("日付ID:" + businessDate.getId()) + "を編集しました。");
		return "list_boot";
	}

	// 削除用のPOST用メソッド
	@PostMapping(value = "/datelist", params = "delete")
	public String postDeleteOne(DateForm dateForm, Model model) {
		System.out.println("delete!");
		// DBから1件削除
		businessDateService.deleteOne(dateForm.getId());

		// DBから一覧表示用のリスト取り出し。
		List<BusinessDate> dateList = businessDateService.getAll();

		model.addAttribute("dateList", dateList);
		model.addAttribute("message", "1件削除しました。");
		return "list_boot";
	}

}