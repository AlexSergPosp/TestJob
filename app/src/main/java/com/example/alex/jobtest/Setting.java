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
        if (MainActivity.prefRand){
            Random random = new Random();
            pageNumber = random.nextInt(MainActivity.pageCount);
        }
        else pageNumber = argumentPage;
        return pageNumber;
    }

    public void addFavorite(ArrayList<Image>arrayList, String comment,HashMap<Integer,Image> hashMap, int index) {
        if (!arrayList.contains(hashMap.get(index-1))) {
            Image image = hashMap.get(index - 1);
            image.setCOM(comment);
            arrayList.add(image);
        }
    }


    public String showFavorite(ArrayList<Image> arrayList) {
        Log.d(MainActivity.TAG, "showFavorite");
        String url = "http://content.govdelivery.com/attachments/fancy_images/USED/2012/11/134067/143625/my-favorite-no_original_crop.jpg";
        Random random = new Random();
        if (arrayList.size() != 0) {
            int i = random.nextInt(arrayList.size());
            url = arrayList.get(i).getURL();
        }
        return url;
    }

    }

