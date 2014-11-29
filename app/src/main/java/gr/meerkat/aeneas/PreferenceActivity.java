package gr.meerkat.aeneas;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by w00dh3n on 29/11/2014.
 */
public class PreferenceActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_wrapper, new SettingsFragment())
                .commit();
        toolbar = (Toolbar) findViewById(R.id.action_bar_settings);
        // use action bar here
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public static class SettingsFragment extends gr.meerkat.aeneas.Utils.PreferenceFragment {
        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
