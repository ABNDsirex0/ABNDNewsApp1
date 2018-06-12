package cloud.krzysztofkin.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class ArticlesLoader extends AsyncTaskLoader<ArrayList<Article>> {
    public static final String LOG_TAG = ArticlesLoader.class.getName();
    String query;

    public ArticlesLoader(Context context, String query_url) {
        super(context);
        query = query_url;
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        return DataUtils.getData(query);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
