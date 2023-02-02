package com.tx.catcat.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;


import com.tx.catcat.Adapter.ChooseQuestionnaireAdapter;
import com.tx.catcat.Object.Questionnaire;
import com.tx.catcat.R;
import com.tx.catcat.util.CatUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChooseQuestionnaire extends BaseActivity {

    String[] assetsList;
    ListView Listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_questionnaire);
        getQuestionnaireInformation();
        setListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        CatUtil.QuestionnaireList = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CatUtil.QuestionnaireList=null;
    }

    /**
     * 给listview设置适配器与item点击事件
     */
    private void setListView()  {
        Listview = findViewById(R.id.ChooseQuestionnaireListview);
        LinkedList<Questionnaire> arraylist=CatUtil.getQuestionnaireList(ChooseQuestionnaire.this,null);
        ChooseQuestionnaireAdapter chooseQuestionnaireAdapter=new ChooseQuestionnaireAdapter(ChooseQuestionnaire.this,R.layout.choose_questionnaire_item,arraylist);
        Listview.setAdapter(chooseQuestionnaireAdapter);
        Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CatUtil.Questionnaireposition=position;
                Intent intent=new Intent(ChooseQuestionnaire.this,DoingQuestionnaire.class);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取所有问卷名称组成的数组
     */
    private void getQuestionnaireInformation() {
        try {
            assetsList = CatUtil.getAssetsList(ChooseQuestionnaire.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}