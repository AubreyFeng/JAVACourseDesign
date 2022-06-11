package server.servers;

import java.util.ArrayList;

import tools.JDBCUtil;
import vo.BorrowDetail;

public class BorrowDetailService {
	public static ArrayList<BorrowDetail> selectByReaderId(int readerId) {
		return JDBCUtil.query("select *from BorrowDetail where id = ?", BorrowDetail.class, readerId);
	}

	public static ArrayList<BorrowDetail> selectAll() {
		return JDBCUtil.query("select *from BorrowDetail", BorrowDetail.class);
	}

	public static ArrayList<BorrowDetail> queryUnreturn(int readerId) {
		return JDBCUtil.query("select *from BorrowDetail where id = ? and returnDate is NULL", BorrowDetail.class,
				readerId);
	}

	public static boolean borrowBook(int readerId, String ISBN, String borrowdate) {
		return JDBCUtil.exeUpdate("insert into borrowDetail values(?,?,?,null,null)", readerId, ISBN, borrowdate);
	}

	public static boolean returnBook(int readerId, String ISBN, String borrowdate) {
		return JDBCUtil.exeUpdate("update borrowdetail set returnDate = ? where id = ? and ISBN = ?", borrowdate,
				readerId, ISBN);
	}

	public static void showBorrowDetails(ArrayList<BorrowDetail> borrowDetails) {
		System.out.println("���߱��    �鼮���    ��������    �黹����    ����");
		System.out.println("=====================================================");
		for (BorrowDetail borrowDetail : borrowDetails)
			System.out.println(borrowDetail.toString());
	}
}
