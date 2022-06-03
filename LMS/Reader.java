public class Reader {
    private int id;// 读者编号
    private String name;// 姓名
    private String gender;// 性别
    private String birthday;// 生日
    private String phoneNum;// 联系电话
    private String regDate;// 注册日期
    private int borrowedNum;// 已借书数量

    public Reader() {
    }

    public Reader(int id, String name, String gender, String birthday, String phoneNum, String regDate,
            int borrowedNum) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phoneNum = phoneNum;
        this.regDate = regDate;
        this.borrowedNum = borrowedNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getBorrowedNum() {
        return borrowedNum;
    }

    public void setBorrowedNum(int borrowedNum) {
        this.borrowedNum = borrowedNum;
    }

    public String toString() {
        return id + "\t" + name + "\t" + gender + "\t" + birthday + "\t" + phoneNum + "\t" + regDate + "\t"
                + borrowedNum;
    }

}
