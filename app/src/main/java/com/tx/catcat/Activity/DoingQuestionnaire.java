package com.tx.catcat.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.tx.catcat.Adapter.DoingQuestionnaireAdapter;
import com.tx.catcat.Object.Questionnaire;
import com.tx.catcat.Object.subject.FatherSubject;
import com.tx.catcat.R;
import com.tx.catcat.util.CatUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.LinkedList;

public class DoingQuestionnaire extends BaseActivity {

    private static final int REQUEST_PICK_IMAGE =11101 ;
    public static ViewPager Viewpager;
    public static Boolean isbug=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_doing_questionnaire);
        try {
        String path=null;
        if (getIntent().getData()!=null){
            path=getIntent().getData().toString().replace("file://","");
        }
        Viewpager= findViewById(R.id.DoingQuestionnaire_viewpager);
        //下面这行阴间代码为获取点击的item对应的Questionnaire对象后开始执行LoadQuestionnaire方法
        LoadQuestionnaire(CatUtil.getQuestionnaireList(DoingQuestionnaire.this,path).get(getIntent().getIntExtra("index",0)));
    } catch (Exception e) {
            BaseActivity.exception=BaseActivity.exception+"\r\n"+e.getMessage();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for(StackTraceElement s:stackTrace){
                BaseActivity.exception=BaseActivity.exception+"\r\n"+s;
            }

            getbug();
        }}

    /**
     * 用对应问卷的Questionnaire对象来将问卷的各个问题加载成对象放入ViewPage中
     * @param questionnaire
     */
    private void LoadQuestionnaire(Questionnaire questionnaire) {
        try {
            LinkedList<View> viewlist = new LinkedList<>();
            LayoutInflater from = LayoutInflater.from(DoingQuestionnaire.this);
            for (FatherSubject obj : questionnaire.getQuestionnaireObjectList()) {
                viewlist.add(from.inflate(CatUtil.SelectLayoutIdBytype(obj), null));
            }
            DoingQuestionnaireAdapter DQA = new DoingQuestionnaireAdapter(viewlist, questionnaire.getQuestionnaireObjectList());
            Viewpager.setAdapter(DQA);
        }catch (Exception e){
            BaseActivity.exception=BaseActivity.exception+"\r\n"+e.getMessage();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for(StackTraceElement s:stackTrace){
                BaseActivity.exception=BaseActivity.exception+"\r\n"+s;
            }

            getbug();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getbug();
    }

    /**
     * 打开相册获取图片
     * @param requestCode The request code passed in {@link #requestPermissions(
     * android.app.Activity, String[], int)}
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private final String IMAGE_TYPE = "image/*";
    public void getImage() {
        Intent getAlbum = new Intent(Intent.ACTION_PICK);
        getAlbum.setType(IMAGE_TYPE);
        startActivityForResult(getAlbum, IMAGE_CODE);
    }

    public static String nowImagePath="";

    private final int IMAGE_CODE = 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        ContentResolver resolver = getContentResolver();
        if (requestCode == IMAGE_CODE) {
                Uri originalUri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                nowImagePath=cursor.getString(column_index);
                DoingQuestionnaireAdapter.setImage(cursor.getString(column_index));
        }
    }
}