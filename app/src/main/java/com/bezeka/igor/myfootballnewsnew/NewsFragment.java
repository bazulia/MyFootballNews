package com.bezeka.igor.myfootballnewsnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bezeka.igor.myfootballnews.R;

import java.util.ArrayList;

/**
 * Created by Igor on 10.02.2015.
 */
public class NewsFragment extends Fragment {

    public static final String ARGS_KEY = "newsList";

    ArrayList<News> newsList = new ArrayList<News>();
    ListView lvNews;

    public static NewsFragment newInstance(ArrayList<News> newsList) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(NewsFragment.ARGS_KEY,newsList);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        newsList = getArguments().getParcelableArrayList(NewsFragment.ARGS_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment,container,false);

        lvNews = (ListView)view.findViewById(R.id.listViewNews);
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                intent.putExtra("href", newsList.get(position).href);
                startActivity(intent);
            }
        });

        lvNews.setAdapter(new NewsAdapter(getActivity(),newsList));

        return view;
    }
}
