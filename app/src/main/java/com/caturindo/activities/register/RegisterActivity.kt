package com.caturindo.activities.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.login.LoginActivity
import com.caturindo.models.RegisterDto
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.isValidEmailAddress
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterContract.View {
    private var presenter: RegisterPresenter? = null
    private val self = this
    private var etRegisterEmail: EditText? = null
    private var etRegisterUsername: EditText? = null
    private var etRegisterPhone: EditText? = null
    private var etRegisterPassword: EditText? = null
    private var btnRegister: Button? = null
    private var btnRegisterLogin: Button? = null
    private var progressBar: ProgressBar? = null
    private val mApiInterface: ApiInterface? = null
    private var rbGroup : RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenter(this)
        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterUsername = findViewById(R.id.etRegisterUsername)
        etRegisterPhone = findViewById(R.id.etRegisterPhone)
        etRegisterPassword = findViewById(R.id.etRegisterPassword)
        progressBar = findViewById(R.id.progressBar)
        btnRegister = findViewById(R.id.btnRegister)
        btnRegisterLogin = findViewById(R.id.btnRegisterLogin)
        rbGroup = findViewById(R.id.rbgrup)
        val email = etRegisterEmail?.text.toString()
        btnRegister?.setOnClickListener(View.OnClickListener {


            if (etRegisterEmail?.getText().toString().equals("") || etRegisterPhone?.getText().toString().equals("") ||
                    etRegisterPassword?.getText().toString().equals("")) {
               showLongErrorMessage("Form registrasi tidak boleh kosong!")
            }

            else if (isValidEmailAddress(etRegisterEmail?.text.toString())==false){
                Log.e("TAG","role ${etRegisterEmail?.text.toString()}")
                showErrorMessage("Salah format email")
                etRegisterEmail?.setError("Salah format email")
            }
            else {


                val select = rbgrup.checkedRadioButtonId
                var role = ""
                if (select!=-1){
                    val radio:RadioButton = findViewById(select)
                    if (radio.text.equals("Manager")){
                        role = "2"
                    }else{
                        role ="3"
                    }
                }else{
                    showErrorMessage("Belum memilih jabatan")
                }

                presenter?.getRegister(
                        etRegisterEmail?.getText().toString(),
                        etRegisterPassword?.getText().toString(),
                        etRegisterUsername?.getText().toString(),
                        etRegisterPhone?.getText().toString(),
                        role
                )
            }
        })
        btnRegisterLogin?.setOnClickListener(View.OnClickListener {
            val intent = Intent(self, LoginActivity::class.java)
            startActivity(intent)
            self.finish()
        })
    }


    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun onSuccessGet(data: RegisterDto?) {
       showLongSuccessMessage("Sukses Register")
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }
}