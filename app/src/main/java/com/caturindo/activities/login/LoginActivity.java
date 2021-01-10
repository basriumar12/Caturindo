package com.caturindo.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.caturindo.R;
import com.caturindo.activities.HomeActivity;
import com.caturindo.models.RegisterDto;
import com.caturindo.preference.Prefuser;
import com.caturindo.utils.ApiInterface;
import com.caturindo.utils.AppConstant;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private LoginPresenter presenter ;
    private LoginActivity self = this;
    private EditText etLoginEmail, etLoginPassword;
    private Button btnLogin;
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
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);

        getData();
    }

    private void getData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLoginEmail.getText().toString().matches("") ||
                        etLoginPassword.getText().toString().matches("")){
                    Toast.makeText(self, "Username atau password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(self, "Berhasil Login", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
        new Prefuser().setUser(data);
    }

    @Override
    public void onErrorGetData(String msg) {
        Toast.makeText(self, msg, Toast.LENGTH_SHORT).show();

    }
}
