package com.example.livecricketapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.livecricketapp.R
import com.facebook.react.modules.core.PermissionListener
import kotlinx.android.synthetic.main.activity_live_match.*
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class LiveMatchActivity : AppCompatActivity(),JitsiMeetActivityInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_match)
        val options: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(URL("https://meet.jit.si"))
            .setRoom("test123")
            .setAudioMuted(true)
            .setVideoMuted(false)
            .setAudioOnly(false)
            .setWelcomePageEnabled(true)
            .build()
        jitsimeetview.join(options)
    }

    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {
        TODO("Not yet implemented")
    }
}