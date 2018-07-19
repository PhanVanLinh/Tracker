package toong.com.androidcleanarchitecture.feature.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import toong.com.androidcleanarchitecture.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}
