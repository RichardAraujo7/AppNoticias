package richardaraujo.io.newsappstarter.presenter.news

import richardaraujo.io.newsappstarter.data.local.model.NewsResponse

interface NewsHome {

    interface Presenter {
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}