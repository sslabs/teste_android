package dev.dextra.newsapp.feature.news

import androidx.lifecycle.MutableLiveData
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.api.repository.NewsRepository
import dev.dextra.newsapp.base.BaseViewModel

class NewsViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    val articles = MutableLiveData<ArticleWrapper>()
    lateinit var source: Source

    fun loadNews(page: Int = 1) {
        addDisposable(
            newsRepository.getEverything(source.id, page)
                .subscribe({ response ->
                    articles.postValue(ArticleWrapper(data = response.articles))
                }, {
                    articles.postValue(ArticleWrapper(error = it))
                })
        )
    }

    class ArticleWrapper(var data: List<Article>? = null, var error: Throwable? = null)
}
