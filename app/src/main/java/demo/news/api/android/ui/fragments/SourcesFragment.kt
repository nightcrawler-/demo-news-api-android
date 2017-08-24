package demo.news.api.android.ui.fragments

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cafrecode.obviator.data.di.Injectable
import com.cafrecode.obviator.data.di.ViewModelFactory
import demo.news.api.android.R
import demo.news.api.android.data.db.entities.Source
import demo.news.api.android.data.viewmodels.SourceViewModel
import demo.news.api.android.databinding.FragmentListBinding
import demo.news.api.android.databinding.ListItemSourceBinding
import javax.inject.Inject


class SourcesFragment : LifecycleFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var sourceViewModel: SourceViewModel
    lateinit var binding: FragmentListBinding
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    private var sourcesAdapter: SourcesAdapter = SourcesAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mLayoutManager = LinearLayoutManager(getActivity())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        val recyclerView = binding.list

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = sourcesAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sourceViewModel = ViewModelProviders.of(this, viewModelFactory).get(SourceViewModel::class.java)

        var options: HashMap<String, String> = HashMap<String, String>()

        options.put("category", "technology")

        sourceViewModel.list(options).observe(this, Observer {
            var me = it
            Log.i(TAG, "fetched sources statu: " + me?.status)
            Log.i(TAG, "fetched sources size : " + me?.data?.size)
            Log.i(TAG, "fetched sources size : " + me?.message)

            if (it != null && it.data != null) {
                sourcesAdapter.sources = it.data
                sourcesAdapter.notifyDataSetChanged()
            }

        })

    }

    private class SourcesAdapter : RecyclerView.Adapter<SourcesAdapter.ViewHolder>() {

        var sources: List<Source>? = null

        class ViewHolder : RecyclerView.ViewHolder {
            var binding: ListItemSourceBinding

            constructor(binding: ListItemSourceBinding) : super(binding.root) {
                this.binding = binding
            }

            fun bind(source: Source) {
                binding.source = source;
                binding.executePendingBindings();
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesAdapter.ViewHolder {
            var layoutInflater = LayoutInflater.from(parent.context)
            var itemBinding: ListItemSourceBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_source, parent, false)
            var holder: SourcesAdapter.ViewHolder = SourcesAdapter.ViewHolder(itemBinding)
            return holder;
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val source: Source = sources?.get(position)!!
            holder.bind(source)
        }

        override fun getItemCount(): Int {
            if (sources != null) {
                return sources?.size!!
            }
            return 0
        }
    }

    companion object {

        val TAG: String = SourcesFragment::class.java.simpleName

        fun newInstance(): SourcesFragment {
            val fragment = SourcesFragment()
            return fragment
        }
    }


}
