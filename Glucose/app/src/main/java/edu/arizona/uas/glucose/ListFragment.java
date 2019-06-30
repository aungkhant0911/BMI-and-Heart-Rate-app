package edu.arizona.uas.glucose;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import edu.arizona.uas.glucose.networking.GlucoseWebViewerActivity;

public class ListFragment extends Fragment {
    RecyclerView recycler;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        MyAdapter adapter = new MyAdapter(GlucoseHistory.histories, getLayoutInflater());
        recycler.setAdapter(adapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==R.id.btn_menu_add)
            GlucoseActivity.replaceActivityFragment(new DetailFragment());

        else if(item.getItemId() == R.id.btn_menu_webview)
            startWebView();;

        return true;
        //return super.onOptionsItemSelected(item);
    }

    private void startWebView() {
        Intent intent = new Intent(getActivity(), GlucoseWebViewerActivity.class);
        startActivity(intent);
    }
}
