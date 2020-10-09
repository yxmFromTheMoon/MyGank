package com.yxm.mygank.controller.fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.yxm.mygank.R
import com.yxm.mygank.common.base.BaseFragment
import com.yxm.mygank.common.event.HideBottomViewEvent
import com.yxm.mygank.common.event.RepeatTabEvent
import com.yxm.mygank.controller.activity.BigPictureActivity
import com.yxm.mygank.controller.adapter.PictureAdapter
import com.yxm.mygank.model.ContentModel
import com.yxm.mygank.model.ContentModel.OnGetContentListener
import com.yxm.mygank.model.bean.ContentBean
import com.yxm.mygank.network.constants.Constants
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by yxm on 2020/4/4
 *
 * @function 妹纸fragment
 */
class GirlFragment : BaseFragment(), OnGetContentListener {
    private val mModel = ContentModel(this)
    private var mAdapter: PictureAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mRefreshLayout: SmartRefreshLayout? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_girl
    }

    override fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.content_girl_rv)
        mRefreshLayout = view.findViewById(R.id.refreshLayout)
        mAdapter = PictureAdapter(R.layout.item_picture)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mAdapter
    }

    override fun initListener() {
        mAdapter!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            val item = adapter.getItem(position) as ContentBean?
            if (item != null) {
                if (view.id == R.id.picture) {
                    BigPictureActivity.start(mContext, item.url)
                }
            }
        }
        mRefreshLayout!!.setOnRefreshListener { lazyLoad() }
        mRefreshLayout!!.setOnLoadMoreListener { mModel.loadMore(Constants.GIRL, Constants.GIRL) }
        mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val event = HideBottomViewEvent(true)
                //上滑
                if (dy > 0) {
                    event.isHide = true
                    EventBus.getDefault().post(event)
                } else if (dy < 0) {
                    event.isHide = false
                    EventBus.getDefault().post(event)
                } else {
                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        })
    }

    override fun lazyLoad() {
        showLoading()
        mModel.getContent(Constants.GIRL, Constants.GIRL)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun tabReClickEvent(event: RepeatTabEvent) {
        if (event.index == 2) {
            mRecyclerView!!.scrollToPosition(0)
            mRefreshLayout!!.autoRefresh()
        }
    }

    override fun onGetContentSuccess(data: List<ContentBean>) {
        disLoading()
        mRefreshLayout!!.finishRefresh()
        mAdapter!!.setNewData(data)
    }

    override fun onGetContentFailure(content: String) {
        disLoading()
        showToast(content + "获取图片失败")
        mRefreshLayout!!.finishRefresh()
        mRefreshLayout!!.finishLoadMore()
    }

    override fun onLoadMore(data: List<ContentBean>) {
        mRefreshLayout!!.finishLoadMore()
        mAdapter!!.addData(data)
    }

    companion object {
        @JvmStatic
        fun newInstance(): GirlFragment {
            return GirlFragment()
        }
    }
}