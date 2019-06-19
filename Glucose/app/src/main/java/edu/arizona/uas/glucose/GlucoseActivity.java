package edu.arizona.uas.glucose;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class GlucoseActivity extends AppCompatActivity {
    private static FragmentManager manager ;
    private final String key = "daily_glucose";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucose);
        GlucoseHistory.initialize(GlucoseActivity.this);

        manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = getCorrectFragmentOnActivityBegin();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }


    private Fragment getCorrectFragmentOnActivityBegin() {

        Fragment fragment;
        Glucose glucose = GlucoseHistory.findGlucoseHistoryByDate(new MyDate(new Date()));

        if( glucose == null) {

            Bundle arg = new Bundle();
            arg.putSerializable(key, glucose);
            fragment = new DetailFragment();
            fragment.setArguments(arg);

        }else if(GlucoseHistory.histories.size() <= 0) {
            fragment = new DetailFragment();

        }else {
            fragment = new MyViewPagerFragment();
        }

        return fragment;
    }


    public static void replaceActivityFragment(Fragment frag) {
        FragmentTransaction transaction = manager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, frag);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
