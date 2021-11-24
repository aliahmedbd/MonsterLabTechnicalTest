package com.monsterlab.technicaltest.view

import android.Manifest
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
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.widget.Toast

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import android.graphics.drawable.Drawable

import android.graphics.drawable.BitmapDrawable

import androidx.annotation.NonNull

import com.bumptech.glide.request.target.CustomTarget

import com.bumptech.glide.Glide

import android.R
import android.os.Environment
import androidx.annotation.Nullable
import androidx.transition.Transition
import com.monsterlab.technicaltest.utils.ImageDownloadUtil

/**
 * Created by Ali Ahmed, mail: aliahmedaiub@gmail.com
 */
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
        imageAdapter = context?.let {
            ImageListAdapter(it) { it ->
                if (it.download_url.isNotEmpty()) {
                    downloadImage(it.download_url)
                }
            }
        }!!
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

    private fun verifyPermissions(): Boolean? {

        // This will return the current Status
        val permissionExternalMemory =
            ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            val STORAGE_PERMISSIONS = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            // If permission not granted then ask for permission real time.
            ActivityCompat.requestPermissions(activity!!, STORAGE_PERMISSIONS, 1)
            return false
        }
        return true
    }

    private fun downloadImage(imageURL: String) {
        if (!verifyPermissions()!!) {
            Toast.makeText(
                context,
                "Please accept the external storage permission",
                Toast.LENGTH_SHORT
            ).show()
        }
        val dirPath: String = Environment.getExternalStorageDirectory().absolutePath
            .toString() + "/" + getString(com.monsterlab.technicaltest.R.string.app_name) + "/"
        val dir = File(dirPath)
        val fileName = imageURL.substring(imageURL.lastIndexOf('/') + 1)
        Glide.with(this)
            .load(imageURL)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?
                ) {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    Toast.makeText(context, "Saving Image...", Toast.LENGTH_SHORT).show()
                  //  saveImage(bitmap, dir, fileName)
                    context?.let { ImageDownloadUtil().addImageToGallery(fileName,bitmap, it) }
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                override fun onLoadFailed(@Nullable errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    Toast.makeText(
                        context,
                        "Failed to Download Image! Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}