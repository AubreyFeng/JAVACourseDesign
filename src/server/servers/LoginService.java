package server.servers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

public class LoginService {
	public static Scanner sr = new Scanner(System.in);

	public static boolean manageLogin() {
		String user = "", password = "";
		Properties pro = new Properties();

		try {
			InputStream in = new BufferedInputStream(new FileInputStream("src/resource/users.properties"));
			pro.load(new InputStreamReader(in, "utf-8"));
			in.close();
			user = pro.getProperty("userName");
			password = pro.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("�������û��������룺");
		for (int i = 0; i < 3; i++) {
			String srUser = sr.nextLine();
			String srPassword = sr.nextLine();

			if (user.equals(srUser) && password.equals(srPassword))
				return true;
			else
				System.out.println("��������û��������벻��ȷ�����������룺");
		}
		return false;
	}

}
