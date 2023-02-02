package com.tx.catcat.Object;

import android.util.Log;

import com.tx.catcat.Object.subject.FatherSubject;

import java.io.InputStream;
import java.util.LinkedList;

public class Questionnaire {
    String QuestionnaireName;
    String author;
    InputStream input;
    Integer QuestionnaireCount;
    LinkedList<FatherSubject> QuestionnaireObjectList;

    public Questionnaire(String questionnaireName, InputStream input,LinkedList LIN) {
        questionnaireName=questionnaireName.replace(".txt","");
        QuestionnaireName = questionnaireName.split("-")[0];
        author=questionnaireName.split("-")[1];
        QuestionnaireObjectList=LIN;
        this.input = input;
    }

    public LinkedList<FatherSubject> getQuestionnaireObjectList() {
        return QuestionnaireObjectList;
    }

    public void setQuestionnaireObjectList(LinkedList<FatherSubject> questionnaireObjectList) {
        QuestionnaireObjectList = questionnaireObjectList;
    }

    public void setSubjectObject(FatherSubject fa,int pos){
        QuestionnaireObjectList.set(pos,fa);
    }

    public String getQuestionnaireName() {
        return QuestionnaireName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setQuestionnaireName(String questionnaireName) {
        QuestionnaireName = questionnaireName;
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public Integer getQuestionnaireCount() {
        return QuestionnaireCount;
    }

    public void setQuestionnaireCount(Integer questionnaireCount) {
        QuestionnaireCount = questionnaireCount;
    }
}
