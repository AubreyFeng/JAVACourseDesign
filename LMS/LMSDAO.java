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
		System.out.println("�������û�����");
		String name = sr.next();
		System.out.println("���������룺");
		String password = sr.next();
		try {
			user = UserDAO.check(name, password);
		} catch (Exception e) {
			System.out.println("�û������������");
		}
		if (user == null)
			return false;

		readerId = user.getId();
		return true;
	}

	public static void addBook() {
		ArrayList<Book> books = null;
		System.out.println("1.��excel�ļ�����    2.�Ӽ�������");
		System.out.print("�����룺");
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
		System.out.println("������Ҫ�޸ĵ�ͼ��ISBN��");
		String ISBN = sr.next();
		Book book = BookDAO.queryByISBN(ISBN).get(0);

		System.out.println("�Ƿ�Ҫ�޸�����(y or n)��");
		if (sr.next().equalsIgnoreCase("y"))
			book.setName(sr.next());

		System.out.println("�Ƿ�Ҫ�޸����(y or n)��");
		if (sr.next().equalsIgnoreCase("y"))
			book.setCategory(sr.next());

		System.out.println("�Ƿ�Ҫ�޸�����(y or n)��");
		if (sr.next().equalsIgnoreCase("y"))
			book.setAuthor(sr.next());

		System.out.println("�Ƿ�Ҫ�޸ĳ�����(y or n)��");
		if (sr.next().equalsIgnoreCase("y"))
			book.setPress(sr.next());

		System.out.println("�Ƿ�Ҫ�޳�������(y or n)��");
		if (sr.next().equalsIgnoreCase("y"))
			book.setPublicationDate(sr.next());

		System.out.println("�Ƿ�Ҫ�޸ĵǼ�����(y or n)��");
		if (sr.next().equalsIgnoreCase("y"))
			book.setRecordDate(sr.next());

		return BookDAO.updateReader(book);
	}

	public static boolean deleteBook() {
		query();
		System.out.println("������Ҫɾ�����ݵ�ISBN��");
		String ISBN = sr.next();

		return BookDAO.deleteBook(ISBN);
	}

	public static boolean addUser() {
		System.out.println("�������û�����");
		String name = sr.next();

		System.out.println("���������룺");
		String password = sr.next();

		if (UserDAO.check(name, password) != null) {
			System.out.println("���û��Ѵ��ڣ�");
			return false;
		}

		return UserDAO.insertUser(name, password);
	}

	public static boolean updateUser() {
		System.out.println("������Ҫ�޸ĵ��û�ID��");
		int id = sr.nextInt();

		return UserDAO.updateUserPWD(id, "123456");// ����Աֻ�ܽ��û����������ó�ΪĬ�ϵġ�123456��
	}

	public static boolean deleteUser() {
		System.out.println("������Ҫɾ�����û�ID��");
		int id = sr.nextInt();

		return UserDAO.deleteUser(id);
	}

	public static void queryBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = null;
		System.out.println("1.��ѯȫ����¼	2.��ѯָ���û�");
		int choice = sr.nextInt();

		switch (choice) {
			case 1:
				borrowDetails = BorrowDetailDAO.selectAll();
				break;

			case 2:
				System.out.println("�������û�ID��");
				int id = sr.nextInt();
				borrowDetails = BorrowDetailDAO.selectByReaderId(id);
				break;
		}

		if (borrowDetails.size() == 0)
			System.out.println("δ�ҵ���ؼ�¼��");
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
		System.out.println("�ܼ�¼������" + total);
		System.out.println("�ѹ黹ͼ�飺" + red);
		System.out.println("δ�黹ͼ�飺" + (total - red));
		System.out.println("�����¼��" + fine);

		System.out.println("�Ƿ񵼳���ϸ���ݵ�Excel(y or n)��");
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
			System.out.println("δ�ҵ����ͼ�飡");
		else
			BookDAO.showBook(list);
	}

	public static boolean borrowBook() {
		query();
		System.out.println("������Ҫ���ĵ�ͼ��ISBN��");
		String ISBN = sr.next();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailDAO.borrowBook(readerId, ISBN, prefix);

	}

	public static boolean returnBook() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailDAO.queryUnreturn(readerId);
		if (borrowDetails.size() == 0) {
			System.out.println("û��δ�黹��ͼ�飡");
			return true;
		} else
			BorrowDetailDAO.showBorrowDetails(borrowDetails);

		System.out.println("������Ҫ�黹��ͼ��ţ�");
		String ISBN = sr.next();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailDAO.returnBook(readerId, ISBN, prefix);
	}

	public static void queryReaderBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailDAO.selectByReaderId(readerId);
		if (borrowDetails.size() == 0)
			System.out.println("δ�ҵ���ؼ�¼��");
		else
			BorrowDetailDAO.showBorrowDetails(borrowDetails);
	}

	public static boolean updatePassword() {
		System.out.println("�������û��������룺");
		String name = sr.next();
		String password = sr.next();

		User user = UserDAO.check(name, password);
		if (user == null) {
			System.out.println("δ�ҵ����û���");
			return false;
		}

		System.out.println("�����������루���볤�Ȳ�����6���ַ���������һ��Сд��ĸ��������һ����д��ĸ������һ�����֣���");
		String newPasswd = sr.next();

		boolean flag = isPasswd(newPasswd);

		while (!flag) {
			System.out.println("���������ʽ����ȷ�����������루���볤�Ȳ�����6���ַ���������һ��Сд��ĸ��������һ����д��ĸ������һ�����֣���");
			newPasswd = sr.nextLine();
			flag = isPasswd(newPasswd);
		}

		return UserDAO.updateUserPWD(user.getId(), newPasswd);
	}

	private static void queryMenu() {
		System.out.println("��ѡ���ѯ��ʽ");
		System.out.println("1.��ѯȫ��");
		System.out.println("2.���鼮��Ų�ѯ");
		System.out.println("3.��������ѯ");
		System.out.println("4.������ѯ");
		System.out.println("5.�����߲�ѯ");
		System.out.println("6.���������ѯ");
		System.out.println("7.����");
		System.out.println("�������ѯ��ʽ�͹ؼ��ʣ�");
	}

	private static boolean isPasswd(String passwd) {
		String regExp = "^.*(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])\\w{6,}"; // 6λ����
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(passwd);
		return matcher.find();
	}
}
