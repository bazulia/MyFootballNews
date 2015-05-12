package com.bezeka.igor.myfootballnewsnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bezeka.igor.myfootballnews.R;

import java.util.ArrayList;

/**
 * Created by Igor on 10.02.2015.
 */
public class ResultFragment extends Fragment {

    public static final String ARGS_KEY = "teamList";

    ArrayList<Team> teamList = new ArrayList<Team>();
    ListView lvResult;

    public static ResultFragment newInstance(ArrayList<Team> teamList) {
        ResultFragment resFragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ResultFragment.ARGS_KEY,teamList);
        resFragment.setArguments(args);
        return resFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        teamList = getArguments().getParcelableArrayList(ResultFragment.ARGS_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment,container,false);

        lvResult = (ListView)view.findViewById(R.id.listViewResult);

        lvResult.setAdapter(new ResultAdapter(getActivity(),teamList));

        return view;
    }
}
