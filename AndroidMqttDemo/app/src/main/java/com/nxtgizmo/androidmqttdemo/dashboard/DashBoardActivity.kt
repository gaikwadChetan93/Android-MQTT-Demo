package com.nxtgizmo.androidmqttdemo.dashboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.nxtgizmo.androidmqttdemo.R
import com.nxtgizmo.androidmqttdemo.mqtt_app.MqttApp

import org.eclipse.paho.android.service.MqttAndroidClient

import javax.inject.Inject

import timber.log.Timber


class DashBoardActivity : AppCompatActivity(), DashboardContract {

    @Inject
    internal var client: MqttAndroidClient? = null

    private var dashboardPresenter: DashboardPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MqttApp).mqttComponent.inject(this)
        dashboardPresenter = DashboardPresenter(this)
        dashboardPresenter!!.connectToMqtt(client)
    }


    override fun onSuccess(successMessage: String) {
        Timber.d(successMessage)
    }

    override fun onError(errorMessage: String) {
        Timber.d(errorMessage)
    }

    override fun onDestroy() {
        super.onDestroy()
        dashboardPresenter!!.unSubscribeMqttChannel(client)
        dashboardPresenter!!.disconnectMqtt(client)
    }
}
