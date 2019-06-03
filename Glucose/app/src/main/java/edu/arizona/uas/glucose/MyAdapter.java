package edu.arizona.uas.glucose;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<Glucose> histories;
    FragmentManager manager;
    LayoutInflater layoutInflater;

    public MyAdapter(List<Glucose> histories, FragmentManager manager, LayoutInflater layoutInflater) {
        this.histories = histories;
        this.manager = manager;
        this.layoutInflater = layoutInflater;
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater, manager, parent);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glucose glucose = histories.get(position);
        holder.bindData(glucose);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}





class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    private TextView lbl_date, lbl_average_glucose;
    private CheckBox chk_normal;
    private Glucose data;
    private FragmentManager manager;
    private final String key = "daily_glucose";


    public MyViewHolder(LayoutInflater inflater, FragmentManager manager, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_snapshot, parent, false));
        itemView.setOnClickListener(this);
        this.manager = manager;
        lbl_date = (TextView) itemView.findViewById(R.id.lbl_date);
        lbl_average_glucose = (TextView) itemView.findViewById(R.id.lbl_average_glucose);
        chk_normal = (CheckBox) itemView.findViewById(R.id.chk_normal);
    }

    public void bindData(Glucose glucose) {
        data = glucose;
        lbl_date.setText(data.date.toString());
        lbl_average_glucose.setText(String.valueOf(data.average));
        chk_normal.setChecked(data.normal);
    }

    @Override
    public void onClick(View v) {
        Bundle arg = new Bundle();
        arg.putSerializable(key, data);

        DetailFragment detailFrag = new DetailFragment();
        detailFrag.setArguments(arg);

        FragmentTransaction transaction = manager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, detailFrag);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}

