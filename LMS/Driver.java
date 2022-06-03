import java.util.Scanner;

public class Driver {
	public static Scanner sr = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Welcome to library manage system!");
		System.out.println("请输入登录项：");
		System.out.println("0.退出  1.管理员    2.读者");
		int choice = sr.nextInt();
		switch (choice) {
			case 0:
				System.exit(0);
				break;

			case 1:
				manage();
				break;

			case 2:
				reader();
				break;
		}
	}

	public static void manage() {
		if (!Login.manageLogin())
			System.exit(0);

		int choice = 0;
		do {
			manageMenu();
			choice = sr.nextInt();

			switch (choice) {
				case 0:
					return;

				case 1:
					LMSDAO.addBook();
					break;

				case 2:
					LMSDAO.updateBook();
					break;

				case 3:
					LMSDAO.deleteBook();
					break;

				case 4:
					LMSDAO.addUser();
					break;

				case 5:
					LMSDAO.updateUser();
					break;

				case 6:
					LMSDAO.deleteUser();
					break;

				case 7:
					LMSDAO.queryBorrowingRecords();
					break;

				case 8:
					LMSDAO.borrowStatistics();
					break;

				default:
					break;
			}

		} while (choice != 0);

	}

	public static void reader() {
		if (!LMSDAO.readerLogin())
			System.exit(0);

		int choice = 0;
		do {
			readerMenu();
			choice = sr.nextInt();

			switch (choice) {
				case 0:
					return;
				case 1:
					LMSDAO.query();
					break;

				case 2:
					LMSDAO.borrowBook();
					break;

				case 3:
					LMSDAO.returnBook();
					break;

				case 4:
					LMSDAO.queryReaderBorrowingRecords();
					break;

				case 5:
					LMSDAO.updatePassword();
					break;

				default:
					break;
			}

		} while (choice != 0);
	}

	private static void manageMenu() {
		System.out.println("管理员菜单");
		System.out.println("=============");
		System.out.println("0.返回");
		System.out.println("1.图书增加");
		System.out.println("2.图书修改");
		System.out.println("3.图书删除");
		System.out.println("4.读者增加");
		System.out.println("5.读者修改");
		System.out.println("6.读者删除");
		System.out.println("7.借阅查询");
		System.out.println("8.借阅统计");
		System.out.print("请输入：");
	}

	private static void readerMenu() {
		System.out.println("读者菜单");
		System.out.println("===============");
		System.out.println("0.返回");
		System.out.println("1.查询图书");
		System.out.println("2.借阅图书");
		System.out.println("3.归还图书");
		System.out.println("4.查询借阅记录");
		System.out.println("5.修改密码");
		System.out.print("请输入：");
	}
}