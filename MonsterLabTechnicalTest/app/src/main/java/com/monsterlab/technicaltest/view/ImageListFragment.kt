package com.monsterlab.technicaltest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.monsterlab.technicaltest.adapter.ImageListAdapter
import com.monsterlab.technicaltest.databinding.FragmentImageListBinding
import com.monsterlab.technicaltest.viewmodel.ImageListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private lateinit var binding: FragmentImageListBinding
    private lateinit var imageAdapter: ImageListAdapter
    private val imageViewModel: ImageListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentImageListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        imageAdapter = context?.let { ImageListAdapter(it) }!!
        initRecyclerview()

        lifecycleScope.launchWhenStarted {
            imageViewModel.getAllImages.collectLatest { response ->
                binding.apply {
                    progressBar.visibility = View.GONE
                    rvImageList.visibility = View.VISIBLE
                }
                imageAdapter.submitData(response)
            }
        }
        return root
    }

    private fun initRecyclerview() {
        binding.apply {
            binding.rvImageList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = imageAdapter
            }
            imageAdapter.addLoadStateListener { loadStates ->
                progressBar.isVisible = loadStates.source.refresh is LoadState.NotLoading &&
                        loadStates.append.endOfPaginationReached && imageAdapter.itemCount < 1
            }
        }
    }
}