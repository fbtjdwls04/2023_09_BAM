package com.KoreaIT.java.BAM;

import java.util.Scanner;

import com.KoreaIT.java.BAM.Controller.ArticleController;
import com.KoreaIT.java.BAM.Controller.Controller;
import com.KoreaIT.java.BAM.Controller.MemberController;

public class App {
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		articleController.makeTestData();
		memberController.makeTestData();
		Controller controller = null;
		
		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();
			String splitCommand[] = command.split(" ");

			if (command.length() == 0)
				continue;
			else if (command.equals("exit"))
				break;
			else if(splitCommand.length == 1) {
				System.out.println("존재하지 않는 명령어입니다.");
				return;
			}
			else if(command.startsWith("member")) {
				controller = memberController;
			}
			else if (command.startsWith("article")) {
				controller = articleController;
			}else {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}
			controller.doAction(command, splitCommand[1]);
				
		}

		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}
}



