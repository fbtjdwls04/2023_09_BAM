package com.KoreaIT.java.BAM.dto;

public class Member extends Dto{
	public String password;
	public String loginId;
	public Member(int memberId, String loginId, String password, String name, String regDate) {
		this.memberId = memberId;
		this.loginId = loginId;
		this.password = password;
		this.regDate = regDate;
		this.name = name;
	}

}
