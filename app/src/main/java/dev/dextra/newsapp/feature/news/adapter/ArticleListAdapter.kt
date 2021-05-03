package dev.dextra.newsapp.feature.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.dextra.newsapp.R
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Article.Companion.DIFF_CALLBACK
import kotlinx.android.synthetic.main.item_article.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ArticleListAdapter(
//    context: Context,
//    val listener: NewsActivity,
//    diffCallback: DiffUtil.ItemCallback<Article>,
//    private val articles: List<Article>
    ) : PagingDataAdapter<Article, ArticleListAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    private val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT)
    private val parseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val articles = ArrayList<Article>()

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
//        val article = getItem(position)
//
//        var convertView2 = convertView
//
//        if (convertView2 == null) {
//            convertView2 = LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false)
//        }
//
//        if(convertView2!=null){
//            convertView2.rootView.article_name.text = article.title
//            convertView2.rootView.article_description.text = article.description
//            convertView2.rootView.article_author.text = article.author
//            convertView2.rootView.article_date.text = dateFormat.format(parseFormat.parse(article.publishedAt))
//            convertView2.setOnClickListener{listener.onClick(article)}
//        }
//
//        return convertView2!!
//    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.view.article_name.text = this.title
            holder.view.article_description.text = this.description
            holder.view.article_author.text = this.author
            holder.view.article_date.text = dateFormat.format(parseFormat.parse(this.publishedAt))
//            holder.view.setOnClickListener{listener.onClick(this)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    class ArticleViewHolder(val view: View) : RecyclerView.ViewHolder(view)

//    companion object {
//        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
//            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
//                oldItem == newItem
//
//            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
//                oldItem == newItem
//        }
//    }
}