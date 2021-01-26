package com.caturindo.activities.meeting.create

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.PermissionRequest
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.SelectLocationActivity
import com.caturindo.activities.meeting.create.CreateMeetingActivity
import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.team.add.AddTeamActivity
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.activity_create_meeting.*
import kotlinx.android.synthetic.main.view_choose_take_photo.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Response
import java.io.File

class CreateMeetingActivity : BaseActivity(), CreatingMeetingContract.View {
    private var presenter: CreateMeetingPresenter = CreateMeetingPresenter(this)
    private var imagePath: String? = ""
    private val OPERATION_GET_FILE = 200
    private val OPERATION_CHOOSE_PHOTO = 2
    lateinit var easyImage: EasyImage
    private val self = this
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var tvLocation: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meeting)
        setupToolbar()
        setupLocationAction()
        initData()

    }

    private fun initData() {
        presenter = CreateMeetingPresenter(this)
        easyImage = EasyImage.Builder(this).setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build()
        initPersmission()

        tv_attachment_label.setOnClickListener {
            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openGallery(this)


            } else {
                EasyPermissions.requestPermissions(
                        this, "This application need your permission to access photo gallery.",
                        91, android.Manifest.permission.CAMERA
                )
            }
        }

        et_tag.setOnClickListener {
            startActivity(Intent(this,AddTeamActivity::class.java))
        }
    }

    private fun chooseFile() {
        val view = layoutInflater.inflate(R.layout.view_choose_take_photo, null)
        val dialog = BottomSheetDialog(this)
        val window = dialog.window
        window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(view)

        view.btn_camera.setOnClickListener {

            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openGallery(this)
//                ImagePicker.create(this) // Activity or Fragment
//                    .start();
                dialog.dismiss()


            } else {
                EasyPermissions.requestPermissions(
                        this, "This application need your permission to access photo gallery.",
                        91, android.Manifest.permission.CAMERA
                )
            }
        }

        dialog.show()
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

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.setText("Create Meeting")
        setupNavigationMenu()
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun setupLocationAction() {
        tvLocation = findViewById(R.id.et_location)
        tvLocation?.setOnClickListener(View.OnClickListener { startActivityForResult(Intent(this@CreateMeetingActivity, SelectLocationActivity::class.java), 24) })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> self.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(requestCode, resultCode, data, this,
                object : DefaultCallback() {
                    override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                        var file = File(imageFiles.get(0).file.toString())
                        var requestFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                        var body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            GlobalScope.launch {
                                val compressedImageFile = Compressor.compress(
                                        this@CreateMeetingActivity,
                                        File(file.path)
                                ) {
                                    resolution(1280, 720)
                                    quality(80) // combine with compressor constraint
                                    format(Bitmap.CompressFormat.JPEG)
                                    size(2_097_152)

                                }

                                requestFile = RequestBody.create(MediaType.parse("image/*"), File(compressedImageFile.path))
                                body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                                presenter.uploadFile(body)

                            }
                        } else {
                            requestFile = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                            body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                            presenter.uploadFile(body)
                        }


                    }
                })


    }

    override fun showProgress() {
        Log.e("TAG", "progress jalan")
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun successUpload(msg: String, uploadDto: UploadDto) {
        showLongSuccessMessage(msg)
        Log.e("TAG", "file ${uploadDto.id}")
        img_upload.visibility = View.VISIBLE
        Glide.with(this@CreateMeetingActivity).load(uploadDto.file).into(img_upload)
    }

    override fun failUpload(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun succesCreate(msg: String) {

    }
}