package com.nxtgizmo.androidmqttdemo.mqtt_app;

import android.app.Application;

import com.nxtgizmo.androidmqttdemo.BuildConfig;
import com.nxtgizmo.androidmqttdemo.di.component.DaggerMqttComponent;
import com.nxtgizmo.androidmqttdemo.di.component.MqttComponent;
import com.nxtgizmo.androidmqttdemo.di.model.AppModule;
import com.nxtgizmo.androidmqttdemo.di.model.NetModule;

import timber.log.Timber;

/**
 * Created by Chetan on 2/7/2017.
 */

public class MqttApp extends Application {
    private MqttComponent mqttComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize Dagger2 component
        mqttComponent = DaggerMqttComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

        //Initialize Timber logger
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //Timber.plant(new CrashReportingTree());
        }
    }

    public MqttComponent getMqttComponent(){
        return  mqttComponent;
    }
}
