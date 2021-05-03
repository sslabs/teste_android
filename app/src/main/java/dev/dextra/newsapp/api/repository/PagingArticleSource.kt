package dev.dextra.newsapp.api.repository

import androidx.annotation.MainThread
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.ArticlesResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val ARTICLE_PAGE_SIZE = 20

class PagingArticleSource(private val newsEndpoint: NewsEndpoint, private val sources: String? = null) : RxPagingSource<Int, Article>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
//        val nextPageNumber = params.key ?: 1
//        val response = newsEndpoint.getEverything(
//            sources = sources,
//            page = nextPageNumber,
//            pageSize = ARTICLE_PAGE_SIZE
//        )
//        return LoadResult.Page(
//            data = response.articles,
//            prevKey = null,
//            nextKey = nextPageNumber + 1
//        )
//    }
//
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
//        return if (state.anchorPosition == null) {
//            val anchorPage = state.closestPageToPosition(state.anchorPosition!!)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        } else null
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Article>> {
        val pageNumber = params.key ?: 1
        val response = newsEndpoint.getEverything(
            sources = sources,
            page = pageNumber,
            pageSize = ARTICLE_PAGE_SIZE
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        return response.map { toLoadResult(it, pageNumber + 1) }
    }
//
    private fun toLoadResult(articlesResponse: ArticlesResponse, nextPage: Int): LoadResult<Int, Article> {
        return LoadResult.Page(
            data = articlesResponse.articles,
            prevKey = null,
            nextKey = nextPage
        )
    }
}