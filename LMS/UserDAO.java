import java.util.ArrayList;

public class UserDAO {

	public static User check(String name, String password) {
		return JDBCUtil.get("select *from User where name = ? and password = ?", User.class, name, password);
	}

	public static ArrayList<User> selectUser() {
		return JDBCUtil.query("select *from User", User.class);
	}

	public static boolean insertUser(String name, String password) {
		return JDBCUtil.exeUpdate("insert into User values(0,?,?)", name, password);// 0是默认自增
		// id是主码，是数据库自动增加，第一个用户id是1000，是测试账户，第一个正式账户是1001
	}

	public static boolean updateUserPWD(int id, String pwd) {
		return JDBCUtil.exeUpdate("update User set password = ? where id =?", pwd, id);
	}

	public static boolean deleteUser(int id) {
		return JDBCUtil.exeUpdate("delete from User where id = ?", id);
	}
}