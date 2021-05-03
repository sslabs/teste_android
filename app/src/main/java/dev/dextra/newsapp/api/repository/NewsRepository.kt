package dev.dextra.newsapp.api.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.rxjava2.flowable
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.ArticlesResponse
import dev.dextra.newsapp.api.model.SourceResponse
import dev.dextra.newsapp.base.repository.EndpointService
import dev.dextra.newsapp.base.repository.Repository
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsRepository(private val endpointService: EndpointService)
    : Repository<NewsEndpoint>(endpointService) {

    fun getSources(country: String?, category: String?): Single<SourceResponse> {
        return schedule(getEndpoint().getSources(country, category))
    }

//    fun getEverything(sources: String?, page: Int = 1, pageSize: Int = 20): Single<ArticlesResponse> {
//        val pager = Pager(PagingConfig(pageSize = 20)) { PagingArticleSource(getEndpoint(), sources) }.flow
    fun getEverything(sources: String?, page: Int = 1, pageSize: Int = 20): Flowable<PagingData<Article>> {

//        return Pager(PagingConfig(pageSize = 20)) { pagingArticleSource }.flow
//        return schedule(PagingArticleSource(endpointService, sources).loadSingle())
//        return schedule(getEndpoint().getEverything(sources, page, pageSize))

        return Pager(PagingConfig(pageSize = 20)) {
            PagingArticleSource(getEndpoint(), sources)
        }.flowable

    }
}
