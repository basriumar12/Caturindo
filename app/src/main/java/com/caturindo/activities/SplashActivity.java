package com.caturindo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.caturindo.R;
import com.caturindo.activities.register.RegisterActivity;
import com.caturindo.models.RegisterDto;
import com.caturindo.preference.Prefuser;
import com.caturindo.utils.AppConstant;

public class SplashActivity extends AppCompatActivity {

    private SplashActivity self = this;
    private SharedPreferences sessions;
    private final int LOADING_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                sessions = getSharedPreferences(AppConstant.SHARED_PREFERENCE_NAME, 0);

                RegisterDto data = new Prefuser().getUser();

                if(data != null){
                    intent = new Intent(self, HomeActivity.class);
                    startActivity(intent);
                    self.finish();
                }else{
                    intent = new Intent(self, RegisterActivity.class);
                    startActivity(intent);
                    self.finish();
                }

            }
        }, LOADING_DURATION);
    }
}
