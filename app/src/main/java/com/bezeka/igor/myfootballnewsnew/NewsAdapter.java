package com.bezeka.igor.myfootballnewsnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bezeka.igor.myfootballnews.R;

import java.util.ArrayList;

/**
 * Created by Igor on 26.01.2015.
 */
public class NewsAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<News> newsList = new ArrayList<>();


    NewsAdapter(Context context, ArrayList<News> newsList){
        this.ctx = context;
        this.newsList = newsList;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if(view==null) {
            view = lInflater.inflate(R.layout.news_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvDate = (TextView) view.findViewById(R.id.tvDate);
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        News n = getNews(position);

            holder.tvDate.setText(n.date);
            holder.tvTitle.setText(n.title);

        return view;
    }

    static class ViewHolder {

        TextView tvDate;
        TextView tvTitle;
    }

    News getNews(int position){
        return ((News)getItem(position));
    }
}
