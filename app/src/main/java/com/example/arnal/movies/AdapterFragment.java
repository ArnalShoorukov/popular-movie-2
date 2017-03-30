package com.example.arnal.movies;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdapterFragment extends FragmentStatePagerAdapter {
   /** Context of the app */
    private Context mContext;
    public AdapterFragment(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new PopularFragment();
        } else if (position == 1){
            return new RatedFragment();
        }else
            return new FavoriteFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return mContext.getString(R.string.category_popular);
        }else if(position == 1){
            return mContext.getString(R.string.category_rated);
        }else {
            return mContext.getString(R.string.category_favorite);
        }
    }
}
