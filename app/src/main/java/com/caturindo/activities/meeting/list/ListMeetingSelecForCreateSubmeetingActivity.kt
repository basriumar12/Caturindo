package com.caturindo.activities.meeting.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.meeting.create.CreateMeetingActivity
import com.caturindo.activities.task.CreateTaskActivity
import com.caturindo.adapters.TransportDetailItemAdapter
import com.caturindo.models.MeetingDtoNew
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.activity_list_meeting.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class ListMeetingSelecForCreateSubmeetingActivity : BaseActivity(), TaskMeetingContract.View, AdapterMeetingTask.OnListener {

    lateinit var presenter: TaskMeetingPresenter
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var rvRoom: RecyclerView? = null
    private var tglBtn: SwitchCompat? = null
    private var bookingForm: LinearLayout? = null
    private var adapter: TransportDetailItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_meeting)

        bindView()
        setupToolbar()

        presenter = TaskMeetingPresenter(this)
        presenter.getMeeting("0")

    }


    private fun bindView() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        rvRoom = findViewById(R.id.rv_room_image)
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        tglBtn = findViewById(R.id.switch_btn_booking)
        bookingForm = findViewById(R.id.booking_form_wrapper)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        mTitle?.text = "List Meeting"
        setupNavigationMenu()

        img_first_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener { finish() }
    }

    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<MeetingDtoNew>) {
        if (data.isNullOrEmpty()){
            paren_data_empty.visibility = View.VISIBLE
        }else{
            paren_data_empty.visibility = View.GONE
        }
        val adapterMeetingTask = AdapterMeetingTask(this,data,this)
        rv_meeting.layoutManager = LinearLayoutManager(this)
        rv_meeting.adapter = adapterMeetingTask
        adapterMeetingTask.notifyDataSetChanged()
    }

    override fun onErrorGetData(msg: String?) {
     showLongErrorMessage(msg)
    }

    override fun onClick(data: MeetingDtoNew) {
        //for create submeeting
        startActivity(Intent(this, CreateMeetingActivity::class.java))
        finish()
        //di isi id pareng meeting
        Prefuser().setIdPerentMeeting(data.id.toString())


    }
}