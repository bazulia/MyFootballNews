package com.bezeka.igor.myfootballnewsnew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Igor on 10.02.2015.
 */
public class SimplePagerAdapter extends FragmentStatePagerAdapter {

    static final int PAGES_COUNT = 3;

    ArrayList<Team> teamList = new ArrayList<>();
    ArrayList<News> newsList = new ArrayList<>();
    ArrayList<Match> matchList = new ArrayList<>();
    ArrayList<Article> articleList = new ArrayList<>();

    int type;

    public SimplePagerAdapter(FragmentManager fm,
                              ArrayList newsList,
                              ArrayList teamList,
                              ArrayList matchList) {
        super(fm);
        this.matchList = matchList;
        this.teamList = teamList;
        this.newsList = newsList;
        type = 1;
    }

    public SimplePagerAdapter(FragmentManager fm,
                              ArrayList articleList){
        super(fm);
        this.articleList = articleList;
        type = 2;
    }

    String[] titles = {"Новости","Таблица","Матчи"};

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {

        if(type==1) {

            switch (position) {
                case 0:
                    return new NewsFragment().newInstance(newsList);
                case 1:
                    return new ResultFragment().newInstance(teamList);
                case 2:
                    return new MatchFragment().newInstance(matchList);
            }
        }

        if(type==2){
            return new ArticleFragment().newInstance(articleList);
        }

        return null;
    }

    @Override
    public int getCount() {

            return titles.length;

    }
}
