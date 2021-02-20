package com.caturindo.activities.transport

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.meeting.create.CreateMeetingActivity
import com.caturindo.adapters.TransportDetailItemAdapter
import com.caturindo.models.RoomDto
import com.caturindo.models.TransportDto
import com.caturindo.preference.ModelBooking
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.activity_room_detail.*
import kotlinx.android.synthetic.main.activity_transport_detail.*
import kotlinx.android.synthetic.main.activity_transport_detail.btn_cancel
import kotlinx.android.synthetic.main.activity_transport_detail.btn_save
import kotlinx.android.synthetic.main.activity_transport_detail.tv_capacity_count
import kotlinx.android.synthetic.main.activity_transport_detail.tv_date
import kotlinx.android.synthetic.main.activity_transport_detail.tv_time
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.util.*

class TransportDetailActivity : BaseActivity() {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNavigationMenu: ImageView? = null
    private var rvTransport: RecyclerView? = null
    private var bookingForm: LinearLayout? = null
    private var tglBtn: SwitchCompat? = null
    private var adapter: TransportDetailItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_detail)
        setupToolbar()
        setupRecyclerView()
        setupToggleButton()
        initData()
    }

    private fun initData() {

        if (Prefuser().getvalidateOnclickRoom().equals("0")){
            parent_booking_on.visibility = View.GONE
        } else{
            parent_booking_on.visibility = View.VISIBLE
        }
        val data =
                intent.getSerializableExtra(TransportDto::class.java.simpleName) as TransportDto

        data.let {
            mTitle?.text = it.nameTransport +" - "+it.id
            tv_capacity_count.text = it.maxPeople


            val images: ArrayList<String?> = ArrayList<String?>()
            it.image?.let { it1 -> images.addAll(it1) }
            adapter = TransportDetailItemAdapter(images, this)
            rvTransport = findViewById(R.id.rv_room_image)
            val manager = GridLayoutManager(this, 4, RecyclerView.VERTICAL, false)
            rvTransport?.setLayoutManager(manager)
            rvTransport?.setAdapter(adapter)
        }


        //set data
        Prefuser().getDateBooking().let {
            tv_time.text = it?.starTime +" - "+it?.endTime
            tv_date.text = it?.date
        }

        tv_time.setOnClickListener {
            showLongInfoMessage("Jam booking tranposrt tidak bisa di ganti !! Jam mulai di pilih ketika memilih transport yang tesedia.")
        }

        tv_date.setOnClickListener {
            showLongInfoMessage("Hari booking tranport tidak bisa di ganti !! Hari dipilih ketika memilih transport yang tesedia.")
        }


        btn_save.setOnClickListener {
            if (et_driver_name.text.toString().isNullOrEmpty()){
                showErrorMessage("Nama driver belum di isi")
                et_driver_name.setError("Tidak bisa kosong")
            }

            else if (et_car_location.text.toString().isNullOrEmpty()){
                showErrorMessage("Lokasi belum di isi")
                et_car_location.setError("Tidak bisa kosong")
            }
            else if (et_note_car.text.toString().isNullOrEmpty()){
                showErrorMessage("Catatan belum di isi")
                et_note_car.setError("Tidak bisa kosong")
            }else{
                Prefuser().setPropertyBooking(ModelBooking(data.id, "", et_driver_name.text.toString(),
                        et_car_location.text.toString(), et_note_car.text.toString()))
                finish()
                showInfoMessage("Booking di simpan")
                startActivity(
                        Intent(this, CreateMeetingActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                finish()
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }

    }

    private fun setupToggleButton() {
        tglBtn = findViewById(R.id.switch_btn_booking)
        bookingForm = findViewById(R.id.booking_form_wrapper)
        tglBtn?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                bookingForm?.setVisibility(View.VISIBLE)
            } else {
                bookingForm?.setVisibility(View.GONE)
            }
        })
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)

        setupNavigationMenu()

        img_first_option.visibility = View.GONE
        img_second_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun setupRecyclerView() {

    }
}