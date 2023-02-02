package com.tx.catcat.Object.subject;

import com.tx.catcat.util.QuestionnaireInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Type_Float extends FatherSubject {
    Integer index;
    String type="Float";
    String Text;
    String title="";
    String OutputWords="#";
     String UserInputWords="#";
    String RunMthod="noMethod";
    /**
     * 构造方法，用于给对应变量赋值
     * @param WordList
     */
    public Type_Float(LinkedList<String> WordList){
        for(String s:WordList){
            String[] split = s.split("=");
            switch (split[0]){
                case "Index":
                    index= Integer.parseInt(split[1]);
                    break;
                case "Text" :
                    Text=split[1];
                    break;
                case "Title" :
                    title=split[1];
                    break;
                case "OutPut" :
                    OutputWords=split[1];
                    break;
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
        return title+"\r\n"+ OutputWords.replace("+In",UserInputWords)+"\r\n";
    }

    public void setOutputWords(String outputWords) {
        UserInputWords=outputWords;
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
