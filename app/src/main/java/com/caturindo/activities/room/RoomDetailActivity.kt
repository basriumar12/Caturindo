package com.caturindo.activities.room

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.meeting.create.CreateMeetingActivity
import com.caturindo.adapters.TransportDetailItemAdapter
import com.caturindo.models.RoomDto
import com.caturindo.preference.ModelBooking
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.activity_room_detail.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.util.*

class RoomDetailActivity : BaseActivity() {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var rvRoom: RecyclerView? = null
    private var tglBtn: SwitchCompat? = null
    private var bookingForm: LinearLayout? = null
    private var adapter: TransportDetailItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_detail)
        bindView()
        setupToolbar()
        setupRecyclerView()
        setupToggleButton()
        initData()
    }

    private fun initData() {

        if (Prefuser().getvalidateOnclickRoom().equals("0")){
            parent_booking?.visibility = View.GONE
        }

        val data =
                intent.getSerializableExtra(RoomDto::class.java.simpleName) as RoomDto

        data.let {
            mTitle?.text = "${it.nameRoom} - ${it.codeRoom}"
            tv_capacity_count.text = it.maxPeople


            //setData tersimpan
            tv_time.text = "${Prefuser().getDateBooking()?.starTime.toString()} - ${Prefuser().getDateBooking()?.endTime.toString()} "
            tv_date.text = "${Prefuser().getDateBooking()?.date.toString()}"
            tv_time.setOnClickListener {
                showLongInfoMessage("Jam booking ruangan tidak bisa di ganti !! Jam mulai di pilih ketika memilih ruangan yang tesedia.")
            }

            tv_date.setOnClickListener {
                showLongInfoMessage("Hari booking ruangan tidak bisa di ganti !! Hari dipilih ketika memilih ruangan yang tesedia.")
            }
            btn_save.setOnClickListener { btnSave ->
                if (Prefuser().getvalidateOnclickRoom().equals("1")) {

                    if (edt_note.text.toString().isNullOrEmpty()) {
                        showErrorMessage("Note belum di isi")
                    } else {
                        Prefuser().setPropertyBooking(ModelBooking("", it.codeRoom, "",
                                it.nameRoom, edt_note.text.toString()))
                        finish()
                        showInfoMessage("Booking di simpan")
                        startActivity(
                                Intent(this, CreateMeetingActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                        finish()

                    }
                } else {
                    showErrorMessage("Booking ruangan hanya dapat dibuat ketika memilih lokasi untuk meeting")
                }
            }

            btn_cancel.setOnClickListener {
                finish()
            }


            val images: ArrayList<String?> = ArrayList<String?>()
            it.image?.let { it1 -> images.addAll(it1) }
            adapter = TransportDetailItemAdapter(images, this)
            val manager = GridLayoutManager(this, 4, RecyclerView.VERTICAL, false)
            rvRoom?.layoutManager = manager
            rvRoom?.adapter = adapter

        }


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

        setupNavigationMenu()

        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {

    }

    private fun setupToggleButton() {

        tglBtn?.setOnCheckedChangeListener { compoundButton, b ->
            //validate booking

            if (b) {
                bookingForm?.visibility = View.VISIBLE
            } else {
                bookingForm?.visibility = View.GONE

            }
        }
    }
}