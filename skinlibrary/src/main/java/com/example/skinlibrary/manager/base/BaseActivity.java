package com.example.skinlibrary.manager.base;

import android.app.Activity;
import android.os.Bundle;

import com.example.skinlibrary.manager.listener.ISkinUpdate;
import com.example.skinlibrary.manager.loader.SkinInflaterFactory;
import com.example.skinlibrary.manager.loader.SkinManager;

public class BaseActivity extends Activity implements ISkinUpdate {

    private SkinInflaterFactory skinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skinInflaterFactory = new SkinInflaterFactory();
        getLayoutInflater().setFactory(skinInflaterFactory);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    public void onThemeUpdate() {
        skinInflaterFactory.applySkin();
    }
}
