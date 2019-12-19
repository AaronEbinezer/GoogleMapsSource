package com.eagleemperor.googlemapssource;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Aaron on 7/18/2018.
 */

public class LoginAndSignin extends Fragment {

    private Button AdminLogin,EmpLogin;
    private GetCredentials mgetCredentials;
    private static final String TAG = "LoginAndSignin";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_and_signin,container,false);
        AdminLogin= (Button) view.findViewById(R.id.admin_log);
        EmpLogin= (Button) view.findViewById(R.id.emp_log);

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgetCredentials.getCredentials(true);
            }
        });

        EmpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgetCredentials.getCredentials(false);
            }
        });
        return view;
    }


    public interface GetCredentials
    {
        public void getCredentials(boolean bool);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mgetCredentials = (GetCredentials) activity;
        }catch (Exception e)
        {
            Log.d(TAG,"onAttach Error");
        }
    }
}
