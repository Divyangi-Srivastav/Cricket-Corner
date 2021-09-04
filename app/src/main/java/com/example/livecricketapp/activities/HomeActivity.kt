package com.example.livecricketapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.livecricketapp.R
import com.facebook.react.modules.core.PermissionListener
import kotlinx.android.synthetic.main.activity_home.*
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetView
import java.net.URL

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btn_submit.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LiveMatchActivity::class.java))
        }
        btn_host_a_tour.setOnClickListener{
            startActivity(Intent (this@HomeActivity , Host_a_tournament::class.java))
        }
    }

}