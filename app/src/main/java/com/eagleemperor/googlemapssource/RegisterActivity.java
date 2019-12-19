package com.eagleemperor.googlemapssource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText EmailId,Password;
    Button Register;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        EmailId= (EditText) findViewById(R.id.reg_email);
        Password= (EditText) findViewById(R.id.reg_password);
        Register= (Button) findViewById(R.id.register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=EmailId.getText().toString();
                String password=Password.getText().toString();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Email or Password is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Registering...");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Log.d(TAG,"Unsuccessful error "+task.toString());
                                    Toast.makeText(getApplicationContext(),"Registered failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
