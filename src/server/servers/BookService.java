package server.servers;

import java.util.ArrayList;

import tools.JDBCUtil;
import vo.Book;

public class BookService {
	public static boolean insertBook(Book book) {
		return JDBCUtil.exeUpdate("insert into book values(?,?,?,?,?,?,?)", book.getISBN(), book.getName(),
				book.getCategory(), book.getAuthor(), book.getPress(), book.getPublicationDate(), book.getRecordDate());
	}

	public static boolean deleteBook(String ISBN) {
		return JDBCUtil.exeUpdate("delete from book where ISBN = ?", ISBN);
	}

	public static boolean updateReader(Book book) {
		return JDBCUtil.exeUpdate(
				"update book set Name = ?,Category = ?,author = ?,press = ?,publicationDate = ?,recordDate = ? where ISBN = ?",
				book.getName(), book.getCategory(), book.getAuthor(), book.getPress(), book.getPublicationDate(),
				book.getRecordDate(), book.getISBN());
	}

	public static ArrayList<Book> queryAll() {
		return JDBCUtil.query("select *from book", Book.class);
	}

	public static ArrayList<Book> queryByISBN(String key) {
		return JDBCUtil.query("select *from book where ISBN = ?", Book.class, key);
	}

	public static ArrayList<Book> queryByName(String key) {// 书名查询是模糊查询
		return JDBCUtil.query("select *from book where Name like ?", Book.class, "%" + key + "%");
	}

	public static ArrayList<Book> queryByCategory(String key) {
		return JDBCUtil.query("select *from book where Category = ?", Book.class, key);
	}

	public static ArrayList<Book> queryByAuthor(String key) {
		return JDBCUtil.query("select *from book where author = ?", Book.class, key);
	}

	public static ArrayList<Book> queryByPress(String key) {
		return JDBCUtil.query("select *from book where press = ?", Book.class, key);
	}

	public static void showBook(ArrayList<Book> list) {
		String title = String.format("%-11s\t%-10s\t%-8s\t%-10s\t%-14s\t%-10s\t%-10s", "图书编号", "书名", "类别", "作者", "出版社",
				"出版日期", "登记日期");
		System.out.println(title);
		System.out.println("==============================================================================================================");
		for (Book book : list)
			System.out.println(book.toString());

	}

}
