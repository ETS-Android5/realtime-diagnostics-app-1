package com.example.mobilehealthprototype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientInfoActivity extends AppCompatActivity {
    Sex p_sex = null;
    int p_id, p_age;
    float p_weight, p_height;
    boolean complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        setUpInterface();
    }

    //Helper Functions
    public void warnError(int header_id){
        TextView header = findViewById(header_id);
        String orig = header.getText().toString();
        String mod_orig = (orig.contains("Please Enter")) ? orig : orig + "(Please Enter A Value)";
        header.setText(mod_orig);
        header.setTextColor(getResources().getColor(R.color.errorColor));
    }

    public float checkValue(int id, int id2){
        String parsed = ((TextView) findViewById(id)).getText().toString();
        if(parsed == null || parsed.trim().equals("")){
            warnError(id2);
            return -1f;
        }
        return Float.parseFloat(parsed);
    }

    //Function that creates the logic & handling behind the activity
    public void setUpInterface(){
        //Radio button setup
        RadioButton moption = (RadioButton) findViewById(R.id.Male_option);
        RadioButton foption = (RadioButton) findViewById(R.id.Female_option);

        View.OnClickListener onRBClick = new View.OnClickListener() {
            public void onClick(View view){
                boolean checked = ((RadioButton) view).isChecked();
                switch(view.getId()) {
                    case R.id.Male_option:
                        if (checked)
                            p_sex = Sex.MALE;
                        break;
                    case R.id.Female_option:
                        if (checked)
                            p_sex = Sex.FEMALE;
                        break;
                }
            }
        };

        moption.setOnClickListener(onRBClick);
        foption.setOnClickListener(onRBClick);

        Button next_step = (Button) findViewById(R.id.next_step_button);
        next_step.setBackgroundColor(getResources().getColor(R.color.addColor));
        next_step.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                p_id = (int) checkValue(R.id.pid_input, R.id.pid_header);
                p_age = (int) checkValue(R.id.age_input, R.id.age_header);
                p_height = checkValue(R.id.height_input, R.id.height_header);
                p_weight = checkValue(R.id.weight_input, R.id.weight_header);
                if(p_sex == null){  warnError(R.id.sex_option_header); }
                complete = (p_id > 0) & (p_age > 0) & (p_sex != null) &(p_height > 0) & (p_weight > 0);

                if(complete) {
                    Intent intent = new Intent(PatientInfoActivity.this, ListSymptoms.class);
                    intent.putExtra("hid", p_id);
                    intent.putExtra("sex", p_sex);
                    intent.putExtra("age", p_age);
                    intent.putExtra("height", p_height);
                    intent.putExtra("weight", p_weight);
                    startActivity(intent);
                }
            }
        });
    }


}
