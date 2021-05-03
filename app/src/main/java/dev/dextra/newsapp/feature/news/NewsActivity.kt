package dev.dextra.newsapp.feature.news

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.dextra.newsapp.R
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.components.LoadPageScrollListener
import dev.dextra.newsapp.feature.news.adapter.ArticleListAdapter
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.android.ext.android.inject

const val NEWS_ACTIVITY_SOURCE = "NEWS_ACTIVITY_SOURCE"

class NewsActivity : AppCompatActivity(), ArticleListAdapter.ArticleClickListener,
    LoadPageScrollListener.LoadPageScrollLoadMoreListener {

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
            it.data?.let { data -> showData(data) }
            it.error?.let { showError() }
            hideLoading()
        })

        super.onCreate(savedInstanceState)

    }

    private fun loadNews() {
        showLoading()
        newsViewModel.loadNews()
    }

    override fun onClick(article: Article) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(article.url)
        startActivity(i)
    }

    override fun onLoadMore(currentPage: Int, totalItemCount: Int, recyclerView: RecyclerView) {
        showLoading()
        newsViewModel.loadNews(page = currentPage)
    }

    private var loading: Dialog? = null

    private fun showLoading() {
        if (loading == null) {
            loading = Dialog(this)
            loading?.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window.setBackgroundDrawableResource(android.R.color.transparent)
                setContentView(R.layout.dialog_loading)
            }
        }
        loading?.show()
    }

    private fun hideLoading() {
        loading?.dismiss()
    }

    private fun showData(articles: List<Article>) = newsAdapter.add(articles)

    private fun showError() = Snackbar.make(
        news_root_view,
        R.string.error_no_more_articles,
        Snackbar.LENGTH_SHORT).show()
}
