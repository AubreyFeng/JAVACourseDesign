package client;

import java.util.Scanner;

import server.dao.LMSDAO;
import server.servers.LoginService;

public class Client {
	public static Scanner sr = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Welcome to library manage system!");
		System.out.println("�������¼�");
		System.out.println("0.�˳�  1.����Ա    2.����");
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
					System.out.println("�����ɹ���");
				break;

			case 5:
				if (LMSDAO.addUser())
					System.out.println("�����ɹ���");
				break;

			case 6:
				if (LMSDAO.updateUser())
					System.out.println("�����ɹ���");
				break;

			case 7:
				if (LMSDAO.deleteUser())
					System.out.println("�����ɹ���");
				break;

			case 8:
				LMSDAO.queryBorrowingRecords();
				break;

			case 9:
				LMSDAO.borrowStatistics();
				break;

			default:
				System.out.println("������Ч��ֻ������0-9��");
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
					System.out.println("�����ɹ���");
				break;

			case 4:
				LMSDAO.queryReaderBorrowingRecords();
				break;

			case 5:
				LMSDAO.updatePassword();
				break;

			default:
				System.out.println("������Ч��ֻ������0-5��");
				break;
			}

		} while (choice != 0);
	}

	private static void manageMenu() {
		System.out.println("����Ա�˵�");
		System.out.println("=============");
		System.out.println("0.����");
		System.out.println("1.ͼ���ѯ");
		System.out.println("2.ͼ������");
		System.out.println("3.ͼ���޸�");
		System.out.println("4.ͼ��ɾ��");
		System.out.println("5.��������");
		System.out.println("6.�����޸�");
		System.out.println("7.����ɾ��");
		System.out.println("8.���Ĳ�ѯ");
		System.out.println("9.����ͳ��");
		System.out.print("�����룺");
	}

	private static void readerMenu() {
		System.out.println("���߲˵�");
		System.out.println("===============");
		System.out.println("0.����");
		System.out.println("1.��ѯͼ��");
		System.out.println("2.����ͼ��");
		System.out.println("3.�黹ͼ��");
		System.out.println("4.��ѯ���ļ�¼");
		System.out.println("5.�޸�����");
		System.out.print("�����룺");
	}
}