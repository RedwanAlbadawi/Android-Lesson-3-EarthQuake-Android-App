package com.example.vijaya.earthquakeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) {
        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = "";
        try {
            //TODO: 1. Create a URL from the requestUrl string and make a GET request to it
            //TODO: 2. Read from the Url Connection and store it as a string(jsonResponse)
                /*TODO: 3. Parse the jsonResponse string obtained in step 2 above into JSONObject to extract the values of
                        "mag","place","time","url"for every earth quake and create corresponding Earthquake objects with them
                        Add each earthquake object to the list(earthquakes) and return it.
                */

            // HTTP connection object
            HttpURLConnection connection = null;

            BufferedReader reader = null;
            try {
                // we will pass url here
                url = new URL(requestUrl);

                // open connection to that url
                connection = (HttpURLConnection) url.openConnection();
                // connect to the url
                connection.connect();

                // if we are connected successfully create a stream
                InputStream stream = connection.getInputStream();

                // associate reader with that stream
                reader = new BufferedReader(new InputStreamReader(stream));

                // string buffer class to store the record
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }
                // if we have data...
                //Log.d("MyQueryUtils",buffer.toString());

                //JSON parsing

                // once we have data its time to parse the json...
                // first we will convert the string to json object
                JSONObject jsonObject = new JSONObject(buffer.toString());

                // because we have JSON array of features so we will convert that big json object of arrays to JSONArray class

                JSONArray jsonArray = jsonObject.getJSONArray("features");

                // iterate through it and get the values of properties object

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    JSONObject propertiesJSON = jsonObject1.getJSONObject("properties");

                    // create earthQuake object by getting the desired attributes from the json
                    Earthquake earthquake = new Earthquake(
                            propertiesJSON.getDouble("mag"),propertiesJSON.getString("place"),
                            propertiesJSON.getLong("time"),
                            propertiesJSON.getString("url")
                    );

                    Log.d("EARTH_QUAKE",earthquake.toString());

                    // add it to list.
                    earthquakes.add(earthquake);
                }
            } catch (MalformedURLException e) {
                Log.d("MyQueryUtils",e.getMessage());
            } catch (IOException e) {
                Log.d("MyQueryUtils",e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    Log.d("MyQueryUtils",e.getMessage());
                }
            }
            Log.d("MyQueryUtils","Record is NULLL");

            // return the list of earthQuake
            return earthquakes;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        }
        // Return the list of earthquakes
        return earthquakes;
    }
}
