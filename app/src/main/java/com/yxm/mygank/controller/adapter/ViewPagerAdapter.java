package com.yxm.mygank.controller.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.controller.fragments.ArticleFragment;
import com.yxm.mygank.controller.fragments.GanHuoFragment;
import com.yxm.mygank.controller.fragments.GirlFragment;
import com.yxm.mygank.network.constants.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Created by yxm on 2020/4/4
 *
 * @function viewpager adapter
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
