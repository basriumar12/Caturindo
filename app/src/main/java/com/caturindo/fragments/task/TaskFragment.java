package com.caturindo.fragments.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caturindo.R;
import com.caturindo.activities.FilterActivity;
import com.caturindo.activities.NotificationActivity;
import com.caturindo.activities.TaskDetailActivity;
import com.caturindo.models.TaskDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TaskFragment extends Fragment implements AdapterTask.OnListener, TaskContract.View {

    private View rootView;
    private Toolbar toolbar;
    private TextView mTitle;
    private ImageView mNotificationOption;
    private ImageView mFilterOption;
    private ImageView mMainMenu;
    private RecyclerView rvTask;
    private ProgressBar progress_circular;
    private  TaskPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolbar();
        presenter = new TaskPresenter(this);
        presenter.getTask();
    }

    private void setupToolbar(){
        progress_circular = rootView.findViewById(R.id.progress_circular);
        toolbar = rootView.findViewById(R.id.toolbar);
        mTitle  =rootView.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mTitle.setText("My Task");
        setupNavigationMenu();
        setupOptionsMenu();
    }

    private void setupNavigationMenu(){
       mMainMenu = rootView.findViewById(R.id.img_toolbar_start_button);
       mMainMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getActivity().finish();
           }
       });
    }



    private void setupOptionsMenu(){
        mNotificationOption = rootView.findViewById(R.id.img_first_option);
        mFilterOption = rootView.findViewById(R.id.img_second_option);

        mNotificationOption.setVisibility(View.VISIBLE);
        mFilterOption.setVisibility(View.VISIBLE);
        mNotificationOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });

        mFilterOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FilterActivity.class));
            }
        });

    }




    @Override
    public void onClick(@NotNull TaskDto data) {
         Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
         intent.putExtra(TaskDto.class.getName(),data);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        progress_circular.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessGet(List<TaskDto> data) {
        AdapterTask adapter = new AdapterTask(rootView.getContext(),data,this);
        rvTask = rootView.findViewById(R.id.rv_task);
        rvTask.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvTask.setLayoutManager(manager);
    }

    @Override
    public void onErrorGetData(@org.jetbrains.annotations.Nullable String msg) {
        Toast.makeText(rootView.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
