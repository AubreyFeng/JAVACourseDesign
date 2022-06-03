public class Reader {
    private int id;// ���߱��
    private String name;// ����
    private String gender;// �Ա�
    private String birthday;// ����
    private String phoneNum;// ��ϵ�绰
    private String regDate;// ע������
    private int borrowedNum;// �ѽ�������

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
