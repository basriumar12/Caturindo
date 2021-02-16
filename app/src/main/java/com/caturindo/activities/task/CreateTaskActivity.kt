package com.caturindo.activities.task

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.meeting.MeetingActivity
import com.caturindo.activities.meeting.create.CreateMeetingActivity
import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.activities.task.add_team.AddTeamTaskActivity
import com.caturindo.models.TaskRequest
import com.caturindo.preference.Prefuser
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.activity_create_task.btn_save
import kotlinx.android.synthetic.main.activity_create_task.et_date
import kotlinx.android.synthetic.main.activity_create_task.et_meeting_title
import kotlinx.android.synthetic.main.activity_create_task.et_tag
import kotlinx.android.synthetic.main.activity_create_task.et_time
import kotlinx.android.synthetic.main.activity_create_task.img_upload
import kotlinx.android.synthetic.main.activity_create_task.progress_circular
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskActivity : BaseActivity(), CreatingTaskContract.View {

    private var imagePath: String? = ""
    private val OPERATION_GET_FILE = 200
    private val OPERATION_CHOOSE_PHOTO = 2
    lateinit var easyImage: EasyImage
    private val self = this
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var tvLocation: TextView? = null
    private var idFile = ""
    private var urlImage = ""
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

        et_meeting_title.text = titleMeeting + " - " + idMeeting

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
            } else {

                val requesDto = TaskRequest(
                        et_task_title.text.toString(),
                        idMeeting,
                        et_date.text.toString(),
                        et_task_desctiption.text.toString(),
                        Prefuser().getUser()?.id.toString(),
                        et_time.text.toString(),
                        idFile

                )

                presenter.postCreate(requesDto)
            }


        }

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
                        } else {
                            val file = File(imageFiles.get(0).file.toString())
                            var requestFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                            var body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

                            requestFile = RequestBody.create(MediaType.parse("image/*"), File(file.path))
                            body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                            presenter.uploadFile(body)


                        }
                    }
                })


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
        idFile = uploadDto.id.toString()
        urlImage = uploadDto.file.toString()
        Glide.with(this).load(uploadDto.file).into(img_upload)
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
}