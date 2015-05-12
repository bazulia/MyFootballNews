package com.bezeka.igor.myfootballnewsnew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bezeka.igor.myfootballnews.R;

import java.util.ArrayList;

import static android.graphics.Color.TRANSPARENT;

/**
 * Created by Igor on 15.02.2015.
 */
public class MatchAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Match> matchList = new ArrayList<>();


    MatchAdapter(Context context, ArrayList<Match> matchList) {
        this.ctx = context;
        this.matchList = matchList;
        this.lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return matchList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null) {
            view = lInflater.inflate(R.layout.match_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvLeftTeam = (TextView) view.findViewById(R.id.tvLeftTeam);
            holder.tvRightTeam = (TextView) view.findViewById(R.id.tvRightTeam);
            holder.tvTime = (TextView) view.findViewById(R.id.tvTime);
            holder.tvScore = (TextView) view.findViewById(R.id.tvScore);
            holder.tvOtherInfo = (TextView) view.findViewById(R.id.tvOtherInfo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Match m = getMatch(position);

        holder.tvLeftTeam.setText(m.leftTeam);
        holder.tvRightTeam.setText(m.rightTeam);
        holder.tvScore.setText(m.score);
        holder.tvTime.setText(m.time);
        holder.tvOtherInfo.setText(m.otherInfo);


        switch (m.state) {
            case -1: {
                holder.tvScore.setBackgroundColor(TRANSPARENT);
                holder.tvScore.setTextColor(Color.BLACK);
            }
            break;
            case 0: {
                holder.tvScore.setBackgroundColor(Color.rgb(255, 0, 0));
                holder.tvScore.setTextColor(Color.WHITE);
            }
            break;
            case 1: {
                holder.tvScore.setBackgroundColor(Color.rgb(0, 93, 0));
                holder.tvScore.setTextColor(Color.WHITE);
            }
            break;
            case 2: {
                holder.tvScore.setBackgroundColor(TRANSPARENT);
                view.setBackgroundColor(Color.WHITE);
            }
        }

        if (m.rightTeam.isEmpty())
            view.setBackgroundColor(Color.WHITE);

        return view;
    }

    static class ViewHolder {
        TextView tvLeftTeam;
        TextView tvRightTeam;
        TextView tvScore;
        TextView tvTime;
        TextView tvOtherInfo;
    }

    Match getMatch(int position) {
        return ((Match) getItem(position));
    }
}
