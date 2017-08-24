package demo.news.api.android.ui.fragments


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
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
import demo.news.api.android.R
import demo.news.api.android.data.db.entities.Resource
import demo.news.api.android.data.db.entities.Source
import demo.news.api.android.data.db.entities.Status
import demo.news.api.android.data.viewmodels.ArticleViewModel
import demo.news.api.android.data.viewmodels.SourceViewModel
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
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var articleViewModel: ArticleViewModel
    lateinit var sourceViewModel: SourceViewModel
    lateinit var binding: FragmentListBinding
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var sources: List<Source>

    var lastLoadedSource: Int = 0


    private lateinit var adapter: ArticlesAdapter

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
        sourceViewModel = ViewModelProviders.of(this, viewModelFactory).get(SourceViewModel::class.java)

        var options: HashMap<String, String> = HashMap<String, String>()

        options.put("source", arguments.getString("source"))
        options.put("apiKey", getString(R.string.api_key))

        var source = arguments.getString("source")

        if (source.equals("*")) {//fetch all
            //only get source view model if all articles are required
            //paginate at the end and add data from next source

            var options: HashMap<String, String> = HashMap<String, String>()
            options.put("", "")
            sourceViewModel.list(options).observe(this, Observer {
                binding.resource = it

                if (it?.data != null && it?.data.isNotEmpty()) {
                    sources = it?.data;
                    loadSource(0)

                    options.put("source", sources.get(0).id)
                    options.put("apiKey", getString(R.string.api_key))
                    articleViewModel.loadAllArticles(options).observe(this, Observer {
                        updateUi(it)
                    })
                }


            })

        } else {
            articleViewModel.list(options).observe(this, Observer {
                updateUi(it)
            })
        }

    }

    /**
     * Recusively load all sources -- for the benfit of eeing all articles. Hmm, not very ideal, refine
     */
    private fun loadSource(index: Int) {
        var options: HashMap<String, String> = HashMap<String, String>()

        if (index >= sources.size) {
            return
        }
        options.put("source", sources.get(index).id)
        options.put("apiKey", getString(R.string.api_key))

        articleViewModel.list(options).observe(this, Observer {
            if (it?.status == Status.SUCCESS) {
                loadSource(++lastLoadedSource)
            }
        })

    }

    private fun updateUi(it: Resource<List<Article>>?) {
        Log.i(SourcesFragment.TAG, "fetched articles statu: " + it?.status)
        Log.i(SourcesFragment.TAG, "fetched articles size : " + it?.data?.size)
        Log.i(SourcesFragment.TAG, "fetched articles size : " + it?.message)

        binding.resource = it

        if (it != null && it.data != null) {
            adapter.articles = it.data
            adapter.notifyDataSetChanged()
        }
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

        @JvmStatic
        fun newInstance(@Nullable source: String?): ArticlesFragment {
            val fragment = ArticlesFragment()
            val args = Bundle()

            args.putString("source", source)
            fragment.arguments = args

            return fragment
        }
    }

}
