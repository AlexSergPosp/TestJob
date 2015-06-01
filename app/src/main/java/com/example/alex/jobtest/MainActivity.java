package com.example.alex.jobtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "mylog";
    static final int pageCount = 10;
    private Handler handler;    // for realize
    private Runnable runnable;  //autosliding
    private Timer timer;        //
    private int position;       // page index
    private static ViewPager viewPager;
    private SharedPreferences sp;
    public static boolean prefAuto;         // from preference for autoSliding
    public static boolean prefFavorite;     //                     favorite
    public static boolean prefRand;         //                     Random
    public static String prefInterval;      //                     autoSliding interval
    public static String prefAnim;          //                     Animation
    static ArrayList<Image> arrayListFavorite = new ArrayList<>(); // contain favorite Image
    private static String comment; // comment for favorite Image
    static Setting setting = new Setting(); // contain setting method

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        getPref();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createToolbar();
        viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(getListenerAdapter());
        sp = PreferenceManager.getDefaultSharedPreferences(this);

    }

    private void createToolbar() {
        Log.d(TAG,"createToolbar()");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_plus_white_18dp));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteOnClick(v);
                }
            });
        }
    }

    private ViewPager.OnPageChangeListener getListenerAdapter() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d(TAG, "onPageSelected, position = " + i);

            }

            @Override
            public void onPageSelected(int position) {
               // Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d(TAG, "onPageScrollStateChanged = " + i);

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG,"onCreateOptionsMenu(Menu menu)");
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, Preference.class));
        return super.onCreateOptionsMenu(menu);
    }
        // add to favorite hash map.
        public void favoriteOnClick(View view) {
        Log.d(TAG,"favoriteOnClick(View view)");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setMessage(getString(R.string.alertMessage));
        alertDialog.setTitle(getString(R.string.alertTitle));
        alertDialog.setView(editText);
        alertDialog.setPositiveButton(getString(R.string.alertPosButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comment = editText.getText().toString();
                setting.addFavorite(arrayListFavorite, comment, GalleryAdapter.imageHashMap, viewPager.getCurrentItem());
            }
        });
        alertDialog.show();

    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Log.d(TAG,"MainActivity MyFragmentPagerAdapter getItem "+position);
            return GalleryAdapter.newInstance(position);
        }

        @Override
        public int getCount() {
            //Log.d(TAG,"MainActivity MyFragmentPagerAdapter getCount");
            return pageCount;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (handler!= null) {
            handler.removeCallbacks(runnable);
        }
        if (timer != null){
            timer.cancel();
        }

    }

     // get and use them
    public void getPref(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        prefAuto = preferences.getBoolean("auto", false);
        prefFavorite = preferences.getBoolean("favorite", false);
        prefRand = preferences.getBoolean("random", false);
        prefInterval = preferences.getString("interval", "1");
        prefAnim = preferences.getString("list","1");
        Log.d(MainActivity.TAG, "get pref(), prefAuto - " + prefAuto
                + " prefFavorite - " + prefFavorite
                + " prefRand - " + prefRand
                + " prefInterval - " + prefInterval
                + " prefAnim - " + prefAnim);
        autoSliding();
        setting.animation(Integer.parseInt(prefAnim), viewPager);


    }
   private void autoSliding(){
        if (prefAuto) {
            Log.d(TAG, "autoSliding()");
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (position == pageCount) {
                        position = 0;
                    } else {
                        position++;
                    }
                    Log.d(TAG, "run start");
                    viewPager.setCurrentItem(position, true);
                }

            };
            int sec = Integer.parseInt(prefInterval) * 1000;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, 1000, sec);

        }
    }

}
