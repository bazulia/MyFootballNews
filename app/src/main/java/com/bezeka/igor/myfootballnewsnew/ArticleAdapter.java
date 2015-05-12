package com.bezeka.igor.myfootballnewsnew;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bezeka.igor.myfootballnews.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Igor on 21.04.2015.
 */
public class ArticleAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Article> articleList = new ArrayList<>();

    ArticleAdapter(Context ctx, ArrayList<Article> articleList){
        this.ctx = ctx;
        this.articleList = articleList;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Transformation transformImage(final ImageView imgView){

        Transformation transformation = new Transformation() {

            @Override public Bitmap transform(Bitmap source) {
                int targetWidth = imgView.getWidth();

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }


                return result;
            }

            @Override public String key() {
                return "transformation" + " desiredWidth";
            }
        };
        return transformation;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if(view==null) {
            view = lInflater.inflate(R.layout.article_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitleArticle);
            holder.tvText = (TextView) view.findViewById(R.id.tvTextArticle);
            holder.imgView = (ImageView) view.findViewById(R.id.imageViewArticle);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }



        holder.tvTitle.setText(getArticle(position).title);
        holder.tvText.setText(getArticle(position).text);

        if(!getArticle(position).srcImage.isEmpty())
        Picasso.with(ctx)
                .load(getArticle(position).srcImage)
                .error(android.R.drawable.stat_notify_error)
                .transform(transformImage(holder.imgView))
                .into(holder.imgView);

        return view;
    }

    static class ViewHolder {

        TextView tvTitle;
        TextView tvText;
        ImageView imgView;
    }

    Article getArticle(int position){
        return ((Article)getItem(position));
    }
}
