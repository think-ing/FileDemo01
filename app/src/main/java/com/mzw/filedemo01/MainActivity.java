package com.mzw.filedemo01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String SDPath;//SD根目录
    private EditText tv01;
    private TextView tv02;
    private File srcFile,encFile,decFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv01 = findViewById(R.id.id_tv01);
        tv02 = findViewById(R.id.id_tv02);

        findViewById(R.id.id_readBtn).setOnClickListener(this);
        findViewById(R.id.id_writeBtn).setOnClickListener(this);
        findViewById(R.id.id_btn_a).setOnClickListener(this);
        findViewById(R.id.id_btn_b).setOnClickListener(this);
        findViewById(R.id.id_btn_c).setOnClickListener(this);
        findViewById(R.id.id_btn_d).setOnClickListener(this);

        String state= Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            SDPath = Environment.getExternalStorageDirectory()+"/zzz";//SD根目录

        }else {
            new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("SD卡不可用")
                    .setPositiveButton("确定",null).create().show();
        }
        File dirFile = new File(SDPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }

        srcFile = new File(SDPath+"/a.mzw"); //初始文件
        encFile = new File(SDPath+"/b.by"); //加密文件
        decFile = new File(SDPath+"/c.mzw"); //解密文件

    }

    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()){
                case R.id.id_writeBtn:
                    if(!srcFile.exists()){//不存在 创建
                        srcFile.createNewFile();
                    }
                    FileUtils.writeFile(srcFile,tv01.getText().toString().trim());
                    Log.i("---mzw---","写入成功...");
                    break;
                case R.id.id_readBtn:
                    if(!srcFile.exists()){
                        Log.i("---mzw---","读取文件不存在...");
                        return;
                    }
                    String str = FileUtils.readFile(srcFile);
                    tv02.setText(str);
                    Log.i("---mzw---","读取成功...");
                    break;
                case R.id.id_btn_a:
                    if(!srcFile.exists()){
                        Log.i("---mzw---","加密文件不存在...");
                        return;
                    }
                    FileUtils.EncFile(srcFile, encFile);
                    Log.i("---mzw---","加密成功..."+encFile.getPath());
                    break;
                case R.id.id_btn_b:
                    if(!encFile.exists()){
                        Log.i("---mzw---","解密文件不存在...");
                        return;
                    }
                    FileUtils.DecFile(encFile, decFile);
                    Log.i("---mzw---","解密成功..."+decFile.getPath());
                    break;
                case R.id.id_btn_c:
                    srcFile.delete();
                    break;
                case R.id.id_btn_d:
                    new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("你确定？？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            srcFile.delete();
                                            encFile.delete();
                                            decFile.delete();
                                        }
                            })
                            .setNegativeButton("取消",null)
//                            .setNeutralButton("取消",null)
                            .create().show();

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
