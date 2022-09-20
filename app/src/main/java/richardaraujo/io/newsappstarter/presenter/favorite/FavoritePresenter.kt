package richardaraujo.io.newsappstarter.presenter.favorite

import richardaraujo.io.newsappstarter.data.local.model.Article
import richardaraujo.io.newsappstarter.repository.NewsDataSource
import richardaraujo.io.newsappstarter.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val dataSource: NewsDataSource
) : FavoriteHome.Presenter {


    fun getAll() {
        this.dataSource.getAllArticle(this)
    }

    fun saveArticle(article: Article) {
        this.dataSource.saveArticle(article)
    }

    fun deleteArticle(article: Article) {
        this.dataSource.deleteArticle(article)
    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }
}