import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class IO {
	public static Scanner sr = new Scanner(System.in);

	public static ArrayList<Book> readFromXLS(String fileName) {
		ArrayList<Book> list = new ArrayList<>();
		Workbook workbook = null;
		Sheet sheet = null;

		try {
			workbook = Workbook.getWorkbook(new File(fileName));
			sheet = workbook.getSheet(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int row = 1; row < sheet.getRows(); row++) {
			Book book = new Book(sheet.getCell(0, row).getContents(), sheet.getCell(1, row).getContents(),
					sheet.getCell(2, row).getContents(), sheet.getCell(3, row).getContents(),
					sheet.getCell(4, row).getContents(), sheet.getCell(5, row).getContents(),
					sheet.getCell(6, row).getContents());

			if (!book.getISBN().equals(""))
				list.add(book);
		}

		System.out.println("成功导入" + list.size() + "个商品！");
		return list;
	}

	public static ArrayList<Book> readFromKeyboard() {
		ArrayList<Book> list = new ArrayList<>();
		String str = sr.nextLine();

		while (!str.equalsIgnoreCase("end")) {
			String[] arr = str.split(",");
			Book book = new Book(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
			int i = 0;
			for (; i < list.size(); i++)
				if (arr[0].equals(list.get(i).getISBN()))
					System.out.println("该商品信息已导入，请重新输入!");

			if (i == list.size()) {
				list.add(book);
				System.out.println("增加成功!");
			}
			str = sr.nextLine();
		}

		return list;
	}

	public static void writeToXLS(ArrayList<BorrowDetail> list, String fileName) {
		try {
			WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
			WritableSheet sheet = book.createSheet("sheet1", 1);
			int row = 1;

			String[] title = { "读者编号", "书籍编号", "借阅日期", "归还日期", "罚金" };
			for (int i = 0; i < title.length; i++) {
				Label lable = new Label(i, 0, title[i]);
				sheet.addCell(lable);
			}

			for (BorrowDetail borrowDetail : list) {

				Label lable = new Label(0, row, borrowDetail.getId() + "");
				sheet.addCell(lable);
				lable = new Label(1, row, borrowDetail.getISBN());
				sheet.addCell(lable);
				lable = new Label(2, row, borrowDetail.getBorrowDate());
				sheet.addCell(lable);
				lable = new Label(3, row, borrowDetail.getReturnDate());
				sheet.addCell(lable);
				lable = new Label(4, row, borrowDetail.getFine());
				sheet.addCell(lable);
				row++;
			}

			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("成功写入 excel");
	}
}
