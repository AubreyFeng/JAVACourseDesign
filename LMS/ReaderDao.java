import java.util.ArrayList;

public class ReaderDao {
    public static boolean insertReader(Reader reader) {
        return JDBCUtil.exeUpdate("insert into reader values(?,?,?,?,?,?,?)", reader.getId(), reader.getName(),
                reader.getGender(), reader.getBirthday(), reader.getPhoneNum(), reader.getRegDate(),
                reader.getBorrowedNum());
    }

    public static boolean deleteReader(Reader reader) {
        return JDBCUtil.exeUpdate("delete from reader where Id = ?", reader.getId());
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
