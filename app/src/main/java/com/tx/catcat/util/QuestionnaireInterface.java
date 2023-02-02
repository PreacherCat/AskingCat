package com.tx.catcat.util;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tx.catcat.Adapter.DoingQuestionnaireAdapter;
import com.tx.catcat.CustomView.LocateTurret;
import com.tx.catcat.Object.subject.FatherSubject;
import com.tx.catcat.R;

public class QuestionnaireInterface {

    static String bodypath="";
    static String turretpath="";
    /**
     * 根据字符串来执行对应方法
     * @param name
     * @param content
     */
    public static void ChooseMethord(String name,String... content){
        switch (name){
            case "LogSomething":
                LogSomething(content);
            case "setBodyPath":
                setBodyPath(content);
            case "setTurretPath":
                setTurretPath(content);
            case "setAllbodyImage":
                setAllbodyImage(content);
            case "setAllturretImage":
                setAllturretImage(content);

        }
    }

    /**
     * Log参数字符串
     * @param content
     */
    public static void LogSomething(String...content){
        Log.e("cao",content[0]);
    }

    /**
     * 设置单位身体的路径
     * @param content
     */
    public static void setBodyPath(String...content){ bodypath=content[0]; }

    /**
     * 获取设置好的身体路径
     * @return
     */
    public static String getBodyPath(){
        Log.e("cao",bodypath);
        return bodypath;}

    /**
     * 设置单位身体的路径
     * @param content
     */
    public static void setTurretPath(String...content){
        turretpath=content[0];
    }

    /**
     * 获取设置好的身体路径
     * @return
     */
    public static String getTurretPath(){return turretpath;}

    /**
     * 设置整个问卷所有的炮台定位题的身体图像
     * @param content
     */
    public static void setAllbodyImage(String...content){
        int i=0;
        for(FatherSubject fa: DoingQuestionnaireAdapter.subjectsList){
            if (fa.getType().equals("TurretLocate")){
                View view = DoingQuestionnaireAdapter.viewList.get(i);
                ImageView body = view.findViewById(R.id.Locate_body);
                body.setImageBitmap(BitmapFactory.decodeFile(bodypath));
            }
            i++;
        }
    }
    /**
     * 设置整个问卷所有的炮台定位题的炮台图像
     * @param content
     */
    public static void setAllturretImage(String...content){
        int i=0;
        for(FatherSubject fa: DoingQuestionnaireAdapter.subjectsList){
            if (fa.getType().equals("TurretLocate")){
                View view = DoingQuestionnaireAdapter.viewList.get(i);
                LocateTurret turret = view.findViewById(R.id.Locate_turret);
                turret.setbitmap();
            }
            i++;
        }
    }
}
