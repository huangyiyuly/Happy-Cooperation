package com.hudongfenxiang.utils;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *.
 *
 * @FileName : Decoder.java
 * @Encoding : UTF-8
 * @Package : com.lezhai365.cms.utils
 * @Link         :  http://lezhai365.com
 * @Created on   :  2013-8-13, 5:31:27
 * @Author       :  Hui.Wang [wanghui@lezhai365.com]
 * @Version      :  1.0
 * @Copyright   : Copyright(c) 2013 西安乐宅网络科技有限公司
 * @Description  :
 *     解码(解密)工具
 */
public class Decoder {

    /**
     * 构造函数.
     */
    public Decoder() {
    }

    /**
     * 将16进制字符串转换成字节数组.
     * @param strHex 16进制字符串
     * @return 字节数组.
     */
    public static byte[] hexStringToByteArray(String strHex) {
        byte[] theResult = null;
        byte[] byteArr = new byte[strHex.length() / 2];
        for (int i = 0; i < byteArr.length; i++) {
            byteArr[i] = (byte) Integer.parseInt(strHex.substring(i*2, i*2+2), 16);
        }
        theResult = byteArr;
        return theResult;
    }

    /**
     * 对称解密方法
     *
     * @param byteSource 需要解密的数据
     * @return 经过解密的数据
     * @throws Exception
     */
    public static byte[] symmetricDecrypto(byte[] byteSource) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int mode = Cipher.DECRYPT_MODE;
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            byte[] keyData = { 9, 1, 3, 7, 2, 1, 2, 0 };
            DESKeySpec keySpec = new DESKeySpec(keyData);
            Key key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, key);
            int blockSize = cipher.getBlockSize();
            int position = 0;
            int length = byteSource.length;
            boolean more = true;
            while (more) {
                if (position + blockSize <= length) {
                    baos.write(cipher.update(byteSource, position, blockSize));
                    position += blockSize;
                } else {
                    more = false;
                }
            }
            if (position < length) {
                baos.write(cipher.doFinal(byteSource, position, length
                    - position));
            } else {
                baos.write(cipher.doFinal());
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            baos.close();
        }
    }

    public static String symmetricDecrypto(String strSource) throws Exception {
        byte[] byteSource = hexStringToByteArray(strSource);
        byte[] byteArr = symmetricDecrypto(byteSource);
        String result = new String(byteArr);
        return result;
    }
    
    public static void main(String[] args) {
        byte[] byteArr = Decoder.hexStringToByteArray("7b9cabe7dbd6c576");
        try {
            byte[] byteArr2 = Decoder.symmetricDecrypto(byteArr);
            System.out.println(new String(byteArr2));
        } catch (Exception ex) {
            Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println(new String(Decoder.hexStringToByteArray("ced2")));
    }
}
