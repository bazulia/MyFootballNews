package com.bezeka.igor.myfootballnewsnew;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bezeka.igor.myfootballnews.R;
import com.bezeka.igor.myfootballnewsnew.common.view.SlidingTabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    NewTread nt;

    int type = 1;

    int state = -1; //

    SimplePagerAdapter simplePagerAdapter;

    String[] titles = {"",""};
    String[] links = {"",""};

    String src = "http://football.ua";

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Menu optionsMenu;
    private CharSequence mTitle;

    RelativeLayout activityNoResponce;
    RelativeLayout activityNoConnection;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;

    FragmentTransaction transaction;

    ListView drawerListView;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;

    ProgressBar progressBar;

    DrawerLayout drawerLayout;

    Button repeatInternet;
    Button repeatResponce;

    public static ArrayList<News> newsList = new ArrayList<>();
    public static ArrayList<Team> teamList = new ArrayList<>();
    public static ArrayList<Match> matchList = new ArrayList<>();
    public static ArrayList<Article> articleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relLayout);

        linearLayout = (LinearLayout) findViewById(R.id.linLayout);

        activityNoResponce = (RelativeLayout) findViewById(R.id.layoutNoResponce);

        activityNoResponce.setVisibility(View.GONE);

        activityNoConnection = (RelativeLayout) findViewById(R.id.layoutNoConnection);

        activityNoConnection.setVisibility(View.GONE);

        repeatInternet = (Button)findViewById(R.id.btnRepeat);
        repeatInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTask();
                nt =  new NewTread();
                nt.execute();
                showProgressBar();
            }
        });

        repeatResponce = (Button)findViewById(R.id.btnRepeatResponce);
        repeatResponce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTask();
                nt =  new NewTread();
                nt.execute();
                showProgressBar();
            }
        });

        showProgressBar();

        getNeededResources();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(),newsList,teamList,matchList);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);

        drawerListView = (ListView)findViewById(R.id.drawer_list_view);

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProgressBar();

                if(position>11){
                    simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(),articleList);
                    type = 2;
                }
                else
                {
                    simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(),newsList,teamList,matchList);
                    type = 1;
                }

                onNavigationDrawerItemSelected(position);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        drawerListView.setItemChecked(0,true);
        drawerListView.setSelection(0);
        simplePagerAdapter.notifyDataSetChanged();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                drawerLayout);
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        mTitle = titles[position];
        getSupportActionBar().setTitle(mTitle);

        src = links[position];
        cancelTask();
        nt =  new NewTread();
        nt.execute();

    }

    private void cancelTask() {
        if (nt == null) return;
        nt.cancel(true);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menuRefresh) {
            new NewTread().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void getNeededResources(){
        titles = getResources().getStringArray(R.array.list_drawer_titles);
        links = getResources().getStringArray(R.array.list_drawer_links);
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

    class NewTread extends AsyncTask<String,Void,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setRefreshActionButtonState(true);
        }

        @Override
        protected String doInBackground(String... params) {
            Document doc;

            try {
                if(!src.contains("http"))
                    doc = Jsoup.connect("http://football.ua/").timeout(5000).get();
                else
                    doc = Jsoup.connect(src).get();

                Elements teams = doc.select(".tournament-table").select("tr");

                Elements news = doc.select(".news-feed li");

                Elements matches = doc.select(".feed-table").select("tr");

                Elements articles = doc.select(".main-left").select("article");

                newsList.clear();
                for(Element link:news){
                    if(!link.select("div").text().equals("")) {
                        newsList.add(new News(
                                link.select("div").text(),                  //date
                                link.select("a").text(),                    //title
                                link.select("a").attr("href").toString())); //link
                    }
                }


                teamList.clear();
                for (Element link : teams) {
                    String name = link.select("a").text();              //name
                    String img = link.select("img").attr("src");        //logo
                    String game = link.select(".games").text();         //num games
                    String point = link.select(".score").text();        //score
                    String pos = link.child(0).text();//  .select("first-child").text();
                    img = img.replace("0x20","0x50");                   //convert img from 20x20 to 50x50
                    teamList.add(new Team(name,img,game,point,pos));
                    System.out.println(link.select("first-child").text());
                }

                matchList.clear();
                for(Element link:matches){
                    if(!link.select(".time").text().equals("")) {

                        if(!link.select(".score").isEmpty())
                            state = -1;
                        System.out.println("score  -  "+link.select(".left-team a").text()+" "+link.select(".right-team a").text());


                        if(!link.select(".score").select(".ended").isEmpty())
                             state = 1;
                            System.out.println("score ended  -  "+link.select(".left-team a").text()+" "+link.select(".right-team a").text());

                        matchList.add(new Match(
                                link.select(".left-team a").text(),
                                link.select(".right-team a").text(),
                                link.select(".score a").text(),
                                link.select(".time").text(),
                                link.select(".score a").attr("href"),
                                state,
                                ""));
                    } else {
                        matchList.add(new Match(
                                "",
                                "",
                                "",
                                "",
                                "",
                                2,
                                link.select("th").select("p").text()));

                    }

                }

                articleList.clear();

                for(Element el:articles){

                    if(!el.select(".big-intro").select("h4").select("a").isEmpty()) {
                        System.out.println("-> FIRST <-");

                        articleList.add(new Article(
                                el.select(".big-intro").select("h4").select("a").text(),
                                el.select(".big-intro").select("p").select("a").text(),
                                el.select(".big-intro").select("a").select("img").attr("src"),
                                el.select(".big-intro").select("a").attr("href")));
                    }
                    else {

                        articleList.add(new Article(
                                el.select(".news-block").select("h3").select("a").text(),
                                el.select(".news-block").select("p").select("a").text(),
                                el.select(".news-block").select("a").select("img").attr("src").replace("310x230", "630x373"),
                                el.select(".news-block").select("a").attr("href")));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mViewPager.setAdapter(simplePagerAdapter);
            if(type==1) {
                mSlidingTabLayout.setViewPager(mViewPager);
                mSlidingTabLayout.setVisibility(View.VISIBLE);
            }else{
                mSlidingTabLayout.setVisibility(View.GONE);
            }
            mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

                @Override
                public int getIndicatorColor(int position) {
                    return Color.parseColor("#188a19");    //define any color in xml resources and set it here, I have used white
                }

                @Override
                public int getDividerColor(int position) {
                    return getResources().getColor(R.color.white);
                }
            });

            setRefreshActionButtonState(false);

            if(!checkInternet()){
                showNoConnection();
                return;
            }
            if(newsList.isEmpty() && articleList.isEmpty()){
                showNoResponce();

            }else{
                showMainScreen();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }



}
