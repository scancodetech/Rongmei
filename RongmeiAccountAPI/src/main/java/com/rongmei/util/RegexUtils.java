package com.rongmei.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean isMobileNO(String mobiles){
        String reg = "^1[356789]\\d{9}$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static boolean isEmailNO(String emails){
        String reg = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(emails);
        return m.matches();
    }
}
