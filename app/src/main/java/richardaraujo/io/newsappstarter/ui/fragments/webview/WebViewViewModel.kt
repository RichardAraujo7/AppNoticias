package richardaraujo.io.newsappstarter.ui.fragments.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import richardaraujo.io.newsappstarter.data.local.model.Article
import richardaraujo.io.newsappstarter.repository.NewsRepository
import kotlinx.coroutines.launch

class WebViewViewModel constructor(
    private val repository: NewsRepository
) : ViewModel() {

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }
}