package com.example.alex.jobtest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class GalleryAdapter extends Fragment {

     static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
     static volatile HashMap<Integer,Image> imageHashMap;
     private int pageNumber;
     static String textForTextView;
     public static ImageView imageView;
     public static String URL1;
     private TextView textViewForcomment;
            // Constructor
    static GalleryAdapter newInstance(int page){
            Log.d(MainActivity.TAG,"GalleryAdapter newInstance");
            GalleryAdapter galleryAdapter = new GalleryAdapter();
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
            galleryAdapter.setArguments(arguments);
            return  galleryAdapter;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, "GalleryAdapter onCreate");
        super.onCreate(savedInstanceState);
        imageLoading();
    }

    private void imageLoading() {
        Log.d(MainActivity.TAG,"imageLoading()");
        // use Universal Image Loader;
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, "GalleryAdapter onCreateView");
        View v = inflater.inflate(R.layout.gallery,null);
        Setting setting = new Setting();
        textViewForcomment = (TextView) v.findViewById(R.id.textView);
        pageNumber = setting.preferenceRandom(pageNumber,getArguments().getInt(ARGUMENT_PAGE_NUMBER));
        Log.d(MainActivity.TAG, "pageNumber" + String.valueOf(pageNumber));

        if (MainActivity.prefFavorite) {
            Image image = MainActivity.setting.getFavorite(MainActivity.arrayListFavorite);
            if (image!=null) {
                URL1 = image.getURL();
                textForTextView = image.getCOM();
            }
        }
        else {
            try {
                imageHashMap = getJsonData();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            textForTextView = imageHashMap.get(pageNumber).getID();
            URL1 = imageHashMap.get(pageNumber).getURL();
        }
            imageView = (ImageView) v.findViewById(R.id.imageView);
            ImageLoader.getInstance().displayImage(URL1, imageView);
        if(textForTextView != null) {
            textViewForcomment.setVisibility(View.VISIBLE);
            textViewForcomment.setText(textForTextView);
        }
        else textViewForcomment.setVisibility(View.INVISIBLE);
        return v;
    }
        private String loadJsonFromRes() throws IOException {
        Log.d(MainActivity.TAG,"loadFromRes()");
        InputStream is = getResources().openRawResource(R.raw.json);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
    }
        private HashMap<Integer,Image> getJsonData() throws JSONException, IOException {
        Log.d(MainActivity.TAG,"getJsonData()");
        JSONObject jsonObject = new JSONObject(loadJsonFromRes());
        JSONArray array = jsonObject.getJSONArray("image");
        HashMap<Integer,Image> imageList = new HashMap<>();
        for (int i = 0; i <array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    int num = Integer.parseInt(object.getString("number"));
                    Image image = new Image();
                    image.setID(object.getString("id"));
                    image.setNUMBER(object.getString("number"));
                    image.setURL(object.getString("url"));
                    imageList.put(num-1, image);
        }
        return imageList;
    }
}