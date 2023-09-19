package com.KoreaIT.java.BAM.dto;

public class Member extends Dto{
	public String password;
	public String name;

	public Member(int id, String userId, String password, String name, String regDate) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.regDate = regDate;
		this.name = name;
	}

}
