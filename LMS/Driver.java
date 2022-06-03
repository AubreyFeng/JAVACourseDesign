import java.util.Scanner;

public class Driver {
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
		System.out.println("����Ա�˵�");
		System.out.println("=============");
		System.out.println("0.����");
		System.out.println("1.ͼ������");
		System.out.println("2.ͼ���޸�");
		System.out.println("3.ͼ��ɾ��");
		System.out.println("4.��������");
		System.out.println("5.�����޸�");
		System.out.println("6.����ɾ��");
		System.out.println("7.���Ĳ�ѯ");
		System.out.println("8.����ͳ��");
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