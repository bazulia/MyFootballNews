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
public class WarningsAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Warning> warningsList = new ArrayList<>();

    WarningsAdapter(Context ctx,ArrayList<Warning> warningsList){
        this.ctx = ctx;
        this.warningsList = warningsList;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return warningsList.size();
    }

    @Override
    public Object getItem(int position) {
        return warningsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view==null) {
            view = lInflater.inflate(R.layout.warnings_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvLeftPlayer = (TextView) view.findViewById(R.id.tvWarningPlayerLeft);
            holder.tvRightPlayer = (TextView) view.findViewById(R.id.tvWarningPlayerRight);
            holder.tvLeftTime = (TextView) view.findViewById(R.id.tvWarningTimeLeft);
            holder.tvRightTime = (TextView) view.findViewById(R.id.tvWarningTimeRight);
            holder.imgLeftWarning = (ImageView) view.findViewById(R.id.warningImgLeft);
            holder.imgRightWarning = (ImageView) view.findViewById(R.id.warningImgRight);

            view.setTag(holder);

        }else{
            holder = (ViewHolder)view.getTag();
        }

        Warning w = getWarning(position);

        if(w.type == Warning.Type.Left){
            holder.tvLeftPlayer.setText(w.name);
            holder.tvRightPlayer.setText("");
            holder.tvLeftTime.setText(w.time);
            holder.tvRightTime.setText("");
            if(w.card == Warning.Card.Yellow) {
                Picasso.with(ctx).load(R.drawable.yellow_card).into(holder.imgLeftWarning);
                Picasso.with(ctx).load(R.drawable.image_empty_50x50).into(holder.imgRightWarning);
            }else{
                Picasso.with(ctx).load(R.drawable.red_card).into(holder.imgLeftWarning);
                Picasso.with(ctx).load(R.drawable.image_empty_50x50).into(holder.imgRightWarning);
            }
        }
        else{
            holder.tvLeftPlayer.setText("");
            holder.tvRightPlayer.setText(w.name);
            holder.tvLeftTime.setText("");
            holder.tvRightTime.setText(w.time);
            if(w.card == Warning.Card.Yellow) {
                Picasso.with(ctx).load(R.drawable.image_empty_50x50).into(holder.imgLeftWarning);
                Picasso.with(ctx).load(R.drawable.yellow_card).into(holder.imgRightWarning);
            }else{
                Picasso.with(ctx).load(R.drawable.image_empty_50x50).into(holder.imgLeftWarning);
                Picasso.with(ctx).load(R.drawable.red_card).into(holder.imgRightWarning);
            }
        }


        return view;
    }
    static class ViewHolder {

        TextView tvLeftPlayer;
        TextView tvRightPlayer;
        TextView tvLeftTime;
        TextView tvRightTime;
        ImageView imgLeftWarning;
        ImageView imgRightWarning;
    }

    Warning getWarning(int position){
        return ((Warning)getItem(position));
    }
}
