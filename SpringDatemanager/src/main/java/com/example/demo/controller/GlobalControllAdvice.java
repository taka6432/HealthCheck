package com.example.demo.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalControllAdvice {

	// DB関連のエラー発生時のエラーメッセージをmodelへ登録、error.htmlを返却
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {

		// 各キーはerror.html内の変数式に対応。
		model.addAttribute("error", "内部サーバーエラー（DB）: GlobalControllAdvice");
		model.addAttribute("message", "DataAccessExceptionが発生しました。");
		// status code:500
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

	// その他のエラー発生時のエラーメッセージをmodelへ登録、error.htmlを返却
	@ExceptionHandler(Exception.class)
	public String dataAccessExceptionHandler(Exception e, Model model) {

		// 各キーはerror.html内の変数式に対応。
		model.addAttribute("error", "内部サーバーエラー（DB以外）: GlobalControllAdvice");
		model.addAttribute("message", "Exceptionが発生しました。");
		// status code:500
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

}