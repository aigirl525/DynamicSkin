package com.example.skinlibrary.manager.entity;

import android.view.View;

public abstract class SkinAttr {
    protected static final String RES_TYPE_NAME_COLOR = "color";
    protected static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    //android:background="@android:color/holo_red_light"

    //background
    public String attrName;
    //@17170454
    public int attrValueRefId;
    //holo_red_light
    public String attrValueRefName;
    //color
    public String attrValueTypeName;

    public abstract void apply(View view);

    @Override
    public String toString() {
        return "SkinAttr{" +
                "attrName='" + attrName + '\'' +
                ", attrValueRefId=" + attrValueRefId +
                ", attrValueRefName=" + attrValueRefName +
                ", attrValueTypeName=" + attrValueTypeName +
                '}';
    }
}
