package com.easychat.webserver.utils;

import java.math.BigInteger;
import java.util.UUID;
import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * Created by yonah on 15-11-13.
 */
public class CommonUtils {

    /**
     * 生成token
     * @return 生成一个UUID并返回
     */

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 进行md5加密
     * @return 返回经过md5加密后的字符串
     */

    public static String md5(String password) {
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            MD5.update(password.getBytes());
            String pwd = new BigInteger(1, MD5.digest()).toString(16);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    /**     * 验证Email
     * * @param email email地址，格式：zhang@gmail.com，zhang@xxx.com.cn，xxx代表邮件服务商
     * * @return 验证成功返回true，验证失败返回false
     * */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }
    /**
     * 验证用户名
     * @return 用户名合法则返回true,否则返回false
     */
    public static boolean checkName(String name){
        String regex="[0-9a-zA-Z_]{6,10}";
        return Pattern.matches(regex, name);
    }
    /**
     * 验证密码
     * @return 密码合法则返回true,否则返回false
     */
    public static boolean checkPassword(String password){
        String regex="[0-9a-zA-Z]{6,10}";
        return Pattern.matches(regex, password);
    }
}
