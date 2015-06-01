package com.example.alex.jobtest;

import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Alex on 31.05.2015.
 */
public class Setting {
    public void animation(int i, ViewPager viewPager) {
        Log.d(MainActivity.TAG,"animation()");
        switch (i) {
            case 1:
                viewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case 2:
                viewPager.setPageTransformer(true, new ZoomOutPageTransforme());
                break;
            case 3:
                viewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
        }
    }
    public int preferenceRandom(int pageNumber,int argumentPage) {
        Log.d(MainActivity.TAG,"preferenceRandom()");
        if (MainActivity.prefRand){
            Random random = new Random();
            pageNumber = random.nextInt(MainActivity.pageCount);
        }
        else pageNumber = argumentPage;
        return pageNumber;
    }

    public void addFavorite(ArrayList<Image>arrayList, String comment,HashMap<Integer,Image> hashMap, int index) {
        Log.d(MainActivity.TAG,"addFavorite()");
            if (!arrayList.contains(hashMap.get(index))) {
                Image image = hashMap.get(index);
                if (image != null) {
                    image.setCOM(comment);
                    arrayList.add(image);
                            }
                        }
                    }

    public Image getFavorite(ArrayList<Image> arrayList) {
        Log.d(MainActivity.TAG, "getFavorite");
        Random random = new Random();
        Image image = null;
        if (arrayList.size() != 0) {
            int i = random.nextInt(arrayList.size());
            image = arrayList.get(i);
        }
        return image;
    }

}

