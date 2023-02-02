package com.tx.catcat.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {

    public  static String exception=null;
    public static HashMap<String,BaseActivity> ActivityList =new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityList.put(this.getClass().getSimpleName(),this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityList.remove(this.getClass().getSimpleName());
    }

    /**
     * 销毁指定activity
     * @param name 需要销毁的名称
     */
    public static void DestoryActivity(String name){
        ActivityList.get(name).finish();
        ActivityList.remove(name);
    }

    /**
     * 跳转到bug界面
     */
    public void getbug(){
        Intent intent = new Intent(BaseActivity.this, Debug.class);
        startActivity(intent);
    }
}
