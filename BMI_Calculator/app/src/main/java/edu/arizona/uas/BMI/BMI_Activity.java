/**
 *  This is main activity of the app.
 *  This activity take in  height /weight input, and produce BMI information
 *
 * @author Aung Khant
 * @email  aungkhant@email.arizona.edu
 */

package edu.arizona.uas.BMI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMI_Activity extends AppCompatActivity {

    Button btn_calc, btn_clear, btn_heart;
    EditText edit_height_in, edit_height_ft, edit_weight;
    TextView lbl_bmi_val, lbl_bmi_lvl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        setTitle("BMI and Heart Rate Calculator");

        assignWidgetRefs();

        btn_clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lbl_bmi_val.setText(null);
                lbl_bmi_lvl.setText(null);

                edit_height_in.setText(null);
                edit_height_ft.setText(null);
                edit_weight.setText(null);
            }
        });

        btn_calc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int height = (Integer.valueOf(edit_height_ft.getText().toString()) * 12) +
                                        Integer.valueOf(edit_height_in.getText().toString());
                int weight = Integer.valueOf(edit_weight.getText().toString());

                float bmi = calculateBMI(height, weight);
                BMI_Level_Indicator  level =  getBMILevel(bmi);
                lbl_bmi_lvl .setText(level.toString());
                lbl_bmi_val.setText("BMI: " + bmi);
            }
        });

        btn_heart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BMI_Activity.this, HeartRate_Activity.class));
            }
        });

        if (savedInstanceState != null) {
            lbl_bmi_lvl.setText(savedInstanceState.getString("bmi_lvl"));
            lbl_bmi_val.setText(savedInstanceState.getString("bmi_val"));
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("bmi_val", lbl_bmi_val.getText().toString());
        savedInstanceState.putString("bmi_lvl", lbl_bmi_lvl.getText().toString());
    }



    /**
     *  Retrieve and assign all widgets
     * @param
     * @return
     */
    private void assignWidgetRefs() {
        btn_calc = (Button) findViewById(R.id.btn_calc);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_heart = (Button) findViewById(R.id.btn_heart);

        edit_height_ft = (EditText) findViewById(R.id.edit_height_ft);
        edit_height_in = (EditText) findViewById(R.id.edit_height_in);
        edit_weight = (EditText) findViewById(R.id.edit_weight);

        lbl_bmi_val = (TextView) findViewById(R.id.lbl_bmi_val);
        lbl_bmi_lvl = (TextView) findViewById(R.id.lbl_bmi_lvl);
    }


    /**
     *  Calculate and return BMI value
     *
     * @param height user height in inch unit.
     * @param weight  user weight in pound.     *
     * @return   return BMI value
     */
    private float calculateBMI(int height, int weight) {
        return (weight * 703) / (height * height);
    }


    /**
     *  Determine BMI level such as Normal, Underweight, Overweight, Obese
     *
     * @param  bmi user bmi value
     * @return   BMI level of Enum type. See BMI_Level_Indicator enum for detail
     */
    private BMI_Level_Indicator getBMILevel(float bmi)  {
        if(bmi < 18.5)
            return  BMI_Level_Indicator.UNDERWEIGHT;

        else if(bmi >= 18.5 && bmi < 24.9)
            return BMI_Level_Indicator.NORMAL;

        else if(bmi >= 24.9 && bmi < 30)
            return BMI_Level_Indicator.OVERWEIGHT;

         return BMI_Level_Indicator.OBESE;
    }
}
