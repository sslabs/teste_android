package dev.dextra.newsapp.feature.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import dev.dextra.newsapp.R
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.base.BaseListActivity
import dev.dextra.newsapp.components.LoadPageScrollListener
import dev.dextra.newsapp.feature.news.adapter.ArticleListAdapter
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_sources.*
import org.koin.android.ext.android.inject

const val NEWS_ACTIVITY_SOURCE = "NEWS_ACTIVITY_SOURCE"

class NewsActivity : BaseListActivity(), ArticleListAdapter.ArticleClickListener,
    LoadPageScrollListener.LoadPageScrollLoadMoreListener {

    override val emptyStateTitle: Int = R.string.empty_state_title_news
    override val emptyStateSubTitle: Int = R.string.empty_state_subtitle_news
    override val errorStateTitle: Int = R.string.error_state_title_news
    override val errorStateSubTitle: Int = R.string.error_state_subtitle_news
    override val mainList: View
        get() = news_list

    private val newsViewModel: NewsViewModel by inject()
    private val newsAdapter = ArticleListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news)

        (intent?.extras?.getSerializable(NEWS_ACTIVITY_SOURCE) as Source).let { source ->
            title = source.name
            newsViewModel.source = source
            loadNews()
        }

        news_list.apply {
            setHasFixedSize(true)
            addOnScrollListener(LoadPageScrollListener(this@NewsActivity))
            adapter = newsAdapter
        }

        newsViewModel.articles.observe(this, Observer {
            showData(it)
            hideLoading()
        })

        newsViewModel.networkState.observe(this, networkStateObserver)

        super.onCreate(savedInstanceState)

    }

    override fun setupLandscape() {
        // Just ignore
    }

    override fun setupPortrait() {
        // Just ignore
    }

    override fun executeRetry() {
        loadNews()
    }

    override fun onClick(article: Article) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(article.url)
        startActivity(i)
    }

    override fun onLoadMore(currentPage: Int, totalItemCount: Int, recyclerView: RecyclerView) {
        newsViewModel.loadNews(page = currentPage)
    }

    private fun loadNews() {
        newsViewModel.loadNews()
    }

    private fun showData(articles: List<Article>) = newsAdapter.add(articles)

}
