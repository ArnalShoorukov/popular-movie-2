package com.example.arnal.movies;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.detail_frag_container);
        setContentView(frameLayout, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

       // DetailFragment detailFragment = new DetailFragment();
        DetailsFragment detailFragment = new DetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.detail_frag_container, detailFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            this.startActivity(new Intent(DetailActivity.this, MainActivity.class));

    }
}
