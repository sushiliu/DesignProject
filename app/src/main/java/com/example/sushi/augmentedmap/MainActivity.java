package com.example.sushi.augmentedmap;

import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.example.sushi.augmentedmap.NearbyPlaces.NearbyFragmentTab;
import com.example.sushi.augmentedmap.ScanQRcodes.ScanFragment;


public class MainActivity extends ActionBarActivity {
    public static final String M_CURRENT_TAB = "M_CURRENT_TAB";


    Fragment routeFragmentTab = new RouteFragmentTab();
    Fragment locationFragmentTab = new LocationFragmentTab();
    Fragment nearbyFragmentTab = new NearbyFragmentTab();

    Fragment historyFragment = new HistoryFragment();
    Fragment scanFragment = new ScanFragment();
    Fragment findmeFragment = new FindmeFragment();

    private TabHost mTabHost;
    private String mCurrentTab;
    public static final String routeTab = "routeTab";
    public static final String locationTab = "locationTab";
    public static final String nearbyTab = "nearbyTab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanbutton = (Button) findViewById(R.id.button2);

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragments(scanFragment,false,false,null);
            }
        });

        Button findmebutton = (Button) findViewById(R.id.button4);

        findmebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragments(findmeFragment,false,false,null);
            }
        });

        Button historybutton = (Button) findViewById(R.id.button3);

        historybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragments(historyFragment,false,false,null);
            }
        });


        }



    public void pushFragments(Fragment fragment,
                              boolean shouldAnimate, boolean shouldAdd, String tag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {
           // ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
           //         R.animator.fragment_slide_left_exit,
            //        R.animator.fragment_slide_right_enter,
             //       R.animator.fragment_slide_right_exit);
        }
        ft.replace(R.id.content, fragment, tag);

        if (shouldAdd) {
            /*
            here you can create named backstack for realize another logic.
            ft.addToBackStack("name of your backstack");
             */
            ft.addToBackStack(null);
        } else {
            /*
            and remove named backstack:
            manager.popBackStack("name of your backstack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            or remove whole:
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
             */
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                pushFragments(historyFragment, false,
                        false, null);
                mTabHost.setVisibility(View.INVISIBLE);
                mTabHost.setEnabled(false);
                return true;

            case R.id.action_scan:
                pushFragments(scanFragment, false,
                        false, null);
                mTabHost.setVisibility(View.VISIBLE);
                mTabHost.setEnabled(true);

                return true;
            case R.id.action_findme:
                pushFragments(findmeFragment, false,
                        false, null);
                mTabHost.setVisibility(View.INVISIBLE);
                mTabHost.setEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
