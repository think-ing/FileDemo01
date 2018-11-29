package com.mzw.filedemo01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by think on 2018/11/28.
 */

public class FileUtils {
    private static final int numOfEncAndDec = 0x99; //加密解密秘钥
    private static int dataOfFile = 0; //文件字节内容
    private static String charsetName = "utf-8";

    //文件加密
    public static void EncFile(File srcFile, File encFile) throws Exception {
        if(!srcFile.exists()){
            return;
        }
        if(!encFile.exists()){
            encFile.createNewFile();
        }
        InputStream fis  = new FileInputStream(srcFile);
        OutputStream fos = new FileOutputStream(encFile);
        while ((dataOfFile = fis.read()) > -1) {
            fos.write(dataOfFile^numOfEncAndDec);
        }
        fis.close();
        fos.flush();
        fos.close();
    }

    //文件解密
    public static void DecFile(File encFile, File decFile) throws Exception {
        if(!encFile.exists()){
            return;
        }
        if(!decFile.exists()){
            decFile.createNewFile();
        }

        InputStream fis  = new FileInputStream(encFile);
        OutputStream fos = new FileOutputStream(decFile);
        while ((dataOfFile = fis.read()) > -1) {
            fos.write(dataOfFile^numOfEncAndDec);
        }

        fis.close();
        fos.flush();
        fos.close();
    }

    //文件写入
    public static void writeFile(File file,String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fos,charsetName);
        writer.write(content);
        writer.close();
        fos.close();
    }

    //文件读取
    public static String readFile(File file) throws IOException {
        char[] buffer = null;

        FileInputStream bis = new FileInputStream(file);
        InputStreamReader reader=new InputStreamReader(bis,"utf-8");
        int len;
        buffer = new char[bis.available()];
        do {
            reader.read(buffer);
        }while ((len = reader.read()) != -1);
        //br.read() != -1  执行这个条件的时候其实它已经读取了一个字符了   所以 以下代码每一行都会少一个字
//        while ((len = reader.read()) != -1) {
//            reader.read(buffer);
//        }
        reader.close();
        bis.close();

        return new String(buffer);
    }


}
