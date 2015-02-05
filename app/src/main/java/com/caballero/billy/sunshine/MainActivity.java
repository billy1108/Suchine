package com.caballero.billy.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    public final String LOG_CYCLE = "LOG_CYCLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
        Log.v(LOG_CYCLE, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(LOG_CYCLE, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LOG_CYCLE, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LOG_CYCLE, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(LOG_CYCLE, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LOG_CYCLE, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(LOG_CYCLE, "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_map) {
            openPreferenceLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openPreferenceLocationInMap(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", location).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Log.d("Error", "Couldn't call");
        }
    }

}
