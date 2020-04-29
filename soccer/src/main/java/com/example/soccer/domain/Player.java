package com.example.soccer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 以下のクラスがEntityであることを宣言→dbのテーブルと紐付ける
public class Player {
	@Id // 以下の変数がテーブルのプライマリーキーになる
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 連番が自動で振られるようになる
	private Long id;
	private String name;
	private Integer age;
	private String team;
	private String position;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override // 以下のメソッドがオーバーライドしている→上書きされていくことを指す→toStringメソッドをPlayerクラスでオーバーライドする
	public String toString() { // returnの記載方法は以下が基本型として理解しておく、独自クラスにtoStringをオーバーライドする、
		return "Player [id=" + id + ", name=" + name + ", age=" + age + ", team=" + team + ", position=" + position
				+ "]";
	}
}
