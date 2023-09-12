import java.util.ArrayList;
import java.util.Scanner;

public class Main{
	static long cnt = 0;
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		int lastArticleId = 0;
		ArrayList<Article> articles = new ArrayList<>();
		
		while(true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();

			if(command.length() == 0) continue;
			if(command.equals("exit")) break;
			
			if(command.equals("article write")) {			// 게시물 작성
				int id = lastArticleId + 1;
				String title;
				String body;
				while(true) {
					System.out.print("제목 ) ");
					title = sc.nextLine().trim();
					if(title.length() == 0) {
						System.out.println("제목을 입력해주세요.");
						continue;
					}
					break;
				}
				while(true) {
					System.out.print("내용 ) ");
					body = sc.nextLine().trim();
					if(body.length() == 0) {
						System.out.println("내용을 입력해주세요.");
						continue;
					}
					break;
				}
				Article newArticle = new Article(id, title, body);
				
				articles.add(newArticle);
				
				System.out.println(id +"번 글이 생성되었습니다.");
				
				lastArticleId++;
				
			}else if(command.equals("article list")) {		// 게시물 목록
				if(articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
				}
				
				for(int i = articles.size()-1; i >= 0; i--) {
					System.out.println("-----------------------------");
					System.out.println(articles.get(i).id + "번 글 -");
					System.out.println("제목: " + articles.get(i).title);
					System.out.println("내용: " + articles.get(i).body);
				}
			}else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}
		
		System.out.println("== 프로그램 끝 ==");
		
		sc.close();
	}
}
class Article {
	int id;
	String title;
	String body;
	
	Article(int id, String title, String body){
		this.id = id;
		this.title = title;
		this.body = body;
	}
}