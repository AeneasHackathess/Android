package gr.meerkat.aeneas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.*;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import gr.meerkat.aeneas.Utils.PackageAdapter;
import gr.meerkat.aeneas.Utils.PackageItem;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private ArrayList<PackageItem> mItem;
    private CharSequence mDrawerTitle;
    private String[] mDrawerActions;
    private FloatingActionButton button;
    private AeneasApplication mApplication;
//    private ServerUtils serverUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.dark_blue));
        }
        super.onCreate(savedInstanceState);
        mApplication = (AeneasApplication) getApplication();
        setContentView(R.layout.activity_main);
        mDrawerActions = new String[]{getResources().getString(R.string.conn_settings),getResources().getString(R.string.about_info)};
        initializeUI();
    }

    private void initializeUI() {
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = getTitle();
        mDrawerTitle = getString(R.string.navigation_drawer_title);
        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        )
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                getSupportActionBar().setTitle(mTitle);
                syncState();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                getSupportActionBar().setTitle(mDrawerTitle);
                syncState();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();

        mItem = new ArrayList<PackageItem>();
        for (String opt : mDrawerActions) {
            PackageItem temp = new PackageItem();
            temp.setName(opt);
            if (opt.equals(getResources().getString(R.string.conn_settings))) temp.setIcon(getResources().getDrawable(R.drawable.conn_settings));
            if (opt.equals(getResources().getString(R.string.about_info))) temp.setIcon(getResources().getDrawable(R.drawable.about_info));
            mItem.add(temp);
        }

        mDrawerList.setAdapter(new PackageAdapter(this, mItem));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setItemChecked(0, true);
    }

    private void selectItem(int position){
        switch (position) {
            case 0:
                startActivity(new Intent(this, PreferenceActivity.class));
                break;
            case 1:

                break;
            default:
                break;
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.d(TAG,"DrawerClicked");
            selectItem(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
        if (mApplication.isRunning()){
            menu.findItem(R.id.service_running_button).setTitle("Disable");
        }else{
            menu.findItem(R.id.service_running_button).setTitle("Enable");
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.service_running_button){
            Log.d(TAG,"service button");
            if (mApplication.isRunning()){
                item.setTitle("Enable");
                mApplication.setRunning(false);
                stopMyService();

            }else{
                item.setTitle("Disable");
                mApplication.setRunning(true);
                startMyService();
            }
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Fab pressed");

    }

    private void stopMyService(){
        Intent intent = new Intent(MainActivity.this, CommunicationService.class);
        PendingIntent pintent = PendingIntent.getService(MainActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pintent);
    }

    private void startMyService(){
        Intent intent = new Intent(MainActivity.this, CommunicationService.class);
        PendingIntent pintent = PendingIntent.getService(MainActivity.this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5*1000, pintent);
    }
}
