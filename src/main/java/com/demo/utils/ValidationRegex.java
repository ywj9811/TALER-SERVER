package com.demo.utils;


import java.util.regex.Pattern;

public class ValidationRegex {
    //전화번호 형식 유효성 검사
    public static boolean isRegexPhonenumber(String target) {
        String regex = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        boolean checkedPhonenumber = pattern.matches(regex,target);
        return checkedPhonenumber;
    }
}
