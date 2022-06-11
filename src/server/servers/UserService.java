package server.servers;
import java.util.ArrayList;

import tools.JDBCUtil;
import vo.User;

public class UserService {

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
		// id��user��borrowDetail�����룬����ֱ��ɾ��������idɾ������ܻ�������ɵļ�¼id�غϸ���
		// �����user��ɾ��ʱ���ǰ�name��password����Ϊnull
		return JDBCUtil.exeUpdate("update user set name = null, password = null where id = ?", id);
	}
}