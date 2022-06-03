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

    // 定义静态模块：只会执行一次()
    /*
     * 要解决硬编码（把某些可能变化的信息直接写在源程序中）问题：将这些信息保存在单独的一个properties文件（格式是key-value形式）中
     * 在代码中读取存在文件中的信息
     */
    static {
        try {
            // 1.读配置信息
            Properties pro = new Properties();
            pro.load(JDBCUtil.class.getResourceAsStream("config.property"));
            driver = pro.getProperty("driver");
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            pwd = pro.getProperty("password");
            // 2.加载驱动
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

    // 封装insert，delete，update之类的操作
    /*
     * 可变参数：概念，参数可以有任意个，语法：参数类型...参数名
     */
    public static boolean exeUpdate(String sql, Object... params) {
        Connection con = null;
        try {
            con = getConnection();
            // 3.创建语句
            PreparedStatement pst = con.prepareStatement(sql);

            // 4.执行语句
            // 有变量则需要对变量赋值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally { // 无论是否有异常，均需完成的事情，关闭连接
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
            conn = getConnection();// 这里的JDBCUtils是额外创建的类，封装了连接和关闭数据库的方法
            pre = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pre.setObject(i + 1, args[i]);
            }

            re = pre.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData me = re.getMetaData();
            // 通过resultsetmetadata获取结果集中的列数
            int co = me.getColumnCount();
            if (re.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < co; i++) {
                    // 获取列值
                    Object columValue = re.getObject(i + 1);
                    // 获取每个列的列名
                    // getColumnname不常用
                    // getColumnLabel可以获取别名，当数据库表不一致时，在语句中设置别名更方便

                    String columnName = me.getColumnLabel(i + 1);
                    // 给p对象指定的columnname属性，赋值为columnvalue，通过反射

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
            conn = getConnection();// 这里的JDBCUtils是额外创建的类，封装了连接和关闭数据库的方法
            pre = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pre.setObject(i + 1, args[i]);
            }

            re = pre.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData me = re.getMetaData();
            // 通过resultsetmetadata获取结果集中的列数
            int co = me.getColumnCount();
            while (re.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < co; i++) {
                    // 获取列值
                    Object columValue = re.getObject(i + 1);
                    // 获取每个列的列名
                    // getColumnname不常用
                    // getColumnLabel可以获取别名，当数据库表不一致时，在语句中设置别名更方便

                    String columnName = me.getColumnLabel(i + 1);
                    // 给p对象指定的columnname属性，赋值为columnvalue，通过反射

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
