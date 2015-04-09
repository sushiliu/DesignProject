package com.example.sushi.augmentedmap.NearbyPlaces;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.sushi.augmentedmap.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by sushi on 01/04/15.
 */
public class NearbyFragmentTab extends Fragment {



    public static final String M_CURRENT_TAB = "M_CURRENT_TAB";

    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    private String[] places;
    private LocationManager locationManager;
    private Location loc;
    private TabHost pTabHost;
    private String mCurrentTab;

    private View view;

    public static final String restaurant = "Restaurant";
    public static final String bank = "Bank";
    public static final String atm = "ATM";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.nearby_layout, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCompo();
        places = getResources().getStringArray(R.array.places);
        currentLocation();

        pTabHost = (TabHost) view.findViewById(R.id.placetab);

        pTabHost.setup();
        if (savedInstanceState != null) {
            mCurrentTab = savedInstanceState.getString(M_CURRENT_TAB);
            initializeTabs();
            pTabHost.setCurrentTabByTag(mCurrentTab);
            /*
            when resume state it's important to set listener after initializeTabs
            */
            pTabHost.setOnTabChangedListener(tablistener);
        } else {
            pTabHost.setOnTabChangedListener(tablistener);
            initializeTabs();
        }
    }

    private View createTabView(final int id, final String text) {
        View tabview = LayoutInflater.from(view.getContext()).inflate(R.layout.tabs_icon, null);
        if(id!=0){ImageView imageView = (ImageView) tabview.findViewById(R.id.tab_icon);
            imageView.setImageDrawable(getResources().getDrawable(id));}
        TextView textView = (TextView) tabview.findViewById(R.id.tab_text);
        textView.setText(text);
        return tabview;
    }

    private void initializeTabs() {
        TabHost.TabSpec spec;

        spec = pTabHost.newTabSpec(restaurant);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view.findViewById(R.id.realcontent);
            }
        });
        spec.setIndicator(createTabView(0, restaurant));
        pTabHost.addTab(spec);

        spec = pTabHost.newTabSpec(bank);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view.findViewById(R.id.realcontent);
            }
        });
        spec.setIndicator(createTabView(0,"Bank"));
        pTabHost.addTab(spec);


        spec = pTabHost.newTabSpec(atm);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view.findViewById(R.id.realcontent);
            }
        });
        spec.setIndicator(createTabView(0,"ATM"));
        pTabHost.addTab(spec);

    }

    TabHost.OnTabChangeListener tablistener = new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {

            mCurrentTab = tabId;

            int itemPosition =-1;
           if (tabId.equals(restaurant)) {

                itemPosition=7;
            } else if (tabId.equals(bank)) {
                itemPosition=1;
            } else if (tabId.equals(atm)) {
                itemPosition=0;
            }

            Log.e(TAG,
                    places[itemPosition].toLowerCase().replace("-",
                            "_"));
            if (loc != null) {
                mMap.clear();
                new GetPlaces(view.getContext(),
                        places[0].toLowerCase().replace(
                                "-", "_").replace(" ", "_")).execute();

        }
    }};

        private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

            private ProgressDialog dialog;
            private Context context;
            private String places;

            public GetPlaces(Context context, String places) {
                this.context = context;
                this.places = places;
            }

            @Override
            protected void onPostExecute(ArrayList<Place> result) {
                super.onPostExecute(result);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                for (int i = 0; i < result.size(); i++) {
                    mMap.addMarker(new MarkerOptions()
                            .title(result.get(i).getName())
                            .position(
                                    new LatLng(result.get(i).getLatitude(), result
                                            .get(i).getLongitude()))
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.pin))
                            .snippet(result.get(i).getVicinity()));
                }
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(result.get(0).getLatitude(), result
                                .get(0).getLongitude())) // Sets the center of the map to
                                // Mountain View
                        .zoom(14) // Sets the zoom
                        .tilt(30) // Sets the tilt of the camera to 30 degrees
                        .build(); // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(context);
                dialog.setCancelable(false);
                dialog.setMessage("Loading..");
                dialog.isIndeterminate();
                dialog.show();
            }

            @Override
            protected ArrayList<Place> doInBackground(Void... arg0) {
                PlacesService service = new PlacesService(
                        "AIzaSyBuI6tyNRBAMD2EEUnyrJTbcvMQnKQgeYg");
                ArrayList<Place> findPlaces = service.findPlaces(service.latitudeGetter(), // 28.632808 loc.getLatitude()
                        service.longtitudeGetter(), places); // 77.218276 loc.getLongitude()

                for (int i = 0; i < findPlaces.size(); i++) {

                    Place placeDetail = findPlaces.get(i);
                    Log.e(TAG, "places : " + placeDetail.getName());
                }
                return findPlaces;
            }

        }




        private void initCompo() {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.realcontent))
                    .getMap();
        }

        private void currentLocation() {

            locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

            String provider = locationManager
                    .getBestProvider(new Criteria(), false);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location == null) {
                locationManager.requestLocationUpdates(provider, 0, 0, listener);
            } else {
                loc = location;
                new GetPlaces(view.getContext(), places[0].toLowerCase().replace(
                        "-", "_")).execute();
                Log.e(TAG, "location : " + location);
            }

        }

        private LocationListener listener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {
                Log.e(TAG, "location update : " + location);
                loc = location;
                locationManager.removeUpdates(listener);
            }
        };

}
