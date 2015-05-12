package com.bezeka.igor.myfootballnewsnew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bezeka.igor.myfootballnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.*;

/**
 * Created by Igor on 26.01.2015.
 */
public class ResultAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Team> teamsList = new ArrayList<>();


    ResultAdapter(Context context, ArrayList<Team> teamsList){
        this.ctx = context;
        this.teamsList = teamsList;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return teamsList.size();
    }

    @Override
    public Object getItem(int position) {
        return teamsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if(view==null) {
            view = lInflater.inflate(R.layout.result_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvPos = (TextView) view.findViewById(R.id.tvNo);
            holder.imgEmblem = (ImageView) view.findViewById(R.id.imgEmblem);
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            holder.tvGames = (TextView) view.findViewById(R.id.tvGames);
            holder.tvPints = (TextView) view.findViewById(R.id.tvPoints);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        Team t = getTeam(position);


            holder.tvPos.setText(t.pos);
            holder.tvName.setText(t.name);
            holder.tvGames.setText(t.games);
            holder.tvPints.setText(t.score);

            if(t.logoLink.contains("http"))
            {
                Picasso.with(ctx).load(t.logoLink)
                        .placeholder(drawable.ic_menu_camera)
                        .into(holder.imgEmblem);
            }else{

                Picasso.with(ctx).load(R.drawable.image_empty_50x50)
                        .placeholder(drawable.ic_menu_camera)
                        .into(holder.imgEmblem);
            }

        if(t.pos.equals("#"))
            view.setBackgroundColor(Color.parseColor("#ececec"));
        else
            view.setBackgroundColor(Color.WHITE);

        return view;
    }

    static class ViewHolder {
        ImageView imgEmblem;
        TextView tvPos;
        TextView tvName;
        TextView tvGames;
        TextView tvPints;
    }

    Team getTeam(int position){
        return ((Team)getItem(position));
    }
}
