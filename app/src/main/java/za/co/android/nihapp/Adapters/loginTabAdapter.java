package za.co.android.nihapp.Adapters;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import za.co.android.nihapp.Fragments.LoginFragment;
import za.co.android.nihapp.Fragments.SignupFragment;
import za.co.android.nihapp.R;

/**
 * Created by Masi on 14-Jun-18.
 */

public class loginTabAdapter extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;

    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.tablayout,null);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return view;
    }

    //----------------------------------------------------------------------------------------------
    class MyAdapter extends FragmentPagerAdapter{

        //------------------------------------------------------------------------------------------
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        //------------------------------------------------------------------------------------------
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new LoginFragment();
                case 1 : return new SignupFragment();
            }
            return null;
        }

        //------------------------------------------------------------------------------------------
        @Override
        public int getCount() {

            return int_items;

        }

        //------------------------------------------------------------------------------------------
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Sign In"; // login
                case 1 :
                    return "Sign Up"; // register
            }
            return null;
        }
        //------------------------------------------------------------------------------------------
    }
}
