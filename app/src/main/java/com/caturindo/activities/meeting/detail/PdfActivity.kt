package com.caturindo.activities.meeting.detail

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.caturindo.BaseActivity
import com.caturindo.R
import kotlinx.android.synthetic.main.activity_pdf.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.net.URLEncoder

class PdfActivity : BaseActivity() {

    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        setupToolbar()
        val data = intent.getStringExtra("IMAGE")
        webview.getSettings().setJavaScriptEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webview.webChromeClient
        }
        startWebView("https://docs.google.com/viewer?url=${URLEncoder.encode(data, "UTF-8")}&embedded=true")
    }
    private fun startWebView(url: String) {
        Log.e("TAG","data $url")
        webview?.settings?.setJavaScriptEnabled(true);
        webview?.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webview?.getSettings()?.setBuiltInZoomControls(true);
        webview?.getSettings()?.setUseWideViewPort(true);
        webview?.getSettings()?.setLoadWithOverviewMode(true);

        webview?.loadUrl(url)
        webview?.webViewClient = object : WebViewClient() {

            var checkOnPageStartedCalled = false
            override fun shouldOverrideUrlLoading(
                    view: WebView,
                    url: String
            ): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("TAG","data $url")
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.e("TAG", "finish load data resource $url")


            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.e("TAG","data $url")
            }

            override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)


            }


            override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)

            }

            override fun onReceivedError(
                    view: WebView,
                    errorCode: Int,
                    description: String,
                    failingUrl: String
            ) {



            }
        }

    }


    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.text = "file attachment"
        setupNavigationMenu()

        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }
}