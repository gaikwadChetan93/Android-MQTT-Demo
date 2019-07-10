package com.nxtgizmo.androidmqttdemo.dashboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nxtgizmo.androidmqttdemo.R;
import com.nxtgizmo.androidmqttdemo.mqtt_app.MqttApp;

import org.eclipse.paho.android.service.MqttAndroidClient;

import javax.inject.Inject;

import timber.log.Timber;


public class DashBoardActivity extends AppCompatActivity implements DashboardContract{

    @Inject
    MqttAndroidClient client;
    private TextView message;

    private DashboardPresenter dashboardPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = findViewById(R.id.message);

        ((MqttApp)getApplication()).getMqttComponent().inject(this);
        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.connectToMqtt(client);
    }



    @Override
    public void onSuccess(String successMessage) {
        Timber.d(successMessage);
        message.setText(successMessage);
    }

    @Override
    public void onError(String errorMessage) {
        Timber.d(errorMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dashboardPresenter.unSubscribeMqttChannel(client);
        dashboardPresenter.disconnectMqtt(client);
    }
}
