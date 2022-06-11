package server.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import server.servers.BookService;
import server.servers.BorrowDetailService;
import server.servers.FileService;
import server.servers.ReaderService;
import server.servers.UserService;
import tools.Validate;
import vo.Book;
import vo.BorrowDetail;
import vo.Reader;
import vo.User;

public class LMSDAO {

	public static Scanner sr = new Scanner(System.in);
	private static int readerId = -1;

	public static boolean readerLogin() {
		readerId = server.servers.ReaderService.login();
		return readerId > 0;
	}

	public static void addBook() {
		ArrayList<Book> books = null;
		System.out.println("1.从excel文件导入    2.从键盘输入");
		System.out.print("请输入：");
		int choice = sr.nextInt();
		switch (choice) {
		case 1:
			books = FileService.readFromXLS("book.xls");
			break;

		case 2:
			books = FileService.readFromKeyboard();
			break;
		}

		for (Book book : books)
			BookService.insertBook(book);

		System.out.println("成功导入" + books.size() + "条数据！");
	}

	public static boolean updateBook() {
		System.out.println("请输入要修改的图书ISBN：");
		String ISBN = null;
		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("你输入的ISBN格式不正确，请重新输入");
		}

		Book book = BookService.queryByISBN(ISBN).get(0);

		System.out.println("是否要修改书名(y or n)：");
		if (sr.next().equalsIgnoreCase("y"))
			book.setName(sr.next());

		System.out.println("是否要修改类别(y or n)：");
		if (sr.next().equalsIgnoreCase("y"))
			book.setCategory(sr.next());

		System.out.println("是否要修改作者(y or n)：");
		if (sr.next().equalsIgnoreCase("y"))
			book.setAuthor(sr.next());

		System.out.println("是否要修改出版社(y or n)：");
		if (sr.next().equalsIgnoreCase("y"))
			book.setPress(sr.next());

		System.out.println("是否要修出版日期(y or n)：");
		if (sr.next().equalsIgnoreCase("y"))
			book.setPublicationDate(sr.next());

		System.out.println("是否要修改登记日期(y or n)：");
		if (sr.next().equalsIgnoreCase("y"))
			book.setRecordDate(sr.next());

		return BookService.updateReader(book);
	}

	public static boolean deleteBook() {
		String ISBN = null;
		query();
		System.out.println("请输入要删除数据的ISBN：");
		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("你输入的ISBN格式不正确，请重新输入");
		}

		return BookService.deleteBook(ISBN);
	}

	public static boolean addUser() {
		System.out.println("请输入用户名：");// 用户表的primary key 是id，不是name，id是自动生成
		String name = sr.next();

		System.out.println("请输入密码：");// 管理员只负责账户注册，初始密码一般是 123456
		String password = sr.next();

		if (UserService.check(name, password) != null) {
			System.out.println("该用户已存在！");
			return false;
		}

		if (UserService.insertUser(name, password)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String prefix = df.format(new Date());

			Reader reader = new Reader(UserService.check(name, password).getId(), name, null, null, null, prefix, 10);

			if (ReaderService.insertReader(reader))
				return true;
		}

		return false;
	}

	public static boolean updateUser() {
		System.out.println("请输入要修改的用户ID：");
		int id = sr.nextInt();

		return UserService.updateUserPWD(id, "123456");// 管理员只能将用户的密码设置成为默认的“123456”
	}

	public static boolean deleteUser() {
		System.out.println("请输入要删除的用户ID：");
		int id = sr.nextInt();

		return UserService.deleteUser(id) && ReaderService.deleteReader(id);
	}

	public static void queryBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = null;
		System.out.println("1.查询全部记录	2.查询指定用户");
		int choice = sr.nextInt();

		switch (choice) {
		case 1:
			borrowDetails = BorrowDetailService.selectAll();
			break;

		case 2:
			System.out.println("请输入用户ID：");
			int id = sr.nextInt();
			borrowDetails = BorrowDetailService.selectByReaderId(id);
			break;
		}

		if (borrowDetails.size() == 0)
			System.out.println("未找到相关记录！");
		else
			BorrowDetailService.showBorrowDetails(borrowDetails);
	}

	public static void borrowStatistics() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailService.selectAll();
		int total = borrowDetails.size(), red = 0, fine = 0;
		for (BorrowDetail borrowDetail : borrowDetails) {
			if (borrowDetail.getReturnDate() != null)
				red++;
			if (borrowDetail.getFine() != null)
				fine++;
		}
		System.out.println("总记录条数：" + total);
		System.out.println("已归还图书：" + red);
		System.out.println("未归还图书：" + (total - red));
		System.out.println("罚金记录：" + fine);

		System.out.println("是否导出详细数据到Excel(y or n)：");
		if (sr.next().equalsIgnoreCase("y")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String prefix = df.format(new Date());
			FileService.writeToXLS(borrowDetails, prefix + "detail.xls");
		}
	}

	public static void query() {
		ArrayList<Book> list = null;
		String key = null;
		queryMenu();
		int choice = sr.nextInt();

		switch (choice) {
		case 1:
			list = BookService.queryAll();
			break;

		case 2:
			key = sr.next();
			list = BookService.queryByISBN(key);
			break;

		case 3:
			key = sr.next();
			list = BookService.queryByName(key);
			break;

		case 4:
			key = sr.next();
			list = BookService.queryByCategory(key);
			break;

		case 5:
			key = sr.next();
			list = BookService.queryByAuthor(key);
			break;

		case 6:
			key = sr.next();
			list = BookService.queryByPress(key);
			break;

		case 7:
			return;
		}

		if (list == null)
			System.out.println("未找到相关图书！");
		else
			BookService.showBook(list);
	}

	public static boolean borrowBook() {
		String ISBN = null;
		query();
		System.out.println("请输入要借阅的图书ISBN：");

		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("你输入的ISBN格式不正确，请重新输入");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailService.borrowBook(readerId, ISBN, prefix);
	}

	public static boolean returnBook() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailService.queryUnreturn(readerId);
		if (borrowDetails.size() == 0) {
			System.out.println("没有未归还的图书！");
			return true;
		} else
			BorrowDetailService.showBorrowDetails(borrowDetails);

		System.out.println("请输入要归还的图书号：");
		String ISBN = null;
		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("你输入的ISBN格式不正确，请重新输入");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailService.returnBook(readerId, ISBN, prefix);
	}

	public static void queryReaderBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailService.selectByReaderId(readerId);
		if (borrowDetails.size() == 0)
			System.out.println("未找到相关记录！");
		else
			BorrowDetailService.showBorrowDetails(borrowDetails);
	}

	public static boolean updatePassword() {
		String newPasswd = null;
		System.out.println("请输入用户名和密码：");
		String name = sr.next();
		String password = sr.next();

		User user = UserService.check(name, password);
		if (user == null) {
			System.out.println("未找到该用户！");
			return false;
		}

		while (true) {
			System.out.println("请输入新密码（密码长度不少于6个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字）：");
			newPasswd = sr.next();

			boolean flag = Validate.isPasswd(newPasswd);
			while (!flag) {
				System.out.println("您的密码不符合复杂性要求（密码长度不少于6个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字），请重新输入：");
				newPasswd = sr.nextLine();
				flag = Validate.isPasswd(newPasswd);
			}

			System.out.println("请输入确认密码：");
			String secondPasswd = sr.next();

			if (newPasswd.equals(secondPasswd))
				break;
			else
				System.out.println("两次输入的密码必须一致，请重新设置新密码：");
		}

		System.out.println("您已成功修改密码，请谨记！");
		return UserService.updateUserPWD(user.getId(), newPasswd);
	}

	private static void queryMenu() {
		System.out.println("请选择查询方式");
		System.out.println("1.查询全部");
		System.out.println("2.按书籍编号查询");
		System.out.println("3.按书名查询");
		System.out.println("4.按类别查询");
		System.out.println("5.按作者查询");
		System.out.println("6.按出版社查询");
		System.out.println("7.返回");
		System.out.println("请输入查询方式和关键词：");
	}
}
