package com.league.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pfy on 2015/11/7.
 */
public class Constants {
    public static String PHONENUM = "1";
    public static String HuanXinID;
    public static String HuanxinPwd;
    public static List<Activity> collection=new ArrayList<Activity>();
    public static void startActivity(Context context,Class<?> clazz){
        Intent intent = new Intent(context,clazz);
        context.startActivity(intent);
    }
    public static void finishAllActivities(){
        for(Activity activity:collection){
            activity.finish();
        }
    }
    public static void addIntoCollection(Activity activity){
        collection.add(activity);
    }
    public static final String RADIOSELECTKIND = "radioSelectKind";
    public static final String RADIOSELECEDTINDEX = "radioSelectedIndex";
    public static final int RADIODEDREE = 1;
    public static final int RADIOPROFESSION = 2;
    public static final int RADIOSEX = 3;
    public static final int RADIOAGE = 4;
    public static final int RADIOHOBBY = 5;
    public static final int RADIORECOMMENDATION = 6;

    public static final List<String> DEGREEITEMS = Arrays.asList("初中及初中以下", "高中", "大专", "本科", "硕士", "博士");
    public static final List<String> SEXITEMS = Arrays.asList("女","男");
    public static final List<String> AGEITEMS = Arrays.asList("20岁以下","20-25岁","25-30岁","30-35岁","35-40岁","40岁以上");
    public static final int REQUESTDEGREE = 1;
    public static final int REQUESTPROFESSION = 2;
    public static final String ProfessinListName = "professionListName";
    public static final String SingleInfoName = "singleInfoName";
    public static final String RecommendationListName = "recommendationListName";
    public static final String HobbyListName = "hobbyListName";
    public static final String SharePrefrencesName = "pfy";
    public static final String SkipGuideAcitivity = "skipguidactivity";
    public static final String DownloadPath = "/mnt/sdcard/Download/";

    public static final String TenYuanThree = "tenyuanthree";
    public static final String TenYuanMore = "tenyuanmore";
    public static final String TenYuanDetail="tenyuandetail";
    public static final String OneYuanDetail="oneyuandetail";
    public static final String TenyuanGrabRecords="tenyuangrabrecords";
    public static final String OneYuanDetailRecords="oneyuandetailrecords";

    public static final String temDirStr = Environment.getExternalStorageDirectory() + "/league";
    public static final String tempImageDirStr = temDirStr + "/images";
    public static File tempDir;
    public static File temImageDir;
    public static String PrefixUrl = "file:///android_asset/index.html#";

    public static File getTempDir() {
        if (tempDir != null)
            return tempDir;
        tempDir = new File(temDirStr);
        if (!tempDir.exists())
            tempDir.mkdirs();
        return tempDir;
    }

    public static File getTempImageDir() {
        if (temImageDir != null)
            return temImageDir;
        temImageDir = new File(tempImageDirStr);
        if (!temImageDir.exists())
            temImageDir.mkdirs();
        return temImageDir;
    }



}
