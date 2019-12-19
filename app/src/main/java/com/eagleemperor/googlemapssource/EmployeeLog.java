package com.eagleemperor.googlemapssource;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmployeeLog extends AppCompatActivity {

    private EditText Name,Password;
    private Button stop_Service,start_Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_log);

        start_Service= (Button) findViewById(R.id.submit);
        stop_Service= (Button) findViewById(R.id.stop_service);
        Name= (EditText) findViewById(R.id.name);
        Password= (EditText) findViewById(R.id.password);

        start_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Name.getText().toString();
                if(!TextUtils.isEmpty(name)) {
                    Intent intent=new Intent(EmployeeLog.this, MyLocationService.class);
                    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(EmployeeLog.this);
                    SharedPreferences.Editor editor=sharedPreferences.edit();

                    editor.putString("Emp_Name",name);
                    editor.commit();
                    startService(intent);
                }
            }
        });

        stop_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(EmployeeLog.this,MyLocationService.class));
            }
        });
    }
}
