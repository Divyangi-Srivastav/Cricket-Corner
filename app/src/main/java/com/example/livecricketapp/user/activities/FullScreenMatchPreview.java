package com.example.livecricketapp.user.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityFullScreenMatchPreviewBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;

public class FullScreenMatchPreview extends AppCompatActivity implements View.OnClickListener {

    private ActivityFullScreenMatchPreviewBinding binding;
    private SingleMatchInfo singleMatchInfo;
    private String tournamentId;
    private FirebaseFirestore db;


    private static final int PERMISSION_REQ_ID = 12;
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
    private String appId = "b7a4b110fced4c69921eb66e205a85d9";
    // Fill the channel name.
    private String channelName = "Match1";
    // Fill the temp token generated on Agora Console.
    private String token = "006b7a4b110fced4c69921eb66e205a85d9IAApmrAhDDjVge3wxDOdX7U5c8MdGB9PjMIJK+/DyBXVqstFYPQAAAAAEABkg7/N0/5eYQEAAQDS/l5h";

    private RtcEngine mRtcEngine;

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // Listen for the remote host joining the channel to get the uid of the host.
        public void onUserJoined(int uid, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Call setupRemoteVideo to set the remote video view after getting uid from the onUserJoined callback.
                    setupRemoteVideo(uid);
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

//        FrameLayout container = findViewById(R.id.local_video_view_container);
//        // Call CreateRendererView to create a SurfaceView object and add it as a child to the FrameLayout.
//        // SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
//        SurfaceView surfaceView = new SurfaceView (getBaseContext());
//        container.addView(surfaceView);
//        // Pass the SurfaceView object to Agora so that it renders the local video.
//        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0));

        ChannelMediaOptions options = new ChannelMediaOptions();
        // For a live streaming scenario, set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;
        // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
        options.clientRoleType = Constants.CLIENT_ROLE_AUDIENCE;


        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        mRtcEngine.joinChannel(token, channelName, (int) System.currentTimeMillis(), options);
        Toast.makeText(this, "hhhhhh", Toast.LENGTH_SHORT).show();
    }

    private void setupRemoteVideo(int uid) {
        FrameLayout container = findViewById(R.id.remote_video_view_container);
        SurfaceView surfaceView = new SurfaceView(getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullScreenMatchPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        singleMatchInfo = new SingleMatchInfo();
        singleMatchInfo = (SingleMatchInfo) getIntent().getSerializableExtra("match");
        tournamentId = getIntent().getStringExtra("tour");

        get_score();

        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) && checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)) {
            initializeAndJoinChannel();
        }

        binding.rewardAPlayer.setOnClickListener(this::onClick);
        binding.fullScreen.setOnClickListener(this::onClick);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }


    // Score update and fetching
    private void get_score() {
        db.collection("Match Info")
                .document(tournamentId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        AllMatchInfo info = value.toObject(AllMatchInfo.class);

                        for (int i = 0; i < info.getMatchInfos().size(); i++) {
                            if (info.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(singleMatchInfo.getMatchNo())) {
                                SingleMatchInfo singleMatchInfo = info.getMatchInfos().get(i);
                                update_score(singleMatchInfo);
                            }
                        }
                    }
                });
    }

    private void update_score(SingleMatchInfo info) {
        binding.team1Score.setText(info.getTeam1Score().getTeamName() + "  " +
                String.valueOf(info.getTeam1Score().getTeamRuns()) + "/" +
                String.valueOf(info.getTeam1Score().getTeamWickets()));
        binding.team2Score.setText(info.getTeam2Score().getTeamName() + "  " +
                String.valueOf(info.getTeam2Score().getTeamRuns()) + "/" +
                String.valueOf(info.getTeam2Score().getTeamWickets()));
    }



    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.reward_a_player:
                Intent intent = new Intent(this, RewardTeamPlayer.class);
                intent.putExtra("match", singleMatchInfo);
                intent.putExtra("tour", tournamentId);
                startActivity(intent);
                break;

            case R.id.full_screen:
                mRtcEngine.stopPreview();
                mRtcEngine.leaveChannel();
                RtcEngine.destroy();
                Intent intent2 = new Intent(this, WatchLiveMatch.class);
                intent2.putExtra("match", singleMatchInfo);
                intent2.putExtra("tour", tournamentId);
                startActivity(intent2);
                finish();
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        mRtcEngine.stopPreview();
        mRtcEngine.leaveChannel();
        RtcEngine.destroy();
    }

}