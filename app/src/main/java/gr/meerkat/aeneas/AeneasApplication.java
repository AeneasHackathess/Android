package gr.meerkat.aeneas;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by w00dh3n on 29/11/2014.
 */
public class AeneasApplication extends Application {
    private final String running="mRunning";

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
