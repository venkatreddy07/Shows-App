package com.task.moviesapp.ui.home.showFragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.moviesapp.R;
import com.task.moviesapp.network.response.search.SearchList;

import java.util.List;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

    private Context context;
    private List<SearchList> searchList;
    private ShowDetails listener;

    ShowsAdapter(Context context, List<SearchList> searchList, ShowDetails listener) {
        this.context = context;
        this.searchList = searchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_shell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchList search = searchList.get(position);
        if (search != null) {

            if (!TextUtils.isEmpty(search.getPoster())) {
                if (search.getPoster().equalsIgnoreCase(context.getString(R.string.na_img))) {
                    holder.bgImage.setVisibility(View.GONE);
                    holder.noImage.setVisibility(View.VISIBLE);
                } else {
                    holder.bgImage.setVisibility(View.VISIBLE);
                    holder.noImage.setVisibility(View.GONE);

                    Glide.with(context)
                            .load(search.getPoster())
                            .into(holder.bgImage);
                }
            }

            if (!TextUtils.isEmpty(search.getTitle())) {
                holder.title.setText(search.getTitle());
            }

            if (!TextUtils.isEmpty(search.getYear())) {
                holder.year.setText(search.getYear());
            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.showId(search.getImdbID());
                    }
                }
            });

            if (search.isBookMarked()) {
                holder.bookmark.setImageDrawable(context.getDrawable(R.drawable.bookmarked));
            } else {
                holder.bookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark));
            }

            holder.bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.bookMark(position);
                    }
                }
            });

            //check for last item
            if (position >= getItemCount() - 1) {
                if (listener != null) {
                    listener.loadShows();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView bgImage,bookmark;
        private TextView title, year, noImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            bgImage = itemView.findViewById(R.id.bg_img);
            bookmark = itemView.findViewById(R.id.bookmark);
            noImage = itemView.findViewById(R.id.no_image);

            title = itemView.findViewById(R.id.title);
            year = itemView.findViewById(R.id.year);
        }
    }

    public interface ShowDetails {
        void showId(String id);

        void loadShows();

        void bookMark(int position);
    }
}