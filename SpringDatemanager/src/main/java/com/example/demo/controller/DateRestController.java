package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.BusinessDate;
import com.example.demo.service.RestService;

@RestController
public class DateRestController {

	@Autowired
	RestService restService;

	// 全件取得
	@GetMapping("/businessdate/get")
	public List<BusinessDate> getAllDate() {
		return restService.getAll();
	}

	// 1件取得
	@GetMapping("/businessdate/get/{id}")
	public BusinessDate getDateOne(@PathVariable("id") int id) {
		return restService.selectOne(id);
	}

}