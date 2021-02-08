package com.caturindo.activities.meeting.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.meeting.add_team.AddTeamMeetingActivity
import com.caturindo.activities.meeting.model.AddMeetingCommentRequest
import com.caturindo.activities.task.add_team.AddTeamTaskActivity
import com.caturindo.activities.task.detail.AdapterComment
import com.caturindo.activities.task.detail.ImageActivity
import com.caturindo.activities.task.detail.model.CommentDto
import com.caturindo.models.MeetingDtoNew
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.activity_meeting_detail.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class MeetingDetailActivity : BaseActivity(), CommentMeetingContract.View, AdapterComment.OnListener {
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null

    private var mNavigationMenu: ImageView? = null
    var idMeeting = ""
    lateinit var presenter: CommentMeetingPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_detail)
        presenter = CommentMeetingPresenter(this)


        setupToolbar()
        iniData()

    }

    private fun iniData() {
        val data =  intent.getSerializableExtra(MeetingDtoNew::class.java.simpleName) as MeetingDtoNew
        data.let {
            idMeeting = it.id.toString()
            presenter.getComment(idMeeting)
            mTitle?.text = it.title
            et_task_description.text = it.description
            tv_member.text = it.tag
            tv_task_time.text = it.time
            tv_task_date.text = it.date
            tv_location.text = it.location

            tv_member.setOnClickListener {view ->
                startActivity(Intent(this, AddTeamMeetingActivity::class.java)
                        .putExtra("ID",it.id)
                )
            }

            img_add_person.setOnClickListener {view ->
                startActivity(Intent(this, AddTeamMeetingActivity::class.java)
                        .putExtra("ID",it.id)
                )
            }

            img_attachment.setOnClickListener {
                startActivity(Intent(this, ImageActivity::class.java)
                        .putExtra("IMAGE",data.file)
                )
            }

            btn_submit.setOnClickListener {
                if (et_comment.text.toString().isNullOrEmpty()){
                    showErrorMessage("Komentar tidak bisa kosong")
                    et_comment.setError("Tidak bisa kosong")
                }else{

                    presenter.postComment(AddMeetingCommentRequest(data.id.toString(),
                            et_comment.text.toString(),
                            Prefuser().getUser()?.id    ))
                }
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_cancel ->{
                presenter.cancelMeeting(idMeeting)
            }

            R.id.menu_done ->{
                presenter.updateMeeting(idMeeting)
            }
        }
        return super.onOptionsItemSelected(item)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.meeting_detail_menu, menu)
        return true
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_toolbar_title)
        setSupportActionBar(toolbar)

        setupNavigationMenu()

        img_first_option.visibility = View.GONE
    }

    private fun setupNavigationMenu() {
        mNavigationMenu = findViewById(R.id.img_toolbar_start_button)
        mNavigationMenu?.setImageResource(R.drawable.ic_baseline_chevron_left_24)
        mNavigationMenu?.setOnClickListener(View.OnClickListener { finish() })
    }
    override fun showProgress() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgress() {

        progress_circular.visibility = View.GONE
    }

    override fun successData(data: ArrayList<CommentDto>) {
        val adapterComment = AdapterComment(this,data,this)
        rvComments.layoutManager = LinearLayoutManager(this)
        rvComments.adapter = adapterComment
        adapterComment.notifyDataSetChanged()
    }

    override fun failGetComment(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun successPostComment(msg: String) {
        showSuccessMessage(msg)
        presenter.getComment(idMeeting)
        et_comment.setText("")
    }

    override fun failPostComment(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun succsesCancel(msg: String) {
        finish()
        showSuccessMessage(msg)
    }

    override fun onClick(data: CommentDto) {
        
    }
}