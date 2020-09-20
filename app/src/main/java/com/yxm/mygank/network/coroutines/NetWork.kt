package com.yxm.mygank.network.coroutines

import com.yxm.mygank.model.bean.BaseModelBean
import com.yxm.mygank.model.bean.ContentBean
import com.yxm.mygank.network.constants.Constants
import com.yxm.mygank.network.core.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/20 8:54
 * @description
 */
object NetWork {

    suspend fun searchArticleList(category: String, type: String, page: Int): BaseModelBean<MutableList<ContentBean>> =
            RetrofitManager.Api().getContent(category, type, page, Constants.PAGE_COUNT).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        it.resume(body)
                    } else {
                        it.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}