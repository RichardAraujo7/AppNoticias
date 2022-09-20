package richardaraujo.io.newsappstarter.util.state.local

import richardaraujo.io.newsappstarter.data.local.model.Article

sealed class ArticleListState {
    data class Success(val list: List<Article>) : ArticleListState()
    data class ErrorMessage(val errorMessage: String) : ArticleListState()
    object Loading : ArticleListState()
    object Empty : ArticleListState()
}