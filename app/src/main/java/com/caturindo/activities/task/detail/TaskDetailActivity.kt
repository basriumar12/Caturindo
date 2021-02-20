package com.caturindo.activities.task.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.caturindo.BaseActivity
import com.caturindo.R
import com.caturindo.activities.task.add_team.AddTeamTaskActivity
import com.caturindo.activities.task.detail.model.AddCommentRequest
import com.caturindo.activities.task.detail.model.CommentDto
import com.caturindo.models.TaskDto
import com.caturindo.preference.Prefuser
import kotlinx.android.synthetic.main.activity_meeting_detail.*
import kotlinx.android.synthetic.main.activity_task_detail.*
import kotlinx.android.synthetic.main.activity_task_detail.btn_submit
import kotlinx.android.synthetic.main.activity_task_detail.et_comment
import kotlinx.android.synthetic.main.activity_task_detail.et_task_description
import kotlinx.android.synthetic.main.activity_task_detail.img_add_person
import kotlinx.android.synthetic.main.activity_task_detail.img_attachment
import kotlinx.android.synthetic.main.activity_task_detail.progress_circular
import kotlinx.android.synthetic.main.activity_task_detail.rvComments
import kotlinx.android.synthetic.main.activity_task_detail.tv_member
import kotlinx.android.synthetic.main.activity_task_detail.tv_task_date
import kotlinx.android.synthetic.main.custom_toolbar.*

class TaskDetailActivity : BaseActivity(), CommentTaskContract.View, AdapterComment.OnListener {
    
    lateinit var presenter: CommentTaskPresenter
    private var toolbar: Toolbar? = null
    private var mTitle: TextView? = null
    private var idTask = ""
    private var idUserCreate = ""
    private var mNavigationMenu: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        setupToolbar()
        iniData()
        

    }

    override fun onResume() {
        super.onResume()

    }

    private fun iniData() {
        presenter = CommentTaskPresenter(this)
        val data =  intent.getSerializableExtra(TaskDto::class.java.simpleName) as TaskDto
        data.let {
            idUserCreate = it.id_user.toString()
            idTask = it.id.toString()
            tv_task_date.text = it.time +" - "+it.date
            mTitle?.text = "${it.nameTask} - ${it.id}"
            et_task_description?.text = it.description
            presenter.getComment(it.id.toString())

            if (!it.member.isNullOrEmpty()){
                val member : ArrayList<String> = ArrayList()
                member.addAll(it.member)

                tv_member.text = member.toString()


            }
        }
        img_add_person.visibility = View.GONE

        img_attachment.setOnClickListener {
            startActivity(Intent(this, ImageActivity::class.java)
                    .putExtra("IMAGE",data.file)
            )
        }


        img_add_person.setOnClickListener {

            if(Prefuser().getUser()?.role.equals("3")){
                showInfoMessage("Anda tidak mempunyai akses")
            }else {
                startActivity(Intent(this, AddTeamTaskActivity::class.java)
                        .putExtra("ID", idTask)
                )
            }
        }
        btn_submit.setOnClickListener {
            if (et_comment.text.toString().isNullOrEmpty()){
                showErrorMessage("Komentar tidak bisa kosong")
                et_comment.setError("Tidak bisa kosong")
            }else{

                presenter.postComment(AddCommentRequest(data.id.toString(),
                        et_comment.text.toString(),
                        Prefuser().getUser()?.id    ))
            }
        }
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
        presenter.getComment(idTask)
        et_comment.setText("")
    }

    override fun failPostComment(msg: String) {
        showLongErrorMessage(msg)
    }

    override fun onClick(data: CommentDto) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_cancel ->{
                if (Prefuser().getUser()?.role.equals("3") || !Prefuser().getUser()?.id.toString().equals(idUserCreate)) {
                    showInfoMessage("Anda tidak mempunya akses")
                }
                else {
                    presenter.postCancel(idTask)
                }
            }

            R.id.menu_done ->{
                if (Prefuser().getUser()?.role.equals("3") || !Prefuser().getUser()?.id.toString().equals(idUserCreate)) {
                    showInfoMessage("Anda tidak mempunya akses")
                }


                else {
                    presenter.postDone(idTask)
                }
            }
        }
        return super.onOptionsItemSelected(item)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.meeting_detail_menu, menu)
        return true
    }
}