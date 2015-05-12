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
 * Created by Igor on 21.04.2015.
 */
public class SideMenuAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<SideMenuItem> sideMenuItems = new ArrayList<>();


    SideMenuAdapter(Context context, ArrayList<SideMenuItem> sideMenuItems){
        this.ctx = context;
        this.sideMenuItems = sideMenuItems;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sideMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return sideMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if(view==null) {
            view = lInflater.inflate(R.layout.drawer_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(android.R.id.text1);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        SideMenuItem sideMenuItem = getSideMenuItem(position);

        holder.tvTitle.setText(sideMenuItem.text);

        return view;
    }

    static class ViewHolder {

        TextView tvTitle;
    }

    SideMenuItem getSideMenuItem(int position){
        return ((SideMenuItem)getItem(position));
    }
}
