package com.example.cuongphan.noteassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CuongPhan on 5/16/2017.
 */

class AdapterNoteList extends BaseAdapter {
    private Context context;
    private List<Note> noteList;
    public AdapterNoteList(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.linearlayout_note_in_notelist, null);

        TextView tv_title = (TextView)convertView.findViewById(R.id.tv_title);
        tv_title.setText(noteList.get(position).getTitle());

        TextView tv_content = (TextView)convertView.findViewById(R.id.tv_content);
        tv_content.setText(noteList.get(position).getContent());

        TextView tv_createdTime = (TextView)convertView.findViewById(R.id.tv_createdTime);
        tv_createdTime.setText(noteList.get(position).getCreateTime());

        return convertView;
    }
}
