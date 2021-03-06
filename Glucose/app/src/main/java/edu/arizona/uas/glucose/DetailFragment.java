package edu.arizona.uas.glucose;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import org.json.JSONObject;

import java.util.Date;

import edu.arizona.uas.glucose.networking.Uploader;
import edu.arizona.uas.glucose.scheduler.DailyGlucoseTracker;


public class DetailFragment extends Fragment {
    private final String key = "daily_glucose";
    private  Glucose glucose =  null;

    TextView lbl_date, lbl_all_status;
    EditText edit_fasting, edit_breakfast, edit_lunch, edit_dinner, edit_note;
    CheckBox chk_normal;
    Button btn_save, btn_clear, btn_history;
    MyDate date;
    ViewPager container;

    public DetailFragment() {
        date = new MyDate(new Date());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if (getArguments() != null) {
            glucose = (Glucose) getArguments().getSerializable("daily_glucose");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                    Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            this.container  = (ViewPager) container;
        } catch (ClassCastException e) {
            this.container = null;
        }

        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        assignAllWidgetRefs(v);
        setClearButtonAction();
        setSaveButtonAction();
        setHistoryButtonAction();
        setDatePickerAction();

        lbl_date.setText(date.toString());

        if(glucose != null) {
            setVals();
            date = glucose.date;
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_detail, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(glucose != null) {
            if(item.getItemId() == R.id.btn_menu_add)
                new Uploader().execute(glucose.getJsonString());
        }

        if(item.getItemId() == R.id.btn_menu_tracker) {
            System.out.println("SERVICE is On");
            DailyGlucoseTracker.setServiceOn(true, getActivity());
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }


    private void setVals() {
        lbl_date.setText(glucose.date.toString());
        lbl_all_status .setText(String.format("[ Fasting: %s] [Breakfast: %s] [Lunch: %s] [Dinner: %s]",
                glucose.fasting_status, glucose.breakfast_status, glucose.lunch_status, glucose.dinner_status));

        edit_fasting.setText(String.valueOf(glucose.fasting_val));
        edit_breakfast.setText(String.valueOf(glucose.breakfast_val));
        edit_lunch.setText(String.valueOf(glucose.lunch_val));
        edit_dinner.setText(String.valueOf(glucose.dinner_val));
        edit_note.setText(String.valueOf(glucose.note));
        chk_normal.setChecked(glucose.normal);
    }


    private void assignAllWidgetRefs(View v) {
        lbl_date = (TextView)  v.findViewById(R.id.lbl_date);
        lbl_all_status = (TextView)  v.findViewById(R.id.lbl_all_status);

        edit_fasting = (EditText) v.findViewById(R.id.edit_fasting);
        edit_breakfast = (EditText) v.findViewById(R.id.edit_breakfast);
        edit_lunch = (EditText) v.findViewById(R.id.edit_lunch);
        edit_dinner = (EditText) v.findViewById(R.id.edit_dinner);
        edit_note = (EditText) v.findViewById(R.id.edit_note);

        chk_normal = (CheckBox) v.findViewById(R.id.chk_normal);

        btn_clear = (Button) v.findViewById(R.id.btn_clear);
        btn_history = (Button) v.findViewById(R.id.btn_history);
        btn_save = (Button) v.findViewById(R.id.btn_save);
    }


    private void setDatePickerAction() {
        lbl_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date = new MyDate(dayOfMonth, monthOfYear, year);
                                lbl_date.setText(date.toString());
                            }
                        }, date.year, date.month, date.day);
                datePickerDialog.show();
            }
        });
    }



    private void setClearButtonAction() {
        btn_clear.setOnClickListener((v)->{
            lbl_date.setText(null);
            lbl_all_status .setText(null);

            edit_fasting.setText(null);
            edit_breakfast.setText(null);
            edit_lunch.setText(null);
            edit_dinner.setText(null);
            edit_note.setText(null);
            chk_normal.setChecked(false);
        });
    }


    private void setSaveButtonAction() {
       btn_save.setOnClickListener((v)->{
           Glucose newglucose = new Glucose(
                   Integer.valueOf(edit_fasting.getText().toString()),
                   Integer.valueOf(edit_breakfast.getText().toString()),
                   Integer.valueOf(edit_lunch.getText().toString()),
                   Integer.valueOf(edit_dinner.getText().toString()),
                   date,
                   edit_note.getText().toString());

           GlucoseHistory.addNewHistory(newglucose);
           if(container != null)
               container.getAdapter().notifyDataSetChanged();

           glucose = newglucose;
           setVals();
        });
    }


    private void setHistoryButtonAction() {
        btn_history.setOnClickListener((v)->{
            GlucoseActivity.replaceActivityFragment(new ListFragment());
        });
    }
}
