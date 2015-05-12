package com.bezeka.igor.myfootballnewsnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bezeka.igor.myfootballnews.R;

import java.util.ArrayList;

/**
 * Created by Татьяна on 23.02.2015.
 */
public class TranslationAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Translation> translationsList = new ArrayList<>();

    TranslationAdapter(Context ctx,ArrayList<Translation> translationsList){
        this.ctx = ctx;
        this.translationsList = translationsList;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return translationsList.size();
    }

    @Override
    public Object getItem(int position) {
        return translationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view==null) {
            view = lInflater.inflate(R.layout.translation_list_item, parent, false);
            holder = new ViewHolder();
            holder.imgEvent = (ImageView) view.findViewById(R.id.imgTranslationEvent);
            holder.tvTime = (TextView) view.findViewById(R.id.tvTranslationTime);
            holder.tvText = (TextView) view.findViewById(R.id.tvTranslationText);

            view.setTag(holder);

        }else{
            holder = (ViewHolder)view.getTag();
        }

        Translation t = getTranslation(position);

      //  if(w.type == Warning.Type.Left) {
            holder.tvText.setText(t.text);
            holder.tvTime.setText(t.time);
       /*     holder.tvLeftTime.setText(w.time);
            holder.tvRightTime.setText("");
            if (w.card == Warning.Card.Yellow) {
                Picasso.with(ctx).load(R.drawable.yellow_card).into(holder.imgLeftWarning);
            }
        }*/

        return view;
    }
    static class ViewHolder {
        ImageView imgEvent;
        TextView tvTime;
        TextView tvText;
    }

    Translation getTranslation(int position){
        return ((Translation)getItem(position));
    }
}
