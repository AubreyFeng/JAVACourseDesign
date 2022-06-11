package client;

import java.util.Scanner;

import server.dao.LMSDAO;
import server.servers.LoginService;

public class Client {
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
		if (!LoginService.manageLogin())
			System.exit(0);

		int choice = 0;
		do {
			manageMenu();
			choice = sr.nextInt();

			switch (choice) {
			case 0:
				return;

			case 1:
				LMSDAO.query();
				break;

			case 2:
				LMSDAO.addBook();
				break;

			case 3:
				LMSDAO.updateBook();
				break;

			case 4:
				if (LMSDAO.deleteBook())
					System.out.println("操作成功！");
				break;

			case 5:
				if (LMSDAO.addUser())
					System.out.println("操作成功！");
				break;

			case 6:
				if (LMSDAO.updateUser())
					System.out.println("操作成功！");
				break;

			case 7:
				if (LMSDAO.deleteUser())
					System.out.println("操作成功！");
				break;

			case 8:
				LMSDAO.queryBorrowingRecords();
				break;

			case 9:
				LMSDAO.borrowStatistics();
				break;

			default:
				System.out.println("输入无效，只能输入0-9！");
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
				if (LMSDAO.returnBook())
					System.out.println("操作成功！");
				break;

			case 4:
				LMSDAO.queryReaderBorrowingRecords();
				break;

			case 5:
				LMSDAO.updatePassword();
				break;

			default:
				System.out.println("输入无效，只能输入0-5！");
				break;
			}

		} while (choice != 0);
	}

	private static void manageMenu() {
		System.out.println("管理员菜单");
		System.out.println("=============");
		System.out.println("0.返回");
		System.out.println("1.图书查询");
		System.out.println("2.图书增加");
		System.out.println("3.图书修改");
		System.out.println("4.图书删除");
		System.out.println("5.读者增加");
		System.out.println("6.读者修改");
		System.out.println("7.读者删除");
		System.out.println("8.借阅查询");
		System.out.println("9.借阅统计");
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