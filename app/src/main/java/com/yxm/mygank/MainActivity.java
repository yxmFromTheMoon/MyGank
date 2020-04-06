package com.yxm.mygank;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.yxm.mygank.common.base.BaseActivity;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.HideBottomViewEvent;
import com.yxm.mygank.common.event.RepeatTabEvent;
import com.yxm.mygank.controller.adapter.ViewPagerAdapter;
import com.yxm.mygank.controller.fragments.ArticleFragment;
import com.yxm.mygank.controller.fragments.GanHuoFragment;
import com.yxm.mygank.controller.fragments.GirlFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.viewpager2.widget.ViewPager2;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * MainActivity
 * 三个模块 干货、文章、妹纸
 */

public class MainActivity extends BaseActivity {

    private PageNavigationView mNavigationView;
    private NavigationController mController;
    private ViewPager2 mViewPager;
    private ArrayList<BaseFragment> fragments = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .titleBar(R.id.main_vp2)
                .statusBarDarkFont(true)
                .init();
        EventBus.getDefault().register(this);
        mNavigationView = findViewById(R.id.navigation_view);
        mViewPager = findViewById(R.id.main_vp2);

        mController = mNavigationView.material()
                .addItem(R.mipmap.home_ganhuo_unselected,
                        R.mipmap.home_ganhuo_selected, "干货")
                .addItem(R.mipmap.home_article_unselected,
                        R.mipmap.home_article_selected, "文章")
                .addItem(R.mipmap.home_girl_unselected,
                        R.mipmap.home_girl_selected, "妹纸")
                .build();

        fragments.add(GanHuoFragment.newInstance());
        fragments.add(ArticleFragment.newInstance());
        fragments.add(GirlFragment.newInstance());
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragments);
        mViewPager.setUserInputEnabled(false);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        mController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                mViewPager.setCurrentItem(index);
            }

            @Override
            public void onRepeat(int index) {
                RepeatTabEvent event = new RepeatTabEvent(index);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideBottomView(HideBottomViewEvent event){
        if(event.isHide()){
            mController.hideBottomLayout();
        }else {
            mController.showBottomLayout();
        }
    }
}
