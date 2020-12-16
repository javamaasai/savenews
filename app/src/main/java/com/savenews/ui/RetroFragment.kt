package com.savenews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.savenews.R
import com.savenews.databinding.PostListLayoutBinding
import com.savenews.model.PostInfo
import com.savenews.viewmodel.RetroViewModel
import com.savenews.viewmodel.RetroViewModelFactory
import kotlinx.android.synthetic.main.post_list_layout.view.*

class RetroFragment(private val progressBar: ProgressBar) : Fragment() {

    lateinit var retroViewModel: RetroViewModel
    var fragmentView:View?=null
    var listAdapter:PostListAdapter?=null
    private var postListLayoutBinding:PostListLayoutBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        postListLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.post_list_layout,container,false)
        fragmentView = postListLayoutBinding?.root
        initAdapter()
        setAdapter()
        fetchRetroInfo()
        return fragmentView
    }

    fun  initViewModel(){
        var retroViewModelFactory = RetroViewModelFactory()
        retroViewModel = ViewModelProviders.of(this,retroViewModelFactory).get(RetroViewModel::class.java)
    }

    fun fetchRetroInfo(){
        retroViewModel.postInfoLiveData?.observe(this,object:Observer<PostInfo>{
            override fun onChanged(t: PostInfo?) {
                t?.apply {
                    listAdapter?.setAdapterList(t.characters)
                    //Save to database
                    listAdapter?.saveDataList(t.characters)
                }
            }
        })
    }

    private fun setAdapter(){
        fragmentView?.post_list?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = listAdapter
        }
//        progressBar.visibility = View.GONE
    }

    private fun initAdapter(){
        listAdapter = PostListAdapter(this@RetroFragment.requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("== this is done ==")
    }

}