package com.KoreaIT.java.BAM.Controller;

import com.KoreaIT.java.BAM.dto.Member;

public abstract class Controller {
	public static Member loginMember;
	
	public abstract void doAction(String command, String string);
	public abstract void makeTestData();
	public static boolean isLogined() {
		return loginMember != null;
	}
}
