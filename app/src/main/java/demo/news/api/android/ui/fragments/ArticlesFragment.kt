package demo.news.api.android.ui.fragments


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cafrecode.obviator.data.db.entities.Article
import com.cafrecode.obviator.data.di.Injectable
import com.cafrecode.obviator.data.di.ViewModelFactory
import demo.news.api.android.R
import demo.news.api.android.data.viewmodels.ArticleViewModel
import demo.news.api.android.databinding.FragmentListBinding
import demo.news.api.android.databinding.ListItemArticleBinding
import demo.news.api.android.ui.DetailActivity
import javax.annotation.Nullable
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ArticlesFragment : LifecycleFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var articleViewModel: ArticleViewModel
    lateinit var binding: FragmentListBinding

    private lateinit var adapter: ArticlesAdapter

    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        adapter = ArticlesAdapter(activity)

        mLayoutManager = LinearLayoutManager(getActivity())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        val recyclerView = binding.list

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        articleViewModel = ViewModelProviders.of(this, viewModelFactory).get(ArticleViewModel::class.java)

        var options: HashMap<String, String> = HashMap<String, String>()

        options.put("source", arguments.getString("source"))
        options.put("apiKey", getString(R.string.api_key))

        articleViewModel.list(options).observe(this, Observer {
            Log.i(SourcesFragment.TAG, "fetched articles statu: " + it?.status)
            Log.i(SourcesFragment.TAG, "fetched articles size : " + it?.data?.size)
            Log.i(SourcesFragment.TAG, "fetched articles size : " + it?.message)

            binding.resource = it

            if (it != null && it.data != null) {
                adapter.articles = it.data
                adapter.notifyDataSetChanged()
            }
        })
    }

    private class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

        var mContext: Context

        var articles: List<Article>? = null

        constructor(mContext: Context) : super() {
            this.mContext = mContext
        }


        class ViewHolder : RecyclerView.ViewHolder {
            var binding: ListItemArticleBinding

            constructor(binding: ListItemArticleBinding) : super(binding.root) {
                this.binding = binding
            }

            fun bind(article: Article) {
                binding.article = article;
                binding.executePendingBindings()
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesAdapter.ViewHolder {
            var layoutInflater = LayoutInflater.from(parent.context)
            var itemBinding: ListItemArticleBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_article, parent, false)
            var holder: ArticlesAdapter.ViewHolder = ArticlesAdapter.ViewHolder(itemBinding)
            return holder;
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item: Article = articles?.get(position)!!
            holder.bind(item)
            holder.itemView.isClickable = true

            holder.itemView.setOnClickListener({
                val intent: Intent = Intent(mContext, DetailActivity::class.java)
                intent.putExtra("url", item.url)
                mContext.startActivity(intent)
            })
        }

        override fun getItemCount(): Int {
            if (articles != null) {
                return articles?.size!!
            }
            return 0
        }
    }

    companion object {

        val TAG: String = ArticlesFragment::class.java.simpleName

        fun newInstance(@Nullable source: String): ArticlesFragment {
            val fragment = ArticlesFragment()
            val args = Bundle()

            args.putString("source", source)
            fragment.arguments = args

            return fragment
        }
    }

}
