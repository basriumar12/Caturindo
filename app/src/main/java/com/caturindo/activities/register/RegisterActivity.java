package com.caturindo.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.caturindo.R;
import com.caturindo.activities.login.LoginActivity;
import com.caturindo.models.RegisterDto;
import com.caturindo.utils.ApiInterface;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private RegisterPresenter presenter ;
    private RegisterActivity self = this;
    private EditText etRegisterEmail, etRegisterUsername, etRegisterPhone, etRegisterPassword;
    private Button btnRegister, btnRegisterLogin;
    private ProgressBar progressBar;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterUsername = findViewById(R.id.etRegisterUsername);
        etRegisterPhone = findViewById(R.id.etRegisterPhone);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);

        progressBar = findViewById(R.id.progressBar);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegisterLogin = findViewById(R.id.btnRegisterLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etRegisterEmail.getText().toString().matches("") || etRegisterPhone.getText().toString().matches("") || etRegisterPassword.getText().toString().matches("")){
                    Toast.makeText(self, "Form registrasi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.getRegister(
                            etRegisterEmail.getText().toString(),
                            etRegisterPassword.getText().toString(),
                            etRegisterUsername.getText().toString(),
                            etRegisterPhone.getText().toString()
                    );
                }
            }

        });
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, LoginActivity.class);
                startActivity(intent);
                self.finish();
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
        Toast.makeText(self, "Sukses Register", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onErrorGetData(String msg) {
        Toast.makeText(self, msg, Toast.LENGTH_SHORT).show();

    }
}
