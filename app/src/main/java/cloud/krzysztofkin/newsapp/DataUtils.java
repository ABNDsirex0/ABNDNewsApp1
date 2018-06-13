package cloud.krzysztofkin.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    private static final String LOG_TAG = DataUtils.class.getSimpleName();

    public static ArrayList<Article> getData(String queryString) {
        // Create URL object
        URL url = createUrl(queryString);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        //makeHttpDataRequest(url) can throw IOException
        try {
            jsonResponse = makeHttpDataRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        ArrayList<Article> articles = extractArticlesFromJson(jsonResponse);

        //return getFakeData(queryString);
        return articles;
    }

    private static ArrayList<Article> extractArticlesFromJson(String jsonResponse) {
        String webTitle;
        String longTitle;
        String sectionName;
        String authorName;
        String webPublicationDate;
        String webUrl;
        Article currentArticle;

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<Article> articles = new ArrayList<>();
        try {
            JSONObject baseRequest = new JSONObject(jsonResponse);
            baseRequest = baseRequest.getJSONObject("response");
            JSONArray jsonArticles = baseRequest.getJSONArray("results");
            for (int i = 0; i < jsonArticles.length(); i++) {
                JSONObject jsonArticle = jsonArticles.getJSONObject(i);
                sectionName = jsonArticle.getString("sectionName");
                webPublicationDate = jsonArticle.getString("webPublicationDate");
                webUrl = jsonArticle.getString("webUrl");

                longTitle = jsonArticle.getString("webTitle");
                if (longTitle.contains("|")) {
                    String[] parts = longTitle.split("\\| ");
                    webTitle = parts[0];
                    authorName = parts[1];
                } else {
                    webTitle = longTitle;
                    authorName = "";
                }

                currentArticle = new Article(webTitle, sectionName, authorName, webPublicationDate, webUrl);
                articles.add(currentArticle);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        return articles;
    }

    private static String makeHttpDataRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException,
                // method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String queryString) {
        URL url = null;
        try {
            url = new URL(queryString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static ArrayList<Article> getFakeData(String queryURL) {
        //TODO wywal try catch i sleep po implementacji pobierania
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v("DataUtils", queryURL);
        //TODO tu prawdziwy return po implementacji pobierania
        ArrayList<Article> fakeData = new ArrayList<Article>();
        for (int i = 0; i < 10; i++) {
            fakeData.add(new Article("webtitle " + i, "section name " + i, "author" + i, "date" + i, "http://www.onet.pl/"));
        }
        return fakeData;
    }

}
