package permisssion.user.permissionlib

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.app.Activity
import android.os.Build


class PermissionUtils {


    companion object {
        var READ_SMS = Manifest.permission.READ_SMS
        var RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
        var SEND_SMS = Manifest.permission.SEND_SMS
        var ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        var ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        var READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
        var CAMERA = Manifest.permission.CAMERA
        var WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        var READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        var READ_CONTACTS = Manifest.permission.READ_CONTACTS
        var WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS
        var CALL_PHONE = Manifest.permission.CALL_PHONE
        var GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS
        var REQUEST_CODE = 10
    }

    private val permission = ArrayList<String>()
    private var INSTANCE: PermissionUtils? = null

    private var mContext: Context? = null
    private var mListener: PermissionListener? = null
    private var deniedPermission = false
    fun with(context: Context): PermissionUtils {
        this.mContext = context
        return this
    }

    fun setListener(mListner: PermissionListener): PermissionUtils {
        this.mListener = mListner
        return this
    }

    private fun isAvailableAllPermission(): Boolean {
        var isAllowed = true
        for (item in permission) {
            isAllowed = ActivityCompat.checkSelfPermission(mContext!!, item) == PackageManager.PERMISSION_GRANTED
            if (!isAllowed)
                break
        }
        return isAllowed
    }

    fun requestPermission(permission: Array<String>) {
        if (mListener == null)
            return
        if (mContext == null)
            return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mListener!!.allPermissionGranted()
            return
        }
        if (permission.size > 0) {
            this.permission.clear()
            this.permission.addAll(permission)
        }
        if (mContext is Activity)
            ActivityCompat.requestPermissions(mContext as Activity, this.permission.toArray(arrayOfNulls(0)), REQUEST_CODE)
    }

    fun onPermissionRequestResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (!isAvailableAllPermission()) {
                    for (name in permissions) {
                        deniedPermission = ActivityCompat.shouldShowRequestPermissionRationale(mContext as Activity, name)
                        if (deniedPermission)
                            break
                    }
                    if (!deniedPermission) {
                        if (mListener != null) {
                            mListener!!.onNeverAskAgainPermission(permissions)
                        }
                    } else {
                        if (mListener != null) {
                            mListener!!.onDenied(permissions)
                        }
                    }
                } else if (mListener != null)
                    mListener!!.allPermissionGranted()
            }
        }
    }
}

interface PermissionListener {
    fun allPermissionGranted()

    fun onNeverAskAgainPermission(string: Array<String>)

    fun onDenied(string: Array<String>)
}