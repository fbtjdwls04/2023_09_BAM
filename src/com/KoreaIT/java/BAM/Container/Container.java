package com.KoreaIT.java.BAM.Container;

import com.KoreaIT.java.BAM.Dao.ArticleDao;
import com.KoreaIT.java.BAM.Dao.MemberDao;
import com.KoreaIT.java.BAM.Service.ArticleService;
import com.KoreaIT.java.BAM.Service.MemberService;

public class Container {
	public static MemberDao memberDao;
	public static ArticleDao articleDao;
	public static MemberService memberService;
	public static ArticleService articleService;
	static {
		memberDao = new MemberDao();
		memberService = new MemberService();
		articleDao = new ArticleDao();
		articleService = new ArticleService();
	}
}
