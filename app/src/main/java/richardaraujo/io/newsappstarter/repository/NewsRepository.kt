package richardaraujo.io.newsappstarter.repository

import androidx.lifecycle.LiveData
import richardaraujo.io.newsappstarter.data.local.db.ArticleDatabase
import richardaraujo.io.newsappstarter.data.local.model.Article
import richardaraujo.io.newsappstarter.data.remote.NewsAPI

class NewsRepository(
    private val api: NewsAPI,
    private val db: ArticleDatabase
) {
    //Remote
    suspend fun getAllRemote() = api.getBreakingNews()
    suspend fun search(query: String) = api.searchNews(query)

    //Local
    fun getAll(): LiveData<List<Article>> = db.getArticleDao().getAll()
    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)
    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}