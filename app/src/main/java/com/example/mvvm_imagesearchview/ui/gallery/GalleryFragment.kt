package com.example.mvvm_imagesearchview.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.mvvm_imagesearchview.R
import com.example.mvvm_imagesearchview.data.UnsplashPhoto
import com.example.mvvm_imagesearchview.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "GalleryFragment"

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), UnsplashPhotoAdapter.onItemClickListener {

    private val viewModel by viewModels<GalleryViewModel> ()

    private var _binding:FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter{ adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter{ adapter.retry() },
            )
            Log.d(TAG, "onViewCreated: when the app is not able to access the data from the api")
            buttonRetry.setOnClickListener{
                adapter.retry()
            }
        }


        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty View
                if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                }else{
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }


    override fun onItemClick(photo: UnsplashPhoto) {
        Log.d(TAG, "onItemClick: clicked on adapter")
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
            
        inflater.inflate(R.menu.menu_gallery,menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    // it will jumps to the position zero
                    Log.d(TAG, "onQueryTextSubmit: for searching")
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    //
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}