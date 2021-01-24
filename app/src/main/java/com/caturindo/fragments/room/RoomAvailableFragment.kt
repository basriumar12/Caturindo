package com.caturindo.fragments.room

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.R
import com.caturindo.activities.RoomDetailActivity
import com.caturindo.adapters.RoomItemAdapter
import com.caturindo.models.RoomDto
import com.caturindo.models.RoomItemModel
import java.text.SimpleDateFormat
import java.util.*


class RoomAvailableFragment : Fragment(), AdapterRoom.OnListener, RoomContract.View {
    private var presenter: RoomPresenter? = null
    private var rootView: View? = null
    private val adapter: RoomItemAdapter? = null
    private var rvRoom: RecyclerView? = null
    private var progress_circular: ProgressBar? = null
    private val itemlist: ArrayList<RoomItemModel>? = null
    private var linearLayout: LinearLayoutCompat? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_room_booked, null)
        rvRoom = rootView?.findViewById(R.id.rvRoomBooked)
        linearLayout = rootView?.findViewById(R.id.paren_data_empty)
        progress_circular = rootView?.findViewById(R.id.progress_circular)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        filterData()
        presenter = RoomPresenter(this)
        // presenter?.getRoom("13:00", "12:00", "2021-01-19") //2021-01-19
    }

    private fun filterData() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_date, null)
        var dialog = rootView?.context?.let { AlertDialog.Builder(it) };
        var customDialog = dialog?.create()
        customDialog?.setView(dialogView)
        customDialog?.show()
        customDialog?.setCancelable(false)

        var valueTimeStart = ""
        var valueTimeEnd = ""
        var valueDate = ""
        val btnOK = dialogView.findViewById<Button>(R.id.btn_ok)
        val timeStart = dialogView.findViewById<TextView>(R.id.tv_time_start)
        val timeEnd = dialogView.findViewById<TextView>(R.id.tv_time_end)
        val date = dialogView.findViewById<TextView>(R.id.tv_date)

        timeStart.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(activity, OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                timeStart.setText("$selectedHour:$selectedMinute")
                valueTimeStart = "$selectedHour:$selectedMinute"
            }, hour, minute, true) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()

        }

        timeEnd.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(activity, OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                timeEnd.setText("$selectedHour:$selectedMinute")
               valueTimeEnd = "$selectedHour:$selectedMinute"
            }, hour, minute, true) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()

        }

        date.setOnClickListener {

            val myCalendar = Calendar.getInstance()
            val date = OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val myFormat = "yyyy-MM-dd" // your format
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                date.setText(sdf.format(myCalendar.time))
                valueDate = sdf.format(myCalendar.time)
            }
            rootView?.context?.let { it1 -> DatePickerDialog(it1, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show() }


        }


        btnOK.setOnClickListener {
            if (valueTimeStart.isNullOrEmpty()) {
                timeStart.text = "Jam mulai belum dipilih"
            }
            if (valueTimeEnd.isNullOrEmpty()) {
                timeEnd.text = "Jam berakhir belum dipilih"
            }

            if (valueDate.isNullOrEmpty()) {
                date.text = "Tanggal belum di pilih"
            }
           else {

                presenter?.getRoom(timeStart.text.toString(), timeEnd.text.toString(), date.text.toString())
                 customDialog?.dismiss()
            }


        }

    }


    override fun onClick(data: RoomDto) {
        startActivity(Intent(activity, RoomDetailActivity::class.java))
    }

    override fun showProgress() {
        progress_circular?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular?.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<RoomDto>) {
        rvRoom?.layoutManager = GridLayoutManager(activity, 2)
        val adapterRoom = rootView?.context?.let { AdapterRoom(it, data, this) }
        rvRoom?.adapter = adapterRoom
        rvRoom?.setHasFixedSize(true)
        adapterRoom?.notifyDataSetChanged()
        if (data.size == 0 || data == null) {
            linearLayout?.visibility = View.VISIBLE
        }
    }

    override fun onErrorGetData(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}