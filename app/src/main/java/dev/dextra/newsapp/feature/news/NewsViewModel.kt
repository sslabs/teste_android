package dev.dextra.newsapp.feature.news

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.rxjava2.cachedIn
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.api.repository.NewsRepository
import dev.dextra.newsapp.base.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NewsViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

//    val articles = MutableLiveData<PagedList<Article>>()

    private lateinit var source: Source

    lateinit var flow: Flowable<PagingData<Article>>

//    init {
//        flow = newsRepository.getEverything(sources = source.id).cachedIn(viewModelScope)
//    }

    fun loadNews(source: Source) {
//        val flow = newsRepository.getEverything(sources = source.id).cachedIn(viewModelScope)
        this.source = source
//        flow = newsRepository.getEverything(sources = source.id).cachedIn(viewModelScope)
        flow = newsRepository.getEverything(sources = source.id)
//        addDisposable(flow.observeOn(AndroidSchedulers.mainThread()).subscribe())
//        flow = newsRepository.getEverything(sources = source.id).cachedIn(viewModelScope)


//        addDisposable(
//            newsRepository.getEverything(source.id)
//                .subscribe { response ->
//                    articles.postValue(response.articles)
//                }
//        )
    }


}
