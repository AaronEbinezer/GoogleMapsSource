package com.eagleemperor.googlemapssource;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Connection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginAndSignin.GetCredentials{

    private static final String TAG = "MainActivity";

    private static final int Error_Dialog_Request = 9001;

    private FirebaseAuth firebaseAuth;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            LoginAndSignin loginAndSignin=new LoginAndSignin();
            fragmentTransaction.add(R.id.container,loginAndSignin);
            fragmentTransaction.commit();
        }


    }

    public void registeringUser() {
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }

    public void loginUser()
    {
//        String email=EmailId.getText().toString();
//        String password=Password.getText().toString();
//        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
//        {
//            Log.d(TAG,"Empty email id");
//            Toast.makeText(getApplicationContext(),"Enter Email Id and Password",Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                {
//                    finish();
//                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"Error Occurred Please try again",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    public void init()
    {
        startActivity(new Intent(MainActivity.this,MapActivity.class));
    }

    public boolean isServicesOk()
    {
//        Log.d(TAG,"isServicesOk : Checking google Services version");
//        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
//
//        if(available== ConnectionResult.SUCCESS)
//        {
//            Log.d(TAG,"isServiceOk : Google Play services is working");
//            return true;
//        }
//        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            Log.d(TAG,"isServicesOk : Error occured but can be fixed");
//            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,Error_Dialog_Request);
//            dialog.show();
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this,"Error in Google Play Serice",Toast.LENGTH_SHORT).show();
//        }
        return false;
    }

    @Override
    public void getCredentials(boolean bool) {

        if(bool)
        {

        }

    }
}
