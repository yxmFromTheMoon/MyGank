package com.yxm.mygank;

import android.view.KeyEvent;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.yxm.mygank.common.base.BaseActivity;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.HideBottomViewEvent;
import com.yxm.mygank.common.event.RepeatTabEvent;
import com.yxm.mygank.controller.activity.WebViewActivity;
import com.yxm.mygank.controller.adapter.ViewPagerAdapter;
import com.yxm.mygank.controller.fragments.ArticleFragment;
import com.yxm.mygank.controller.fragments.GanHuoFragment;
import com.yxm.mygank.controller.fragments.GirlFragment;
import com.yxm.mygank.imageloader.ImageLoaderManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
    private DrawerLayout mDrawerLayout;
    private TextView mGitHub;
    private TextView mBlog;
    private TextView appVersion;
    private TextView moreInfo;
    private TextView email;
    private TextView clearCache;


    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private long currentTime = 0;


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
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mGitHub = findViewById(R.id.github);
        mBlog = findViewById(R.id.blog);
        moreInfo = findViewById(R.id.more_info);
        appVersion = findViewById(R.id.version);
        email = findViewById(R.id.email);
        clearCache = findViewById(R.id.clear_cache);

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
                if (index == 0) {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                } else {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
                mViewPager.setCurrentItem(index);
            }

            @Override
            public void onRepeat(int index) {
                RepeatTabEvent event = new RepeatTabEvent(index);
                EventBus.getDefault().post(event);
            }
        });

        mGitHub.setOnClickListener(view ->
                WebViewActivity.start(mContext, "https://github.com/yxmFromTheMoon/MyGank", "MyGank")
        );

        mBlog.setOnClickListener(view ->
                WebViewActivity.start(mContext, "https://blog.csdn.net/qq_37029648", "我的博客")
        );

        email.setOnClickListener(view ->
                showToast("去GitHub看看吧")
        );

        moreInfo.setOnClickListener(view ->
                showToast("去博客看看吧")
        );
        clearCache.setOnClickListener(view -> {
            ImageLoaderManager.getInstance().clearAllCache(mContext);
            showToast("清除缓存成功");
        });
    }

    @Override
    public void initData() {
        appVersion.setText("当前版本：" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideBottomView(HideBottomViewEvent event) {
        if (event.isHide()) {
            mController.hideBottomLayout();
        } else {
            mController.showBottomLayout();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                long lastTime = System.currentTimeMillis();
                if (lastTime - currentTime > 2000) {
                    showToast("再按一次退出程序");
                    currentTime = lastTime;
                } else {
                    super.onKeyDown(keyCode, event);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
