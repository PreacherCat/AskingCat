package com.tx.catcat.Object.subject;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.tx.catcat.util.QuestionnaireInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Type_OneChoose3Box extends FatherSubject{
    String box1;
    String box1_output;
    String box2;
    String box2_output;
    String box3;
    String box3_output;
    Integer index;
    String type="OneChoose3Box";
    String Text;
    String title="";
    String OutputWords="";
    String UserInputWords="";
    LinkedList box;
    String RunMthod="noMethod";

    public String getBox1() {
        return box1;
    }

    public String getBox1_output() {
        return box1_output;
    }

    public String getBox2() {
        return box2;
    }

    public String getBox2_output() {
        return box2_output;
    }

    public String getBox3() {
        return box3;
    }

    public String getBox3_output() {
        return box3_output;
    }

    /**
     * 构造方法，用于给对应变量赋值
     * @param WordList
     */
    public Type_OneChoose3Box(LinkedList<String> WordList){
        for(String s:WordList){
            String[] split = s.split("=");
            switch (split[0]){
                case "Index":
                    index= Integer.parseInt(split[1]);break;
                case "Text":
                    Text=split[1];break;
                case "Title":
                    title=split[1];break;
                case "Box1":
                    box1= split[1];break;
                case "Box1_Input":
                    box1_output= split[1];break;
                case "Box2":
                    box2= split[1];break;
                case "Box2_Input":
                    box2_output= split[1];break;
                case "Box3":
                    box3= split[1];break;
                case "Box3_Input":
                    box3_output= split[1];break;
                case "OutPut":
                    OutputWords=split[1];break;
                case "RunMethod":
                    RunMthod=split[1];break;
            }
        }
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getOutputWords() {
        return title+"\r\n"+ OutputWords.replace("+In",UserInputWords)+"\r\n";
    }

    @Override
    public void setOutputWords(String outputWords) {
        UserInputWords=outputWords;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void RunMethod(String runtime) {
        if (RunMthod.equals("noMethod")){
            return;
        }
        String[] split = RunMthod.split(":");
        String[] canshu = split[1].split(",");
        for (int i = 0; i<canshu.length; i++){
            if (canshu [i].equals("UserInputWords")) {
                canshu[i] =UserInputWords;
            }
        }
        if (runtime.equals(split[0].split(",")[0])){
            QuestionnaireInterface.ChooseMethord(split[0].split(",")[1],canshu);
        }
    }
}
