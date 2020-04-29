package com.example.soccer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.soccer.domain.Player;
import com.example.soccer.service.PlayerService;

@Controller
@RequestMapping("/soccer") // 以下のクラス内のメソッドに全てがマッピング対象となる、以下にcrudの処理内容をそれぞれ記載する
public class PlayerController {
	@Autowired
	private PlayerService playerService; // playerServiceを紐付けて使用できるようになる

	@GetMapping
	public String index(Model model) { // 引数にModel型の値を設定することで、Modelのインスタンスが自動的に渡される
		List<Soccer> soccer = playerService.findAll();
		model.addAttribute("soccer", players); // modelに値を入れることで、テンプレートに値を渡すことができる
		return "soccer/index"; // ④
	}

	@GetMapping("new")
	public String newPlayer(Model model) {
		return "soccer/new";
	}

	@GetMapping("{id}/edit")
	public String edit(@PathVariable Long id, Model model) { // このアノテーションを付けるとURL上の値を取得することができる→localhost/players/1にアクセスされるとidに１が入るイメージ
		Player player = playerService.findOne(id);
		model.addAttribute("player", player);
		return "soccer/edit";
	}

	@GetMapping("{id}")
	public String show(@PathVariable Long id, Model model) {
		Player player = playerService.findOne(id);
		model.addAttribute("player", player);
		return "soccer/show";
	}

	@PostMapping
	public String create(@ModelAttribute Player player) { // このアノテーションを付けると送信されたリクエストのbody情報を取得できる
		playerService.save(player);
		return "redirect:/soccer"; // playersにリダイレクトされる→処理が終わった後自動的にplaersにアクセスされるイメージ
	}

	@PutMapping("{id}")
	public String update(@PathVariable Long id, @ModelAttribute Player player) {
		player.setId(id);
		playerService.save(player);
		return "redirect:/soccer";
	}

	@DeleteMapping("{id}")
	public String destroy(@PathVariable Long id) {
		playerService.delete(id);
		return "redirect:/soccer";
	}
}

<dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><scope>runtime</scope></dependency>

<dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-data-jpa</artifactId></dependency>

<dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><scope>runtime</scope></dependency>