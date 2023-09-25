package com.KoreaIT.java.BAM.Service;

import java.util.ArrayList;

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
	public int getNewId() {
		return memberDao.getNewId();
	}
	public void remove(Member loginMember) {
		memberDao.remove(loginMember);
	}
	public int size() {
		return memberDao.size();
	}
	public ArrayList<Member> getMembers() {
		return memberDao.getMembers();
	}
}
