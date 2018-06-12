package cloud.krzysztofkin.newsapp;

import android.util.Log;

import java.util.ArrayList;

public class DataUtils {
    public static ArrayList<Article> getData(String queryURL){
        //TODO wywal try catch i sleep po implementacji pobierania
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v("DataUtils",queryURL);
        //TODO tu prawdziwy return po implementacji pobierania
        ArrayList<Article> fakeData = new ArrayList<Article>();
        for(int i =0;i<10;i++) {
            fakeData.add(new Article("webtitle "+i, "section name "+i, "author"+i, "date"+i, "http://www.onet.pl/"));
        }
        return fakeData;
    }
}
