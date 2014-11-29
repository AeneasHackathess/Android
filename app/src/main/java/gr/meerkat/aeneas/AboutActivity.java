package gr.meerkat.aeneas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by w00dh3n on 29/11/2014.
 */
public class AboutActivity extends ActionBarActivity {
        private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        // use action bar here
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    public void onOpenTwitterClick(View view) {
        openBrowser("http://forum.xda-developers.com/showthread.php?t=2405849");
    }
    private void openBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }
}
