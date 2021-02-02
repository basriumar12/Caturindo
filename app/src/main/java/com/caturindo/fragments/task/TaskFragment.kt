package com.caturindo.fragments.task

import android.content.Intent
import android.os.Bundle
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
import com.caturindo.activities.task.detail.TaskDetailActivity
import com.caturindo.activities.task.SelectMeetingTaskActivity
import com.caturindo.models.TaskDto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_bar.*

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
        presenter = TaskPresenter(this)

    }

    override fun onResume() {
        super.onResume()
        presenter?.getTask()
    }

    private fun setupToolbar() {
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
            showLongInfoMessage("Onproses development")
            //startActivity(new Intent(getActivity(), NotificationActivity.class));
        })
        mFilterOption?.setOnClickListener(View.OnClickListener {
            showLongInfoMessage("Onproses development")
            //startActivity(new Intent(getActivity(), FilterActivity.class));
        })
        floatingActionButton?.setOnClickListener { startActivity(Intent(activity, SelectMeetingTaskActivity::class.java)) }
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
            if (!data.isNullOrEmpty()) {
                val adapter = rootView?.context?.let { AdapterTask(it, data, this) }
                rvTask = rootView?.findViewById(R.id.rv_task)
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
            }
        } catch (e: NullPointerException) {

        }

    }

    override fun onErrorGetData(msg: String?) {
        showLongErrorMessage(msg)
    }
}