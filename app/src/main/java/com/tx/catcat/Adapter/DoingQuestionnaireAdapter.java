package com.tx.catcat.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.tx.catcat.Activity.BaseActivity;
import com.tx.catcat.Activity.DoingQuestionnaire;
import com.tx.catcat.CustomView.LocateTurret;
import com.tx.catcat.Object.Questionnaire;
import com.tx.catcat.Object.subject.FatherSubject;
import com.tx.catcat.Object.subject.Type_Finish;
import com.tx.catcat.Object.subject.Type_Float;
import com.tx.catcat.Object.subject.Type_Image;
import com.tx.catcat.Object.subject.Type_OneChoose3Box;
import com.tx.catcat.Object.subject.Type_String;
import com.tx.catcat.Object.subject.Type_TurretLocate;
import com.tx.catcat.R;
import com.tx.catcat.util.CatUtil;
import com.tx.catcat.util.QuestionnaireInterface;

import java.io.BufferedReader;
import java.util.LinkedList;

public class DoingQuestionnaireAdapter extends PagerAdapter {

    public static LinkedList<View> viewList;
    public static LinkedList<FatherSubject> subjectsList;

    public DoingQuestionnaireAdapter(LinkedList<View> viewlist,LinkedList<FatherSubject> test) {
        this.viewList = viewlist;
        this.subjectsList=test;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = viewList.get(position);
        FatherSubject fatherSubject = subjectsList.get(position);
        view= SetViewByType(fatherSubject.getType(),fatherSubject,view,position);
        view=SetInputListener(fatherSubject,view,position);
        container.addView(view);
        return view;
    }

    /**
     * 给各种类型的问卷设置输入事件
     * @param view
     * @return
     */
    private View SetInputListener(FatherSubject fatherSubject,View view,int position) {
      try{
        switch (fatherSubject.getType()){
            case"String":
            case"Float":
                EditText e = view.findViewById(R.id.String_Edittext);
                e.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        FatherSubject a=subjectsList.get(position);
                        a.RunMethod("1");
                        a.setOutputWords(s.toString());
                        Questionnaire questionnaire = CatUtil.QuestionnaireList.get(CatUtil.Questionnaireposition);
                        questionnaire.setSubjectObject(a,position);
                        CatUtil.QuestionnaireList.set(CatUtil.Questionnaireposition,questionnaire);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                return view;
            case "OneChoose3Box" :
                Type_OneChoose3Box a=(Type_OneChoose3Box)subjectsList.get(position);
                        RadioButton R1 = view.findViewById(R.id.onechoose_3box_radio_1);
                        RadioButton R2 = view.findViewById(R.id.onechoose_3box_radio_2);
                        RadioButton R3 = view.findViewById(R.id.onechoose_3box_radio_3);
                        R1.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View v) {
                                a.RunMethod("1");
                                if(R1.isChecked()){
                                    a.setOutputWords(a.getBox1_output());
                                }else if (R2.isChecked()){
                                    a.setOutputWords(a.getBox2_output());
                                }else if (R3.isChecked()){
                                    a.setOutputWords(a.getBox3_output());
                                }
                                Questionnaire questionnaire = CatUtil.QuestionnaireList.get(CatUtil.Questionnaireposition);
                                questionnaire.setSubjectObject(a,position);
                                CatUtil.QuestionnaireList.set(CatUtil.Questionnaireposition,questionnaire);
                            }
                        });
                        R2.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View v) {
                                a.RunMethod("2");
                                if(R1.isChecked()){
                                    a.setOutputWords(a.getBox1_output());
                                }else if (R2.isChecked()){
                                    a.setOutputWords(a.getBox2_output());
                                }else if (R3.isChecked()){
                                    a.setOutputWords(a.getBox3_output());
                                }
                                Questionnaire questionnaire = CatUtil.QuestionnaireList.get(CatUtil.Questionnaireposition);
                                questionnaire.setSubjectObject(a,position);
                                CatUtil.QuestionnaireList.set(CatUtil.Questionnaireposition,questionnaire);
                            }
                        });
                        R3.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View v) {
                                a.RunMethod("3");
                                if(R1.isChecked()){
                                    a.setOutputWords(a.getBox1_output());
                                }else if (R2.isChecked()){
                                    a.setOutputWords(a.getBox2_output());
                                }else if (R3.isChecked()){
                                    a.setOutputWords(a.getBox3_output());
                                }
                                Questionnaire questionnaire = CatUtil.QuestionnaireList.get(CatUtil.Questionnaireposition);
                                questionnaire.setSubjectObject(a,position);
                                CatUtil.QuestionnaireList.set(CatUtil.Questionnaireposition,questionnaire);

                            }
                        });
                        return view;
            case "Image":
                type_image=(Type_Image)subjectsList.get(position);
                nowImage= view.findViewById(R.id.Image_imagebutton);
                nowImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type_image.RunMethod("1");
                        DoingQuestionnaire doingQuestionnaire = (DoingQuestionnaire) BaseActivity.ActivityList.get("DoingQuestionnaire");
                        doingQuestionnaire.getImage();
                        Questionnaire questionnaire = CatUtil.QuestionnaireList.get(CatUtil.Questionnaireposition);
                        questionnaire.setSubjectObject(type_image,position);
                        CatUtil.QuestionnaireList.set(CatUtil.Questionnaireposition,questionnaire);
                    }
                });
                break;
            case "TurretLocate":
                Type_TurretLocate a2 = (Type_TurretLocate) subjectsList.get(position);
                LocateTurret turret = view.findViewById(R.id.Locate_turret);
                turret.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction()==MotionEvent.ACTION_UP){
                            a2.setOutputWords(turret.XY);
                            Questionnaire questionnaire = CatUtil.QuestionnaireList.get(CatUtil.Questionnaireposition);
                            questionnaire.setSubjectObject(a2,position);
                            CatUtil.QuestionnaireList.set(CatUtil.Questionnaireposition,questionnaire);
                        }
                        return false;
                    }
                });
                break;
        }
      }catch (Exception e){
          BaseActivity.exception=BaseActivity.exception+"\r\n"+e.getMessage();
          StackTraceElement[] stackTrace = e.getStackTrace();
          for(StackTraceElement s:stackTrace){
              BaseActivity.exception=BaseActivity.exception+"\r\n"+s;
          }
          DoingQuestionnaire.isbug=true;
          BaseActivity.DestoryActivity(DoingQuestionnaire.class.getSimpleName());
      }
        return view;
    }

    /**
     * 根据Subject对象类型将view设置好
     * @param type
     * @param fatherSubject
     * @param view
     * @param position
     * @return
     */
    private View SetViewByType(String type,FatherSubject fatherSubject,View view,int position) {
       try {
           switch (type) {
               case "String":
                   TextView p = (TextView) view.findViewById(R.id.String_Positon);
                   p.setText(String.valueOf(position + 1));
                   TextView text = (TextView) view.findViewById(R.id.String_Text);
                   text.setText(((Type_String) fatherSubject).getText());
                   Button b = view.findViewById(R.id.String_nextButton);
                   b = SetButtontoNext(b, fatherSubject, position);
                   Button bb = view.findViewById(R.id.String_lastButton);
                   bb = SetButtontoLast(bb, position);
                   return view;
               case "Float":
                   TextView p2 = (TextView) view.findViewById(R.id.String_Positon);
                   p2.setText(String.valueOf(position + 1));
                   TextView text2 = (TextView) view.findViewById(R.id.String_Text);
                   text2.setText(((Type_Float) fatherSubject).getText());
                   EditText edit = view.findViewById(R.id.String_Edittext);
                   edit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                   Button b2 = view.findViewById(R.id.String_nextButton);
                   b2 = SetButtontoNext(b2, fatherSubject, position);
                   Button bb2 = view.findViewById(R.id.String_lastButton);
                   bb2 = SetButtontoLast(bb2, position);
                   return view;
               case "OneChoose3Box":
                   TextView p3 = (TextView) view.findViewById(R.id.onechoose_3box_Positon);
                   p3.setText(String.valueOf(position + 1));
                   TextView text3 = (TextView) view.findViewById(R.id.onechoose_3box_Text);
                   text3.setText(((Type_OneChoose3Box) fatherSubject).getText());
                   RadioButton R1 = view.findViewById(R.id.onechoose_3box_radio_1);
                   R1.setText(((Type_OneChoose3Box) fatherSubject).getBox1());
                   RadioButton R2 = view.findViewById(R.id.onechoose_3box_radio_2);
                   R2.setText(((Type_OneChoose3Box) fatherSubject).getBox2());
                   RadioButton R3 = view.findViewById(R.id.onechoose_3box_radio_3);
                   R3.setText(((Type_OneChoose3Box) fatherSubject).getBox3());
                   Button b3 = view.findViewById(R.id.onechoose_3box_nextButton);
                   b3 = SetButtontoNext(b3, fatherSubject, position);
                   Button bb3 = view.findViewById(R.id.onechoose_3box_lastButton);
                   bb3 = SetButtontoLast(bb3, position);
                   return view;
               case "Image":
                   TextView p4 = (TextView) view.findViewById(R.id.Image_Positon);
                   p4.setText(String.valueOf(position + 1));
                   TextView text4 = (TextView) view.findViewById(R.id.Image_Text);
                   text4.setText(((Type_Image) fatherSubject).getText());
                   Button b4 = view.findViewById(R.id.image_nextButton);
                   b4 = SetButtontoNext(b4, fatherSubject, position);
                   Button bb4 = view.findViewById(R.id.image_lastButton);
                   bb4 = SetButtontoLast(bb4, position);
                   return view;
               case "TurretLocate":
                   TextView p5 = (TextView) view.findViewById(R.id.Locate_Positon);
                   p5.setText(String.valueOf(position + 1));
                   TextView text5 = (TextView) view.findViewById(R.id.Locate_Text);
                   text5.setText(((Type_TurretLocate) fatherSubject).getText());
                   Button b5 = view.findViewById(R.id.Locate_nextButton);
                   b5 = SetButtontoNext(b5, fatherSubject, position);
                   Button bb5 = view.findViewById(R.id.Locate_lastButton);
                   bb5 = SetButtontoLast(bb5, position);
                   return view;
               case "Finish":
                   Button b9 = view.findViewById(R.id.finish_button);
                   b9.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           CatUtil.OutputAll();
                           BaseActivity.DestoryActivity("DoingQuestionnaire");
                       }
                   });
                   return view;
           }
       }catch (Exception e){
           BaseActivity.exception=BaseActivity.exception+"\r\n"+e.getMessage();
           StackTraceElement[] stackTrace = e.getStackTrace();
           for(StackTraceElement s:stackTrace){
               BaseActivity.exception=BaseActivity.exception+"\r\n"+s;
           }
           DoingQuestionnaire.isbug=true;
           BaseActivity.DestoryActivity(DoingQuestionnaire.class.getSimpleName());
       }
        return null;
    }

    /**
     * 给每个题目的Button设置前往下一个题目的点击事件
     * @param button
     * @param pos
     * @return
     */
    private Button SetButtontoNext(Button button,FatherSubject fa,int pos){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fa.RunMethod("0");
                CatUtil.NextSubject(pos);
            }
        });
        return button;
    }/**
     * 给每个题目的Button设置前往上一个题目的点击事件
     * @param button
     * @param pos
     * @return
     */
    private Button SetButtontoLast(Button button,int pos){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatUtil.LastSubject(pos);
            }
        });
        return button;
    }

    public static ImageButton nowImage;
    public static Type_Image type_image;
    public static void setImage(String path){
       nowImage.setImageBitmap(BitmapFactory.decodeFile(DoingQuestionnaire.nowImagePath));
       type_image.setOutputWords(DoingQuestionnaire.nowImagePath);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
    }
}
