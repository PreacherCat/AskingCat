package com.tx.catcat.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.tx.catcat.Activity.DoingQuestionnaire;
import com.tx.catcat.Object.Questionnaire;
import com.tx.catcat.Object.subject.FatherSubject;
import com.tx.catcat.Object.subject.Type_Finish;
import com.tx.catcat.Object.subject.Type_Float;
import com.tx.catcat.Object.subject.Type_Image;
import com.tx.catcat.Object.subject.Type_OneChoose3Box;
import com.tx.catcat.Object.subject.Type_String;
import com.tx.catcat.Object.subject.Type_TurretLocate;
import com.tx.catcat.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CatUtil {

    public static LinkedList<Questionnaire> QuestionnaireList=null;
    public static int Questionnaireposition=0;
    public static String Modpath=null;

    /**
     * 复制任意文件到指定位置
     * @param oldpath
     * @param newpath
     * @throws IOException
     */
    public static void CopyFileto(String oldpath,String newpath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(oldpath);
        FileOutputStream fileOutputStream = new FileOutputStream(newpath);
        byte[] by=new byte[32];
        while(fileInputStream.read(by)!=-1){
            fileOutputStream.write(by);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static String getModpath(){
        if (Modpath!=null){
            return Modpath;
        }
        File file=new File("/storage/emulated/0/");
        setModpath(file,"rustedWarfare/units");
        return Modpath;
    }

    private static String setModpath(File file,String name) {
        if (file.isDirectory()) {
            if (file.getAbsolutePath().endsWith(name)) {
                Modpath=file.getAbsolutePath();
            }
            File[] files = file.listFiles();
            if (files==null){
                return "no";
            }

           aa: for (File file1 : files) {
                if (file1.isDirectory()) {
                    if (setModpath(file1, name).equals("no")){
                        continue aa;
                    }
                    if (file1.getAbsolutePath().endsWith(name)) {
                        Modpath=file1.getAbsolutePath();
                    }
                }
            }
        }
        return "no";
    }


    /**
     * 获取Asset中问卷名称的数组
     * @param context
     * @return String[]
     * @throws IOException
     */
    public static String[] getAssetsList(Context context)  {
        try {

            return context.getAssets().list("Questionnaire");
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    /**
     * 获取Asset中指定问卷名称的输入流
     * @param context
     * @return String[]
     * @throws IOException
     */
    public static InputStream getAssetsInputByName(Context context,String name) {
        try {
            return context.getAssets().open("Questionnaire/"+name);
        } catch (IOException e) {
            Log.w("cao",e.getMessage());
            return null;
        }
    }

    /**
     * 获取带有完整属性的Questionnaire的集合,包括每道题目的对象
     * @param context
     * @return
     */
    public static LinkedList<Questionnaire> getQuestionnaireList(Context context,String path){
        LinkedList<Questionnaire> arraylist=new LinkedList<>();
        BufferedReader bufferedReader;
        String line=null;
        LinkedList QuestionnaireobjList = new LinkedList();
        LinkedList<String> WordList = new LinkedList<>();
        Object nowObject=null;
        try {
           aa: for (String s:CatUtil.getAssetsList(context)){
                if (path==null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(getAssetsInputByName(context, s)));
                }else {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
                }
                while ((line=bufferedReader.readLine())!=null){
                    if (line.startsWith("Type")){
                        nowObject=SelectQuestionnaireObjByType(line.split("=")[1],WordList);
                        continue;
                    }
                    WordList.add(line);
                    if (line.startsWith("-stop")){
                        QuestionnaireobjList.add(nowObject);
                        WordList = new LinkedList<>();
                        continue;
                    }
                }
                QuestionnaireobjList.add(new Type_Finish());
                if (path==null) {
                    arraylist.add(new Questionnaire(s, CatUtil.getAssetsInputByName(context, s), QuestionnaireobjList));
                }else{
                    arraylist.add(new Questionnaire(s, new FileInputStream(path), QuestionnaireobjList));
                   break aa;
                }
                WordList = new LinkedList<>();
                QuestionnaireobjList=new LinkedList();
            }
            QuestionnaireList=arraylist;
            return QuestionnaireList;
        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            return  null;
        }

}
    /**
     * 最后一步，根据type生成问题对象
     * @param type
     * @return
     */
    private static Object SelectQuestionnaireObjByType(String type,LinkedList<String> wordlist) {
        switch (type){
            case "String":
                return new Type_String(wordlist);
            case "Float":
                return new Type_Float(wordlist);
            case "Image":
                return new Type_Image(wordlist);
            case "OneChoose3Box":
                return new Type_OneChoose3Box(wordlist);
            case "TurretLocate":
                return new Type_TurretLocate(wordlist);
            case "Finish":
                return new Type_Finish();
        }
        return null;
    }

    /**
     * 根据对象获取对应的layoutid
     * @param subject
     * @return
     */
    public static int SelectLayoutIdBytype(FatherSubject subject){
        switch (subject.getType()){
            case "String":
            case "Float":
                return R.layout.type_string;
            case "Image":
                return R.layout.type_image;
            case "OneChoose3Box":
                return R.layout.type_onechoose_3box;
           case "TurretLocate":
                return R.layout.type_locateturret;
            case "Finish":
                return R.layout.type_finish;
        }
        return 0;
    }

    /**
     * 前往下一个问卷
     * @param position
     */
    public static void NextSubject(int position){
         DoingQuestionnaire.Viewpager.setCurrentItem(++position);
    }
    /**
     * 前往上一个问卷
     * @param position
     */
    public static void LastSubject(int position){
         DoingQuestionnaire.Viewpager.setCurrentItem(--position);
    }

    /**
     * 生成单位
     */
    public static void OutputAll(){
        try {
            //输出代码
            String path= getModpath()+"/"+new Random().nextInt(100000)+".rwmod";
            ZipOutputStream zipOutputStream=new ZipOutputStream(new FileOutputStream(path));
            FileInputStream fileInputStream=null;
            byte[] by =new byte[32];


            zipOutputStream.putNextEntry(new ZipEntry(String.valueOf(new Random().nextInt(100000))+".ini"));
            for(FatherSubject fa:QuestionnaireList.get(Questionnaireposition).getQuestionnaireObjectList()){
                zipOutputStream.write(fa.getOutputWords().getBytes());
            }
            LinkedList<FatherSubject> questionnaireObjectList = CatUtil.QuestionnaireList.get(CatUtil.Questionnaireposition).getQuestionnaireObjectList();
            for (FatherSubject fa: questionnaireObjectList){
                if (fa.getType().equals("Image")){
                    Type_Image image =(Type_Image) fa;
                    String[] split = image.getUserInputWords().split("/");
                    zipOutputStream.putNextEntry(new ZipEntry(split[split.length-1]));
                    fileInputStream=new FileInputStream(image.getUserInputWords());
                    while (fileInputStream.read(by)!=-1){
                        zipOutputStream.write(by);
                    }
                    zipOutputStream.flush();
                }
            }
            fileInputStream.close();
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   }
