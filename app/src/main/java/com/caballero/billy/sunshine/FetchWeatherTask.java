package com.caballero.billy.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BillyCaballero on 31/01/15.
 */
public class FetchWeatherTask extends AsyncTask<Object, Void, String[]> {

    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    private ForecastFragment forecastFragment;

    @Override
    protected String[] doInBackground(Object... params) {
        this.forecastFragment = (ForecastFragment) params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;
        String format = "json";
        String units = "metric";
        int numDays = 7;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, (String) params[1])
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in teparsing.
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();

            Log.v(LOG_TAG, "Forecast Json String " + forecastJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try{
            return WeatherDataParser.getWeatherDataFromJson(forecastJsonStr, numDays);
        }catch (JSONException e){
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        if(strings != null){
            forecastFragment.mForestcastAdapter.clear();
            for (String dayForecastStr : strings){
                forecastFragment.mForestcastAdapter.add(dayForecastStr);
            }
        }
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}
