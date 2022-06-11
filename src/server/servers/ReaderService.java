package server.servers;

import java.util.ArrayList;
import java.util.Scanner;

import tools.JDBCUtil;
import vo.Reader;
import vo.User;

public class ReaderService {

	public static Scanner sr = new Scanner(System.in);

	public static int login() {
		int readerId = -1;
		User user = null;
		int n = 3;

		while (n-- > 0) {
			System.out.println("请输入用户名：");
			String name = sr.next();
			System.out.println("请输入密码：");
			String password = sr.next();
			try {
				user = UserService.check(name, password);
				readerId = user.getId();
				return readerId;
			} catch (Exception e) {
				System.out.println("用户名或密码错误，请重新输入！");
			}
		}

		return -1;
	}

	public static boolean insertReader(Reader reader) {
		return JDBCUtil.exeUpdate("insert into reader values(?,?,?,?,?,?,?)", reader.getId(), reader.getName(),
				reader.getGender(), reader.getBirthday(), reader.getPhoneNum(), reader.getRegDate(),
				reader.getBorrowedNum());
	}

	public static boolean deleteReader(int id) {
		return JDBCUtil.exeUpdate("delete from reader where Id = ?", id);
	}

	public static boolean updateReader(Reader reader) {
		return JDBCUtil.exeUpdate(
				"update Reader set id = ?,Name = ?,gender = ?,birthday = ?,phoneNum = ?,regDate = ?,borrowedNum = ? where id = ?",
				reader.getId(), reader.getName(), reader.getGender(), reader.getBirthday(), reader.getPhoneNum(),
				reader.getRegDate(), reader.getBorrowedNum(), reader.getId());
	}

	public static ArrayList<Reader> queryReader() {
		return JDBCUtil.query("select *from Reader", Reader.class);
	}

	public static ArrayList<Reader> queryReaderById(String id) {
		return JDBCUtil.query("select *from Reader where Id = ?", Reader.class, id);
	}

	public static ArrayList<Reader> queryReaderByName(String name) {
		return JDBCUtil.query("select *from Reader where name = ?", Reader.class, name);
	}

}
