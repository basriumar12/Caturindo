package com.caturindo.activities.reset_pass

import android.os.Bundle
import android.util.Log
import android.view.View
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.reset_pass.model.ResetPassRequest
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import kotlinx.android.synthetic.main.activity_reset_pass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPassActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)

        resetPass()
    }

    private fun resetPass() {
        btn_reset.setOnClickListener {
            if (etLoginEmail.text.toString().isNullOrEmpty()) {
                showLongErrorMessage("Email wajib di isi")
            } else {
                progressBar.visibility = View.VISIBLE
                val api = ServiceGenerator.createService(
                        ApiInterface::class.java,
                        Constant.USERNAME,
                        Constant.PASS
                )

                api.postResetPass(ResetPassRequest(etLoginEmail.text.toString())).enqueue(object : Callback<BaseResponse<List<String>>> {
                    override fun onFailure(call: Call<BaseResponse<List<String>>>, t: Throwable) {
                        Log.e("TAG","data gagal ${t.localizedMessage}")
                        progressBar.visibility = View.GONE
                        showLongInfoMessage("Gagal Request")
                    }

                    override fun onResponse(call: Call<BaseResponse<List<String>>>, response: Response<BaseResponse<List<String>>>) {

                        progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            if (response.body()?.status?.equals(true)!!) {
                                showLongSuccessMessage("Berhasil Reset Password, Cek password yang telah dikirimkan di Email.")
                                finish()
                            }else{
                                showInfoMessage(response.body()?.message)

                            }
                        }
                    }

                })

            }
        }
    }
}