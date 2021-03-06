package com.example.encryptsample.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 3DES（Triple DES、DESede，进行了三重DES加密的算法，对称加密算法）
 * 生成密钥
 * 3DES加密（对称加密）
 * 3DES解密
 */
public class TripleDESUtils {
    private TripleDESUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static byte[] key;

    static {
        try {
            key = initKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("AESUtil", "aes initKey error UnsupportedEncodingException");
        }
    }

    /**
     * 加密
     *
     * @param dataStr 需要加密的数据
     * @return 加密后的数据
     */
    public static String encrypt(String dataStr) {
        try {
            byte[] data = dataStr.getBytes();
            byte[] encryptData = encrypt(data, key);
            return ConvertUtils.bytes2HexString(encryptData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("AESUtil", "aes encrypt error NoSuchAlgorithmException");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AESUtil", "aes encrypt error " + e.getMessage());
        }
        return null;
    }

    /**
     * 解密
     *
     * @param dataStr 需要解密的数据
     * @return 解密后的数据
     */
    public static String decrypt(String dataStr) {
        try {
            byte[] data = ConvertUtils.hexString2Bytes(dataStr);
            byte[] decryptData = decrypt(data, key);
            return new String(decryptData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("AESUtil", "aes decrypt error NoSuchAlgorithmException");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AESUtil", "aes decrypt error " + e.getMessage());
        }
        return null;
    }

    /*
     * 生成密钥
     */
    private static byte[] initKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");   //密钥生成器
        keyGen.init(168);                                           //可指定密钥长度为112或168，默认为168
        SecretKey secretKey = keyGen.generateKey();                 //生成密钥
        return secretKey.getEncoded();                              //密钥字节数组
    }

    /*
     * 3DES 加密
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "DESede");         //恢复密钥

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");  //Cipher完成加密或解密工作类
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);                    //对Cipher初始化，解密模式
        byte[] cipherBytes = cipher.doFinal(data);                      //加密data
        return cipherBytes;
    }

    /*
     * 3DES 解密
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "DESede");         //恢复密钥

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");  //Cipher完成加密或解密工作类
        cipher.init(Cipher.DECRYPT_MODE, secretKey);                    //对Cipher初始化，解密模式
        byte[] plainBytes = cipher.doFinal(data);                       //解密data
        return plainBytes;
    }
}
