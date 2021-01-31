package com.caturindo.fragments.transport

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseFragment
import com.caturindo.R
import com.caturindo.activities.transport.TransportDetailActivity
import com.caturindo.models.TransportDto
import com.caturindo.models.TransportItemModel
import com.caturindo.preference.ModelDate
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.fragment_transport_available.paren_data_empty
import kotlinx.android.synthetic.main.fragment_transport_available.progress_circular
import kotlinx.android.synthetic.main.fragment_transport_available.tv_date_booking
import kotlinx.android.synthetic.main.fragment_transport_available.tv_date_booking_change
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class TransportAvailableFragment : BaseFragment(), TransportContract.View, AdapterTransport.OnListener {
    private var rootView: View? = null
    private var rvTransport: RecyclerView? = null
    private val itemlist: ArrayList<TransportItemModel>? = null
    private lateinit var presenter : TransportPresenter
    private var keterangan = ""
    private var valueTimeStart = ""
    private var valueTimeEnd = ""
    private var valueDate = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_transport_available, null)
        rvTransport = rootView?.findViewById(R.id.rvTransportAvailable)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TransportPresenter(this)
        filterData()
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

        timeStart.setOnClickListener {
            try {
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
                val minute = mcurrentTime[Calendar.MINUTE]
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(rootView?.context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->

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
                mTimePicker = TimePickerDialog(rootView?.context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
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
            val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
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

                presenter?.getTransport(timeStart.text.toString(), timeEnd.text.toString(), date.text.toString())
                customDialog?.dismiss()
                keterangan = "Tanggal Booking $valueDate\nJam Mulai $valueTimeStart - Jam Berakhir $valueTimeEnd "
            }


        }

    }


    override fun onClick(data: TransportDto) {

        startActivity(Intent(rootView?.context, TransportDetailActivity::class.java)
                .putExtra(TransportDto::class.java.simpleName, data)
        )


        Prefuser().setDateBooking(ModelDate(valueTimeStart,valueTimeEnd,valueDate))

    }

    override fun showProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun hideProgress() {
        progress_circular.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<TransportDto>) {
        rvTransport?.layoutManager = GridLayoutManager(rootView?.context, 2)
        val adapterRoom = rootView?.context?.let { AdapterTransport(it, data, this) }
        rvTransport?.adapter = adapterRoom
        rvTransport?.setHasFixedSize(true)
        adapterRoom?.notifyDataSetChanged()
        tv_date_booking.text = "$keterangan ${data.size} transport tersedia."
        if (data.size == 0 || data == null) {
            paren_data_empty?.visibility = View.VISIBLE
        }else{
            paren_data_empty?.visibility = View.GONE
        }
    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }
}