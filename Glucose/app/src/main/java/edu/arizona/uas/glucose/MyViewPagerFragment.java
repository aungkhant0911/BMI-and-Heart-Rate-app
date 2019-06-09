package edu.arizona.uas.glucose;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerFragment extends Fragment {

    private final String key = "daily_glucose";


    public MyViewPagerFragment() {
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
        View v = inflater.inflate(R.layout.fragment_my_view_pager, container, false);
        ViewPager mViewPager = (ViewPager) v.findViewById(R.id.my_view_pager);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int i) {
                Glucose data = GlucoseHistory.histories.get(i);
                Bundle arg = new Bundle();
                arg.putSerializable(key, data);

                DetailFragment detailFrag = new DetailFragment();
                detailFrag.setArguments(arg);

                return detailFrag;
            }

            @Override
            public int getCount() {
                return GlucoseHistory.histories.size();
            }
        });

        return v;

    }


}
