package com.caturindo.activities.meeting.create

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.SelectLocationActivity
import com.caturindo.activities.grup.model.ResponseGroupDto
import com.caturindo.activities.meeting.MeetingActivity
import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.team.AdapterGroupForCreate
import com.caturindo.activities.team.add.AddTeamMeetingActivity
import com.caturindo.constant.Constant
import com.caturindo.fragments.meeting.MeetingFragment
import com.caturindo.models.BaseResponse
import com.caturindo.models.BookingRequest
import com.caturindo.models.MeetingRequest
import com.caturindo.preference.ModelMeeting
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.caturindo.utils.SwipeToDeleteCallback
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.rximagepicker.ImagePickerObservable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.activity_create_meeting.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.view_choose_take_photo.view.*
import kotlinx.android.synthetic.main.view_grup.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CreateMeetingActivity : BaseActivity(), CreatingMeetingContract.View, AdapterGroupForCreate.OnListener {
    private var presenter: CreateMeetingPresenter = CreateMeetingPresenter(this)
    private var imagePath: String? = ""
    private val OPERATION_GET_FILE = 200
    private val OPERATION_CHOOSE_PHOTO = 2
    private val FILE_REQUEST_CODE = 139
    lateinit var easyImage: EasyImage
    private val self = this
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var tvLocation: TextView? = null
    private var idFile: ArrayList<String> = ArrayList()
    private var urlImage: ArrayList<String> = ArrayList()
    private var idMeetingParent = ""
    private var idGrup = ""
    lateinit var dialog: BottomSheetDialog
    private var adapterImageUpload = AdapterFileUpload(urlImage as ArrayList<String>, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meeting)
        setupToolbar()
        setupLocationAction()
        initData()
        getValidateSubmeeting()
    }

    private fun getValidateSubmeeting() {
        if (!Prefuser().getIdParentMeeting().isNullOrEmpty()) {
            idMeetingParent = Prefuser().getIdParentMeeting().toString()
            et_id_meeting_parent.visibility = View.VISIBLE
            et_id_meeting_parent.text = idMeetingParent

        }


    }


    private fun initData() {

        presenter = CreateMeetingPresenter(this)
        easyImage = EasyImage.Builder(this).setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build()
        initPersmission()

        et_grup.setOnClickListener {
            getGrup()
        }
        tv_attachment_label.setOnClickListener {
            chooseFile()

        }


        rv_image.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_image.adapter = adapterImageUpload
        //delete image adapater
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_image.adapter as AdapterFileUpload
                adapter.removeAt(viewHolder.adapterPosition)
                urlImage.removeAt(viewHolder.adapterPosition)
                idFile.removeAt(viewHolder.adapterPosition)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv_image)


        et_tag.setOnClickListener {
            startActivity(Intent(this, AddTeamMeetingActivity::class.java))
        }

        et_time.setOnClickListener {
            showErrorMessage("Waktu akan di isi otmatis ketika memilih location")
        }

        et_date.setOnClickListener {
            showErrorMessage("Hari akan di isi otmatis ketika memilih location")
        }




        btn_cancel.setOnClickListener {
            finish()
        }

        btn_save.setOnClickListener {

            if (et_meeting_title.text.toString().isNullOrEmpty()) {
                showLongErrorMessage("Judul meeting tidak bisa kosong")
                et_meeting_title.setError("Tidak bisa kosong")
            } else if (et_meeting_desctiption.text.toString().isNullOrEmpty()) {
                showLongErrorMessage("Deskripsi meeting tidak bisa kosong")
                et_meeting_desctiption.setError("Tidak bisa kosong")
            } else if (idGrup.isNullOrEmpty()) {
                showLongErrorMessage("Belum memilih grup")
            } else {
                val dateTime = Prefuser().getDateBooking()
                val propertyBooking = Prefuser().getPropertyBooking()
                val bodyBooking = BookingRequest(
                        propertyBooking?.codeTransport,
                        dateTime?.date,
                        propertyBooking?.driverName,
                        propertyBooking?.note,
                        dateTime?.starTime,
                        propertyBooking?.location,
                        Prefuser().getUser()?.id?.toInt(),
                        dateTime?.endTime,
                        propertyBooking?.codeRoom


                )
                Prefuser().setBooking(bodyBooking)

                val bodyMeeting = MeetingRequest(
                        "",
                        dateTime?.date,
                        et_meeting_desctiption.text.toString(),
                        propertyBooking?.location,
                        Prefuser().getUser()?.id?.toInt(),
                        et_time.text.toString(),
                        "",//et_tag.text.toString(),
                        et_meeting_title.text.toString(),
                        idFile.toString().replace("[", "").replace("]", ""),
                        idGrup
                )
                presenter.postCreate(bodyMeeting, idMeetingParent)


            }
        }

    }

    override fun onResume() {
        super.onResume()

        Log.e("TAG","data image $urlImage")
        if (!Prefuser().getAddTeam().isNullOrEmpty()) {
            Prefuser().getAddTeam().let {

                val name: ArrayList<String> = ArrayList()

                it?.forEach {
                    name.add(it.username.toString())
                }
                et_tag.text = name.toString()

            }
        }

        if (Prefuser().getDateBooking() != null) {
            et_date.text = Prefuser().getDateBooking()?.date
            et_time.text = Prefuser().getDateBooking()?.starTime + " - " + Prefuser().getDateBooking()?.endTime
        }

        if (Prefuser().getPropertyBooking() != null) {
            et_location.text = Prefuser().getPropertyBooking()?.location
        }


        if (Prefuser().getPropertyMeeting() != null) {
            et_meeting_desctiption.setText(Prefuser().getPropertyMeeting()?.desc.toString())
            et_meeting_title.setText(Prefuser().getPropertyMeeting()?.title.toString())
            idFile = Prefuser().getPropertyMeeting()?.idFile as ArrayList<String>
//            urlImage = Prefuser().getPropertyMeeting()?.url as ArrayList<String>
//            adapterImageUpload.notifyDataSetChanged()
            Glide.with(this@CreateMeetingActivity).load(Prefuser().getPropertyMeeting()?.url.toString()).into(img_upload)
            img_upload.visibility = View.VISIBLE


        }

        et_grup.text = Prefuser().getNameGrup().toString()

        idGrup = Prefuser().getUser().toString()
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

            dialog.dismiss()
            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                //easyImage.openCameraForImage(this)

                ImagePicker.create(this) // Activity or Fragment
                        .start();
                Prefuser().setPropertyMeeting(ModelMeeting(
                        idFile,
                        et_meeting_title.text.toString(),
                        et_meeting_desctiption.text.toString(),
                        urlImage

                ))


            } else {
                EasyPermissions.requestPermissions(
                        this, "This application need your permission to access photo gallery.",
                        91, android.Manifest.permission.CAMERA
                )
            }
        }

        view.btn_gallery.setOnClickListener {

            dialog.dismiss()
            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openDocuments(this)
                Prefuser().setPropertyMeeting(ModelMeeting(
                        idFile,
                        et_meeting_title.text.toString(),
                        et_meeting_desctiption.text.toString(),
                        urlImage

                ))


            } else {
                EasyPermissions.requestPermissions(
                        this, "This application need your permission to access photo gallery.",
                        91, android.Manifest.permission.CAMERA
                )
            }
        }
        view.btn_pdf.setOnClickListener {

            dialog.dismiss()
            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                val intent = Intent(this, FilePickerActivity::class.java)
                intent.putExtra(FilePickerActivity.CONFIGS, Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowFiles(true)
                        .setShowImages(false)
                        .setShowVideos(false)
                        .setMaxSelection(1)
                        .setSkipZeroSizeFiles(true)
                        .build())
                startActivityForResult(intent, FILE_REQUEST_CODE)
                Prefuser().setPropertyMeeting(ModelMeeting(
                        idFile,
                        et_meeting_title.text.toString(),
                        et_meeting_desctiption.text.toString(),
                        urlImage

                ))


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

        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun setupLocationAction() {
        tvLocation = findViewById(R.id.et_location)
        tvLocation?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@CreateMeetingActivity,
                    SelectLocationActivity::class.java))

            Prefuser().setPropertyMeeting(ModelMeeting(
                    idFile,
                    et_meeting_title.text.toString(),
                    et_meeting_desctiption.text.toString(),
                    urlImage

            ))
        })
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
                        Log.e("TAG","data image ${imageFiles.get(0).file.toString()}")

                        val file = File(imageFiles.get(0).file.toString())
                        var requestFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                        var body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

                        requestFile = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                        body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                        if (imageFiles.get(0).file.length() >= 12219894) {
                            showLongErrorMessage("Size foto ini lebih dari 12 MB, Pilih foto yang lain yang kurang dari 12MB")
                        } else if (imageFiles.isNullOrEmpty()) {
                            showLongErrorMessage("Gagal mengambil foto dari camera")
                        } else {


                            presenter.uploadFile(body)

                        }

                    }

                    override fun onImagePickerError(error: Throwable, source: MediaSource) {
                        super.onImagePickerError(error, source)
                        Log.e("TAG","data image error ${error.message}")
                    }
                })

        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                FILE_REQUEST_CODE -> {
                    val files: ArrayList<com.jaiselrahman.filepicker.model.MediaFile> = data!!.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)

                    val file = File(files?.get(0)?.path)
                    var requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(file.path))
                    var body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), File(file.path))
                    body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    presenter.uploadFile(body)
                }
            }

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            var images = ImagePicker.getImages(data)
            // or get a single image only
            var image = ImagePicker.getFirstImageOrNull(data)

            val file = File(image.path.toString())
            var requestFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), File(file.path))
            var body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

            requestFile = RequestBody.create(MediaType.parse("image/*"), File(file.path))
            body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    presenter.uploadFile(body)




        }

    }

    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun successUpload(msg: String, uploadDto: UploadDto) {
        showLongSuccessMessage(msg)
        idFile.add(uploadDto.id.toString())
        urlImage.add(uploadDto.file.toString())
        adapterImageUpload.notifyDataSetChanged()

        Prefuser().setPropertyMeeting(ModelMeeting(
                idFile,
                et_meeting_title.text.toString(),
                et_meeting_desctiption.text.toString(),
                urlImage

        ))

    }

    override fun failUpload(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun failCreate(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun succesCreate(msg: String) {
        showSuccessMessage(msg)

        finish()
        //MeetingActivity().loadFragment(MeetingFragment())
    }

    fun getGrup() {
        val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Constant.USERNAME,
                Constant.PASS
        )
        progress_circular.visibility = View.VISIBLE
        api.getGroup(Prefuser().getUser()?.id).enqueue(object : Callback<BaseResponse<List<ResponseGroupDto>>> {
            override fun onFailure(call: Call<BaseResponse<List<ResponseGroupDto>>>, t: Throwable) {
                progress_circular.visibility = View.GONE
                showLongErrorMessage("Gagal dapatkan data grup")
            }

            override fun onResponse(call: Call<BaseResponse<List<ResponseGroupDto>>>, response: Response<BaseResponse<List<ResponseGroupDto>>>) {
                progress_circular.visibility = View.GONE
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        setDataGrup(response.body()?.data as MutableList<ResponseGroupDto>)
                    } else {
                        setDataGrup(response.body()?.data as MutableList<ResponseGroupDto>)
                    }
                }
            }
        })
    }

    private fun setDataGrup(mutableList: MutableList<ResponseGroupDto>) {
        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.view_grup, null)
        val window = dialog.window
        window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(view)


        val adapter = AdapterGroupForCreate(this, mutableList, this)
        view.rv_grup.layoutManager = LinearLayoutManager(this)
        view.rv_grup.adapter = adapter
        adapter?.notifyDataSetChanged()
        view.rv_grup.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        dialog.show()

    }

    override fun onClickGrup(data: ResponseGroupDto) {
        et_grup.text = data.namaTeam
        idGrup = data.id.toString()
        Prefuser().setIdGrup(data.id.toString())
        Prefuser().setNameGrup(data.namaTeam.toString())
        dialog.dismiss()
    }
}