package com.example.skinlibrary.manager.loader;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skinlibrary.manager.config.SkinConfig;
import com.example.skinlibrary.manager.entity.AttrFactory;
import com.example.skinlibrary.manager.entity.SkinAttr;
import com.example.skinlibrary.manager.entity.SkinItem;
import com.example.skinlibrary.manager.util.L;
import com.example.skinlibrary.manager.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class SkinInflaterFactory implements Factory {
    //存储在activity中需要换肤的view + attrs
    private List<SkinItem> skinItems = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        boolean isSkinEnable = attributeSet.getAttributeBooleanValue(SkinConfig.NAMESPACE,SkinConfig.ATTR_SKIN_ENABLE,false);

        if (!isSkinEnable){
            return null;
        }
        //获取需要换肤的view
        View view = createView(context,name,attributeSet);

        if (view == null){
            return null;
        }
        //获取view下需要动态修改值的属性
        parseSkinAttr(context,attributeSet,view);

        return view;
    }

    private View createView(Context context, String name,AttributeSet attributeSet){
        View view = null;
        try {
            //判断是不是自定义控件
            if (-1 == name.indexOf(".")){
                if ("View".equals(name)){
                    view = LayoutInflater.from(context).createView(name,"abdroid.view.",attributeSet);
                }
                if (view == null){
                    view = LayoutInflater.from(context).createView(name,"android.widget.",attributeSet);
                }
                if (view == null){
                    view = LayoutInflater.from(context).createView(name,"android.wibkit.",attributeSet);
                }
            }else {
                view  = LayoutInflater.from(context).createView(name,null,attributeSet);
            }
            L.i("about to create " + name);

        } catch (Exception e) {
            L.e("error while create 【" + name + "】 : " + e.getMessage());
            view = null;
        }
        return view;
    }

   private void parseSkinAttr(Context context,AttributeSet attributeSet,View view){
       //android:background="@android:color/holo_red_light"

        List<SkinAttr> viewAttrs = new ArrayList<>();

        for (int i = 0 ; i < attributeSet.getAttributeCount();i++){
            //background
            String attrName = attributeSet.getAttributeName(i);
            //@17170454
            String attrValue = attributeSet.getAttributeValue(i);
            if(!AttrFactory.isSupportedAttr(attrName)){
                continue;
            }
            if (attrValue.startsWith("@")){
                try {
                    //17170454
                   int id = Integer.parseInt(attrValue.substring(1));
                   //holo_red_light
                   String entryName = context.getResources().getResourceEntryName(id);
                   //color
                   String typeName = context.getResources().getResourceTypeName(id);

                   L.e(attrName+"/"+attrValue+"/"+id+"/"+entryName+"/"+typeName);

                   SkinAttr skinAttr = AttrFactory.getSkinAttr(attrName,id,entryName,typeName);
                   if (skinAttr != null){
                       viewAttrs.add(skinAttr);
                   }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }catch (Resources.NotFoundException e){
                    e.printStackTrace();
                }
            }
        }
        if (!ListUtils.isEmpty(viewAttrs)){
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;
            skinItems.add(skinItem);
            skinItem.apply();
        }

    }


    public void applySkin(){
        if(ListUtils.isEmpty(skinItems)){
            return;
        }

        for(SkinItem si : skinItems){
            if(si.view == null){
                continue;
            }
            si.apply();
        }
    }




















}
