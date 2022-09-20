package richardaraujo.io.newsappstarter.ui.fragments.favorite

import androidx.lifecycle.*
import richardaraujo.io.newsappstarter.data.local.model.Article
import richardaraujo.io.newsappstarter.repository.NewsRepository
import richardaraujo.io.newsappstarter.util.state.local.ArticleListEvent
import richardaraujo.io.newsappstarter.util.state.local.ArticleListState
import kotlinx.coroutines.launch

class FavoriteViewModel constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _favorite = MutableLiveData<ArticleListEvent>()
    //vou observar isso
    val favorite: LiveData<ArticleListState> = _favorite.switchMap {
        // criterio de avaliação
        //quero observar uma mudanda no meu metodo getAll
        when (it) {
            //aqui dentro o que ele vai disparar e transformar
            //deve retornar um Livedata
            ArticleListEvent.Fetch -> getAll()
        }
    }

    fun dispatch(event: ArticleListEvent) {
        this._favorite.postValue(event)
    }

    private fun getAll(): LiveData<ArticleListState> {
        return liveData {
            try {
                emit(ArticleListState.Loading)
                val source: LiveData<ArticleListState> = repository.getAll()
                        //mapeia a lista e verifica
                    .map { list ->
                        if (list.isEmpty()) {
                            ArticleListState.Empty
                        } else {
                            ArticleListState.Success(list)
                        }
                    }
                emitSource(source)
            } catch (ex: Exception) {
                emit(ArticleListState.ErrorMessage("Algo deu errado"))
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }
}