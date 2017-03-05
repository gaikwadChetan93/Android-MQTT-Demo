package com.nxtgizmo.androidmqttdemo.dashboard;

import android.location.Location;

/**
 * Created by chetan_g on 22/11/16.
 */

interface DashboardContract {
     void onSuccess(String successMessage);
     void onError(String errorMessage);
}
