package com.nxtgizmo.androidmqttdemo.dashboard;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import timber.log.Timber;


/**
 * Created by chetan_g on 22/11/16.
 */

public class DashboardPresenter {

    private static final String TAG = "DashBoardPresenter";
    private DashBoardActivity view;

    DashboardPresenter(DashBoardActivity view ) {
        this.view = view;
    }

    void connectToMqtt(final MqttAndroidClient client) {
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Timber.d("On Mqtt connect success");
                    subscribeToMqttChannel(client);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Timber.d("On Mqtt connect failure %s",exception.getMessage());

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    void subscribeToMqttChannel(MqttAndroidClient client) {
        String topic = "demo";
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos, iMqttMessageListener);

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
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    IMqttMessageListener iMqttMessageListener = new IMqttMessageListener() {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Timber.d("Message received %s",message);
            JSONObject signallStatus = new JSONObject(message.toString());

        }
    };

    void unSubscribeMqttChannel(MqttAndroidClient client) {
        final String topic = "demo";
        try {
            IMqttToken unsubToken = client.unsubscribe(topic);
            unsubToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The subscription could successfully be removed from the client
                    Timber.d("On Mqtt unSubscribed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // some error occurred, this is very unlikely as even if the client
                    // did not had a subscription to the topic the unsubscribe action
                    // will be successfully
                    Timber.d("On Mqtt unSubscribe failure %s",exception.getMessage());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    void disconnectMqtt(MqttAndroidClient client) {
        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Timber.d("On Mqtt disconnected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Timber.d("On Mqtt disconnect failure %s",exception.getMessage());

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
