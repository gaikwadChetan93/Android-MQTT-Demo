package com.nxtgizmo.androidmqttdemo.dashboard

import android.location.Location

/**
 * Created by chetan_g on 22/11/16.
 */

internal interface DashboardContract {
    fun onSuccess(successMessage: String)
    fun onError(errorMessage: String)
}
