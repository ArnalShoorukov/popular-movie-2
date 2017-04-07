package com.example.arnal.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arnal.movies.R;
import com.example.arnal.movies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnal on 3/31/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private static final String LOG_TAG = TrailerAdapter.class.getName();

    private ArrayList<Trailer> trailerList;
    private Context context;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailerList) {
        this.context = context;
        this.trailerList = new ArrayList<>();
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList.clear();
        this.trailerList.addAll(trailerList);
        notifyDataSetChanged();
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {

        final Trailer trailer = trailerList.get(position);

        final TextView title = holder.title;
        title.setText(trailer.getName());


        final ImageView imageView = holder.poster;

      Picasso.with(context)
                .load("http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg ")
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String videoPath = "https://www.youtube.com/watch?v=" + trailer.getKey();
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath));
               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerList.isEmpty() ? 0 : trailerList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.trailerText);
            poster = (ImageView) itemView.findViewById(R.id.trailerImage);
        }
    }


}
