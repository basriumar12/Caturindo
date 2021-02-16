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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseFragment
import com.caturindo.R
import com.caturindo.activities.room.RoomDetailActivity
import com.caturindo.models.RoomDto
import com.caturindo.preference.ModelDate
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.fragment_room_available.*
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*


class RoomAvailableFragment : BaseFragment(), AdapterRoom.OnListener, RoomContract.View {
    private var presenter: RoomPresenter? = null
    private var rootView: View? = null
    private var rvRoom: RecyclerView? = null
    private var progress_circular: ProgressBar? = null
    private var linearLayout: LinearLayoutCompat? = null
    private var keterangan = ""
    private var valueTimeStart = ""
    private var valueTimeEnd = ""
    private var valueDate = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_room_available, null)
        rvRoom = rootView?.findViewById(R.id.rvRoomBooked)
        linearLayout = rootView?.findViewById(R.id.paren_data_empty)
        progress_circular = rootView?.findViewById(R.id.progress_circular)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        filterData()
        presenter = RoomPresenter(this)
        tv_date_booking_change.setOnClickListener {
            filterData()
        }
    }

    private fun filterData() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_date, null)
        var dialog = rootView?.context?.let { AlertDialog.Builder(it) };
        var customDialog = dialog?.create()
        customDialog?.setView(dialogView)
        customDialog?.show()
        customDialog?.setCancelable(false)


        val btnOK = dialogView.findViewById<Button>(R.id.btn_ok)
        val timeStart = dialogView.findViewById<TextView>(R.id.tv_time_start)
        val timeEnd = dialogView.findViewById<TextView>(R.id.tv_time_end)
        val date = dialogView.findViewById<TextView>(R.id.tv_date)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            customDialog?.dismiss()
        }
        timeStart.setOnClickListener {
            try {
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
                val minute = mcurrentTime[Calendar.MINUTE]
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(rootView?.context, OnTimeSetListener { timePicker, selectedHour, selectedMinute ->

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
                    timeStart.setText("$hours:$minute")
                    valueTimeStart = "$hours:$minute"
                }, hour, minute, true) //Yes 24 hour time

                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            }catch (e : NullPointerException){

            }

        }

        timeEnd.setOnClickListener {
            try {
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
                val minute = mcurrentTime[Calendar.MINUTE]
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(rootView?.context, OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
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
                    timeEnd.setText("$hours:$minute ")
                    valueTimeEnd = "$hours:$minute"
                }, hour, minute, true) //Yes 24 hour time

                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            }catch (e : NullPointerException){

            }

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
                showErrorMessage("Jam mulai belum dipilih")
            } else if (valueTimeEnd.isNullOrEmpty()) {
                timeEnd.text = "Jam berakhir belum dipilih"
                showErrorMessage("Jam berakhir belum dipilih")
            } else if (valueDate.isNullOrEmpty()) {
                date.text = "Tanggal belum di pilih"
                showErrorMessage("Tanggal belum di pilih")
            }

            else {

                presenter?.getRoom(timeStart.text.toString(), timeEnd.text.toString(), date.text.toString())
                customDialog?.dismiss()
                keterangan = "Tanggal Booking $valueDate\nJam Mulai $valueTimeStart - Jam Berakhir $valueTimeEnd "
            }


        }

    }


    override fun onClick(data: RoomDto) {
        startActivity(Intent(rootView?.context, RoomDetailActivity::class.java)
                .putExtra(RoomDto::class.java.simpleName, data))

        Prefuser().setDateBooking(ModelDate(valueTimeStart,valueTimeEnd,valueDate))



    }

    override fun showProgress() {
        progress_circular?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular?.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<RoomDto>) {
        rvRoom?.layoutManager = GridLayoutManager(rootView?.context, 2)
        val adapterRoom = rootView?.context?.let { AdapterRoom(it, data, this) }
        rvRoom?.adapter = adapterRoom
        rvRoom?.setHasFixedSize(true)
        adapterRoom?.notifyDataSetChanged()
        tv_date_booking.text = "$keterangan ${data.size} ruangan tersedia."
        if (data.size == 0 || data == null) {
            linearLayout?.visibility = View.VISIBLE
        }else{
            linearLayout?.visibility = View.GONE
        }
    }

    override fun onErrorGetData(msg: String?) {
        showErrorMessage(msg)
    }
}