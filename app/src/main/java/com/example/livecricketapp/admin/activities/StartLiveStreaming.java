package com.example.livecricketapp.admin.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.livecricketapp.R;
import com.example.livecricketapp.admin.adapters.AdRequestsAdapter;
import com.example.livecricketapp.admin.adapters.CommentsAdapter;
import com.example.livecricketapp.databinding.ActivityStartLiveStreamingBinding;
import com.example.livecricketapp.model.AdBanner;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.Comments;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.StreamingCred;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class StartLiveStreaming extends AppCompatActivity implements AdRequestsAdapter.On_Click, CommentsAdapter.On_Click {

    private ActivityStartLiveStreamingBinding binding;
    private static final int PERMISSION_REQ_ID = 22;
    private Boolean isMuted = true;
    private FirebaseFirestore db;
    private List<AdBanner> adBanners = new ArrayList<>();
    private List<String> commentList = new ArrayList<>();
    private AdRequestsAdapter adRequestsAdapter;
    private CommentsAdapter commentsAdapter;
    private String tournamentId;
    private String matchNo;
    private Comments comments;
    private AllMatchInfo allMatchInfo;
    private SingleMatchInfo singleMatchInfo;
    private int a = 0;


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
    private String token = "006b7a4b110fced4c69921eb66e205a85d9IADvzFuD6XlMuM27aKzcWP2OWn90MiYU0CfatL8cSG2lo8tFYPQAAAAAEAAac4ysmRWrYQEAAQCYFath";

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
        binding = ActivityStartLiveStreamingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");
        comments = new Comments();
        singleMatchInfo = new SingleMatchInfo();
        allMatchInfo = new AllMatchInfo();

        get_cred();

        get_ad_data();
        adRequestsAdapter = new AdRequestsAdapter(this, adBanners, "admin", this::change_status);
        binding.recyclerViewAds.setAdapter(adRequestsAdapter);
        binding.recyclerViewAds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        get_comments();
        commentsAdapter = new CommentsAdapter(this, commentList, this::delete_comment, "admin");
        binding.recyclerViewComments.setAdapter(commentsAdapter);
        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        get_single_match_info();
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

    // update match status to live streaming
    private void get_single_match_info() {
        db.collection("Match Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allMatchInfo = documentSnapshot.toObject(AllMatchInfo.class);
                        for (int i = 0; i < allMatchInfo.getMatchInfos().size(); i++) {
                            if (allMatchInfo.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(matchNo)) {
                                singleMatchInfo = allMatchInfo.getMatchInfos().get(i);
                                allMatchInfo.getMatchInfos().remove(i);
                                a = i;
                                update_match_status();
                            }
                        }
                    }
                });
    }

    public void update_match_status() {
        singleMatchInfo.setMatchStatus(1);
        List<SingleMatchInfo> matchInfos = new ArrayList<>();
        matchInfos = allMatchInfo.getMatchInfos();
        matchInfos.add(a, singleMatchInfo);
        allMatchInfo.setMatchInfos(matchInfos);
        db.collection("Match Info").document(tournamentId).set(allMatchInfo);
    }


    // get and update comments
    private void get_comments() {
        db.collection("Comments")
                .document(tournamentId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        if (value.exists()) {
                            commentList.clear();
                            comments = value.toObject(Comments.class);
                            commentList.addAll(comments.getCommentsList());
                            commentsAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void delete_comment(int a) {
        commentList.remove(a);
        comments.setCommentsList(commentList);

        db.collection("Comments").document(tournamentId).set(comments);
        Toast.makeText(this, "Comment Removed Successfully", Toast.LENGTH_SHORT).show();
    }


    // advertisement add and remove
    private void get_ad_data() {
        db.collection("Ads")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        adBanners.clear();
                        if (!value.isEmpty()) {
                            for (QueryDocumentSnapshot snapshot : value) {
                                AdBanner banner = snapshot.toObject(AdBanner.class);
                                adBanners.add(banner);
                                adRequestsAdapter.notifyDataSetChanged();
                            }
                        }
                        adRequestsAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void change_status(int a) {

        switch (adBanners.get(a).getAdStatus()) {
            case 0:
                adBanners.get(a).setAdStatus(1);
                db.collection("Ads")
                        .document(adBanners.get(a).getAdId()).set(adBanners.get(a));
                break;
            case 1:
                db.collection("Ads")
                        .document(adBanners.get(a).getAdId()).delete();
                adBanners.remove(a);
                break;
        }
        adRequestsAdapter.notifyDataSetChanged();

    }

    protected void onDestroy() {
        super.onDestroy();
        mRtcEngine.stopPreview();
        mRtcEngine.leaveChannel();
        RtcEngine.destroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirmation")
                .setMessage("Are you sure to end the live stream")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(StartLiveStreaming.this, MatchEndResult.class);
                        intent.putExtra("tour", tournamentId);
                        intent.putExtra("match", matchNo);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }


}