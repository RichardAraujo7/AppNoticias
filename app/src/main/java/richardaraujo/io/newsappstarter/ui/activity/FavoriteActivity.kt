package richardaraujo.io.newsappstarter.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import richardaraujo.io.newsappstarter.R
import richardaraujo.io.newsappstarter.ui.adapter.MainAdapter
import richardaraujo.io.newsappstarter.databinding.ActivityFavoriteBinding
import richardaraujo.io.newsappstarter.data.local.model.Article
import richardaraujo.io.newsappstarter.repository.NewsDataSource
import richardaraujo.io.newsappstarter.presenter.ViewHome
import richardaraujo.io.newsappstarter.presenter.favorite.FavoritePresenter


class FavoriteActivity : BaseActivity(), ViewHome.Favorite {

    private val mainAdapter by lazy {
        MainAdapter()
    }
    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: ActivityFavoriteBinding


    override fun getLayout(): ViewBinding {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)
        presenter.getAll()
        configRecycle()
        clickAdapter()

    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val article = mainAdapter.differ.currentList[position]
            presenter.deleteArticle(article)
            Snackbar.make(
                viewHolder.itemView,
                getString(R.string.article_delete_successful),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(getString(R.string.undo)) {
                    presenter.saveArticle(article)
                    mainAdapter.notifyItemInserted(position)
                }
                show()
            }
            presenter.getAll()
        }
    }

    private fun configRecycle() {
        with(binding.rvFavorite) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity, DividerItemDecoration.VERTICAL
                )
            )
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
        }
    }

    private fun clickAdapter() {
        mainAdapter.setOnclickListener { article ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }

    override fun showArticles(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
    }
}