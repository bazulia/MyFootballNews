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
 * Created by Igor on 15.02.2015.
 */
public class MatchFragment extends Fragment {

    public static final String ARGS_KEY = "matchList";

    ArrayList<Match> matchList = new ArrayList<Match>();
    ListView lvMatch;

    public static MatchFragment newInstance(ArrayList<Match> matchList) {
        MatchFragment matchFragment = new MatchFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(MatchFragment.ARGS_KEY,matchList);
        matchFragment.setArguments(args);
        return matchFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        matchList = getArguments().getParcelableArrayList(MatchFragment.ARGS_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.match_list_fragment,container,false);

        lvMatch = (ListView)view.findViewById(R.id.listViewMatch);
        lvMatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(matchList.get(position).link.contains("football")) {
                    Intent intent = new Intent(getActivity(), DetailMatchActivity.class);
                    intent.putExtra("href", matchList.get(position).link);
                    intent.putExtra("state",matchList.get(position).state);
                    startActivity(intent);
                }
            }
        });
        lvMatch.setAdapter(new MatchAdapter(getActivity(),matchList));

        return view;
    }
}
