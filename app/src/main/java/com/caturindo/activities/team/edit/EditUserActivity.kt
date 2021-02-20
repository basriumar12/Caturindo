package com.caturindo.activities.team.edit

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.team.model.EditUserRequest
import com.caturindo.activities.team.model.UpdateUploadUserDto
import com.caturindo.models.UserDtoNew
import com.caturindo.preference.Prefuser
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_update_user.*
import kotlinx.android.synthetic.main.activity_update_user.img_background
import kotlinx.android.synthetic.main.custom_toolbar.*

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class EditUserActivity : BaseActivity(), EditUserContract.View {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var imagePath: String? = ""
    private val OPERATION_GET_FILE = 200
    private val OPERATION_CHOOSE_PHOTO = 2
    lateinit var easyImage: EasyImage
    private val self = this
    private var imageProfile = false
    private var imageProfileBackground = false

    lateinit var progressDialog: ProgressDialog
    private lateinit var presenter: EditUserPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)
        presenter = EditUserPresenter(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)


        initData()
        setupToolbar()

    }

    override fun onResume() {
        super.onResume()
        presenter.getUser(Prefuser().getUser()?.id.toString())
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.setText("Edit profile")
        setupNavigationMenu()
        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun initData() {


        easyImage = EasyImage.Builder(this).setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build()
        initPersmission()
        Glide.with(this).load("error").error(R.drawable.ic_baseline_account_circle_24).into(img_profile_edit)

        img_edit_profile.setOnClickListener {


            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openGallery(this)

                imageProfile = true
                imageProfileBackground = false
            } else {
                EasyPermissions.requestPermissions(
                        this, "This application need your permission to access photo gallery.",
                        91, android.Manifest.permission.CAMERA
                )
            }
        }

        img_edit_background.setOnClickListener {


            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openGallery(this)

                imageProfile = false
                imageProfileBackground = true
            } else {
                EasyPermissions.requestPermissions(
                        this, "This application need your permission to access photo gallery.",
                        91, android.Manifest.permission.CAMERA
                )
            }
        }


        btn_cancel.setOnClickListener {
            finish()
        }

        btn_save.setOnClickListener {
            if (edt_email.text.toString().isNullOrEmpty()) {
                edt_email.setError("Tidak bisa kosong")

            } else if (edt_name.text.toString().isNullOrEmpty()) {
                edt_name.setError("Tidak bisa kosong")

            } else if (edt_phone.text.toString().isNullOrEmpty()) {
                edt_phone.setError("Tidak bisa kosong")
            } else if (edt_username.text.toString().isNullOrEmpty()) {
                edt_username.setError("Tidak bisa kosong")
            } else if (edt_whatsapp.text.toString().isNullOrEmpty()) {
                edt_whatsapp.setError("Tidak bisa kosong")
            } else {

                val editUserRequest = EditUserRequest(
                        edt_whatsapp.text.toString(),
                        edt_phone.text.toString(),
                        edt_name.text.toString(),
                        Prefuser().getUser()?.id.toString(),
                        edt_email.text.toString(),
                        edt_username.text.toString()
                )
                presenter.editUser(editUserRequest)
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(requestCode, resultCode, data, this,
                object : DefaultCallback() {
                    override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                        if (imageFiles.get(0).file.length() >= 2219894) {
                            showLongErrorMessage("Size foto ini lebih dari 2 MB, Pilih foto yang lain yang kurang dari 2MB")
                        } else {
                            Log.e("TAG", "image size ${imageFiles.get(0).file.length()}")

                            val file = File(imageFiles.get(0).file.toString())
                            var requestFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                            var body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)
                            val idUser: RequestBody = RequestBody.create(
                                    MultipartBody.FORM, Prefuser().getUser()?.id
                                    .toString())

                            if (imageProfile == true) {
                                requestFile = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                                body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                                presenter.uploadImage(idUser, body)

                            }


                            if (imageProfileBackground == true) {

                                requestFile = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                                body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                                presenter.uploadBackgroundImage(idUser, body)


                            }


                        }
                    }
                })


    }

    private fun initPersmission() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (it.areAllPermissionsGranted()) {
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {

                        token?.continuePermissionRequest()
                    }
                })
                .check()

    }

    override fun showProgress() {
        progressDialog.show()
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressDialog.dismiss()

        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: UpdateUploadUserDto) {
        Glide.with(this).load(data.imageProfile).into(img_profile_edit)
    }

    override fun onSuccessGetBackground(data: UpdateUploadUserDto) {

        Glide.with(this).load(data.imageBackground).into(img_background)
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }

    override fun onSuccessEditUser(msg: String?) {
        showSuccessMessage(msg)
        finish()
    }

    override fun onSuccessGetDetail(data: UserDtoNew) {
        if (data != null)
            edt_email.setText(data.email)
        edt_name.setText(data.name)
        edt_username.setText(data.username)
        if (data.whatsapp != null) {
            edt_whatsapp.setText(data.whatsapp)
        }
        var phone = ""
        if (data.phone.equals("null")) {
            phone =""
        }
        else if (data.phone.equals(null)){
            phone =""
        }
        else {

            phone = data.phone.toString()
        }

        edt_phone.setText(phone)


        Glide.with(this)
                .load(data.imageProfile)
                .error(R.drawable.no_imge)
                .apply(RequestOptions().circleCrop())
                .into(img_profile_edit)
        Glide.with(this)
                .load(data.imageBackground)
                .into(img_background)
    }
}