package com.edu.slider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.edu.aplis.R;

public class MainPage extends AppCompatActivity {
    public static final String MESSAGE_STATUS = "message_status";
    TextView tvStatus;
    Button btnSend;
    private AddTaskActivity addTaskActivity;

    Activity context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jhjh);
        context = MainPage.this;
        tvStatus = findViewById(R.id.tvStatus);
        btnSend = findViewById(R.id.btnSend);
        addTaskActivity = new AddTaskActivity(this);
//        addTaskActivity.checkEnabled(context);
//        addTaskActivity.showdrawer(context,true);
       /* WorkManager workManager = WorkManager.getInstance();
        OneTimeWorkRequest oneTimeWorkRequest=new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .build();*/

      /*  btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workManager.enqueue(oneTimeWorkRequest);
            }
        });

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {
                    WorkInfo.State state = workInfo.getState();
                    tvStatus.append(state.toString() + "\n");
                }
            }
        });*/

    }

    public void discoverclick(View view) {
//        if (addTaskActivity.)
//        addTaskActivity.showdrawer(context);
    }
}
