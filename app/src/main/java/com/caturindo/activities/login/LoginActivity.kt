package com.caturindo.activities.login

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.HomeActivity
import com.caturindo.activities.login.LoginActivity
import com.caturindo.activities.register.RegisterActivity
import com.caturindo.activities.reset_pass.ResetPassActivity
import com.caturindo.models.RegisterDto
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.AppConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : BaseActivity(), LoginContract.View {
    private var presenter: LoginPresenter? = null
    private val self = this
    private var etLoginEmail: EditText? = null
    private var etLoginPassword: EditText? = null
    private var btnLogin: Button? = null
    private var btnRegisterLogin: Button? = null
    private var sessions: SharedPreferences? = null
    private var progressBar: ProgressBar? = null
    private val mApiInterface: ApiInterface? = null
    private var fcm = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        sessions = getSharedPreferences(AppConstant.SHARED_PREFERENCE_NAME, 0)
        progressBar = findViewById(R.id.progressBar)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegisterLogin = findViewById(R.id.btnRegisterLogin)
        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPassword = findViewById(R.id.etLoginPassword)

        val tvReset = findViewById<TextView>(R.id.tv_reset_pass)
        tvReset.setOnClickListener { startActivity(Intent(this@LoginActivity, ResetPassActivity::class.java)) }
        btnRegisterLogin?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        })

        btnLogin?.setOnClickListener {
            if (etLoginEmail?.text.toString().equals("") ||
                    etLoginPassword!!.text.toString().equals("")) {
                showLongErrorMessage("Username atau password tidak boleh kosong!")
            } else {

                if (!fcm.isNullOrEmpty()) {
                    presenter?.getLogin(etLoginEmail?.text.toString(),
                            etLoginPassword?.text.toString(), fcm)
                }else{
                    showLongErrorMessage("Silahkan close aplikasi, dan buka kembali!")
                }
            }
        }

        getFcm()
    }

  fun getFcm(){

      FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
          if (!task.isSuccessful) {
              return@OnCompleteListener
          }

          // Get new FCM registration token
          val token = task.result
          fcm =token.toString()
          Log.e("TOKEN fcm", "$token")
      })


  }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun onSuccessGet(data: RegisterDto?) {
        showLongSuccessMessage("Berhasil Login")
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
        Prefuser().setUser(data)
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }
}