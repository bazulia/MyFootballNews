package com.bezeka.igor.myfootballnewsnew;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bezeka.igor.myfootballnews.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static android.R.color.background_dark;
import static android.R.drawable.ic_menu_view;
import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Igor on 24.01.2015.
 */
public class DetailNewsActivity extends ActionBarActivity {

    TextView tvTitle;
    TextView tvSubTitle;
    TextView tvText;

    Bitmap mainImage = null;

    RelativeLayout activityNoResponce;
    RelativeLayout activityNoConnection;
    LinearLayout detailLayout;
    LinearLayout linearLayout;

    private RelativeLayout relativeLayout;
    private ScrollView scrollView;

    public ImageView imageView;

    ProgressDialog progressDialog;

    ArrayList<String> bodySimpleList = new ArrayList<>();
    ArrayList<String> bodyTableList = new ArrayList<>();


    String textTitle;
    String textSubTitle;
    String textText;
    String urlImage;

    Button repeatInternet;
    Button repeatResponce;

    Elements title;
    Elements subTitle;
    Elements text;
    Elements tableText;

    public Bitmap networkBitmap = null;

    public String href;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_activity);

        href = getIntent().getStringExtra("href");

        relativeLayout = (RelativeLayout)findViewById(R.id.relLayout);

        scrollView = (ScrollView)findViewById(R.id.scrollView1);

        relativeLayout = (RelativeLayout) findViewById(R.id.relLayout);

        linearLayout = (LinearLayout) findViewById(R.id.mainLayout);

        activityNoResponce = (RelativeLayout) findViewById(R.id.layoutNoResponce);

        activityNoResponce.setVisibility(View.GONE);

        activityNoConnection = (RelativeLayout) findViewById(R.id.layoutNoConnection);

        activityNoConnection.setVisibility(View.GONE);

        repeatInternet = (Button)findViewById(R.id.btnRepeat);
        repeatInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                new NewThread().execute();

            }
        });

        repeatResponce = (Button)findViewById(R.id.btnRepeatResponce);
        repeatResponce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                new NewThread().execute();

            }
        });

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        tvText = (TextView) findViewById(R.id.tvText);

        imageView = (ImageView) findViewById(R.id.imageView1);

        if(!checkInternet())
            showNoConnection();
        showProgressBar();

        new NewThread().execute();

    }

    private boolean checkInternet(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    private void showNoConnection(){
        activityNoConnection.setVisibility(View.VISIBLE);
        activityNoResponce.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);
    }
    private void showNoResponce(){
        activityNoConnection.setVisibility(View.INVISIBLE);
        activityNoResponce.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);
    }
    private void showProgressBar(){
        activityNoConnection.setVisibility(View.INVISIBLE);
        activityNoResponce.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
    }
    private void showMainScreen(){
        activityNoConnection.setVisibility(View.INVISIBLE);
        activityNoResponce.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);
    }

    Transformation transformImage(ImageView imgView){

        Transformation transformation = new Transformation() {

            @Override public Bitmap transform(Bitmap source) {
                int targetWidth = imageView.getWidth();

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

    private void makeBodyOfNews() {

        tvTitle.setText(textTitle);
        tvSubTitle.setText(textSubTitle);

        LinearLayout ll = (LinearLayout) findViewById(R.id.layoutTwo);


        Picasso.with(this)
                .load(urlImage)
                .error(android.R.drawable.stat_notify_error)
                .transform(transformImage(imageView))
                .into(imageView);


        for (String s : bodySimpleList) {
            Log.d("myLog", s);

            if (s.contains("http")) {
                ImageView img = new ImageView(this);
                img.setPadding(20, 40, 20, 40);
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ll.addView(img);
                Picasso.with(this).load(s)
                        .transform(transformImage(img))
                        .into(img);
            } else {
                TextView textView = new TextView(this);
                if (s.contains("[s]")) {
                    s = s.substring(3);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
                textView.setPadding(20, 0, 20, 0);
                textView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
                textView.setText(s + "\n");
                textView.setTextColor(Color.BLACK);
                ll.addView(textView);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar actions click
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class NewThread extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            Document doc;

            try {
                doc = Jsoup.connect(href).get();

                if(href.contains("football.ua")) {

                    textTitle = doc.select("h1").text();
                    textSubTitle = doc.select(".intro").first().text();
                    urlImage = doc.select(".article-photo img").attr("src").toString();

                    Elements textTableLinks = doc.select(".article-text").select("table").select("td");
                    for (Element link : textTableLinks) {
                        bodyTableList.add(link.text());
                    }

                    Elements textSimpleLinks = doc.select(".article-text").select("p");
                    for (Element link : textSimpleLinks) {
                        if (!link.hasText()) {
                            bodySimpleList.add(link.select("img").attr("src"));
                            Log.d("myLog", link.select("img").attr("src").toString());
                        } else if (link.select("strong").hasText()) {
                            bodySimpleList.add("[s]" + link.text());
                        } else {
                            bodySimpleList.add(link.text());
                        }
                    }
                }
                else{

                    textTitle = doc.select("h1#ctl00_News3_c1_5_header").text();
                    textSubTitle = doc.select(".intro").first().text();
                    urlImage = "http://isport.ua/"+doc.select("#ctl00_News3_c1_5_image").attr("src").toString();


                    textText = doc.select("#ctl00_News3_c1_5_FullText").text();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            makeBodyOfNews();

            if(!checkInternet()){
                showNoConnection();
                return;
            }
            if(textTitle.isEmpty() && checkInternet()){
                showNoResponce();

            }else{
                showMainScreen();
           }
        }

    }

}
