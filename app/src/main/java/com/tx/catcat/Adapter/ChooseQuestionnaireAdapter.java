package com.tx.catcat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tx.catcat.Object.Questionnaire;
import com.tx.catcat.R;

import java.util.LinkedList;

public class ChooseQuestionnaireAdapter extends ArrayAdapter {
    LinkedList<Questionnaire> Viewlist;
    Context context;
    public ChooseQuestionnaireAdapter(@NonNull Context context, int resourceid, LinkedList<Questionnaire> viewlist) {
        super(context, resourceid);
        Viewlist = viewlist;
        this.context=context;
    }

    @Override
    public int getCount() {
        return Viewlist.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Questionnaire questionnaire = Viewlist.get(position);
        View view= LayoutInflater.from(getContext()).inflate(R.layout.choose_questionnaire_item,null);
        TextView title = view.findViewById(R.id.choose_item_title);
        TextView author = view.findViewById(R.id.choose_item_author);
        title.setText(questionnaire.getQuestionnaireName());
        author.setText("作者:"+questionnaire.getAuthor());
        return view;
    }

}
