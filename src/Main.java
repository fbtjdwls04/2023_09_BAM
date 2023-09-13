import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");

		Date date = new Date();
		SimpleDateFormat nDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		Scanner sc = new Scanner(System.in);
		int lastArticleId = 0;
		ArrayList<Article> articles = new ArrayList<>();
		
		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();
			String splitCommand[] = command.split(" ");

			if (command.length() == 0)
				continue;
			if (command.equals("exit"))
				break;
			if (command.equals("article write")) { // 게시물 작성
				int id = lastArticleId + 1;
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
				Article newArticle = new Article(id, title, body, nDate.format(date));

				articles.add(newArticle);

				System.out.println(id + "번 글이 생성되었습니다.");
				System.out.println(nDate.format(date));
				lastArticleId++;

			} else if (command.equals("article list")) { // 게시물 목록
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
				}

				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.println("-----------------------------");
					System.out.println("번호 : " + article.id);
					System.out.println("날짜 : " + article.regDate);
					System.out.println("제목 : " + article.title);
					System.out.println("내용 : " + article.body);
					System.out.println("조회수 : " + article.hit);
				}
			} else if (command.startsWith("article detail")) {	// 게시물 개별 확인
				if(!numberCheck(splitCommand)) {
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				boolean findIdCheck = false;
				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						article.hitUp();
						System.out.println("-----------------------------");
						System.out.println("번호 : " + article.id);
						System.out.println("날짜 : " + article.regDate);
						System.out.println("제목 : " + article.title);
						System.out.println("내용 : " + article.body);
						System.out.println("조회수 : " + article.hit);
						findIdCheck = true;
						break;
					}
				}
				if (!findIdCheck) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
				}

			} else if (command.startsWith("article delete")) {	// 게시물 삭제
				if(!numberCheck(splitCommand)) {
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				boolean findIdCheck = false;
				for (int i = 0; i < articles.size(); i++) {
					if (articles.get(i).id == id) {
						articles.remove(i);
						System.out.println(id + "번 게시물을 삭제하였습니다.");
						findIdCheck = true;
						break;
					}
				}
				if (!findIdCheck) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
				}
			}else if (command.startsWith("article modify")) {	// 게시물 삭제
				if(!numberCheck(splitCommand)) {
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				boolean findIdCheck = false;
				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
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
						article.articleModify(title, body);
						System.out.println(id + "번 글이 수정되었습니다.");
						findIdCheck = true;
						break;
					}
				}
				if (!findIdCheck) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
				}
			}
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}
	static boolean numberCheck(String str[]) {
		if (str.length < 3) {
			System.out.println("게시물 번호를 입력해주세요.");
			return false;
		} else {
			try {
				Integer.parseInt(str[2]);
			} catch (NumberFormatException ex) {
				System.out.println("숫자를 입력해주세요.");
				return false;
			}
		}
		return true;
	}
}

class Article {
	int id;
	String title;
	String body;
	String regDate;
	int hit;

	Article(int id, String title, String body, String regDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.hit = 0;
	}
	void articleModify(String title, String body) {
		this.title = title;
		this.body = body;
	}
	void hitUp() {
		this.hit++;
	}
}