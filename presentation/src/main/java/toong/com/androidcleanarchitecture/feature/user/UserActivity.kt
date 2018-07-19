package toong.com.androidcleanarchitecture.feature.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import toong.com.androidcleanarchitecture.R

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }
}
