package com.KoreaIT.java.BAM.Dao;

import java.util.ArrayList;

import com.KoreaIT.java.BAM.dto.Member;

public class MemberDao extends Dao {
	ArrayList<Member> members;
	
	public MemberDao(){
		members = new ArrayList<>();
	}
	
	public boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if(index == -1) {
			return false;
		}
		return true;
	}

	public int getMemberIndexByLoginId(String loginId) {
		int index = -1;
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).userId.equals(loginId)) {
				return i;
			}
		}
		return index;
	}
	public Member getMemberByLoginId(String loginId) {
		Member member = null;
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).userId.equals(loginId)) {
				member = members.get(i);
				return member;
			}
		}
		return member;
	}

	public void add(Member member) {
		members.add(member);
		lastId = member.id;
	}
}
