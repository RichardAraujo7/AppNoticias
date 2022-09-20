package richardaraujo.io.newsappstarter.presenter.favorite

import richardaraujo.io.newsappstarter.data.local.model.Article

interface FavoriteHome {

    interface Presenter {
        fun onSuccess(articles: List<Article>)
    }
}