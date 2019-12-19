package com.eagleemperor.googlemapssource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView textView;
    private EditText Name,UserId,Password,PhoneNo,newName;
    private Button Logout,Submit,nameChange,Emp_Login;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabseReference;
    private static final String TAG = "ProfileActivity";
    EmployeeDetails employeeDetails;
    String key;
    String names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        }
        setContentView(R.layout.activity_profile);
//        Name= (EditText) findViewById(R.id.employee_name);
//        UserId= (EditText) findViewById(R.id.user_id);
//        Password= (EditText) findViewById(R.id.emp_password);
//        PhoneNo= (EditText) findViewById(R.id.phone_no);
//        Submit= (Button) findViewById(R.id.pro_submit);


        mDatabseReference=FirebaseDatabase.getInstance().getReference();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Profile Activity ","into sumbit");
                names=Name.getText().toString();
                String userId=UserId.getText().toString();
                String password=Password.getText().toString();
                String phoneNo=PhoneNo.getText().toString();
                key=mDatabseReference.push().getKey();

                if(!TextUtils.isEmpty(names) || !TextUtils.isEmpty(userId) || !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneNo))
                {

                    Log.d("Profile Activity ","if else statement");
                    employeeDetails=new EmployeeDetails(names,userId,phoneNo,password);
//                    Task k=mDatabseReference.child(key).setValue(employeeDetails);
                    DatabaseReference mRef = mDatabseReference.getDatabase().getReference("users");
                    Task result=mRef.child(names).setValue(employeeDetails);
                    if (result.isSuccessful())
                    {
                        Log.d("Profile Activity ","into successfull");
                        Toast.makeText(getApplicationContext(),"Upload Success ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("Profile Activity ","error sumbit");
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        nameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=newName.getText().toString();
                if(!TextUtils.isEmpty(name))
                {
                    Log.d(TAG,"namechange");

                    Task k=mDatabseReference.child("users").child(names).child("name").setValue(name);
                    if(k.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    }

                    Log.d(TAG,"FirebaseDatabase "+key);
                }
            }
        });


        Emp_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfileActivity.this,EmployeeLog.class));
            }
        });

        textView= (TextView) findViewById(R.id.profile_name);




        FirebaseUser user=firebaseAuth.getCurrentUser();

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });

    }
}
