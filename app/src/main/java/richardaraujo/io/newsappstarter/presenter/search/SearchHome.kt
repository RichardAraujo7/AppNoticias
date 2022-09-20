package richardaraujo.io.newsappstarter.presenter.search

import richardaraujo.io.newsappstarter.data.local.model.NewsResponse

interface SearchHome {

    interface Presenter{
        fun search(term: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}