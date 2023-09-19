package com.KoreaIT.java.BAM.dto;

public class Article extends Dto{
	public String title;
	public String body;
	public int hit;

	public Article(int id, String userId, String title, String body, String regDate, String updateDate) {
		this(id, userId, title, body, regDate, updateDate, 0);
	}

	public Article(int id, String userId, String title, String body, String regDate, String updateDate, int hit) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.hit = hit;
	}

	public void articleModify(String title, String body, String updateDate) {
		this.title = title;
		this.body = body;
		this.updateDate = updateDate;
	}

	public void hitUp() {
		this.hit++;
	}
}