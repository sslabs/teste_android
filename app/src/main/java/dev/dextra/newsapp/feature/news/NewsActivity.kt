package dev.dextra.newsapp.feature.news

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dev.dextra.newsapp.R
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.feature.news.adapter.ArticleListAdapter
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_sources.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


const val NEWS_ACTIVITY_SOURCE = "NEWS_ACTIVITY_SOURCE"

class NewsActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by inject()
    private val newsAdapter = ArticleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news)

        (intent?.extras?.getSerializable(NEWS_ACTIVITY_SOURCE) as Source).let { source ->
            title = source.name

//            newsViewModel.source = source

            news_list.apply {
                setHasFixedSize(true)
                adapter = newsAdapter
            }

            loadNews(source)
            newsViewModel.flow.subscribe {
                newsAdapter.submitData(lifecycle, it)
                hideLoading()
            }


//            lifecycleScope.launch {
//                newsViewModel.flow.collectLatest { pagingData ->
//                    newsAdapter.submitData(pagingData)
//                    hideLoading()
//                }
//            }
        }

//        news_list.apply {
//            setHasFixedSize(true)
//            adapter = newsAdapter
//        }

//        newsViewModel.articles.observe(this, Observer {
//            showData(it)
//            hideLoading()
//        });

        super.onCreate(savedInstanceState)

    }

    private fun loadNews(source: Source) {
        showLoading()
        newsViewModel.loadNews(source)
    }

    fun onClick(article: Article) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(article.url)
        startActivity(i)
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

    private fun showData(articles: List<Article>) {
//        val viewAdapter = ArticleListAdapter(this@NewsActivity, this@NewsActivity, articles)
//        news_list.adapter = viewAdapter
    }
}
