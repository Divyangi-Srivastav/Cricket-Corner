package com.example.livecricketapp.model;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0; //private mode
    String Filename = "cricketApp";
    String  FIRST_TIME_LAUNCHED = "firsttimelaunched";
    String Key_temp = "userKey";
    String Name_temp = "userName";
    String Address_temp = "userAddress";
    String Phone_temp = "userPhone";


    public Shared(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Filename,mode);
        editor = sharedPreferences.edit();
    }

    public boolean isFirstTimeLaunched() {
        return sharedPreferences.getBoolean(FIRST_TIME_LAUNCHED, true);
    }

    public void setFirstTimeLaunched(Boolean firstTimeLaunched) {
        editor.putBoolean(FIRST_TIME_LAUNCHED, firstTimeLaunched);
        editor.commit();
    }

    public  void setUserKeyShared(String userKeyShared){
        editor.putString(Key_temp,userKeyShared);
        editor.commit();
    }

    public String getUserKeyShared(){
        return sharedPreferences.getString(Key_temp, "");
    }

    public  void setUserNameShared(String userNameShared){
        editor.putString(Name_temp,userNameShared);
        editor.commit();
    }

    public String getUserNameShared(){
        return sharedPreferences.getString(Name_temp, "");
    }

    public  void setUserAddressShared(String userAddressShared){
        editor.putString(Address_temp,userAddressShared);
        editor.commit();
    }

    public String getUserAddressShared(){
        return sharedPreferences.getString(Address_temp, "");
    }

    public  void setUserPhoneShared(String userPhoneShared){
        editor.putString(Phone_temp,userPhoneShared);
        editor.commit();
    }

    public String getUserPhoneShared(){
        return sharedPreferences.getString(Phone_temp, "");
    }


}
