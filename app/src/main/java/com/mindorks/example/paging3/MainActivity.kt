package com.mindorks.example.paging3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.example.paging.R
import com.mindorks.example.paging3.adapter.MainListAdapter
import com.mindorks.example.paging3.data.APIService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var mainListAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupList()
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                mainListAdapter.submitData(it)
            }
        }

    }

    private fun setupList() {
        mainListAdapter = MainListAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainListAdapter
        }
        mainListAdapter.addLoadStateListener {
            handleState(it.append)
            handleState(it.refresh)
            handleState(it.prepend)
        }
    }

    private fun handleState(state: LoadState) {
        when(state){
            is LoadState.Loading->{}
            is LoadState.NotLoading->{}
            is LoadState.Error->{
                Toast.makeText(this, state.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(APIService.getApiService())
            )[MainViewModel::class.java]
    }


}