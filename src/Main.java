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
		
		Member member = null;
		while (true) {
			System.out.print(member == null ? "명령어 ) " : member.userId + " ) 명령어 ) ");
			String command = sc.nextLine().trim();
			String splitCommand[] = command.split(" ");

			if (command.length() == 0)
				continue;
			if (command.equals("exit"))
				break;
			if (command.equals("login")) {
				if (member != null) {
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
				member = temp;
				System.out.println(member.userId + "님 환영합니다.");

			} else if (command.equals("join")) {
				String userId;
				String password;
				while(true) {
					boolean check = true;
					while(true) {
						System.out.print("아이디 : ");
						userId = sc.nextLine().trim();
						if(userId.length() == 0) {
							System.out.println("아이디를 입력해주세요");
							continue;
						}
						break;
					}
					
					for (int i = 0; i < members.size(); i++) {
						if (members.get(i).userId.equals(userId)) {
							System.out.println("이미 사용중인 아이디입니다.");
							check = false;
							break;
						}
					}
					if (check) {
						break;
					}
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
				int id = lastUserId + 1;
				String regDate = Util.getNow();
				Member newMember = new Member(id, userId, password, regDate);
				members.add(newMember);
				lastUserId++;
				System.out.println("계정이 생성되었습니다.");

			} else if (command.equals("logout")) {
				if (member == null) {
					System.out.println("로그인 상태가 아닙니다.");
					continue;
				}
				member = null;
				System.out.println("로그아웃 되었습니다.");

			} else if (command.equals("article write")) { // 게시물 작성
				if (member == null) {
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
				Article newArticle = new Article(id, member.userId, title, body, regDate, regDate);

				articles.add(newArticle);

				System.out.println(id + "번 글이 생성되었습니다.");
				System.out.println(regDate);
				lastArticleId++;

			} else if (command.startsWith("article list")) { // 게시물 목록
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
				}
				// 검색어가 없을 시
				if (splitCommand.length == 2) {
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						articleDao.printArticle(article);
					}
				
				}
				// 검색어가 있을 시
				else {
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						if(article.title.contains(splitCommand[2])) {
							articleDao.printArticle(article);
						}
					}
				}

			} else if (command.startsWith("article detail")) { // 게시물 개별 확인
				if (!numberCheck(splitCommand)) {
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				int findIndex = articleDao.findArticleIndexById(id);
				if (findIndex == -1) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
					continue;
				}
				Article article = articles.get(findIndex);
				article.hitUp();
				articleDao.printArticle(article);

			} else if (command.startsWith("article delete")) { // 게시물 삭제
				if (!numberCheck(splitCommand)) {
					continue;
				}
				if (member == null) {
					System.out.println("로그인 후 사용이 가능합니다.");
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				int findIndex = articleDao.findArticleIndexById(id);
				if (findIndex == -1) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
					continue;
				}
				if (!articles.get(findIndex).userId.equals(member.userId)) {
					System.out.println("권한이 없습니다.");
					continue;
				}
				articles.remove(findIndex);
				System.out.println(id + "번 게시물을 삭제하였습니다.");

			} else if (command.startsWith("article modify")) { // 게시물 수정
				if (!numberCheck(splitCommand)) {
					continue;
				}
				if (member == null) {
					System.out.println("로그인 후 사용이 가능합니다.");
					continue;
				}

				int id = Integer.parseInt(splitCommand[2]);
				int findIndex = articleDao.findArticleIndexById(id);
				if (findIndex == -1) {
					System.out.println(id + "번 글은 존재하지 않습니다.");
					continue;
				}
				Article article = articles.get(findIndex);
				if (!article.userId.equals(member.userId)) {
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
	
	private static void makeTestData(int cnt) {
		for(int i = 0; i < cnt; i++) {
			articleDao.add(new Article(++lastArticleId, "테스트", "안녕","반갑다",Util.getNow(),Util.getNow(),10));
		}
		System.out.printf("테스트를 위한 데이터 %d개 생성 완료.\n",cnt);
	}
}

class ArticleDao {
	public ArrayList<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public int findArticleIndexById(int id) {
		int index = -1;
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).id == id) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void printArticle(Article article) {
		System.out.println("-----------------------------");
		System.out.println("번호 : " + article.id);
		System.out.println("글쓴이 : " + article.userId);
		System.out.println("날짜 : " + article.regDate);
		System.out.println("수정된 날짜 : " + article.updateDate);
		System.out.println("제목 : " + article.title);
		System.out.println("내용 : " + article.body);
		System.out.println("조회수 : " + article.hit);
	}
	
	public void add(Article article) {
		articles.add(article);
	}
	public void remove(int index) {
		articles.remove(index);
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
}

class Member {
	int id;
	String userId;
	String password;
	String regDate;

	Member(int id, String userId, String password, String regDate) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.regDate = regDate;
	}
}
