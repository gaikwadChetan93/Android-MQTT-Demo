package com.nxtgizmo.androidmqttdemo.dashboard

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject

import timber.log.Timber


/**
 * Created by chetan_g on 22/11/16.
 */

class DashboardPresenter internal constructor(private val view: DashBoardActivity) {

    private var iMqttMessageListener: IMqttMessageListener =
        IMqttMessageListener { topic, message ->
            Timber.d("Message received %s", message)
            val signallStatus = JSONObject(message.toString())
        }

    internal fun connectToMqtt(client: MqttAndroidClient) {
        try {
            val token = client.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // We are connected
                    Timber.d("On Mqtt connect success")
                    subscribeToMqttChannel(client)
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Timber.d("On Mqtt connect failure %s", exception.message)

                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    internal fun subscribeToMqttChannel(client: MqttAndroidClient) {
        val topic = "demo"
        val qos = 1
        try {
            val subToken = client.subscribe(topic, qos, iMqttMessageListener)

            /*subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Timber.d("Mqtt channel subscribe success");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    Timber.d("Mqtt channel subscribe error %s",exception.getMessage());
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
            */
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    internal fun unSubscribeMqttChannel(client: MqttAndroidClient) {
        val topic = "demo"
        try {
            val unsubToken = client.unsubscribe(topic)
            unsubToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // The subscription could successfully be removed from the client
                    Timber.d("On Mqtt unSubscribed")
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    // some error occurred, this is very unlikely as even if the client
                    // did not had a subscription to the topic the unsubscribe action
                    // will be successfully
                    Timber.d("On Mqtt unSubscribe failure %s", exception.message)
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    internal fun disconnectMqtt(client: MqttAndroidClient) {
        try {
            val token = client.disconnect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // We are connected
                    Timber.d("On Mqtt disconnected")
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Timber.d("On Mqtt disconnect failure %s", exception.message)

                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = "DashBoardPresenter"
    }
}
