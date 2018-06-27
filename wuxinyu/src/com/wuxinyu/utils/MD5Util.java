package com.wuxinyu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类函数
 * <p>
 *     将用户的密码用MD5进行加密
 * </p>
 */
public class MD5Util {

    /**
     * 将源字符串使用MD5加密为字节数组
     * @param source 源字符串
     * @return MD5计算后的hash值
     * @throws NoSuchAlgorithmException
     */
    private static byte[] encode2Bytes(String source) throws NoSuchAlgorithmException {
        //获取MD5实例
        MessageDigest md = MessageDigest.getInstance("MD5");
        //重置摘要以供进一步使用
        md.reset();
        //使用指定的byteBuffer更新摘要
        md.update(source.getBytes());
        //返回hash值
        return md.digest();
    }

    /**
     * 将源字符串使用MD5加密为32位16进制数
     * @param source 源字符串
     * @return 32位16进制数
     * @throws NoSuchAlgorithmException
     */
    public static String encode2Hex(String source) throws NoSuchAlgorithmException {
        //获得源字符串的字节数组
        byte[] data = encode2Bytes(source);
        //最终生成的字符串
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            //补位
            if(hex.length() == 1){
                hexString.append(0);
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 验证字符串是否匹配
     * @param source 待验证字符串
     * @param know 已使用MD5加密的字符串 一般保存于数据库中（经过MD5加密后的用户的密码）
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean validate(String source, String know) throws NoSuchAlgorithmException {
        return know.equals(encode2Hex(source));
    }

//    public static void main(String[] args) {
//        try {
//            String str = MD5Util.encode2Hex("aa");
//            System.out.println(str);
//            System.out.println(MD5Util.validate("aa",str));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
}
