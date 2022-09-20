package richardaraujo.io.newsappstarter.util.state.local

sealed class ArticleListEvent {
    object Fetch : ArticleListEvent()
}