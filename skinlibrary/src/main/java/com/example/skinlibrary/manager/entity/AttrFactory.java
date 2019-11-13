package com.example.skinlibrary.manager.entity;

public class AttrFactory {

    public static SkinAttr getSkinAttr(String attrName,int id,String entryName,String typeName){

        SkinAttr mSkinAttr = null;

        if("background".equals(attrName)){
            mSkinAttr = new BackgroundAttr();
        }
        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = id;
        mSkinAttr.attrValueRefName = entryName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }
    public static boolean isSupportedAttr(String attrName){
        if("background".equals(attrName)){
            return true;
        }

        return false;
    }
}
