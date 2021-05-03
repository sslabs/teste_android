package dev.dextra.newsapp.feature.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.dextra.newsapp.R
import dev.dextra.newsapp.api.model.Article
import kotlinx.android.synthetic.main.item_article.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ArticleListAdapter(
    private val clickListener: ArticleClickListener,
) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    private val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT)
    private val parseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val dataset = ArrayList<Article>()

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        dataset[position].apply {
            holder.view.article_name.text = this.title
            holder.view.article_description.text = this.description
            holder.view.article_author.text = this.author
            holder.view.article_date.text = dateFormat.format(parseFormat.parse(this.publishedAt))
            holder.view.setOnClickListener{ clickListener.onClick(this) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    fun add(articles: List<Article>) {
        dataset.addAll(articles)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface ArticleClickListener {
        fun onClick(article: Article)
    }
}