package com.example.livecricketapp.user.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.AdRequestsAdapter;
import com.example.livecricketapp.databinding.ActivityWatchLiveMatchBinding;
import com.example.livecricketapp.model.AdBanner;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.Comments;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;

public class WatchLiveMatch extends AppCompatActivity implements View.OnClickListener, AdRequestsAdapter.On_Click {

    private ActivityWatchLiveMatchBinding binding;
    private SingleMatchInfo singleMatchInfo;
    private String tournamentId;
    private FirebaseFirestore db;
    private List<AdBanner> adBanners = new ArrayList<>();
    private AdRequestsAdapter adapter;
    private Comments comments;


    private static final int PERMISSION_REQ_ID = 22;
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
    private String token = "006b7a4b110fced4c69921eb66e205a85d9IAC4io7SVl58xKMfq1CMhiAw8RpW2uXed5fQhMh486o92MtFYPQAAAAAEAApikcim5BdYQEAAQCckF1h";

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
        mRtcEngine.joinChannel(token, channelName, 897, options);
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
        binding = ActivityWatchLiveMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        singleMatchInfo = new SingleMatchInfo();
        singleMatchInfo = (SingleMatchInfo) getIntent().getSerializableExtra("match");
        tournamentId = getIntent().getStringExtra("tour");

        comments = new Comments();

        get_ad_data();
        adapter = new AdRequestsAdapter(this, adBanners, "user", this::change_status);
        binding.recyclerViewAds.setAdapter(adapter);
        binding.recyclerViewAds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.rewardAPlayer.setOnClickListener(this::onClick);
        binding.send.setOnClickListener(this::onClick);

        get_score();
        get_comments();

        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) && checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)) {
            initializeAndJoinChannel();
        }

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

            case R.id.send:

                List<String> commentList = new ArrayList<>();
                commentList = comments.getCommentsList();
                commentList.add(binding.comment.getText().toString());
                binding.comment.getText().clear();
                comments.setCommentsList(commentList);

                if ( !comments.getMatchNo().equalsIgnoreCase(singleMatchInfo.getMatchNo()) )
                {
                    comments.setTournamentId(tournamentId);
                    comments.setMatchNo(singleMatchInfo.getMatchNo());
                }

                update_comments();

                break;

        }

    }

    // get and update comments
    private void get_comments() {
        db.collection("Comments")
                .document(tournamentId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.exists()) {
                            comments = value.toObject(Comments.class);
                        }
                    }
                });
    }

    private void update_comments()
    {
        db.collection("Comments")
                .document(tournamentId)
                .set(comments);
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


    // advertisement update and fetching
    private void get_ad_data() {
        db.collection("Ads")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        adBanners.clear();
                        if (!value.isEmpty()) {
                            for (QueryDocumentSnapshot snapshot : value) {
                                AdBanner banner = snapshot.toObject(AdBanner.class);
                                if (banner.getAdStatus() == 1)
                                    adBanners.add(banner);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    public void change_status(int a) {

    }


    protected void onDestroy() {
        super.onDestroy();
        mRtcEngine.stopPreview();
        mRtcEngine.leaveChannel();
        RtcEngine.destroy();
    }
}