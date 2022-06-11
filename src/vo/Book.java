package vo;

public class Book {
	private String ISBN;// 图书编号
	private String name;// 书名
	private String category;// 类别
	private String author;// 作者
	private String press;// 出版社
	private String publicationDate;// 出版日期
	private String recordDate;// 登记日期

	public Book() {
	}

	public Book(String iSBN, String name, String category, String author, String press, String publicationDate,
			String recordDate) {
		ISBN = iSBN;
		this.name = name;
		this.category = category;
		this.author = author;
		this.press = press;
		this.publicationDate = publicationDate;
		this.recordDate = recordDate;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String toString() {
		return String.format("%-11s\t%-10s\t%-8s\t%-10s\t%-14s\t%-10s\t%-10s", ISBN, name, category, author, press,
				publicationDate, recordDate);
	}

	public String tostring() {
		return String.format("insert into book values(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\");", ISBN, name,
				category, author, press, publicationDate, recordDate);
	}

}
