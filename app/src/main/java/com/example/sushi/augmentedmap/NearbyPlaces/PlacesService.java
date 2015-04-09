package com.example.sushi.augmentedmap.NearbyPlaces;

/**
 * Created by sushi on 08/04/15.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 *  Create request for Places API.
 *
 * @author Karn Shah
 * @Date   10/3/2013
 *
 */
public class PlacesService {

    private String API_KEY;

    public PlacesService(String apikey) {
        this.API_KEY = apikey;
    }

    public void setApiKey(String apikey) {
        this.API_KEY = apikey;
    }

    public ArrayList<Place> findPlaces(double latitude, double longitude,
                                       String placeSpacification) {

        String urlString = makeUrl(latitude, longitude, placeSpacification);

        try {
            String json = getJSON(urlString);
            //System.out.println("jjjjjjjjjjjjjjjjjjjj"+urlString);
            System.out.println("json"+json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("results");

            ArrayList<Place> arrayList = new ArrayList<Place>();
            for (int i = 0; i < array.length(); i++) {
                try {
                    Place place = Place
                            .jsonToPontoReferencia((JSONObject) array.get(i));
                    Log.v("Places Services ", "" + place);
                    arrayList.add(place);
                } catch (Exception e) {
                }
            }
            return arrayList;
        } catch (JSONException ex) {
            Logger.getLogger(PlacesService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
    }

    // https://maps.googleapis.com/maps/api/place/search/json?location=28.632808,77.218276&radius=500&types=atm&sensor=false&key=apikey
    private String makeUrl(double latitude, double longitude, String place) {
        StringBuilder urlString = new StringBuilder(
                "https://maps.googleapis.com/maps/api/place/search/json?");

        setApiKey("AIzaSyBuI6tyNRBAMD2EEUnyrJTbcvMQnKQgeYg");

        if (place.equals("")) {
            urlString.append("&location=");
            urlString.append(Double.toString(latitude));
            urlString.append(",");
            urlString.append(Double.toString(longitude));
            urlString.append("&radius=1000");
            // urlString.append("&types="+place);
            urlString.append("&sensor=false&key="+API_KEY);
        } else {
            urlString.append("&location=");
            urlString.append(Double.toString(latitude));
            urlString.append(",");
            urlString.append(Double.toString(longitude));
            urlString.append("&radius=1000");
            urlString.append("&types=" + place);
            urlString.append("&sensor=false&key="+API_KEY);
        }
        return urlString.toString();
    }

    public JSONObject getPosition() {
        String json = "France";
        try {

            JSONObject obj = new JSONObject(json);
            Log.d("My App", obj.toString());

            return obj;
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
        return null;
    }

    public Double latitudeGetter() {

        Place place = Place.jsonToPontoReferencia(getPosition());
        Double latitude = place.getLatitude();
        return latitude;
    }

    public Double longtitudeGetter() {

        Place place = Place.jsonToPontoReferencia(getPosition());
        Double longtitude = place.getLatitude();
        return longtitude;
    }

    protected String getJSON(String url) throws JSONException {
        return getUrlContents(url);
    }

    private String getUrlContents(String theUrl) throws JSONException {
        StringBuilder content = new StringBuilder();


        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            //System.out.println("this"+urlConnection);

            InputStream in = urlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()), 8);


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
