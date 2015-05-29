package com.example.alex.jobtest;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Alex on 29.05.2015.
 */
public class JsonParsing extends Activity{
   // возвращает json файл хран€шийс€ в assets в  виде String переменной
    public String loadJsonFromAssets(){
        String json = null;
        try {
            InputStream inputStream = getAssets().open("imagejson.json");
            int size  = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
            return json;
    }
    // возвпащет ArrayList с вложенными данными по всем объектам json файла
         public ArrayList<Image> getJsonData() throws JSONException {
             JSONObject jsonObject = new JSONObject(loadJsonFromAssets());
             JSONArray array = jsonObject.getJSONArray("image");
             ArrayList<Image> imageList = new ArrayList<>();

             for (int i = 0; i <array.length() ; i++) {
                 JSONObject object = array.getJSONObject(i);
                 Image image = new Image();
                 image.setID(object.getString("name"));
                 image.setNUMBER(object.getString("number"));
                 image.setURL(object.getString("url"));
                 imageList.add(image);
                 return imageList;
             }
            }
}
