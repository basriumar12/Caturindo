package com.caturindo

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.valdesekamdem.library.mdtoast.MDToast

open class BaseFragment : Fragment(), Baseview {
    lateinit var loading: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun showErrorMessage(message: String?) {
        MDToast.makeText(context,message, MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show()
    }

    override fun showSuccessMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show()
    }

    override fun showInfoMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show()
    }

    override fun showLongErrorMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show()
    }

    override fun showLongSuccessMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show()
    }

    override fun showLongInfoMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show()
    }

    override fun showProgressDialog() {
        
    }

    override fun showProgressDialog(message: String?) {
        
    }

    override fun updateMessageDialog(message: String?) {
        
    }

    override fun dismissProgressDialog() {
        
    }


    override fun closeActivity() {

    }

    override fun refresh() {
        onResume()
    }



    override fun intentTo(target: Class<*>) {
        context?.startActivity(Intent(context, target))
    }


}
