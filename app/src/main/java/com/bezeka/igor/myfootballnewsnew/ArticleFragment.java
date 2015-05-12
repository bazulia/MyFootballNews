package com.bezeka.igor.myfootballnewsnew;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bezeka.igor.myfootballnews.R;

import java.util.ArrayList;

/**
 * Created by Igor on 21.04.2015.
 */
public class ArticleFragment extends Fragment {

    public static final String ARGS_KEY = "articleList";

    ArrayList<Article> articleList = new ArrayList<Article>();
    ListView lvArticle;

    public static ArticleFragment newInstance(ArrayList<Article> articleList) {
        ArticleFragment articleFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("articleList",articleList);
        articleFragment.setArguments(args);
        return articleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        articleList = getArguments().getParcelableArrayList("articleList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_list_fragment,container,false);

        lvArticle = (ListView)view.findViewById(R.id.listViewArticle);
        lvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(articleList.get(position).link.contains("football")) {
                    Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                    intent.putExtra("href", articleList.get(position).link);
                    startActivity(intent);
                }
            }
        });
        lvArticle.setAdapter(new ArticleAdapter(getActivity(),articleList));

        return view;
    }


}
