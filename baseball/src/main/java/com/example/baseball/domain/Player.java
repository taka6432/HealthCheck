package com.example.baseball.domain;

public class Player {

//	@Override
//	public String toString() {
//		return "player [id=" + id + ", name=" + name + ", age=" + age + ", team=" + team + ", position=" + position
//				+ "]";
//	}

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

}
