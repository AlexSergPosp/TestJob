package com.example.alex.jobtest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class GalleryAdapter extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int pageNumber;
     public static ImageView imageView;
     public static TextView textView;
     public static ProgressBar progressBar;

    public static String[] URL1 = {"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRhG5rMM3UX8z4JG0PE9JnTPa7iaXOiB6pLE_soQNbwlryt8mdy5A",
            "http://rewalls.com/large/201203/63548.jpg"
            ,"http://www.nastol.com.ua/pic/201208/2560x1440/nastol.com.ua-29508.jpg"
            ,"http://kdlt.ru/wp-content/uploads/2012/09/127.jpg"
            ,"http://www.wallsgeneration.ru/images/original/lamba-[1920x1200]-[2385235].jpg"
            ,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR81wd_ZmOYeFtHCPPQBheBILa7pOWU0VZl3u2gdz7WsVp0Un-w"
            ,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR0TD8VTNwUGR5GpBwqGaPsYeWg4FMibzaO4vrhACKsWYDlgCKhfw"
            ,"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR7kc-mLhgU28WVKduFfAjeVZPmUt1ybUjejT-hv_jkOH0bH3Ld"
            ,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSzeU-InYeIZbDjb8bxz5NzsR3QCcPtHhd9f_jaT2gYImoyYfFA"
            ,"http://f9.ifotki.info/org/71a2543234450c4cd8b01c150cccfcc44e24d797267247.jpg"};
    Loader task = new Loader();

    static GalleryAdapter newInstance(int page){
            Log.d(MainActivity.TAG,"GalleryAdapter newInstance");
            GalleryAdapter galleryAdapter = new GalleryAdapter();
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PAGE_NUMBER,page);
            galleryAdapter.setArguments(arguments);
            return  galleryAdapter;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG,"GalleryAdapter onCreate");
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        // do rand or raw
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.gallery,null);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            textView = (TextView) v.findViewById(R.id.textView);
            task.execute(URL1[pageNumber]);
        return v;
    }
}