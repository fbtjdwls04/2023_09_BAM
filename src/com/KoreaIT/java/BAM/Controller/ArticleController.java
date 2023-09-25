package com.KoreaIT.java.BAM.Controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.KoreaIT.java.BAM.Service.ArticleService;
import com.KoreaIT.java.BAM.Util.Util;
import com.KoreaIT.java.BAM.dto.Article;

public class ArticleController extends Controller {
	Scanner sc;
	ArticleService articleService;
	String command;
	String methodCommand;
	String splitCommand[];
	public ArticleController(Scanner sc) {
		this.sc = sc;
		articleService = new ArticleService();
	}

	public void doAction(String command, String methodCommand) {
		this.command = command;
		this.methodCommand = methodCommand;
		this.splitCommand = command.split(" ");
		switch (methodCommand) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "delete" :
			doDelete();
			break;
		case "modify" :
			doModify();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다.");
			break;
		}
	}
	private void printArticle(Article article) {
		System.out.println("-----------------------------");
		System.out.println("번호 : " + article.id);
		System.out.println("날짜 : " + article.regDate);
		System.out.println("수정된 날짜 : " + article.updateDate);
		System.out.println("글쓴이 : " + article.name);
		System.out.println("제목 : " + article.title);
		System.out.println("내용 : " + article.body);
		System.out.println("조회수 : " + article.hit);
	}
	
	public void makeTestData() {
		articleService.add(new Article(articleService.getNewId(),1, "admin" , "제목1", "저쩔", Util.getNow(), Util.getNow(), 15));
		articleService.add(new Article(articleService.getNewId(),2, "a" , "제목2", "어쩔", Util.getNow(), Util.getNow(), 12));
		articleService.add(new Article(articleService.getNewId(),3, "b" , "제목3", "ㅎㅇㅎㅇ", Util.getNow(), Util.getNow(), 4));

		System.out.println("테스트를 위한 데이터 3개 생성 완료.");
	}
	
	private boolean numberCheck(String splitCommand[]) {
		if (splitCommand.length < 3) {
			System.out.println("게시물 번호를 입력해주세요.");
			return false;
		} else {
			try {
				Integer.parseInt(splitCommand[2]);
			} catch (NumberFormatException ex) {
				System.out.println("숫자를 입력해주세요.");
				return false;
			}
		}
		return true;
	}

	private void doWrite() {
		if (!isLogined()) {
			System.out.println("로그인 후 사용이 가능합니다.");
			return;
		}
		int id = articleService.getNewId();
		String title;
		String body;
		while (true) {
			System.out.print("제목 ) ");
			title = sc.nextLine().trim();
			if (title.length() == 0) {
				System.out.println("제목을 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("내용 ) ");
			body = sc.nextLine().trim();
			if (body.length() == 0) {
				System.out.println("내용을 입력해주세요.");
				continue;
			}
			break;
		}
		String regDate = Util.getNow();
		Article newArticle = new Article(id, loginMember.memberId,loginMember.name, title, body, regDate, regDate);

		articleService.add(newArticle);

		System.out.println(id + "번 글이 생성되었습니다.");
		System.out.println(regDate);
	}

	private void showList() {
		if (command.startsWith("article list"))	{
			if (articleService.size() == 0) {
				System.out.println("게시글이 없습니다.");
			}
		}

		String searchKeyword = command.substring("article list".length()).trim();
		System.out.println("검색어 : " + searchKeyword);

		ArrayList<Article> filterArticles = articleService.getArticles();

		if (searchKeyword.length() > 0) {

			filterArticles = new ArrayList<>();

			for (Article article : articleService.getArticles()) {
				if (article.title.contains(searchKeyword)) {
					filterArticles.add(article);
				}
			}
		}
		if (filterArticles.size() == 0) {
			System.out.println("검색 결과가 없습니다.");
		}

		for (int i = filterArticles.size() - 1; i >= 0; i--) {
			printArticle(filterArticles.get(i));
		}

	}

	private void showDetail() {
		if (!numberCheck(splitCommand)) {
			return;
		}

		int id = Integer.parseInt(splitCommand[2]);
		Article article = articleService.getArticleById(id);
		if (article == null) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}

		article.hitUp();

		printArticle(article);
	}
	private void doDelete() {
		if (!numberCheck(splitCommand)) {
			return;
		}
		if (!isLogined()) {
			System.out.println("로그인 후 사용이 가능합니다.");
			return;
		}
		
		int id = Integer.parseInt(splitCommand[2]);
		Article article = articleService.getArticleById(id);
		if (article == null) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}
		if (article.memberId != loginMember.memberId) {
			System.out.println("권한이 없습니다.");
			return;
		}
		articleService.remove(article);
		System.out.println(id + "번 게시물을 삭제하였습니다.");
	}
	private void doModify() {
		if (!numberCheck(splitCommand)) {
			return;
		}
		if (!isLogined()) {
			System.out.println("로그인 후 사용이 가능합니다.");
			return;
		}

		int id = Integer.parseInt(splitCommand[2]);
		Article article = articleService.getArticleById(id);
		if (article == null) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}
		if (article.memberId != loginMember.memberId) {
			System.out.println("권한이 없습니다.");
			return;
		}

		String title;
		String body;
		while (true) {
			System.out.print("제목 ) ");
			title = sc.nextLine().trim();
			if (title.length() == 0) {
				System.out.println("제목을 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("내용 ) ");
			body = sc.nextLine().trim();
			if (body.length() == 0) {
				System.out.println("내용을 입력해주세요.");
				continue;
			}
			break;
		}
		String updateDate = Util.getNow();
		article.articleModify(title, body, updateDate);
		System.out.println(id + "번 글이 수정되었습니다.");
	}
}