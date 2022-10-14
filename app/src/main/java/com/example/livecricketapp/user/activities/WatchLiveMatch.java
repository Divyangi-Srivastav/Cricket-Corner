package com.example.livecricketapp.user.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.livecricketapp.R;
import com.example.livecricketapp.admin.adapters.AdRequestsAdapter;
import com.example.livecricketapp.admin.adapters.CommentsAdapter;
import com.example.livecricketapp.databinding.ActivityWatchLiveMatchBinding;
import com.example.livecricketapp.model.AdBanner;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.Comments;
import com.example.livecricketapp.model.PlayerScoreCard;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.StreamingCred;
import com.example.livecricketapp.model.TeamScoreCard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;

public class WatchLiveMatch extends AppCompatActivity implements View.OnClickListener, AdRequestsAdapter.On_Click, CommentsAdapter.On_Click {

    private ActivityWatchLiveMatchBinding binding;
    private SingleMatchInfo singleMatchInfo;
    private String tournamentId;
    private FirebaseFirestore db;
    private List<AdBanner> adBanners = new ArrayList<>();
    private List<String> commentList = new ArrayList<>();
    private AdRequestsAdapter adRequestsAdapter;
    private CommentsAdapter commentsAdapter;
    private Comments comments;
    private int OVERS ;


    private String URL = "https://agora-cricket.herokuapp.com/rtc/channel/2/uid/0/?expiry=90000";


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
    private String appId = "96b7a16527604155aa52269e67eaf7fa";
    // Fill the channel name.
    private String channelName = "channel";
    // Fill the temp token generated on Agora Console.
    private String token = "007eJxTYDjT2R/+453TyWqFY48fLlGKfT/3xHS57uBXt46UT7okNPWXAoOlWZJ5oqGZqZG5mYGJoalpYqKpkZGZZaqZeWpimnlaYsxc1+Rb79yS80oyGRihEMRnZ0jOSMzLS81hYAAADHEmuw==";

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

        ChannelMediaOptions options = new ChannelMediaOptions();
        // For a live streaming scenario, set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;
        // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
        options.clientRoleType = Constants.CLIENT_ROLE_AUDIENCE;


        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        mRtcEngine.joinChannel(token, channelName, (int) System.currentTimeMillis(), options);
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
        OVERS = getIntent().getIntExtra("over" , 0);

        comments = new Comments();

        get_ad_data();
        adRequestsAdapter = new AdRequestsAdapter(this, adBanners, "user", this::change_status);
        binding.recyclerViewAds.setAdapter(adRequestsAdapter);
        binding.recyclerViewAds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        get_comments();
        commentsAdapter = new CommentsAdapter(WatchLiveMatch.this, commentList, WatchLiveMatch.this::delete_comment, "user");
        binding.recyclerViewComments.setAdapter(commentsAdapter);
        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(WatchLiveMatch.this));

        binding.rewardAPlayer.setOnClickListener(this::onClick);
        binding.send.setOnClickListener(this::onClick);
        binding.fullScreen.setOnClickListener(this::onClick);

        get_score();

        get_credential();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

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

                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

                List<String> commentList = new ArrayList<>();
                commentList = comments.getCommentsList();
                commentList.add(binding.comment.getText().toString());
                binding.comment.getText().clear();
                comments.setCommentsList(commentList);

                if (!comments.getMatchNo().equalsIgnoreCase(singleMatchInfo.getMatchNo())) {
                    comments.setTournamentId(tournamentId);
                    comments.setMatchNo(singleMatchInfo.getMatchNo());
                }

                update_comments();

                break;

            case R.id.full_screen:

                mRtcEngine.stopPreview();
                mRtcEngine.leaveChannel();
                RtcEngine.destroy();
                Intent intent2 = new Intent(this, FullScreenMatchPreview.class);
                intent2.putExtra("match", singleMatchInfo);
                intent2.putExtra("tour", tournamentId);
                startActivity(intent2);
                finish();
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

    private void update_comments() {
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

        binding.batsman1Name.setText(info.getBatsman1());
        binding.batsman2Name.setText(info.getBatsman2());
        binding.bowlerName.setText(info.getBowler());

        if ( info.getScore().get(0).toString().equalsIgnoreCase("7") )
            binding.ball1.setText("W");
        else if ( info.getScore().get(1).toString().equalsIgnoreCase("7") )
            binding.ball2.setText("W");
        else if ( info.getScore().get(2).toString().equalsIgnoreCase("7") )
            binding.ball3.setText("W");
        else if ( info.getScore().get(3).toString().equalsIgnoreCase("7") )
            binding.ball4.setText("W");
        else if ( info.getScore().get(4).toString().equalsIgnoreCase("7") )
            binding.ball5.setText("W");
        else if ( info.getScore().get(5).toString().equalsIgnoreCase("7") )
            binding.ball6.setText("W");


        if ( !info.getScore().get(0).toString().equalsIgnoreCase("-1") )
            binding.ball1.setText(String.valueOf(info.getScore().get(0)));
        else if ( !info.getScore().get(1).toString().equalsIgnoreCase("-1") )
            binding.ball2.setText(String.valueOf(info.getScore().get(1)));
        else if ( !info.getScore().get(2).toString().equalsIgnoreCase("-1") )
            binding.ball3.setText(String.valueOf(info.getScore().get(2)));
        else if ( !info.getScore().get(3).toString().equalsIgnoreCase("-1") )
            binding.ball4.setText(String.valueOf(info.getScore().get(3)));
        else if ( !info.getScore().get(4).toString().equalsIgnoreCase("-1") )
            binding.ball5.setText(String.valueOf(info.getScore().get(4)));
        else if ( !info.getScore().get(5).toString().equalsIgnoreCase("-1") )
            binding.ball6.setText(String.valueOf(info.getScore().get(5)));

        if (info.getTeam1Score().getTeamName().equalsIgnoreCase(info.getBattingTeam()))
        {
            binding.battingTeam.setText(info.getTeam1Score().getTeamName());
            binding.bowlingTeam.setText( info.getTeam2Score().getTeamName());
            binding.teamScore.setText(String.valueOf(info.getTeam1Score().getTeamRuns()));
            binding.teamWickets.setText(String.valueOf(info.getTeam1Score().getTeamWickets()));
            int balls = info.getTeam1Score().getTeamBalls();
            int overs = balls / 6 ;
            balls %= 6 ;
            binding.overCount.setText(String.valueOf(overs) + "." + String.valueOf(balls));
            binding.totalOverCount.setText(String.valueOf(OVERS));
            for ( int i=0 ; i < info.getTeam1Score().getCards().size() ; i++ )
            {
                if(info.getTeam1Score().getCards().get(i).getPlayerName().equalsIgnoreCase(info.getBatsman1()))
                {
                    binding.batsman1Score.setText(String.valueOf(info.getTeam1Score().getCards().get(i).getRuns()));
                    binding.batsman1Balls.setText(String.valueOf(info.getTeam1Score().getCards().get(i).getBalls()));
                }

                if(info.getTeam1Score().getCards().get(i).getPlayerName().equalsIgnoreCase(info.getBatsman2()))
                {
                    binding.batsman2Score.setText(String.valueOf(info.getTeam1Score().getCards().get(i).getRuns()));
                    binding.batsman2Balls.setText(String.valueOf(info.getTeam1Score().getCards().get(i).getBalls()));
                }
            }

            if(info.getTeam2Score().getTeamRuns() <= 0){
                binding.targetCard.setVisibility(View.GONE);
            }else{
                binding.targetCard.setVisibility(View.VISIBLE);
            }
                binding.target.setText("Target " + (info.getTeam2Score().getTeamRuns()+1));
        }


        if (info.getTeam2Score().getTeamName().equalsIgnoreCase(info.getBattingTeam()))
        {
            binding.battingTeam.setText(info.getTeam2Score().getTeamName());
            binding.bowlingTeam.setText( info.getTeam1Score().getTeamName());
            binding.teamScore.setText(String.valueOf(info.getTeam2Score().getTeamRuns()));
            binding.teamWickets.setText(String.valueOf(info.getTeam2Score().getTeamWickets()));
            int balls = info.getTeam2Score().getTeamBalls();
            int overs = balls / 6 ;
            balls %= 6 ;
            binding.overCount.setText(String.valueOf(overs) + "." + String.valueOf(balls));
            for ( int i=0 ; i < info.getTeam2Score().getCards().size() ; i++ )
            {
                if(info.getTeam2Score().getCards().get(i).getPlayerName().equalsIgnoreCase(info.getBatsman1()))
                {
                    binding.batsman1Score.setText(String.valueOf(info.getTeam2Score().getCards().get(i).getRuns()));
                    binding.batsman1Balls.setText(String.valueOf(info.getTeam2Score().getCards().get(i).getBalls()));
                }

                if(info.getTeam2Score().getCards().get(i).getPlayerName().equalsIgnoreCase(info.getBatsman2()))
                {
                    binding.batsman2Score.setText(String.valueOf(info.getTeam2Score().getCards().get(i).getRuns()));
                    binding.batsman2Balls.setText(String.valueOf(info.getTeam2Score().getCards().get(i).getBalls()));
                }
            }
            if(info.getTeam1Score().getTeamRuns() <= 0){
                binding.targetCard.setVisibility(View.GONE);
            }else{
                binding.targetCard.setVisibility(View.VISIBLE);
            }
                binding.target.setText("Target " +(info.getTeam1Score().toString()+1));
        }

        binding.ball1.setText(setBall(info.getScore().get(0)));
        binding.ball2.setText(setBall(info.getScore().get(1)));
        binding.ball3.setText(setBall(info.getScore().get(2)));
        binding.ball4.setText(setBall(info.getScore().get(3)));
        binding.ball5.setText(setBall(info.getScore().get(4)));
        binding.ball6.setText(setBall(info.getScore().get(5)));

        for(int i=5;i>=0;i--){
            int run = info.getScore().get(i);
            if(run!=-1){
                String higlight = "Dot";
                if(run>=0 && run<=6){
                    switch (run){
                        case 1: higlight = "ONE";
                        break;
                        case 2: higlight = "TWO";
                            break;
                        case 3: higlight = "THREE";
                            break;
                        case 4: higlight = "FOUR";
                            break;
                        case 5: higlight = "FIVE";
                            break;
                        case 6: higlight = "SIX";
                            break;
                    }
                    binding.highlightCard.setCardBackgroundColor(Color.GREEN);
                }else if(run == 7){
                    binding.highlightCard.setCardBackgroundColor(Color.RED);
                }
                binding.highlight.setText(higlight);
                break;
            }
        }
    }

    public String setBall(int run){
        if(run>=0 && run<=6){
            return String.valueOf(run);
        }else if(run == -1){
            return " ";
        }else if(run == 7){
            return "W";
        }else{
            return "-";
        }
    }

    private void set_scores(SingleMatchInfo info) {
        TeamScoreCard card = info.getTeam1Score();

        PlayerScoreCard a, b;
        a = card.getCards().get(0);
        b = card.getCards().get(1);
        for (int i = 0; i < card.getCards().size(); i++) {
            if (card.getCards().get(i).getRuns() > a.getRuns()) {
                b = a;
                a = card.getCards().get(i);
            }
        }
        binding.batsman1Name.setText(a.getPlayerName() + " " + a.getRuns() + "( " + a.getBalls() + " )" + "   " + b.getPlayerName() + " " + b.getRuns() + "( " + b.getBalls() + " )");

        card = info.getTeam2Score();
        a = card.getCards().get(0);
        b = card.getCards().get(0);
        for (int i = 0; i < card.getCards().size(); i++) {
            if (card.getCards().get(i).getRuns() > a.getRuns()) {
                b = a;
                a = card.getCards().get(i);
            }
        }
        binding.batsman2Name.setText(a.getPlayerName() + " " + a.getRuns() + "( " + a.getBalls() + " )" + "   " + b.getPlayerName() + " " + b.getRuns() + "( " + b.getBalls() + " )");

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
                                adRequestsAdapter.notifyDataSetChanged();
                            }
                        }
                        adRequestsAdapter.notifyDataSetChanged();
                    }
                });
    }


    protected void onDestroy() {
        super.onDestroy();
        mRtcEngine.stopPreview();
        mRtcEngine.leaveChannel();
        RtcEngine.destroy();
    }


    @Override
    public void change_status(int a) {

    }

    @Override
    public void delete_comment(int a) {

    }

    private void get_credential()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    token = obj.getString("rtcToken");

                    if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) && checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)) {
                        initializeAndJoinChannel();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

}