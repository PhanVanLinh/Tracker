package vn.linh.tracker.infrastructure

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import vn.linh.tracker.util.constant.REQUEST_CODE_RUNTIME_PERMISSION
import java.util.*

class RequestPermissionProvider(val activity: Activity) {
    private lateinit var requestPermissionListener: RequestPermissionListener

    fun requestPermission(permissions: Array<String>, listener: RequestPermissionListener) {
        requestPermissionListener = listener

        if (!needRequestRuntimePermissions()) {
            requestPermissionListener.onSuccess()
            return
        }
        requestUnGrantedPermissions(permissions, REQUEST_CODE_RUNTIME_PERMISSION)
    }

    private fun needRequestRuntimePermissions(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun requestUnGrantedPermissions(permissions: Array<String>, requestCode: Int) {
        val unGrantedPermissions = findUnGrantedPermissions(permissions)
        if (unGrantedPermissions.isEmpty()) {
            requestPermissionListener.onSuccess()
            return
        }
        ActivityCompat.requestPermissions(activity, unGrantedPermissions, requestCode)
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun findUnGrantedPermissions(permissions: Array<String>): Array<String> {
        val unGrantedPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (!isPermissionGranted(permission)) {
                unGrantedPermissionList.add(permission)
            }
        }
        return unGrantedPermissionList.toTypedArray()
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                   grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_RUNTIME_PERMISSION) {
            if (grantResults.isEmpty()) {
                for (grantResult in grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        requestPermissionListener.onFailed()
                        return
                    }
                }
                requestPermissionListener.onSuccess()
            } else {
                requestPermissionListener.onFailed()
            }
        }
    }

    interface RequestPermissionListener {
        fun onSuccess()

        fun onFailed()
    }
}