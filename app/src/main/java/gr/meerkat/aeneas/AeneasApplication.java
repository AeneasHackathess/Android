package gr.meerkat.aeneas;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by w00dh3n on 29/11/2014.
 */
public class AeneasApplication extends Application {
    private final String running="mRunning";
    private final static String prefsPulseRate = "mPulseRate";
    private final static String prefsLong = "mLong";
    private final static String prefsLat = "mLat";
    private final static String prefsMove = "mMove";
    private final static String prefsStatus = "mStatus";

    public String getStatus() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(prefsStatus, "Check");
    }

    public String getPulse() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(prefsPulseRate, "80");
    }

    public String getLong(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(prefsLong, "22.96");
    }
    public String getLat(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(prefsLat,"40.63");
    }

    public boolean isRunning(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean(running,false);
    }

    public void setRunning(Boolean run){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(running, run);
        editor.apply();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
