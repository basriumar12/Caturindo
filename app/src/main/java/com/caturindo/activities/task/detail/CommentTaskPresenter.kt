package com.caturindo.activities.task.detail

import android.content.Context
import com.caturindo.activities.task.detail.model.AddCommentDto
import com.caturindo.activities.task.detail.model.AddCommentRequest
import com.caturindo.activities.task.detail.model.CommentDto
import com.caturindo.constant.Constant
import com.caturindo.models.*
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentTaskPresenter(private val view: CommentTaskContract.View) : CommentTaskContract.Presenter {


    var context: Context? = null
    val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Constant.USERNAME,
            Constant.PASS
    )


    override fun getComment(idTask: String) {

        view.showProgress()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
            
                api.getComment(idTask).enqueue(object  :retrofit2.Callback<BaseResponse<List<CommentDto>>>{
                    override fun onFailure(call: Call<BaseResponse<List<CommentDto>>>, t: Throwable) {
                        view.hideProgress()
                        view.failGetComment("Gagal Request data")
                    }

                    override fun onResponse(call: Call<BaseResponse<List<CommentDto>>>, response: Response<BaseResponse<List<CommentDto>>>) {
                        view.hideProgress()
                        if (response.isSuccessful){
                            if (response.body()?.status == true){
                                view.successData(response.body()?.data as ArrayList<CommentDto>)
                            }else{
                             //   view.failGetComment("gagal, ${response.body()?.message}")
                            }
                        }else{

                            //view.failGetComment("gagal, ${response.body()?.message}")
                        }
                    }
                })

            }
        }
    }

    override fun postComment(addCommentRequest: AddCommentRequest) {
        view.showProgress()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                api.postComment(addCommentRequest).enqueue(object : Callback<BaseResponse<AddCommentDto>>{
                    override fun onFailure(call: Call<BaseResponse<AddCommentDto>>, t: Throwable) {
                        view.hideProgress()
                        view.failPostComment("gagal create data")
                    }

                    override fun onResponse(call: Call<BaseResponse<AddCommentDto>>, response: Response<BaseResponse<AddCommentDto>>) {

                        view.hideProgress()
                        if (response.isSuccessful){
                            if (response.body()?.status == true){
                                view.successPostComment("Berhasil, ${response.body()?.message}")
                            }else{
                                view.failPostComment("gagal, ${response.body()?.message}")
                            }
                        }else{

                            view.failPostComment("gagal, ${response.body()?.message}")
                        }
                    }
                })
            }
        }
    }


}