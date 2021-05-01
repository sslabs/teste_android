package dev.dextra.newsapp.feature.news

import androidx.lifecycle.MutableLiveData
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.api.repository.NewsRepository
import dev.dextra.newsapp.base.BaseViewModel

class NewsViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    val articles = MutableLiveData<List<Article>>()

    fun loadNews(source: Source) {
        addDisposable(
            newsRepository.getEverything(source.id)
                .subscribe { response ->
                    articles.postValue(response.articles)
                }
        )
    }
}
