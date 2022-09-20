package richardaraujo.io.newsappstarter.ui.activity

import android.webkit.WebViewClient
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import richardaraujo.io.newsappstarter.R
import richardaraujo.io.newsappstarter.databinding.ActivityArticleBinding
import richardaraujo.io.newsappstarter.data.local.model.Article
import richardaraujo.io.newsappstarter.repository.NewsDataSource
import richardaraujo.io.newsappstarter.presenter.ViewHome
import richardaraujo.io.newsappstarter.presenter.favorite.FavoritePresenter

class ArticleActivity : BaseActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: ActivityArticleBinding

    override fun getLayout(): ViewBinding {
        binding = ActivityArticleBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }

        binding.fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(
                it, R.string.article_saved_successful,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }

    override fun showArticles(articles: List<Article>) {}
}