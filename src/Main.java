import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);
		int lastArticleId = 0;
		int lastUserId = 0;
		ArticleDao articleDao = new ArticleDao();
		MemberDao memberDao = new MemberDao();
		ArrayList<Article> articles = articleDao.articles;
		ArrayList<Member> members = memberDao.members;
		
		Member member = null;
		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();
			String splitCommand[] = command.split(" ");

			if (command.length() == 0)
				continue;
			if (command.equals("exit"))
				break;
			if (command.equals("log in")) {
				if(member != null) {
					System.out.println("이미 로그인 중입니다.");
					continue;
				}
				
				String userId;
				String password;
				System.out.print("아이디 : ");
				userId = sc.nextLine().trim();
				System.out.print("비밀번호 : ");
				password = sc.nextLine().trim();
				Member temp = null;
				for(int i = 0; i < members.size(); i++) {
					if(members.get(i).userId.equals(userId)) {
						temp = members.get(i);
					}
				}
				if(temp == null) {
					System.out.println("존재하지 않는 아이디입니다.");
					continue;
				}
				if(!temp.password.equals(password)) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					continue;
				}
				member = temp;
				System.out.println(member.userId + "님 환영합니다.");
				
			}else if(command.equals("sign up")) {
				boolean check = true;
				String userId;
				String password;
				System.out.print("아이디 : ");
				userId = sc.nextLine().trim();
				for(int i = 0; i < members.size(); i++) {
					if(members.get(i).userId.equals(userId)) {
						System.out.println("이미 사용중인 아이디입니다.");
						check = false;
						break;
					}
				}
				if(!check) {
					continue;
				}
				System.out.print("비밀번호 : ");
				password = sc.nextLine().trim();
				while(true) {
					System.out.print("비밀번호확인 : ");
					String passwordTry = sc.nextLine().trim();
					
					if(password.equals(passwordTry)) {
						break;
					}
					System.out.println("비밀번호가 동일하지 않습니다.");
				}
				int id = lastUserId + 1;
				String regDate = Util.getNow();
				Member newMember = new Member(id,userId,password,regDate);
				members.add(newMember);
				lastUserId++;
				System.out.println("계정이 생성되었습니다.");
				
			}else if(command.equals("log out")) {
				if(member == null) {
					System.out.println("로그인 상태가 아닙니다.");
					continue;
				}
				member = null;
				System.out.println("로그아웃 되었습니다.");
				
			}else if (command.equals("article write")) { // 게시물 작성
				if(member == null) {
					System.out.println("로그인 후 사용이 가능합니다.");
					continue;
				}
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
				String regDate = Util.getNow();
				Article newArticle = new Article(id, member.userId, title, body, regDate);

				articles.add(newArticle);

				System.out.println(id + "번 글이 생성되었습니다.");
				System.out.println(regDate);
				lastArticleId++;

			} else if (command.equals("article list")) { // 게시물 목록
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
				}

				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.println("-----------------------------");
					System.out.println("번호 : " + article.id);
					System.out.println("글쓴이 : " + article.userId);
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
						System.out.println("글쓴이 : " + article.userId);
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
				if(member == null) {
					System.out.println("로그인 후 사용이 가능합니다.");
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				boolean findIdCheck = false;
				for (int i = 0; i < articles.size(); i++) {
					if (articles.get(i).id == id) {
						findIdCheck = true;
						if(!articles.get(i).userId.equals(member.userId)) {
							System.out.println("권한이 없습니다.");
							break;
						}
						articles.remove(i);
						System.out.println(id + "번 게시물을 삭제하였습니다.");
						break;
					}
				}
				if (!findIdCheck) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
				}
			}else if (command.startsWith("article modify")) {	// 게시물 수정
				if(!numberCheck(splitCommand)) {
					continue;
				}
				if(member == null) {
					System.out.println("로그인 후 사용이 가능합니다.");
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				boolean findIdCheck = false;
				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.id == id) {
						findIdCheck = true;
						if(!article.userId.equals(member.userId)) {
							System.out.println("권한이 없습니다.");
							break;
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
						article.articleModify(title, body);
						System.out.println(id + "번 글이 수정되었습니다.");
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
	
	static boolean numberCheck(String splitCommand[]) {
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
}

class ArticleDao{
	public ArrayList<Article> articles;
	
	public ArticleDao() {
		articles = new ArrayList<>();
	}
	
	public int findIndexById(int id) {
		int index = -1;
		for(int i = 0; i < articles.size(); i++) {
			if(articles.get(i).id == id) {
				index = i;
				break;
			}
		}
		
		return index;
	}
}

class Article {
	int id;
	String userId;
	String title;
	String body;
	String regDate;
	int hit;

	Article(int id, String userId,String title, String body, String regDate) {
		this.id = id;
		this.userId = userId;
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

class Member {
	int id;
	String userId;
	String password;
	String regDate;
	
	Member(int id, String userId, String password, String regDate){
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.regDate = regDate;
	}
}

class MemberDao {
	ArrayList<Member> members;
	MemberDao(){
		members = new ArrayList<>();
	}
}
