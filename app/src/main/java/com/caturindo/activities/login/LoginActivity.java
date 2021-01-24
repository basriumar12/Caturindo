package com.caturindo.activities.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.caturindo.BaseActivity;
import com.caturindo.R;
import com.caturindo.activities.HomeActivity;
import com.caturindo.activities.register.RegisterActivity;
import com.caturindo.activities.reset_pass.ResetPassActivity;
import com.caturindo.models.RegisterDto;
import com.caturindo.preference.Prefuser;
import com.caturindo.utils.ApiInterface;
import com.caturindo.utils.AppConstant;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private LoginPresenter presenter ;
    private LoginActivity self = this;
    private EditText etLoginEmail, etLoginPassword;
    private Button btnLogin,btnRegisterLogin;
    private SharedPreferences sessions;
    private ProgressBar progressBar;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        sessions = getSharedPreferences(AppConstant.SHARED_PREFERENCE_NAME, 0);

        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterLogin = findViewById(R.id.btnRegisterLogin);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);

        getData();
        TextView tvReset = findViewById(R.id.tv_reset_pass);
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPassActivity.class));
            }
        });
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void getData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLoginEmail.getText().toString().matches("") ||
                        etLoginPassword.getText().toString().matches("")){
                    showLongErrorMessage("Username atau password tidak boleh kosong!");
                } else {

                    presenter.getLogin(etLoginEmail.getText().toString(),
                                        etLoginPassword.getText().toString() );
                }
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessGet(RegisterDto data) {
       showLongSuccessMessage("Berhasil Login");
        startActivity(new Intent(this, HomeActivity.class));
        finish();
        new Prefuser().setUser(data);
    }

    @Override
    public void onErrorGetData(String msg) {
       showLongErrorMessage(msg);

    }
}
