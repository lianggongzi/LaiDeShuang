package com.example.administrator.laideshuang.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.laideshuang.NumberBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018\11\6 0006.
 * 导入资料的数据库
 */

public class SerialDao {

    private MyOpenHelper helper;
    private SQLiteDatabase db;

    public SerialDao(Context context) {
        helper = new MyOpenHelper(context);
    }

    //写增删改查的方法
    public void init() {
        //打开数据库
        db = helper.getReadableDatabase();
    }

    //添加的方法
    public boolean insert(NumberBean numberBean) {
        boolean isExist = isNewsExist(numberBean);
        if (isExist) {
            db.close();
            return false; //返回添加失败
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("waibuNumber", numberBean.getWaibuNumber());
            contentValues.put("aoshiNumber", numberBean.getAoshiNumber());
            contentValues.put("fenyunNumber", numberBean.getFenyunNumber());
            db.insert("Customer", null, contentValues);
            db.close();
            return true;//返回添加成功
        }
    }


    //删除的方法
    public void delete() {
        init();
        //根据newsURL进行数据删除
        db.delete("Customer", null, null);
        db.close();
    }


//    //查询的方法-多条件查询
//    public String select(String str) {
//        init();
//        String companyName = null;
//        Cursor cursor = db.query("Customer", null, "fenyunNumber = ?", new String[]{str}, null, null, null);
////        Cursor cursor = db.query("Customer", null, null, null, null, null, null);
////        Cursor cursor = db.query("SerialNumberBiao1",null,"model = ? and brand = ?", new String[]{str,s},null,null,null);
//        while (cursor.moveToNext()) {
//            companyName = cursor.getString(cursor.getColumnIndex("name"));
//        }
//        return companyName;
//    }

    //查询的方法
    public List<NumberBean> select1(String str){
        init();
        List<NumberBean> list = new ArrayList<>();
        Cursor cursor = db.query("Customer", null, "fenyunNumber = ?", new String[]{str}, null, null, null);
        while (cursor.moveToNext()) {
            String waibuNumber= cursor.getString(cursor.getColumnIndex("waibuNumber"));
            String aoshiNumber= cursor.getString(cursor.getColumnIndex("aoshiNumber"));
            String fenyunNumber= cursor.getString(cursor.getColumnIndex("fenyunNumber"));
            NumberBean numberBean = new NumberBean();
            numberBean.setAoshiNumber(aoshiNumber);
            numberBean.setWaibuNumber(waibuNumber);
            numberBean.setFenyunNumber(fenyunNumber);
            list.add(numberBean);
        }
        return  list;
    }


    //判断是否存在
    public boolean isNewsExist(NumberBean numberBean) {
        init();
        Cursor cursor = db.query("Customer", null, "fenyunNumber = ?", new String[]{numberBean.getFenyunNumber()}, null, null, null);
//        Log.i("Tag",newsInfo.getUrl());
        if (cursor.moveToFirst()) {
            return true; // 已经存在该数据
        } else {
            return false;//不存在
        }
    }
}
