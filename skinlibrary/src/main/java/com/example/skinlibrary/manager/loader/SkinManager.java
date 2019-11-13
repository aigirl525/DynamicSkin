package com.example.skinlibrary.manager.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.example.skinlibrary.manager.listener.ISkinUpdate;

import java.io.File;
import java.lang.reflect.Method;

public class SkinManager {
    private Context context;
    private  static SkinManager instance;
    private String skinPackageName;
    private Resources mResources;


    public static SkinManager getInstance() {
        if (instance == null){
            instance = new SkinManager();
        }
        return instance;
    }

    public SkinManager() {
    }
   public void init(Context ct){
        context = ct.getApplicationContext();
   }
   public void load(String skinPackagePath){

        new AsyncTask<String,Void, Resources>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Resources doInBackground(String... strings) {
                try {
                    if (strings.length == 1){
                        String skinPath = strings[0];
                        File file = new File(skinPath);
                        if (file == null || !file.exists()){
                            return null;
                        }
                        PackageManager packageManager = context.getPackageManager();
                        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(skinPath,PackageManager.GET_ACTIVITIES);
                        skinPackageName = packageInfo.packageName;

                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssertPath = assetManager.getClass().getMethod("addAssetPath",String.class);
                        addAssertPath.invoke(assetManager,skinPath);

                        Resources superRes = context.getResources();
                        Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(),
                                superRes.getConfiguration());

                        return skinResource;
                    }
                    return  null;
                }catch (Exception e){
                    return  null;
                }
            };
            @Override
            protected void onPostExecute(Resources resources) {
                super.onPostExecute(resources);
                mResources = resources;
                if (mResources != null)
                observe.onThemeUpdate();
            };
        }.execute(skinPackagePath);

   }
    public int getColor(int resId){
        int originColor = context.getResources().getColor(resId);
        if(mResources == null){
            return originColor;
        }

        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
        int trueColor = 0;

        try{
            trueColor = mResources.getColor(trueResId);
        }catch(Resources.NotFoundException e){
            e.printStackTrace();
            trueColor = originColor;
        }

        return trueColor;
    }
    private ISkinUpdate observe;

    public void attach(ISkinUpdate obs){
        observe = obs;
    }
}
