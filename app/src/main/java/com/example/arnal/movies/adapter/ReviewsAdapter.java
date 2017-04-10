package com.example.arnal.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arnal.movies.R;
import com.example.arnal.movies.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnal on 3/31/17.
 */


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private ArrayList<Review> reviewList;
    private Context context;


    public ReviewsAdapter(Context context, ArrayList<Review> reviewList) {
        this.context = context;
        this.reviewList = new ArrayList<>();
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList.clear();
        this.reviewList.addAll(reviewList);
        notifyDataSetChanged();
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {

        //RecyclerView recyclerView = holder.recyclerView;

        Review review = reviewList.get(position);

        final TextView author = holder.author;
        final TextView contentView = holder.contentReview;
        author.setText(review.getAuthor());
        contentView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.isEmpty() ? 0 : reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView author;
        public TextView contentReview;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView)itemView.findViewById(R.id.author_review);
            contentReview = (TextView)itemView.findViewById(R.id.content_review);
        }
    }
}
