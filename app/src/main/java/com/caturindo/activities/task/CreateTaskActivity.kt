package com.caturindo.activities.task

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
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
import com.caturindo.activities.grup.model.ResponseGroupDto
import com.caturindo.activities.meeting.MeetingActivity
import com.caturindo.activities.meeting.create.AdapterFileUpload
import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.team.AdapterGroup
import com.caturindo.activities.team.AdapterGroupForCreate
import com.caturindo.constant.Constant
import com.caturindo.models.BaseResponse
import com.caturindo.models.TaskRequest
import com.caturindo.preference.ModelMeeting
import com.caturindo.preference.Prefuser
import com.caturindo.utils.ApiInterface
import com.caturindo.utils.ServiceGenerator
import com.caturindo.utils.SwipeToDeleteCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.activity_create_task.btn_save
import kotlinx.android.synthetic.main.activity_create_task.et_date
import kotlinx.android.synthetic.main.activity_create_task.et_grup
import kotlinx.android.synthetic.main.activity_create_task.et_meeting_title
import kotlinx.android.synthetic.main.activity_create_task.et_tag
import kotlinx.android.synthetic.main.activity_create_task.et_time
import kotlinx.android.synthetic.main.activity_create_task.img_upload
import kotlinx.android.synthetic.main.activity_create_task.progress_circular
import kotlinx.android.synthetic.main.activity_create_task.tv_attachment_label
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.view_choose_take_photo.view.*
import kotlinx.android.synthetic.main.view_grup.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskActivity : BaseActivity(), CreatingTaskContract.View, AdapterGroupForCreate.OnListener {

    private val FILE_REQUEST_CODE = 139
    lateinit var dialog: BottomSheetDialog
    private var imagePath: String? = ""
    private val OPERATION_GET_FILE = 200
    private val OPERATION_CHOOSE_PHOTO = 2
    lateinit var easyImage: EasyImage
    private val self = this
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var tvLocation: TextView? = null
    private var idFile: ArrayList<String> = ArrayList()
    private var urlImage: ArrayList<String> = ArrayList()

    private var adapterImageUpload = AdapterFileUpload(urlImage as ArrayList<String>, this)

    private var idGrup = ""
    lateinit var presenter: CreateTaskPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        setupToolbar()
        setupNavigationMenu()
        initData()
    }

    private fun initData() {
        presenter = CreateTaskPresenter(this)
        easyImage = EasyImage.Builder(this).setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build()
        initPersmission()

        val idMeeting = intent.getStringExtra("ID")
        val titleMeeting = intent.getStringExtra("TITLE")

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


        et_meeting_title.text = titleMeeting + " - " + idMeeting

        et_grup.setOnClickListener {
            getGrup()
        }
        et_time.setOnClickListener {

            try {
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
                val minute = mcurrentTime[Calendar.MINUTE]
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->

                    var minute = ""
                    var hours = ""
                    if (selectedMinute < 10) {
                        minute = "0$selectedMinute"
                    } else {
                        minute = "$selectedMinute"
                    }

                    if (selectedHour < 10) {
                        hours = "0$selectedHour"
                    } else {
                        hours = "$selectedHour"
                    }
                    et_time.setText("$hours:$minute")

                }, hour, minute, true) //Yes 24 hour time

                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            } catch (e: NullPointerException) {

            }
        }


        et_date.setOnClickListener {
            val myCalendar = Calendar.getInstance()
            val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val myFormat = "yyyy-MM-dd" // your format
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                et_date.setText(sdf.format(myCalendar.time))

            }
            this?.let { it1 -> DatePickerDialog(it1, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show() }


        }

        tv_attachment_label.setOnClickListener {
            chooseFile()
//            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
//                easyImage.openGallery(this)
//
//
//            } else {
//                EasyPermissions.requestPermissions(
//                        this, "This application need your permission to access photo gallery.",
//                        91, android.Manifest.permission.CAMERA
//                )
//            }
        }
        et_tag.setOnClickListener {


        }
        btn_save.setOnClickListener {
            if (et_meeting_title.text.toString().isNullOrEmpty()) {
                showErrorMessage("Judul meeting tidak bisa kosong")
            } else if (et_task_title.text.toString().isNullOrEmpty()) {
                showErrorMessage("Judul task tidak bisa kosong")
            } else if (et_task_desctiption.text.toString().isNullOrEmpty()) {
                showErrorMessage("Deskripsi task tidak bisa kosong")
            } else if (et_date.text.toString().isNullOrEmpty()) {
                showErrorMessage("Tanggal task tidak bisa kosong")

            } else if (et_time.text.toString().isNullOrEmpty()) {
                showErrorMessage("Waktu task tidak bisa kosong")
            } else if (idGrup.isNullOrEmpty()) {
                showLongErrorMessage("Belum memilih grup")
            } else {

                val requesDto = TaskRequest(
                        et_task_title.text.toString(),
                        idMeeting,
                        et_date.text.toString(),
                        et_task_desctiption.text.toString(),
                        Prefuser().getUser()?.id.toString(),
                        et_time.text.toString(),
                        idFile.toString().replace("[", "").replace("]",""),
                        idGrup

                )

                presenter.postCreate(requesDto)
            }


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

            dialog.dismiss()
            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openCameraForImage(this)



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



            } else {
                EasyPermissions.requestPermissions(
                        this, "This application need your permission to access photo gallery.",
                        91, android.Manifest.permission.CAMERA
                )
            }
        }

        dialog.show()
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

    override fun onResume() {
        super.onResume()

        if (!Prefuser().getTeamTask().isNullOrEmpty()) {
            Prefuser().getTeamTask().let {
                var name: ArrayList<String> = ArrayList()

                it?.forEach {
                    name.add(it.username.toString())
                }
                et_tag.text = name.toString()
            }
        }

        idGrup = Prefuser().getIdGrup().toString()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> self.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)
        mTitle?.setText("Create Task")
        setupNavigationMenu()

        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(requestCode, resultCode, data, this,
                object : DefaultCallback() {
                    override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {


                        if (imageFiles.get(0).file.length() >= 2219894) {
                            showLongErrorMessage("Size foto ini lebih dari 2 MB, Pilih foto yang lain yang kurang dari 2MB")
                        }
                        else if (imageFiles.isNullOrEmpty()) {
                            showLongErrorMessage("Gagal mengambil foto dari camera")
                        }
                        else {
                            val file = File(imageFiles.get(0).file.toString())
                            var requestFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                            var body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

                            requestFile = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                            body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                            presenter.uploadFile(body)


                        }
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




    }

    override fun showProgress() {

        progress_circular.visibility = View.VISIBLE

    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun successUpload(msg: String, uploadDto: UploadDto) {
        showSuccessMessage(msg)
        img_upload.visibility = View.VISIBLE
        idFile.add(uploadDto.id.toString())
        urlImage.add(uploadDto.file.toString())
        adapterImageUpload.notifyDataSetChanged()


    }

    override fun failUpload(msg: String) {
        showLongErrorMessage(msg)

    }

    override fun failCreate(msg: String) {
        showLongErrorMessage(msg)

    }

    override fun succesCreate(msg: String) {
        startActivity(
                Intent(this, MeetingActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        finish()

        showSuccessMessage(msg)
    }

    override fun onClickGrup(data: ResponseGroupDto) {
        et_grup.text = data.namaTeam
        idGrup = data.id.toString()
        Prefuser().setIdGrup(data.id.toString())
        dialog.dismiss()
    }


}