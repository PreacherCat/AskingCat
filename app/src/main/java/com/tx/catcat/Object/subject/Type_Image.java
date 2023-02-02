package com.tx.catcat.Object.subject;

import android.util.Log;

import com.tx.catcat.util.QuestionnaireInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Type_Image extends FatherSubject {
    Integer index;
    String type="Image";
    String Text;
    String title="";
    String OutputWords;
    //image中的UserInputWords为图片路径
    String UserInputWords="";
    String RunMthod="noMethod";
    /**
     * 构造方法，用于给对应变量赋值
     * @param WordList
     */
    public Type_Image(LinkedList<String> WordList){
        for(String s:WordList){
            String[] split = s.split("=");
            switch (split[0]){
                case "Index":
                    index= Integer.parseInt(split[1]);
                    break;
                case "Text" :
                    Text=split[1];break;
                case "Title" :
                    title=split[1];break;
                case "OutPut" :
                    OutputWords=split[1];break;
                case "RunMethod":
                    RunMthod=split[1];break;
            }
        }
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getOutputWords() {
        String[] split = UserInputWords.split("/");
        return title+"\r\n"+ OutputWords.replace("+In",split[split.length-1])+"\r\n";
    }

    public String getUserInputWords() {
        return UserInputWords;
    }

    public void setOutputWords(String outputWords) {
        UserInputWords=outputWords.replace(" ","").replace("\r\n","");
    }
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
