package com.example.HealthCheck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.HealthCheck.entity.HealthCheck;
import com.example.HealthCheck.service.HealthService;

@Controller
@RequestMapping("/kenko")
public class HealthController {
	@Autowired
	private HealthService healthService;

	@GetMapping

	public String index(@ModelAttribute HealthCheck healthCheck, Model model) {

		return "index";
	}

	// 一覧表示
	@PostMapping("/new")
	public String postIndex(Model model) {
//		List<HealthCheck> health = HealthService.getAll();
//		model.addAttribute("healthList", health);
		return "new";
	}

}
