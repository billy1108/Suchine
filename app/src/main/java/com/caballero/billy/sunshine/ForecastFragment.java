package com.caballero.billy.sunshine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by BillyCaballero on 31/01/15.
 */
public class ForecastFragment extends Fragment {

    public ArrayAdapter<String> mForestcastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute(this, "94043");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listItemView = (ListView) rootView.findViewById(R.id.listview_forescast);

        ArrayList<String> items = new ArrayList<String>();
        items.add("17 Fake Street");
        items.add("Phoney town");
        items.add("Makebelieveland");

        mForestcastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forescast, R.id.list_item_forescast_textview, items);
        listItemView.setAdapter(mForestcastAdapter);
        return rootView;
    }
}
