package com.KoreaIT.java.BAM.Service;

import com.KoreaIT.java.BAM.Container.Container;
import com.KoreaIT.java.BAM.Dao.MemberDao;
import com.KoreaIT.java.BAM.dto.Member;

public class MemberService {
	MemberDao memberDao;
	
	public MemberService() {
		memberDao = Container.memberDao;
	}
	public void join(Member member) {
		memberDao.add(member);
	}
	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}
	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
}
