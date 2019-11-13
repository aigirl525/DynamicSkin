package com.example.skinlibrary.manager.entity;

import android.view.View;

import com.example.skinlibrary.manager.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class SkinItem {
    public View view;

    public List<SkinAttr> attrs;

    public SkinItem() {
        this.attrs = new ArrayList<>();
    }

    public  void apply(){
        if (ListUtils.isEmpty(attrs)){
            return;
        }
        for (SkinAttr attr : attrs){
            attr.apply(view);
        }
    }

    public void clean(){
        if (ListUtils.isEmpty(attrs)){
            return;
        }
        for (SkinAttr attr : attrs){
            attr = null;
        }
    }
}
