package richardaraujo.io.newsappstarter.repository

import android.content.Context
import richardaraujo.io.newsappstarter.data.local.model.Article
import richardaraujo.io.newsappstarter.data.local.db.ArticleDatabase
import richardaraujo.io.newsappstarter.data.remote.RetrofitInstance
import richardaraujo.io.newsappstarter.presenter.favorite.FavoriteHome
import richardaraujo.io.newsappstarter.presenter.news.NewsHome
import richardaraujo.io.newsappstarter.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db: ArticleDatabase = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(RetrofitInstance.api, db)

    fun getBreakingNews(callback: NewsHome.Presenter) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = RetrofitInstance.api.getBreakingNews("br")
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun searchNews(term: String, callback: SearchHome.Presenter) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = RetrofitInstance.api.searchNews(term)
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun saveArticle(article: Article) {
        CoroutineScope(Dispatchers.Main).launch {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticle(callback: FavoriteHome.Presenter) {
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll().value!!

            withContext(Dispatchers.Main) {
                callback.onSuccess(allArticles)
            }
        }
    }

    fun deleteArticle(article: Article?) {
        CoroutineScope(Dispatchers.Main).launch {
            article?.let { articleDeleted ->
                newsRepository.delete(articleDeleted)
            }
        }
    }
}