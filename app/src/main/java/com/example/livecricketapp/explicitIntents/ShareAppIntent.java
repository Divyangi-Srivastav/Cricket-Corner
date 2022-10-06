package com.example.livecricketapp.explicitIntents;

import android.content.Context;
import android.content.Intent;

public class ShareAppIntent {

    public static void shareApp(Context context){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Cricket Corner: Live Cricket");
            String shareMessage= "\nCricket Corner: Live Cricket \nAn awesome platform to watch live cricket matches with scoreboard update.\n" +
                    "Download Now from playstore: \n\n";
            //TODO: add link here
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "Share"));
        } catch(Exception e) {
            //e.toString();
        }
    }

}
