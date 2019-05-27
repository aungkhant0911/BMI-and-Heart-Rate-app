/**
 *  Activity class for calculating heart rate.
 *  This activity is called from BMI_Activity, the main activity of the app.
 *
 * @author Aung Khant
 */

package edu.arizona.uas.BMI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HeartRate_Activity extends AppCompatActivity {
    Button btn_calc_heart, btn_clear_heart;
    TextView lbl_max_rate_label, lbl_max_rate, lbl_target_rate_label, lbl_target_rate;
    EditText edit_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        setTitle("BMI and Heart Rate Calculator");

        assignWidgetRefs();

        btn_calc_heart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lbl_max_rate_label.setText("Maximum Heart Rate");
                lbl_target_rate_label.setText("Target Heart Rate");

                int age = Integer.valueOf(edit_age.getText().toString());
                int max_rate =  (220 - age);
                lbl_max_rate.setText("" + max_rate);
                lbl_target_rate.setText(String.format("[from %.1f to  %.1f]", max_rate * 0.5, max_rate * 0.85));
            }
        });


        btn_clear_heart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lbl_max_rate_label.setText("");
                lbl_target_rate_label.setText("");
                lbl_max_rate.setText("");
                lbl_target_rate.setText("");
                edit_age.setText("");
            }
        });
    }


    /**
     *  Retrieve and assign all widgets
     * @param
     * @return
     */
    private void assignWidgetRefs() {
        btn_calc_heart = (Button) findViewById(R.id.btn_calc_heart);
        btn_clear_heart = (Button) findViewById(R.id.btn_clear_heart);

        edit_age = (EditText) findViewById(R.id.edit_age);

        lbl_max_rate_label = (TextView) findViewById(R.id.lbl_max_rate_label);
        lbl_max_rate = (TextView) findViewById(R.id.lbl_max_rate);
        lbl_target_rate_label = (TextView) findViewById(R.id.lbl_target_rate_label);
        lbl_target_rate = (TextView) findViewById(R.id.lbl_target_rate);
    }
}
