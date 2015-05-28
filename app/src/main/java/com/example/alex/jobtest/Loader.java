package com.example.alex.jobtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Alex on 28.05.2015.
 */
public class Loader extends AsyncTask<String, Void, Bitmap> {


    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        for (String url : params){
            bitmap = downloadImage(url);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap!=null){
            GalleryAdapter.imageView.setImageBitmap(bitmap);
            GalleryAdapter.progressBar.setVisibility(View.INVISIBLE);
        } else GalleryAdapter.progressBar.setVisibility(View.VISIBLE);
    }

    private Bitmap downloadImage(String url){
        Log.d(MainActivity.TAG, "downloadImage()");

        Bitmap bitmap = null;
        InputStream inputStream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;
        try {
            while(bitmap == null){
                inputStream = getHttpconetcion(url);
                bitmap = BitmapFactory.decodeStream(inputStream,null,bmOptions);
                inputStream.close();
            }
        } catch (IOException ie){
            ie.printStackTrace();
        }
        return bitmap;

    }
    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpconetcion(String urlString) throws IOException{
        InputStream inputStream = null;
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return inputStream;
    }
}
