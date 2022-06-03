import java.util.ArrayList;

public class UserDAO {

	public static User check(String name, String password) {
		return JDBCUtil.get("select *from User where name = ? and password = ?", User.class, name, password);
	}

	public static ArrayList<User> selectUser() {
		return JDBCUtil.query("select *from User", User.class);
	}

	public static boolean insertUser(String name, String password) {
		return JDBCUtil.exeUpdate("insert into User values(0,?,?)", name, password);// 0��Ĭ������
		// id�����룬�����ݿ��Զ����ӣ���һ���û�id��1000���ǲ����˻�����һ����ʽ�˻���1001
	}

	public static boolean updateUserPWD(int id, String pwd) {
		return JDBCUtil.exeUpdate("update User set password = ? where id =?", pwd, id);
	}

	public static boolean deleteUser(int id) {
		return JDBCUtil.exeUpdate("delete from User where id = ?", id);
	}
}