/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudongfenxiang.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @FileName : PropertyUtil.java
 * @Encoding : UTF-8
 * @Package : com.lezhai365.utils
 * @Link         :  http://lezhai365.com
 * @Created on   :  Sep 11, 2013, 11:29:26 AM
 * @Author       :  Hui.Wang [wanghui@lezhai365.com]
 * @Version      :  1.0
 * @Copyright    :  Copyright(c) 2013 西安乐宅网络科技有限公司
 * @Description  :
 *      property文件工具类
 */
public class PropertyUtil {


    /**
     * 从一个property文件中读取数据
     *
     * @param fileName
     * @return
     */
    public static Properties read(String fileName) {
        fileName = ResourceUtil.getRealFileName(fileName);
        Properties prop = new Properties();
        BufferedInputStream bu = null;
        try {
            bu = new BufferedInputStream(new FileInputStream(fileName));
            prop.load(bu);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block  
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } finally {
            try {
                bu.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }
        return prop;
    }

    /**
     * 把一个map写入 property文件中
     *
     * @param fileName
     * @param pro
     * @return
     */
    public static Boolean write(String fileName, Map<String, Object> pro) {
        fileName = ResourceUtil.getRealFileName(fileName);
        Boolean result = false;
        Properties prop = new Properties();
        try {
            File f = new File(fileName);
            //配置文件不存在就创建一个
            if (!f.exists()) {
                f.createNewFile();
            }
            InputStream fis = new FileInputStream(fileName);
            //从输入流中读取属性列表（键和元素对）
            prop.load(fis);
            OutputStream fos = new FileOutputStream(fileName);
            for (Map.Entry<String, Object> entry : pro.entrySet()) {
                String parameterName = entry.getKey();
                String parameterValue = entry.getValue().toString();
                prop.setProperty(parameterName, parameterValue);
            }
            prop.store(fos, "Update '" + pro.toString() + "' value");
        } catch (IOException e) {
            System.err.println("Visit " + fileName + " for updating " + pro.toString() + " value error");
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(":" +System.getProperty("file.separator"));;
        Map m = new HashMap<String, String>();
        m.put("h", "kdfikk");
        m.put("l", "kkkk");
        m.put("u", "909");
        m.put("d", "k8");
        write("conf/ok.properties", m);
    }
}
