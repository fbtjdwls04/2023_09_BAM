import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static ArticleDao articleDao = new ArticleDao();
	static MemberDao memberDao = new MemberDao();
	static int lastArticleId = 0;
	static int lastUserId = 0;
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);
		ArrayList<Article> articles = articleDao.articles;
		ArrayList<Member> members = memberDao.members;
		makeTestData(4);
		
		Member loginMember = null;
		while (true) {
			System.out.print(loginMember == null ? "명령어 ) " : loginMember.userId + " ) 명령어 ) ");
			String command = sc.nextLine().trim();
			String splitCommand[] = command.split(" ");

			if (command.length() == 0)
				continue;
			if (command.equals("exit"))
				break;
			// 로그인
			if (command.equals("login")) {
				if (loginMember != null) {
					System.out.println("이미 로그인 중입니다.");
					continue;
				}

				String userId;
				String password;
				
				while(true) {
					System.out.print("아이디 : ");
					userId = sc.nextLine().trim();
					if(userId.length() == 0) {
						System.out.println("아이디를 입력해주세요.");
						continue;
					}
					break;
				}
				while(true) {
					System.out.print("비밀번호 : ");
					password = sc.nextLine().trim();
					if(password.length() == 0) {
						System.out.println("비밀번호를 입력해주세요.");
						continue;
					}
					break;
				}
				Member temp = null;
				for (int i = 0; i < members.size(); i++) {
					if (members.get(i).userId.equals(userId)) {
						temp = members.get(i);
						break;
					}
				}
				if (temp == null) {
					System.out.println("존재하지 않는 아이디입니다.");
					continue;
				}
				if (!temp.password.equals(password)) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					continue;
				}
				loginMember = temp;
				System.out.println(loginMember.userId + "님 환영합니다.");

			}
			// 회원 가입
			else if (command.equals("join")) {
				
				String userId;
				String password;
				String name;
				
				while(true) {
					System.out.print("아이디 : ");
					userId = sc.nextLine().trim();
					if(userId.length() == 0) {
						System.out.println("아이디를 입력해주세요");
						continue;
					}
					
					if (memberDao.isJoinableUserId(userId)) {
						System.out.println("이미 사용중인 아이디입니다.");
						continue;
					}
					
					break;
				}
				
				while(true) {
					System.out.print("비밀번호 : ");
					password = sc.nextLine().trim();
					if(password.length() == 0) {
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
				
				while(true) {
					System.out.print("이름 : ");
					name = sc.nextLine().trim();
					if(userId.length() == 0) {
						System.out.println("이름을 입력해주세요");
						continue;
					}
					break;
				}
				int id = lastUserId + 1;
				String regDate = Util.getNow();
				Member newMember = new Member(id, userId, password, name,regDate);
				members.add(newMember);
				lastUserId++;
				System.out.println(id+"번 계정이 생성되었습니다.");

			}
			// 로그 아웃
			else if (command.equals("logout")) {
				if (loginMember == null) {
					System.out.println("로그인 상태가 아닙니다.");
					continue;
				}
				loginMember = null;
				System.out.println("로그아웃 되었습니다.");

			} 
			// 게시물 작성
			else if (command.equals("article write")) { 
				if (loginMember == null) {
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
				Article newArticle = new Article(id, loginMember.userId, title, body, regDate, regDate);

				articles.add(newArticle);

				System.out.println(id + "번 글이 생성되었습니다.");
				System.out.println(regDate);
				lastArticleId++;

			}
			// 게시물 목록
			else if (command.startsWith("article list")) { 
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
				}
				String searchKeyword = command.substring("article list".length()).trim();
				System.out.println("검색 결과 : " + searchKeyword);
				
				ArrayList<Article> filterArticles = articles;
				
				if(searchKeyword.length() > 0) {
					filterArticles = new ArrayList<>();
					for(Article article : articles) {
						if(article.title.contains(searchKeyword)) {
							filterArticles.add(article);
						}
					}
				}
				
				for(int i = filterArticles.size()-1; i >= 0; i--) {
					printArticle(filterArticles.get(i));
				}
			}
			// 게시물 개별 확인
			else if (command.startsWith("article detail")) { 
				if (!numberCheck(splitCommand)) {
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				Article article = articleDao.getArticleById(id);
				if (article == null) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
					continue;
				}
				
				article.hitUp();
				printArticle(article);

			}
			// 게시물 삭제
			else if (command.startsWith("article delete")) { 
				if (!numberCheck(splitCommand)) {
					continue;
				}
				if (loginMember == null) {
					System.out.println("로그인 후 사용이 가능합니다.");
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				Article article = articleDao.getArticleById(id);
				if (article == null) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
					continue;
				}
				if (!article.userId.equals(loginMember.userId)) {
					System.out.println("권한이 없습니다.");
					continue;
				}
				articles.remove(article);
				System.out.println(id + "번 게시물을 삭제하였습니다.");

			}
			// 게시물 수정
			else if (command.startsWith("article modify")) { 
				if (!numberCheck(splitCommand)) {
					continue;
				}
				if (loginMember == null) {
					System.out.println("로그인 후 사용이 가능합니다.");
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				Article article = articleDao.getArticleById(id);
				if (article == null) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
					continue;
				}
				if (!article.userId.equals(loginMember.userId)) {
					System.out.println("권한이 없습니다.");
					continue;
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
				
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("== 프로그램 끝 ==");

		sc.close();

	}
	// 명령어 뒤에 오는 문자열이 숫자인지 아닌지 판별 또는 무입력 확인
	private static boolean numberCheck(String splitCommand[]) {
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
	// 테스트 데이터 생성
	private static void makeTestData(int cnt) {
		for(int i = 1; i <= cnt; i++) {
			articleDao.add(new Article(++lastArticleId, "테스트", "안녕"+i,"반갑다",Util.getNow(),Util.getNow(),10));
		}
		System.out.printf("테스트를 위한 데이터 %d개 생성 완료.\n",cnt);
	}
	private static void printArticle(Article article) {
		System.out.println("-----------------------------");
		System.out.println("번호 : " + article.id);
		System.out.println("글쓴이 : " + article.userId);
		System.out.println("날짜 : " + article.regDate);
		System.out.println("수정된 날짜 : " + article.updateDate);
		System.out.println("제목 : " + article.title);
		System.out.println("내용 : " + article.body);
		System.out.println("조회수 : " + article.hit);
	}
}

class ArticleDao {
	public ArrayList<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}
	
	public int getArticleIndexById(int id) {
		int index = -1;
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).id == id) {
				index = i;
				break;
			}
		}
		return index;
	}

	public Article getArticleById(int id) {
		int index = getArticleIndexById(id);
		if (index != -1) {
			return articles.get(index);
		}
		
		return null;
	}
	
	public void add(Article article) {
		articles.add(article);
	}
	public void remove(Article article) {
		articles.remove(article);
	}
}

class Article {
	int id;
	String userId;
	String title;
	String body;
	String regDate;
	String updateDate;
	int hit;
	Article(int id, String userId, String title, String body, String regDate, String updateDate) {
		this(id, userId,title,body,regDate,updateDate,0);
	}
	Article(int id, String userId, String title, String body, String regDate, String updateDate, int hit) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.hit = hit;
	}

	void articleModify(String title, String body, String updateDate) {
		this.title = title;
		this.body = body;
		this.updateDate = updateDate;
	}

	void hitUp() {
		this.hit++;
	}
}

class MemberDao {
	ArrayList<Member> members;

	MemberDao() {
		members = new ArrayList<>();
	}

	public boolean isJoinableUserId(String userId) {
		for(Member member : members) {
			if(member.userId.equals(userId)) {
				return true;
			}
		}
		return false;
	}
}

class Member {
	int id;
	String userId;
	String password;
	String regDate;
	String name;

	Member(int id, String userId, String password,String name,String regDate) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.regDate = regDate;
		this.name = name;
	}
	
}
