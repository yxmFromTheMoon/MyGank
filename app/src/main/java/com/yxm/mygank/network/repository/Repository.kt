package com.yxm.mygank.network.repository

import androidx.lifecycle.liveData
import com.yxm.mygank.model.bean.BaseModelBean
import com.yxm.mygank.model.bean.ContentBean
import com.yxm.mygank.network.coroutines.NetWork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/20 9:07
 * @description
 */
object Repository {
    fun searchArticleList(category: String, type: String, page: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val response = NetWork.searchArticleList(category, type, page)
            if (response.totalCounts > 0) {
                val list = response.data
                Result.success(list)
            }else{
                Result.failure(RuntimeException("列表为空"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}