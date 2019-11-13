package com.example.dynamicskin;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.skinlibrary.manager.base.BaseActivity;
import com.example.skinlibrary.manager.loader.SkinManager;

import java.io.File;

public class MainActivity extends BaseActivity {
    private static final String SKIN_NAME = "BlackFantacy.skin";
    private static final String SKIN_DIR = Environment
            .getExternalStorageDirectory() + File.separator + SKIN_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().init(this);
        setContentView(R.layout.activity_main);

        //注意：使用运行时权限
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    public void load(View view){
        File skin = new File(SKIN_DIR);

        if(skin == null || !skin.exists()){
            Toast.makeText(getApplicationContext(), "请检查" + SKIN_DIR + "是否存在", Toast.LENGTH_SHORT).show();
            return;
        }

        SkinManager.getInstance().load(skin.getAbsolutePath());
    }
}
