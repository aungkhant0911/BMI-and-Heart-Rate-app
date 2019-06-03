package edu.arizona.uas.glucose;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListFragment extends Fragment {
    RecyclerView recycler;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list, container, false);
        recycler = (RecyclerView) v.findViewById(R.id.recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        MyAdapter adapter = new MyAdapter(GlucoseHistory.histories, getFragmentManager(), getLayoutInflater());
        recycler.setAdapter(adapter);
    }

}
