package com.example.livecricketapp.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityBroadcastLiveEventBinding;
import com.example.livecricketapp.databinding.ActivityStartLiveStreamingBinding;
import com.example.livecricketapp.model.StreamingCred;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;

public class BroadcastLiveEvent extends AppCompatActivity {

    private ActivityBroadcastLiveEventBinding binding ;
    private static final int PERMISSION_REQ_ID = 22;
    private Boolean isMuted = true;
    private FirebaseFirestore db;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }
        return true;
    }

    // Fill the App ID of your project generated on Agora Console.
    private String appId = "96b7a16527604155aa52269e67eaf7fa";
    // Fill the channel name.
    private String channelName = "channel";
    // Fill the temp token generated on Agora Console.
    private String token = "007eJxTYDjmsmbb7gebf+VoabZU7Tn+iF86Y9dv3puRVkasLxfK5O9UYEgyTzRJMjQ0SEtOTTFJNrO0NDJMTTIzSzUyME20ME2xVHETSV4yXTTZ9H4sCyMDBIL47AzJGYl5eak5DAwAJjIibA==";

    private RtcEngine mRtcEngine;

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // Listen for the remote host joining the channel to get the uid of the host.
        public void onUserJoined(int uid, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Call setupRemoteVideo to set the remote video view after getting uid from the onUserJoined callback.
//                    setupRemoteVideo(uid);
                }
            });
        }
    };

    private void initializeAndJoinChannel() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            mRtcEngine = RtcEngine.create(config);
        } catch (Exception e) {
            throw new RuntimeException("Check the error." + e.toString());
        }
        // By default, video is disabled, and you need to call enableVideo to start a video stream.
        mRtcEngine.enableVideo();
        // Start local preview.
        mRtcEngine.startPreview();


        FrameLayout container = findViewById(R.id.local_video_view_container);
        // Call CreateRendererView to create a SurfaceView object and add it as a child to the FrameLayout.
        // SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        SurfaceView surfaceView = new SurfaceView(getBaseContext());
        container.addView(surfaceView);
        // Pass the SurfaceView object to Agora so that it renders the local video.
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0));

        ChannelMediaOptions options = new ChannelMediaOptions();
        // For a live streaming scenario, set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;
        // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;

        mRtcEngine.muteLocalAudioStream(isMuted);
        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        mRtcEngine.joinChannel(token, channelName, 567, options);
    }

//    private void setupRemoteVideo(int uid) {
//        FrameLayout container = findViewById(R.id.remote_video_view_container);
//        SurfaceView surfaceView = new SurfaceView (getBaseContext());
//        surfaceView.setZOrderMediaOverlay(true);
//        container.addView(surfaceView);
//        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBroadcastLiveEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        get_cred();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    private void get_cred() {
        db.collection("Cred")
                .document("Streaming")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        StreamingCred streamingCred = documentSnapshot.toObject(StreamingCred.class);
                        channelName = streamingCred.getName();
                        token = streamingCred.getId();
                        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
                        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) && checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)) {
                            initializeAndJoinChannel();
                        }
                    }
                });
    }

    public void switch_camera(View view) {
        mRtcEngine.switchCamera();
    }

    public void microphone_change(View view) {
        if (isMuted) {
            isMuted = false;
            binding.mic.setImageResource(R.drawable.ic_baseline_mic_24);
        } else {
            isMuted = true;
            binding.mic.setImageResource(R.drawable.ic_baseline_mic_off_24);
        }
        mRtcEngine.muteLocalAudioStream(isMuted);
    }

}