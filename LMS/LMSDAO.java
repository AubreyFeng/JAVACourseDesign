import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LMSDAO {

	public static Scanner sr = new Scanner(System.in);
	private static int readerId = -1;

	public static boolean readerLogin() {
		User user = null;
		System.out.println("请输入用户名：");
		String name = sr.next();
		System.out.println("请输入密码：");
		String password = sr.next();
		try {
			user = UserDAO.check(name, password);
		} catch (Exception e) {
			System.out.println("用户名或密码错误！");
		}
		if (user == null)
			return false;

		readerId = user.getId();
		return true;
	}

	public static void addBook() {
		ArrayList<Book> books = null;
		System.out.println("1.从excel文件导入    2.从键盘输入");
		System.out.print("请输入：");
		int choice = sr.nextInt();
		switch (choice) {
			case 1:
				books = IO.readFromXLS("book.xls");
				break;

			case 2:
				books = IO.readFromKeyboard();
				break;
		}

		for (Book book : books)
			BookDAO.insertBook(book);
	}

	public static boolean updateBook() {
		System.out.println("请输入要修改的图书ISBN：");
		String ISBN = sr.next();
		Book book = BookDAO.queryByISBN(ISBN).get(0);

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

		return BookDAO.updateReader(book);
	}

	public static boolean deleteBook() {
		query();
		System.out.println("请输入要删除数据的ISBN：");
		String ISBN = sr.next();

		return BookDAO.deleteBook(ISBN);
	}

	public static boolean addUser() {
		System.out.println("请输入用户名：");
		String name = sr.next();

		System.out.println("请输入密码：");
		String password = sr.next();

		if (UserDAO.check(name, password) != null) {
			System.out.println("该用户已存在！");
			return false;
		}

		return UserDAO.insertUser(name, password);
	}

	public static boolean updateUser() {
		System.out.println("请输入要修改的用户ID：");
		int id = sr.nextInt();

		return UserDAO.updateUserPWD(id, "123456");// 管理员只能将用户的密码设置成为默认的“123456”
	}

	public static boolean deleteUser() {
		System.out.println("请输入要删除的用户ID：");
		int id = sr.nextInt();

		return UserDAO.deleteUser(id);
	}

	public static void queryBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = null;
		System.out.println("1.查询全部记录	2.查询指定用户");
		int choice = sr.nextInt();

		switch (choice) {
			case 1:
				borrowDetails = BorrowDetailDAO.selectAll();
				break;

			case 2:
				System.out.println("请输入用户ID：");
				int id = sr.nextInt();
				borrowDetails = BorrowDetailDAO.selectByReaderId(id);
				break;
		}

		if (borrowDetails.size() == 0)
			System.out.println("未找到相关记录！");
		else
			BorrowDetailDAO.showBorrowDetails(borrowDetails);
	}

	public static void borrowStatistics() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailDAO.selectAll();
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
		if (sr.next().equalsIgnoreCase("y"))
			IO.writeToXLS(borrowDetails, "detail.xls");
	}

	public static void query() {
		ArrayList<Book> list = null;
		String key = null;
		queryMenu();
		int choice = sr.nextInt();

		switch (choice) {
			case 1:
				list = BookDAO.queryAll();
				break;

			case 2:
				key = sr.next();
				list = BookDAO.queryByISBN(key);
				break;

			case 3:
				key = sr.next();
				list = BookDAO.queryByName(key);
				break;

			case 4:
				key = sr.next();
				list = BookDAO.queryByCategory(key);
				break;

			case 5:
				key = sr.next();
				list = BookDAO.queryByAuthor(key);
				break;

			case 6:
				key = sr.next();
				list = BookDAO.queryByPress(key);
				break;

			case 7:
				return;
		}

		if (list == null)
			System.out.println("未找到相关图书！");
		else
			BookDAO.showBook(list);
	}

	public static boolean borrowBook() {
		query();
		System.out.println("请输入要借阅的图书ISBN：");
		String ISBN = sr.next();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailDAO.borrowBook(readerId, ISBN, prefix);

	}

	public static boolean returnBook() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailDAO.queryUnreturn(readerId);
		if (borrowDetails.size() == 0) {
			System.out.println("没有未归还的图书！");
			return true;
		} else
			BorrowDetailDAO.showBorrowDetails(borrowDetails);

		System.out.println("请输入要归还的图书号：");
		String ISBN = sr.next();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailDAO.returnBook(readerId, ISBN, prefix);
	}

	public static void queryReaderBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailDAO.selectByReaderId(readerId);
		if (borrowDetails.size() == 0)
			System.out.println("未找到相关记录！");
		else
			BorrowDetailDAO.showBorrowDetails(borrowDetails);
	}

	public static boolean updatePassword() {
		System.out.println("请输入用户名和密码：");
		String name = sr.next();
		String password = sr.next();

		User user = UserDAO.check(name, password);
		if (user == null) {
			System.out.println("未找到该用户！");
			return false;
		}

		System.out.println("请输入新密码（密码长度不少于6个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字）：");
		String newPasswd = sr.next();

		boolean flag = isPasswd(newPasswd);

		while (!flag) {
			System.out.println("密码输入格式不正确，请重新输入（密码长度不少于6个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字）：");
			newPasswd = sr.nextLine();
			flag = isPasswd(newPasswd);
		}

		return UserDAO.updateUserPWD(user.getId(), newPasswd);
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

	private static boolean isPasswd(String passwd) {
		String regExp = "^.*(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])\\w{6,}"; // 6位数字
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(passwd);
		return matcher.find();
	}
}
