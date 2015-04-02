package com.example.sushi.augmentedmap;

import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.support.v4.view.GravityCompat;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;


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


    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        addDrawerItems();



        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!"+position, Toast.LENGTH_SHORT).show();

                if(position==0){
                    pushFragments(historyFragment, false,
                            false, null);
                    return;
                }else if(position==1){
                    pushFragments(scanFragment, false,
                            false, null);
                    return;
                }else if(position==2){
                    pushFragments(findmeFragment, false,
                            false, null);
                    return;
                }else{
                    return;
                }

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup();

        if (savedInstanceState != null) {
            mCurrentTab = savedInstanceState.getString(M_CURRENT_TAB);
            initializeTabs();
            mTabHost.setCurrentTabByTag(mCurrentTab);
            /*
            when resume state it's important to set listener after initializeTabs
            */
            mTabHost.setOnTabChangedListener(listener);
        } else {
            mTabHost.setOnTabChangedListener(listener);
            initializeTabs();
        }
    }

    private void addDrawerItems() {

        String[] osArray = { "History", "Scan", "Where am I" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }



    private View createTabView( final String text) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        //ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        //imageView.setImageDrawable(getResources().getDrawable(id));
        TextView textView = (TextView) view.findViewById(R.id.tab_text);
        textView.setText(text);
        return view;
    }

    private void initializeTabs() {
        TabHost.TabSpec spec;

        spec = mTabHost.newTabSpec(routeTab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView("Route"));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(locationTab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView("Location"));
        mTabHost.addTab(spec);


        spec = mTabHost.newTabSpec(nearbyTab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView("Nearby"));
        mTabHost.addTab(spec);

    }

    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {

            mCurrentTab = tabId;

            if (tabId.equals(routeTab)) {
                pushFragments(routeFragmentTab, false,
                        false, null);
            } else if (tabId.equals(locationTab)) {
                pushFragments(locationFragmentTab, false,
                        false, null);
            } else if (tabId.equals(nearbyTab)) {
                pushFragments(nearbyFragmentTab, false,
                        false, null);
            }

        }
    };

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
        ft.replace(R.id.realtabcontent, fragment, tag);

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
                return true;
            case R.id.action_scan:
                pushFragments(scanFragment, false,
                        false, null);
                return true;
            case R.id.action_findme:
                pushFragments(findmeFragment, false,
                        false, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
