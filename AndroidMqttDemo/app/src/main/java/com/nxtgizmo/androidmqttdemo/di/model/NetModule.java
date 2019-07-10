package com.nxtgizmo.androidmqttdemo.di.model;

import android.app.Application;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetModule {

    public NetModule() {
    }

    @Provides @Singleton
    MqttAndroidClient provideMqttAndroidClient (Application application) {
        String clientId = MqttClient.generateClientId();
        return new MqttAndroidClient(application, "tcp://broker.hivemq.com:1883",
                clientId);
    }
}