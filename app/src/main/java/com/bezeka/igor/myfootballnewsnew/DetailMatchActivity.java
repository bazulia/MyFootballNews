package com.bezeka.igor.myfootballnewsnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bezeka.igor.myfootballnews.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Татьяна on 21.02.2015.
 */
public class DetailMatchActivity extends ActionBarActivity {

    RelativeLayout activityNoResponce;
    RelativeLayout activityNoConnection;
    LinearLayout detailLayout;
    LinearLayout linearLayout;

    int state = 0;

    private Menu optionsMenu;

    boolean goalsHide = true;
    boolean warningsHide = true;
    boolean translationHide = true;

    private String link = null;

    private String leftTeamLineup = null;
    private String rightTeamLineup = null;

    private String leftTeamSubLineup = null;
    private String rightTeamSubLineup = null;

    private ArrayList<Goal> goalsList = new ArrayList<>();
    private ArrayList<Warning> warningsList = new ArrayList<>();
    private ArrayList<Translation> translationsList = new ArrayList<>();

    private String leftTeamLogo = null;
    private String rightTeamLogo = null;
    private String leftTeamScore = null;
    private String rightTeamScore = null;
    private String leftTeamName = null;
    private String rightTeamName = null;
    private String sostavLeftTitle = null;
    private String sostavRightTitle = null;
    private String sostavMainTitle = null;

    /////

    private TextView tvLeftTeamScore;
    private TextView tvRightTeamScore;
    private TextView tvScoreNull;
    private TextView tvLeftTeamName;
    private TextView tvRightTeamName;
    private TextView tvLeftTeamLineup;
    private TextView tvRightTeamLineup;
    private TextView tvLeftTeamSubLineup;
    private TextView tvRightTeamSubLineup;
    private ImageView imgLeftTeamLogo;
    private ImageView imgRightTeamLogo;
    private TextView tvSostavLeftTitle;
    private TextView tvSostavRightTitle;
    private TextView tvSostavMainTitle;
    private TextView tvGoalsHeader;
    private TextView tvWarningsHeader;
    private TextView tvTranslationHeader;
    private ImageView imgGoalsDropdown;
    private ImageView imgWarningsDropdown;
    private ImageView imgTranslationDropdown;

    private Spinner spinnerGoals;
    private Spinner spinnerWarnings;

    private ListView goalsListView;
    private ListView warningsListView;

    private LinearLayout linearMatchDetails;
    private LinearLayout linGoals;
    private LinearLayout linWarnings;
    private LinearLayout linTranslation;

    private RelativeLayout relativeLayout;
    private ScrollView scrollView;

    Animation animationDown;
    Animation animationUp;
    Animation animationOpenElementGoals;
    Animation animationCloseElementGoals;
    Animation animationOpenElementWarnings;
    Animation animationCloseElementWarnings;
    Animation animationOpenElementTranslation;
    Animation animationCloseElementTranslation;

    LinearLayout llTranslation;

    Button repeatInternet;
    Button repeatResponce;

    GoalsAdapter goalsAdapter;
    WarningsAdapter warningsAdapter;
    TranslationAdapter translationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_match_activity);

        link = getLink();

        setResources();

        linearMatchDetails.setVisibility(View.GONE);

        if(!checkInternet())
            showNoConnection();
        showProgressBar();

        new NewThread().execute();
    }

    private void setResources() {
        tvLeftTeamName = (TextView) findViewById(R.id.tvTeamNameLeft);
        tvRightTeamName = (TextView) findViewById(R.id.tvTeamNameRight);
        tvLeftTeamScore = (TextView) findViewById(R.id.tvScoreLeft);
        tvRightTeamScore = (TextView) findViewById(R.id.tvScoreRight);
        tvScoreNull = (TextView) findViewById(R.id.tvScoreNull);
        imgLeftTeamLogo = (ImageView) findViewById(R.id.imgTeamLeft);
        imgRightTeamLogo = (ImageView) findViewById(R.id.imgTeamRight);
        tvLeftTeamLineup = (TextView) findViewById(R.id.tvMainLineupLeft);
        tvRightTeamLineup = (TextView) findViewById(R.id.tvMainLineupRight);
        tvLeftTeamSubLineup = (TextView) findViewById(R.id.tvSubLineupLeft);
        tvRightTeamSubLineup = (TextView) findViewById(R.id.tvSubLineupRight);
        tvSostavLeftTitle = (TextView) findViewById(R.id.tvSostavLeftTitle);
        tvSostavRightTitle = (TextView) findViewById(R.id.tvSostavRightTitle);
        tvSostavMainTitle = (TextView) findViewById(R.id.tvSostavMainTitle);
        tvGoalsHeader = (TextView) findViewById(R.id.tvGoals);
        tvWarningsHeader = (TextView) findViewById(R.id.tvWarnings);
        tvTranslationHeader = (TextView) findViewById(R.id.tvTranslation);
        imgGoalsDropdown = (ImageView) findViewById(R.id.imgGoalsPointer);
        imgWarningsDropdown = (ImageView) findViewById(R.id.imgWarningsPointer);
        imgTranslationDropdown = (ImageView) findViewById(R.id.imgTranslationPointer);

        linGoals = (LinearLayout) findViewById(R.id.linGoals);
        linWarnings = (LinearLayout) findViewById(R.id.linWarnings);
        linTranslation = (LinearLayout) findViewById(R.id.linTranslation);
        linearMatchDetails = (LinearLayout) findViewById(R.id.linearDetailsMatch);

        animationDown = AnimationUtils.loadAnimation(
                this, R.anim.rotate_pointer_down);
        animationUp = AnimationUtils.loadAnimation(
                this, R.anim.rotate_pointer_up);
        animationCloseElementGoals = AnimationUtils.loadAnimation(
                this, R.anim.close);
        animationOpenElementGoals = AnimationUtils.loadAnimation(
                this, R.anim.open);

        animationCloseElementWarnings = AnimationUtils.loadAnimation(
                this, R.anim.close);
        animationOpenElementWarnings = AnimationUtils.loadAnimation(
                this, R.anim.open);

        animationCloseElementTranslation = AnimationUtils.loadAnimation(
                this, R.anim.close);
        animationOpenElementTranslation = AnimationUtils.loadAnimation(
                this, R.anim.open);

        animationCloseElementGoals.setAnimationListener(animCloseListenGoals);
        animationOpenElementGoals.setAnimationListener(animOpenListenGoals);

        animationCloseElementWarnings.setAnimationListener(animCloseListenWarnings);
        animationOpenElementWarnings.setAnimationListener(animOpenListenWarnings);

        animationCloseElementTranslation.setAnimationListener(animCloseListenTranslation);
        animationOpenElementTranslation.setAnimationListener(animOpenListenTranslation);


        goalsListView = (ListView) findViewById(R.id.lvGoals);
        warningsListView = (ListView) findViewById(R.id.lvWarnings);

        goalsAdapter = new GoalsAdapter(getApplicationContext(), goalsList);
        warningsAdapter = new WarningsAdapter(getApplicationContext(), warningsList);
        translationAdapter = new TranslationAdapter(getApplicationContext(), translationsList);

        relativeLayout = (RelativeLayout) findViewById(R.id.relLayout);

        scrollView = (ScrollView) findViewById(R.id.scrollView1);

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

        linearLayout = (LinearLayout) findViewById(R.id.linLayout);
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


    @SuppressLint("ResourceAsColor")
    private void setData() {
        tvLeftTeamName.setText(leftTeamName);
        tvRightTeamName.setText(rightTeamName);
        tvRightTeamScore.setText(rightTeamScore);
        tvLeftTeamScore.setText(leftTeamScore);
//        tvLeftTeamLineup.setText(leftTeamLineup);
//        tvRightTeamLineup.setText(rightTeamLineup);
//        tvLeftTeamSubLineup.setText(leftTeamSubLineup);
//        tvRightTeamSubLineup.setText(rightTeamSubLineup);
//        tvSostavLeftTitle.setText(sostavLeftTitle);
//        tvSostavRightTitle.setText(sostavRightTitle);
//        tvSostavMainTitle.setText(sostavMainTitle);
        Picasso.with(getApplicationContext()).load(leftTeamLogo).into(imgLeftTeamLogo);
        Picasso.with(getApplicationContext()).load(rightTeamLogo).into(imgRightTeamLogo);
        goalsListView.setAdapter(goalsAdapter);
        warningsListView.setAdapter(warningsAdapter);


        LinearLayout linBG = (LinearLayout) findViewById(R.id.linScore);


        switch (getState()) {
            case -1:
                break;
            case 0: {
                linBG.setBackgroundColor(Color.rgb(237, 28, 36));
                tvLeftTeamScore.setTextColor(Color.WHITE);
                tvRightTeamScore.setTextColor(Color.WHITE);
                tvScoreNull.setTextColor(Color.WHITE);
            }
            break;
            case 1: {
                linBG.setBackgroundColor(Color.rgb(24, 138, 25));
                tvLeftTeamScore.setTextColor(Color.WHITE);
                tvRightTeamScore.setTextColor(Color.WHITE);
                tvScoreNull.setTextColor(Color.WHITE);
            }
            break;
        }


        /*
        * Make dynamic body of translation
        * */
        llTranslation = (LinearLayout) findViewById(R.id.llTranslation);

        for (Translation t : translationsList) {


            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.HORIZONTAL);
            item.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            item.setBaselineAligned(true);

            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(25, 25);
            imgParams.topMargin = 27;
            imgParams.leftMargin = 0;
            img.setLayoutParams(imgParams);
            switch (t.event) {
                case goal:
                    img.setImageResource(R.drawable.ball);
                    break;
                case yellow:
                    img.setImageResource(R.drawable.yellow_card);
                    break;
                case red:
                    img.setImageResource(R.drawable.red_card);
                    break;
                case end_match:
                    img.setImageResource(R.drawable.red_whistle);
                    break;
                case start_match:
                    img.setImageResource(R.drawable.whistle);
                    break;
                case change:
                    img.setImageResource(R.drawable.change);
                    break;
                case pause:
                    img.setImageResource(R.drawable.timer);
                    break;
                default:
                    break;
            }
            TextView tvTime = new TextView(this);
            tvTime.setLayoutParams(new ViewGroup.LayoutParams(
                    65,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            tvTime.setPadding(20, 20, 0, 20);
            tvTime.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            tvTime.setTextColor(Color.BLACK);
            tvTime.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvTime.setText(t.time);

            TextView tvText = new TextView(this);
            tvText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            tvText.setPadding(20, 20, 20, 20);
            tvText.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            tvText.setTextColor(Color.BLACK);
            tvText.setText(t.text);

            item.addView(img);
            item.addView(tvTime);
            item.addView(tvText);

            llTranslation.addView(item);

        }

        setListViewHeightToNull(goalsListView);
        linGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goalsHide) {
                    imgGoalsDropdown.startAnimation(animationDown);
                    imgGoalsDropdown.setRotation(90);
                    goalsListView.startAnimation(animationOpenElementGoals);
                    goalsHide = false;
                } else {
                    goalsListView.startAnimation(animationCloseElementGoals);
                    imgGoalsDropdown.startAnimation(animationUp);
                    imgGoalsDropdown.setRotation(0);
                    goalsHide = true;
                }
            }
        });

        setListViewHeightToNull(warningsListView);
        linWarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warningsHide) {
                    imgWarningsDropdown.startAnimation(animationDown);
                    imgWarningsDropdown.setRotation(90);
                    warningsListView.startAnimation(animationOpenElementWarnings);
                    warningsHide = false;
                } else {
                    warningsListView.startAnimation(animationCloseElementWarnings);
                    imgWarningsDropdown.startAnimation(animationUp);
                    imgWarningsDropdown.setRotation(0);
                    warningsHide = true;
                }
            }
        });

        setTranslationHide(llTranslation);
        linTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (translationHide) {
                    imgTranslationDropdown.startAnimation(animationDown);
                    imgTranslationDropdown.setRotation(90);
                    llTranslation.startAnimation(animationOpenElementTranslation);
                    translationHide = false;
                } else {
                    llTranslation.startAnimation(animationCloseElementTranslation);
                    imgTranslationDropdown.startAnimation(animationUp);
                    imgTranslationDropdown.setRotation(0);
                    translationHide = true;
                }
            }
        });

        /*
        * End of making
        * */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
//            case R.id.action_menuRefresh:{
//
//                new NewThread().execute();
//                return true;
//            }
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    Animation.AnimationListener animCloseListenGoals = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            setListViewHeightToNull(goalsListView);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener animOpenListenGoals = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            setListViewHeightBasedOnChildren(goalsListView);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener animCloseListenWarnings = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            setListViewHeightToNull(warningsListView);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener animOpenListenWarnings = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            setListViewHeightBasedOnChildren(warningsListView);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener animCloseListenTranslation = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            setTranslationHide(llTranslation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener animOpenListenTranslation = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            setTranslationOpen(llTranslation);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()) - 1);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightToNull(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = 0;//totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setTranslationHide(LinearLayout lin) {

        ViewGroup.LayoutParams params = lin.getLayoutParams();
        params.height = 0;
        lin.setLayoutParams(params);
    }

    public static void setTranslationOpen(LinearLayout lin) {
        ViewGroup.LayoutParams params = lin.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lin.setLayoutParams(params);
    }

    public String getLink() {
        return getIntent().getStringExtra("href");
    }

    public int getState() {
        return getIntent().getIntExtra("state", 0);
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu
                    .findItem(R.id.action_menuRefresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }

    public class NewThread extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            setRefreshActionButtonState(true);
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            Document doc = null;

            try {
                doc = Jsoup.connect(link).get();

                System.out.println("---End");

                leftTeamLogo = doc.select("div.left-logo a img").attr("src");
                rightTeamLogo = doc.select("div.right-logo a img").attr("src");

                leftTeamLogo = leftTeamLogo.replace("0x35", "0x100");
                rightTeamLogo = rightTeamLogo.replace("0x35", "0x100");

                leftTeamName = doc.select("div.left-logo a img").attr("alt");
                rightTeamName = doc.select("div.right-logo a img").attr("alt");
                leftTeamScore = doc.select("div.score").select("span").first().text();
                rightTeamScore = doc.select("div.score").select("span").last().text();


                Elements elements = doc.select("div.match-details-teams");


                for (Element el : elements) {
                    if (el.className().equals("score ended")) {
                        state = 2;
                        Log.d("myLog", "MATCH IS ENDED....");
                    }
                }

                Elements lineup = doc.select(".line-up-table tr td.name");
                int i = 1;
                int nL = 1;
                int nR = 1;

                if (!lineup.select("a").text().equals("")) {//проверяем есть ли текстовая трансляция

                    for (Element el : lineup) {

                        // rightTeamLineup = all.last().select("a").text();
                        if ((i % 2) == 0) {
                            if (nL <= 11)
                                leftTeamLineup += el.select("a").text() + ", ";
                            else
                                leftTeamSubLineup += el.select("a").text() + ", ";
                            nL++;
                        } else {
                            if (nR <= 11)
                                rightTeamLineup += el.select("a").text() + ", ";
                            else
                                rightTeamSubLineup += el.select("a").text() + ", ";
                            nR++;
                        }
                        i++;

                    }

                    leftTeamLineup = leftTeamLineup.replace("null", "");
                    leftTeamSubLineup = leftTeamSubLineup.replace("null", "");
                    rightTeamLineup = rightTeamLineup.replace("null", "");
                    rightTeamSubLineup = rightTeamSubLineup.replace("null", "");

                    leftTeamLineup = leftTeamLineup.substring(0, (leftTeamLineup.length()) - 2) + ".";
                    leftTeamSubLineup = "Запасные: " + leftTeamSubLineup.substring(0, (leftTeamSubLineup.length()) - 2) + ".";
                    rightTeamLineup = rightTeamLineup.substring(0, (rightTeamLineup.length()) - 2) + ".";
                    rightTeamSubLineup = "Запасные: " + rightTeamSubLineup.substring(0, (rightTeamSubLineup.length()) - 2) + ".";

                    sostavLeftTitle = leftTeamName;
                    sostavRightTitle = rightTeamName;
                    sostavMainTitle = "Составы команд:";

                    Elements events = doc.select(".match-details-table tr td");

                    Goal.Type typeG;
                    Warning.Card card = null;
                    Warning.Type typeW;

                    goalsList.clear();
                    warningsList.clear();

                    for (Element event : events) {
                        if (!event.select("a").text().equals("")) {
                            if (event.select("i").hasClass("goal")) {
                                if ((leftTeamLineup + leftTeamSubLineup).contains(event.select("a").text()))
                                    typeG = Goal.Type.Right;
                                else
                                    typeG = Goal.Type.Left;

                                goalsList.add(new Goal(
                                                event.select("a").text(),
                                                event.select("span b").text() + "'",
                                                typeG)
                                );
                            } else {

                                if (event.select("i").hasClass("yellow-card"))
                                    card = Warning.Card.Yellow;
                                if (event.select("i").hasClass("red-card"))
                                    card = Warning.Card.Red;

                                if ((leftTeamLineup + leftTeamSubLineup).contains(event.select("a").text()))
                                    typeW = Warning.Type.Right;
                                else
                                    typeW = Warning.Type.Left;

                                warningsList.add(new Warning(
                                        event.select("a").text(),
                                        event.select("span b").text() + "'",
                                        typeW,
                                        card
                                ));

                            }
                        }
                    }
                }


                translationsList.clear();

                Elements translation = doc.select(".online-match ul.online-list li");
                Translation.Event event = Translation.Event.none;
                for (Element item : translation) {

                    Element sub = item.select("p").first();

                    if (item.select("p").first().className().equals("icon icon1"))
                        event = Translation.Event.start_match;
                    if (item.select("p").first().className().equals("icon icon11"))
                        event = Translation.Event.none;
                    if (item.select("p").first().className().equals("icon icon7"))
                        event = Translation.Event.goal;
                    if (item.select("p").first().className().equals("icon icon4"))
                        event = Translation.Event.yellow;
                    if (item.select("p").first().className().equals("icon icon9"))
                        event = Translation.Event.pause;
                    if (item.select("p").first().className().equals("icon icon3"))
                        event = Translation.Event.change;
                    if (item.select("p").first().className().equals("icon icon10"))
                        event = Translation.Event.end_match;
                    if (item.select("p").first().className().equals("icon icon5"))
                        event = Translation.Event.red;

                    translationsList.add(new Translation(
                            item.select("div.text").text(),
                            item.select("p.time").text(),
                            event
                    ));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            setData();
            if (!goalsList.isEmpty() || !warningsList.isEmpty())
                linearMatchDetails.setVisibility(View.VISIBLE);

            setRefreshActionButtonState(false);
            if(!checkInternet()){
                showNoConnection();
                return;
            }
            if(leftTeamName.isEmpty() && checkInternet()){
                showNoResponce();

            }else{
                showMainScreen();
            }
        }

    }

}
