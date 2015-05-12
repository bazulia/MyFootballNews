package com.bezeka.igor.myfootballnewsnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bezeka.igor.myfootballnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Татьяна on 23.02.2015.
 */
public class GoalsAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Goal> goalsList = new ArrayList<>();

    GoalsAdapter(Context ctx, ArrayList<Goal> goalsList){
        this.ctx = ctx;
        this.goalsList = goalsList;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return goalsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goalsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view==null) {
            view = lInflater.inflate(R.layout.goals_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvLeftPlayer = (TextView) view.findViewById(R.id.tvGoalPlayerLeft);
            holder.tvRightPlayer = (TextView) view.findViewById(R.id.tvGoalPlayerRight);
            holder.tvLeftTime = (TextView) view.findViewById(R.id.tvGoalTimeLeft);
            holder.tvRightTime = (TextView) view.findViewById(R.id.tvGoalTimeRight);
            holder.imgLeftGoal = (ImageView) view.findViewById(R.id.goalImgLeft);
            holder.imgRightGoal = (ImageView) view.findViewById(R.id.goalImgRight);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        Goal g = getGoal(position);
        if(g.type == Goal.Type.Left){
            holder.tvLeftPlayer.setText(g.name);
            holder.tvRightPlayer.setText("");
            holder.tvLeftTime.setText(g.time);
            holder.tvRightTime.setText("");
            Picasso.with(ctx).load(R.drawable.ball).into(holder.imgLeftGoal);
            Picasso.with(ctx).load(R.drawable.image_empty_50x50).into(holder.imgRightGoal);
        }
        else{
            holder.tvLeftPlayer.setText("");
            holder.tvRightPlayer.setText(g.name);
            holder.tvLeftTime.setText("");
            holder.tvRightTime.setText(g.time);
            Picasso.with(ctx).load(R.drawable.image_empty_50x50).into(holder.imgLeftGoal);
            Picasso.with(ctx).load(R.drawable.ball).into(holder.imgRightGoal);
        }



        return view;
    }
    static class ViewHolder {

        TextView tvLeftPlayer;
        TextView tvRightPlayer;
        TextView tvLeftTime;
        TextView tvRightTime;
        ImageView imgLeftGoal;
        ImageView imgRightGoal;
    }

    Goal getGoal(int position){
        return ((Goal)getItem(position));
    }
}
