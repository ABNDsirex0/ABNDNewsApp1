package cloud.krzysztofkin.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ArticlesAdapter extends ArrayAdapter<Article> {
    public ArticlesAdapter(@NonNull Context context, @NonNull ArrayList<Article> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.article, parent, false);
        }

        //find TextViews
        TextView titleTextView = listItemView.findViewById(R.id.title_textView);
        TextView sectionTextView = listItemView.findViewById(R.id.section_textView);
        TextView dateTextView = listItemView.findViewById(R.id.date_textView);
        TextView authorTextView = listItemView.findViewById(R.id.author_textView);

        //get the artice at given position
        Article currentArticle = getItem(position);

        //set text to text views
        titleTextView.setText(currentArticle.getWebTitle());
        sectionTextView.setText(currentArticle.getSectionName());
        dateTextView.setText(currentArticle.getWebPublicationDate());
        authorTextView.setText(currentArticle.getAuthorName());
        
        return listItemView;
    }
}
