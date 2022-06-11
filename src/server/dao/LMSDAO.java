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
		System.out.println("1.��excel�ļ�����    2.�Ӽ�������");
		System.out.print("�����룺");
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

		System.out.println("�ɹ�����" + books.size() + "�����ݣ�");
	}

	public static boolean updateBook() {
		System.out.println("������Ҫ�޸ĵ�ͼ��ISBN��");
		String ISBN = null;
		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("�������ISBN��ʽ����ȷ������������");
		}

		Book book = BookService.queryByISBN(ISBN).get(0);

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

		return BookService.updateReader(book);
	}

	public static boolean deleteBook() {
		String ISBN = null;
		query();
		System.out.println("������Ҫɾ�����ݵ�ISBN��");
		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("�������ISBN��ʽ����ȷ������������");
		}

		return BookService.deleteBook(ISBN);
	}

	public static boolean addUser() {
		System.out.println("�������û�����");// �û����primary key ��id������name��id���Զ�����
		String name = sr.next();

		System.out.println("���������룺");// ����Աֻ�����˻�ע�ᣬ��ʼ����һ���� 123456
		String password = sr.next();

		if (UserService.check(name, password) != null) {
			System.out.println("���û��Ѵ��ڣ�");
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
		System.out.println("������Ҫ�޸ĵ��û�ID��");
		int id = sr.nextInt();

		return UserService.updateUserPWD(id, "123456");// ����Աֻ�ܽ��û����������ó�ΪĬ�ϵġ�123456��
	}

	public static boolean deleteUser() {
		System.out.println("������Ҫɾ�����û�ID��");
		int id = sr.nextInt();

		return UserService.deleteUser(id) && ReaderService.deleteReader(id);
	}

	public static void queryBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = null;
		System.out.println("1.��ѯȫ����¼	2.��ѯָ���û�");
		int choice = sr.nextInt();

		switch (choice) {
		case 1:
			borrowDetails = BorrowDetailService.selectAll();
			break;

		case 2:
			System.out.println("�������û�ID��");
			int id = sr.nextInt();
			borrowDetails = BorrowDetailService.selectByReaderId(id);
			break;
		}

		if (borrowDetails.size() == 0)
			System.out.println("δ�ҵ���ؼ�¼��");
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
		System.out.println("�ܼ�¼������" + total);
		System.out.println("�ѹ黹ͼ�飺" + red);
		System.out.println("δ�黹ͼ�飺" + (total - red));
		System.out.println("�����¼��" + fine);

		System.out.println("�Ƿ񵼳���ϸ���ݵ�Excel(y or n)��");
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
			System.out.println("δ�ҵ����ͼ�飡");
		else
			BookService.showBook(list);
	}

	public static boolean borrowBook() {
		String ISBN = null;
		query();
		System.out.println("������Ҫ���ĵ�ͼ��ISBN��");

		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("�������ISBN��ʽ����ȷ������������");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailService.borrowBook(readerId, ISBN, prefix);
	}

	public static boolean returnBook() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailService.queryUnreturn(readerId);
		if (borrowDetails.size() == 0) {
			System.out.println("û��δ�黹��ͼ�飡");
			return true;
		} else
			BorrowDetailService.showBorrowDetails(borrowDetails);

		System.out.println("������Ҫ�黹��ͼ��ţ�");
		String ISBN = null;
		while (sr.hasNext()) {
			ISBN = sr.next();
			if (Validate.isISBN(ISBN))
				break;
			else
				System.out.println("�������ISBN��ʽ����ȷ������������");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String prefix = df.format(new Date());

		return BorrowDetailService.returnBook(readerId, ISBN, prefix);
	}

	public static void queryReaderBorrowingRecords() {
		ArrayList<BorrowDetail> borrowDetails = BorrowDetailService.selectByReaderId(readerId);
		if (borrowDetails.size() == 0)
			System.out.println("δ�ҵ���ؼ�¼��");
		else
			BorrowDetailService.showBorrowDetails(borrowDetails);
	}

	public static boolean updatePassword() {
		String newPasswd = null;
		System.out.println("�������û��������룺");
		String name = sr.next();
		String password = sr.next();

		User user = UserService.check(name, password);
		if (user == null) {
			System.out.println("δ�ҵ����û���");
			return false;
		}

		while (true) {
			System.out.println("�����������루���볤�Ȳ�����6���ַ���������һ��Сд��ĸ��������һ����д��ĸ������һ�����֣���");
			newPasswd = sr.next();

			boolean flag = Validate.isPasswd(newPasswd);
			while (!flag) {
				System.out.println("�������벻���ϸ�����Ҫ�����볤�Ȳ�����6���ַ���������һ��Сд��ĸ��������һ����д��ĸ������һ�����֣������������룺");
				newPasswd = sr.nextLine();
				flag = Validate.isPasswd(newPasswd);
			}

			System.out.println("������ȷ�����룺");
			String secondPasswd = sr.next();

			if (newPasswd.equals(secondPasswd))
				break;
			else
				System.out.println("����������������һ�£����������������룺");
		}

		System.out.println("���ѳɹ��޸����룬����ǣ�");
		return UserService.updateUserPWD(user.getId(), newPasswd);
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
}
