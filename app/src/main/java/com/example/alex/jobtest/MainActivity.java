package com.example.alex.jobtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity {
    public static String TAG = "mylog";
    static final int pageCount = 10;
    private Handler handler;
    private Runnable runnable;
    Timer timer;
    int position;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    SharedPreferences sp;
    public static boolean prefAuto;
    public static boolean prefFavorite;
    public static boolean prefRand;
    public static String prefInterval;
    public static String prefAnim;
    static ArrayList<Image> arrayListFavorite = new ArrayList<>();
    String comment;
    static int i = 0;
    static Setting setting = new Setting();


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
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
       // viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(getListenerAdapter());
        sp = PreferenceManager.getDefaultSharedPreferences(this);

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
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, Preference.class));
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
        // add to avorite hash map.
        public void favoriteOnClick(View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setMessage("Введите коментарий");
        alertDialog.setTitle("Комментарий");
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
            Log.d(TAG, "if start");
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
