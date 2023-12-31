package com.KoreaIT.java.BAM.Controller;

import java.util.Scanner;

import com.KoreaIT.java.BAM.Service.MemberService;
import com.KoreaIT.java.BAM.Util.Util;
import com.KoreaIT.java.BAM.dto.Member;

public class MemberController extends Controller{
	String command;
	String methodCommand;
	MemberService memberService; 
	
	Scanner sc;
	public MemberController(Scanner sc){
		this.sc = sc;
		memberService = new MemberService();
	}
	public void makeTestData() {
		memberService.join(new Member(memberService.getNewId(), "admin", "admin", "운영자", Util.getNow()));
		memberService.join(new Member(memberService.getNewId(), "a", "a", "회원1", Util.getNow()));
		memberService.join(new Member(memberService.getNewId(), "b", "b", "회원2", Util.getNow()));
		
		System.out.println("테스트를 위한 계정 3개 생성 완료.");
	}
	public void doAction(String command, String methodCommand) {
		this.command = command;
		this.methodCommand = methodCommand;
		switch(methodCommand) {
		case "join" :
			doJoin();
			break;
		case "login" :
			doLogin();
			break;
		case "logout" :
			doLogout();
			break;
		case "mypage" :
			doMypage();
			break;
		case "secession":
			doSecession();
			break;
		case "list":
			showList();
			break;
		default :
			System.out.println("존재하지 않는 명령어입니다.");
			break;
		}
	}
	/**회원 가입*/
	private void doJoin() {
		String loginId;
		String password;
		String name;
		
		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}

			if (memberService.isJoinableLoginId(loginId)) {
				System.out.println("이미 사용중인 아이디입니다.");
				continue;
			}

			break;
		}

		while (true) {
			System.out.print("비밀번호 : ");
			password = sc.nextLine().trim();
			if (password.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.print("비밀번호확인 : ");
			String passwordTry = sc.nextLine().trim();

			if (password.equals(passwordTry)) {
				break;
			}
			System.out.println("비밀번호가 동일하지 않습니다.");
		}

		while (true) {
			System.out.print("이름 : ");
			name = sc.nextLine().trim();
			if (loginId.length() == 0) {
				System.out.println("이름을 입력해주세요");
				continue;
			}
			break;
		}
		
		int memberId = memberService.getNewId();
		String regDate = Util.getNow();
		Member newMember = new Member(memberId, loginId, password, name, regDate);
		memberService.join(newMember);
		System.out.println(memberId + "번 계정이 생성되었습니다.");
		if (command.equals("member login")) {
			

		}
	}
	/**로그인 기능*/
	private void doLogin() {
		if (isLogined()) {
			System.out.println("이미 로그인 중입니다.");
			return;
		}

		String loginId;
		String password;

		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			password = sc.nextLine().trim();
			if (password.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			break;
		}
		Member temp = memberService.getMemberByLoginId(loginId);
		
		if (temp == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			return;
		}
		
		if (!temp.password.equals(password)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		
		loginMember = temp;
		System.out.println(loginMember.name + "님 환영합니다.");
		
	}
	/**로그아웃 기능*/
	private void doLogout() {
		if (!isLogined()) {
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}
		loginMember = null;
		System.out.println("로그아웃 되었습니다.");
	}
	/**로그인 되어있는 계정 정보창*/
	private void doMypage() {
		if(!isLogined()) {
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}
		System.out.println("-----------------------------");
		System.out.println("회원 번호 : " + loginMember.memberId);
		System.out.println("가입 일 : " + loginMember.regDate);
		System.out.println("아이디 : " + loginMember.loginId);
		System.out.println("이름 : " + loginMember.name);
	}
	/**회원 탈퇴*/
	private void doSecession() {
		if(!isLogined()) {
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}
		if(loginMember.memberId == 1) {
			System.out.println("관리자 계정은 삭제할 수 없습니다.");
			return;
		}
		System.out.println("정말 탈퇴하시겠습니까? (y or n)");
		String confirm = sc.nextLine();
		if(confirm.equals("n")) return;
		else if(confirm.equals("y")) {
			memberService.remove(loginMember);
			loginMember = null;
			System.out.println("회원 탈퇴가 완료되었습니다.");
		}
	}
	/**회원목록 열람 ( 관리자 전용 )*/
	private void showList() {
		if(loginMember == null || loginMember.memberId != 1) {
			System.out.println("접근 불가");
			return;
		}
		for(Member member : memberService.getMembers()) {
			System.out.printf("회원번호 : %d | 아이디 : %s | 비밀번호 : %s | 이름 : %s | 생성일 : %s\n",member.memberId, member.loginId, member.password, member.name, member.regDate);
		}
	}
}

