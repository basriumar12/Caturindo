package com.caturindo.fragments.task

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caturindo.BaseFragment
import com.caturindo.R
import com.caturindo.activities.FilterActivity
import com.caturindo.activities.notif.NotificationActivity
import com.caturindo.activities.task.detail.TaskDetailActivity
import com.caturindo.activities.task.SelectMeetingTaskActivity
import com.caturindo.activities.task.model.TaskNewRequest
import com.caturindo.models.TaskDto
import com.caturindo.models.UpdateTokenRequest
import com.caturindo.preference.Prefuser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_search_bar.*
import kotlinx.android.synthetic.main.fragment_task.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFragment : BaseFragment(), AdapterTask.OnListener, TaskContract.View {
    private var rootView: View? = null
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var mNotificationOption: ImageView? = null
    private var mFilterOption: ImageView? = null
    private var mMainMenu: ImageView? = null
    private var rvTask: RecyclerView? = null
    private var progress_circular: ProgressBar? = null
    private var presenter: TaskPresenter? = null
    private var floatingActionButton: FloatingActionButton? = null
   private var currentDate =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_task, null)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        val sdf = SimpleDateFormat("yyyy-M")
         currentDate = sdf.format(Date())
        if (currentDate.isNullOrEmpty()){
            currentDate = ""
        }
        Prefuser().setCarruntDate(currentDate)
        presenter = TaskPresenter(this)
        getFcm()

    }

    fun getFcm(){

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.e("TOKEN fcm", "$token")

            presenter?.getUpdateToken(UpdateTokenRequest(token, Prefuser().getUser()?.id?.toInt()))
        })


    }
    override fun onResume() {
        super.onResume()

        presenter?.getAllTask(TaskNewRequest("",Prefuser().getUser()?.id,Prefuser().getCarruntDate()))
    }

    private fun setupToolbar() {
        rvTask = rootView?.findViewById(R.id.rv_task)
        progress_circular = rootView?.findViewById(R.id.progress_circular)
        floatingActionButton = rootView?.findViewById(R.id.fabCreateTask)
        toolbar = rootView?.findViewById(R.id.toolbar)
        mTitle = rootView?.findViewById(R.id.tv_toolbar_title)
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        mTitle?.setText("My Task")
        setupNavigationMenu()
        setupOptionsMenu()
    }

    private fun setupNavigationMenu() {
        mMainMenu = rootView?.findViewById(R.id.img_toolbar_start_button)
        mMainMenu?.setOnClickListener(View.OnClickListener { activity?.finish() })
    }

    private fun setupOptionsMenu() {
        mNotificationOption = rootView?.findViewById(R.id.img_first_option)
        mFilterOption = rootView?.findViewById(R.id.img_second_option)
        mNotificationOption?.setVisibility(View.VISIBLE)
        mFilterOption?.setVisibility(View.VISIBLE)
        mNotificationOption?.setOnClickListener(View.OnClickListener {
            //showLongInfoMessage("Onproses development")
            startActivity(Intent(rootView?.context, NotificationActivity::class.java))
        })
        mFilterOption?.setOnClickListener(View.OnClickListener {

            startActivity( Intent(getActivity(), FilterActivity::class.java))
        })

        if(Prefuser().getUser()?.role.equals("3")){
            floatingActionButton?.visibility = View.GONE
        }
        floatingActionButton?.setOnClickListener {
            Prefuser().setCarruntDate(currentDate)
            startActivity(Intent(activity, SelectMeetingTaskActivity::class.java)) }
    }

    override fun onClick(data: TaskDto) {
        startActivity(Intent(activity, TaskDetailActivity::class.java)
                .putExtra(TaskDto::class.java.simpleName, data))

    }

    override fun showProgress() {
        progress_circular?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_circular?.visibility = View.GONE
    }

    override fun onSuccessGet(data: MutableList<TaskDto>) {
        try {
            val adapter = rootView?.context?.let { AdapterTask(it, data, this) }
            if (!data.isNullOrEmpty()) {

                rvTask?.setAdapter(adapter)
                val manager = LinearLayoutManager(rootView?.context, LinearLayoutManager.VERTICAL, false)
                rvTask?.setLayoutManager(manager)

                search_text.setOnQueryTextListener(object :
                        androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        adapter?.filter?.filter(newText)
                        return false
                    }

                })
            }else{
                adapter?.notifyDataSetChanged()

            }

        } catch (e: NullPointerException) {

        }

    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }

    override fun dataEmpty() {
        rvTask?.visibility = View.GONE
        showLongErrorMessage("Data task kosong")
        paren_data_empty.visibility = View.VISIBLE

    }
}