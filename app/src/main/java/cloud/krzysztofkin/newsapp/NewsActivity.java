package cloud.krzysztofkin.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>> {
    ArticlesAdapter articleListAdapter;
    private static final String QUERY_URL = "to jest query url";
    ProgressBar progressBar;
    TextView errorMessageView;
    ListView articleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //find elements of main activity
        progressBar = findViewById(R.id.progress_bar);
        errorMessageView = findViewById(R.id.error_message_view);
        articleListView = findViewById(R.id.article_list);

        //set visibility elements on main activity
        progressBar.setVisibility(View.VISIBLE);
        errorMessageView.setVisibility(View.GONE);

        //setup listview elements
        articleListAdapter = new ArticlesAdapter(NewsActivity.this, new ArrayList<Article>());
        articleListView.setAdapter(articleListAdapter);
        articleListView.setEmptyView(errorMessageView);

        //set connectivity manager and check network connection:
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            errorMessageView.setText(R.string.no_internet);
        }

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        return new ArticlesLoader(this, QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        progressBar.setVisibility(View.GONE);
        articleListAdapter.clear();
        if (articles != null && !articles.isEmpty()) {
            Log.v("main","lista pełna");
            articleListAdapter.addAll(articles);
        }
        errorMessageView.setText(R.string.no_data);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        articleListAdapter.clear();
    }
}
