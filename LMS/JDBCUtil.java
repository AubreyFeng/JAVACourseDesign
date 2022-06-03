import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Properties;

public class JDBCUtil {
    private static String driver;
    private static String url;
    private static String user;
    private static String pwd;

    // ���徲̬ģ�飺ֻ��ִ��һ��()
    /*
     * Ҫ���Ӳ���루��ĳЩ���ܱ仯����Ϣֱ��д��Դ�����У����⣺����Щ��Ϣ�����ڵ�����һ��properties�ļ�����ʽ��key-value��ʽ����
     * �ڴ����ж�ȡ�����ļ��е���Ϣ
     */
    static {
        try {
            // 1.��������Ϣ
            Properties pro = new Properties();
            pro.load(JDBCUtil.class.getResourceAsStream("config.property"));
            driver = pro.getProperty("driver");
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            pwd = pro.getProperty("password");
            // 2.��������
            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    // ��װinsert��delete��update֮��Ĳ���
    /*
     * �ɱ���������������������������﷨����������...������
     */
    public static boolean exeUpdate(String sql, Object... params) {
        Connection con = null;
        try {
            con = getConnection();
            // 3.�������
            PreparedStatement pst = con.prepareStatement(sql);

            // 4.ִ�����
            // �б�������Ҫ�Ա�����ֵ
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally { // �����Ƿ����쳣��������ɵ����飬�ر�����
            release(con);

        }
        return false;
    }

    public static void release(Connection con) {
        try {
            if (con != null) {
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T get(String sql, Class<T> clazz, Object... args) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet re = null;
        try {
            conn = getConnection();// �����JDBCUtils�Ƕ��ⴴ�����࣬��װ�����Ӻ͹ر����ݿ�ķ���
            pre = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pre.setObject(i + 1, args[i]);
            }

            re = pre.executeQuery();
            // ��ȡ�������Ԫ����
            ResultSetMetaData me = re.getMetaData();
            // ͨ��resultsetmetadata��ȡ������е�����
            int co = me.getColumnCount();
            if (re.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < co; i++) {
                    // ��ȡ��ֵ
                    Object columValue = re.getObject(i + 1);
                    // ��ȡÿ���е�����
                    // getColumnname������
                    // getColumnLabel���Ի�ȡ�����������ݿ��һ��ʱ������������ñ���������

                    String columnName = me.getColumnLabel(i + 1);
                    // ��p����ָ����columnname���ԣ���ֵΪcolumnvalue��ͨ������

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columValue);
                    field.setAccessible(false);
                }
                return t;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(conn);
        }

        return null;

    }

    public static <T> ArrayList<T> query(String sql, Class<T> clazz, Object... args) {
        ArrayList<T> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet re = null;
        try {
            conn = getConnection();// �����JDBCUtils�Ƕ��ⴴ�����࣬��װ�����Ӻ͹ر����ݿ�ķ���
            pre = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pre.setObject(i + 1, args[i]);
            }

            re = pre.executeQuery();
            // ��ȡ�������Ԫ����
            ResultSetMetaData me = re.getMetaData();
            // ͨ��resultsetmetadata��ȡ������е�����
            int co = me.getColumnCount();
            while (re.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < co; i++) {
                    // ��ȡ��ֵ
                    Object columValue = re.getObject(i + 1);
                    // ��ȡÿ���е�����
                    // getColumnname������
                    // getColumnLabel���Ի�ȡ�����������ݿ��һ��ʱ������������ñ���������

                    String columnName = me.getColumnLabel(i + 1);
                    // ��p����ָ����columnname���ԣ���ֵΪcolumnvalue��ͨ������

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columValue);
                    field.setAccessible(false);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(conn);
        }

        return null;
    }
}
