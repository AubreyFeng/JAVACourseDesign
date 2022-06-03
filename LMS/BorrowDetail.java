public class BorrowDetail {
    private int id;// ���߱��
    private String ISBN;// �鼮���
    private String borrowDate;// ��������
    private String returnDate;// �滮����
    private String fine;// ����

    public BorrowDetail() {
    }

    public BorrowDetail(int id, String iSBN, String borrowDate, String returnDate, String fine) {
        this.id = id;
        ISBN = iSBN;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String toString() {
        return id + "\t" + ISBN + "\t" + borrowDate + "\t" + returnDate + "\t" + fine;
    }

}
