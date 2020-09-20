package com.yxm.mygank.controller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yxm.mygank.common.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by yxm on 2020/4/4
 *
 * @function viewpager2 adapter
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<BaseFragment> fragments;

    public ViewPagerAdapter(FragmentActivity context, ArrayList<BaseFragment> fragments){
        super(context);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }


    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
