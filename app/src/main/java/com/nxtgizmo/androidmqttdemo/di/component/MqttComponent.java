package com.nxtgizmo.androidmqttdemo.di.component;

import com.nxtgizmo.androidmqttdemo.dashboard.DashBoardActivity;
import com.nxtgizmo.androidmqttdemo.di.model.AppModule;
import com.nxtgizmo.androidmqttdemo.di.model.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface MqttComponent {
   void inject(DashBoardActivity dashBoardActivity);
}