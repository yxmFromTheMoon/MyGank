package com.yxm.mygank.controller.activity

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yxm.mygank.R
import com.yxm.mygank.common.base.BaseActivity
import com.yxm.mygank.model.viewmodel.ArticleListViewModel
import com.yxm.mygank.network.constants.Constants
import kotlinx.android.synthetic.main.activity_test.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/20 9:35
 * @description
 */
class TestActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ArticleListViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {

        test_tv.setOnClickListener {
            viewModel.getArticleList(Constants.GANHUO, "Android")
        }
        viewModel.articleLiveData.observe(this, {
            val list = it.getOrNull()
            Log.d("test_data", "${viewModel.mPage}")
            list?.let {
                viewModel.articleList.addAll(it)
            }
        })
    }

    override fun initListener() {

    }

    override fun initData() {

    }
}