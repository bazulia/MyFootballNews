package com.bezeka.igor.myfootballnewsnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bezeka.igor.myfootballnews.R;

/**
 * Created by Татьяна on 24.02.2015.
 */
public class ProgressFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_fragment,container);

        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        return view;
    }
}
