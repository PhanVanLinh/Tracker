package vn.linh.tracker.feature.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseActivity
import vn.linh.tracker.infrastructure.RequestLocationProvider
import vn.linh.tracker.infrastructure.RequestPermissionProvider
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var requestPermissionProvider: RequestPermissionProvider

    @Inject
    lateinit var requestLocationProvider: RequestLocationProvider

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionProvider.requestPermission(arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_EXTERNAL_STORAGE), object : RequestPermissionProvider.RequestPermissionListener {
            override fun onSuccess() {
                Toast.makeText(this@MainActivity, "request permission success", Toast.LENGTH_SHORT).show()
            }

            override fun onFailed() {
                Toast.makeText(this@MainActivity, "request permission failed", Toast.LENGTH_SHORT).show()
            }
        })
        requestLocationProvider.request(object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                super.onLocationResult(result)

//                result.lastLocation todo handle last location here
            }
        })

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestPermissionProvider.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestLocationProvider.onActivityResult(requestCode, resultCode, data)
    }
}
