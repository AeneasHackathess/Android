package gr.meerkat.aeneas.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import gr.meerkat.aeneas.AeneasApplication;
import gr.meerkat.aeneas.CommunicationService;

/**
 * Created by w00dh3n on 29/11/2014.
 */
public class ServerUtils {
    private Future<JsonObject> loading;
    private Context context;
    private final static String prefsUsername = "mUsername";
    private final static String prefsPulseRate = "mPulseRate";
    private final static String prefsLong = "mLong";
    private final static String prefsLat = "mLat";
    private final static String prefsMove = "mMove";
    private final static String prefsStatus = "mStatus";
    public ServerUtils(Context context) {
        this.context = context;
    }
    public void cancel(){
        loading.cancel();
    }
    /**
     * Function for querying site for ada
     *
     */
    public void loadDecisions() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String username = sharedPreferences.getString(prefsUsername,"");
        if (loading != null && !loading.isDone() && !loading.isCancelled())
            return;
        loading = Ion.with(context)
                .load(String.format("http://83.212.100.212/Aeneas/android?username=%s&check=%s", Uri.encode(sharedPreferences.getString(prefsUsername,"")),Uri.encode(AeneasApplication.getPressedCheck())))
                .addHeader("Accept", "application/json")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("SERVER", "completed");
                        Log.d("SERVER",String.format("http://83.212.100.212/Aeneas/android?username=%s&check=%s", Uri.encode(sharedPreferences.getString(prefsUsername,"")),Uri.encode(AeneasApplication.getPressedCheck())));
                        try {
                            if (e != null)
                                throw e;
// Log.d("SERVER",result.toString());
                            JsonArray results = result.getAsJsonArray("response");
                            JsonObject responses = results.get(0).getAsJsonObject();
                            String pulse = responses.get("pulse").getAsString();
                            String lat = responses.get("lat").getAsString();
                            String lng = responses.get("lng").getAsString();
                            String mov = responses.get("move").getAsString();
                            String[] separated = mov.split("T");
                            String date = separated[0];
                            String time = separated[1].split("Z")[0];
                            String state = responses.get("state").getAsString();
                            updateStatus(lat,lng,date+"\n"+time,state,pulse);
                            Log.d("SERVER",responses.get("move").getAsString());
                            ((CommunicationService) context).broadcastToMain();
                            if (!state.equals("check")){
                                ((CommunicationService) context).prepareNotification();
                            }
                            AeneasApplication.setPressedCheck("0");
//                            Log.d("SERVER", results.get("pulse").getAsString());

// fragment.readyToShow();
                        } catch (Exception ex) {
                            Log.d("SERVER", "EERRRR" + ex.toString());
                        }
                    }
                });
    }
    /**
     * Function that updates status
     *
     *
     */
    public void updateStatus(String lat,String lon,String move,String status,String pulse) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefsStatus, status);
        editor.apply();
        editor.putString(prefsLat, lat);
        editor.apply();
        editor.putString(prefsLong, lon);
        editor.apply();
        editor.putString(prefsMove, move);
        editor.apply();
        editor.putString(prefsPulseRate, pulse);
        editor.apply();
    }
}
