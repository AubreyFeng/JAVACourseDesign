package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
	public static boolean isPasswd(String passwd) {
		String regExp = "^.*(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])\\w{6,}"; // 6位数字
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(passwd);
		return matcher.find();
	}

	public static boolean isISBN(String isbn) {
		// 不能仅使用正则表达式来验证ISBN，因为最后一位是使用校验算法计算的，本节中的正则表达式仅验证ISBN的格式
		String regExp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(isbn);
		return matcher.find();
	}
}
