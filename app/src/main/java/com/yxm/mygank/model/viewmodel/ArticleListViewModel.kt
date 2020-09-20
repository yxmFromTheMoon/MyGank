package com.yxm.mygank.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yxm.mygank.model.bean.ContentBean
import com.yxm.mygank.network.repository.Repository

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/20 8:47
 * @description
 */
class ArticleListViewModel : ViewModel() {
    private val articleListLiveData = MutableLiveData<Map<String, Any>>()
    var mPage = 1
    val articleList = ArrayList<ContentBean>()

    val articleLiveData = Transformations.switchMap(articleListLiveData) { paramsMap ->
        Repository.searchArticleList(paramsMap["category"] as String,
                paramsMap["type"] as String, paramsMap["page"] as Int)
    }

    fun getArticleList(category: String, type: String) {
        val paramsMap = HashMap<String, Any>()
        paramsMap["category"] = category
        paramsMap["type"] = type
        paramsMap["page"] = mPage
        articleListLiveData.value = paramsMap
    }
}