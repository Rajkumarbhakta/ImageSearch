package com.rkbapps.imagesearch.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkbapps.imagesearch.R
import com.rkbapps.imagesearch.adapter.ImagePagingAdapter
import com.rkbapps.imagesearch.databinding.FragmentHomeBinding
import com.rkbapps.imagesearch.model.ImageModelClass
import com.rkbapps.imagesearch.model.Photo
import com.rkbapps.imagesearch.repository.HomeFragmentRepository
import com.rkbapps.imagesearch.services.ApiInstance
import com.rkbapps.imagesearch.viewmodelfactory.HomeFragmentViewModelFactory
import com.rkbapps.imagesearch.viewmodels.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private lateinit var img: ImageModelClass
    private val photoList: MutableList<Photo> = ArrayList()
    private lateinit var adapter: ImagePagingAdapter
    private var page = 1
    private val perPage = 30
    private var isLoading = false
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val repository = HomeFragmentRepository(ApiInstance.getApi())
        viewModel = ViewModelProvider(
            requireActivity(),
            HomeFragmentViewModelFactory(repository)
        )[HomeFragmentViewModel::class.java]

        adapter = ImagePagingAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        //binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter


        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
            Log.d("Result", it.toString())
        }

//        viewModel.photos.observe(viewLifecycleOwner) {
//
//                adapter.submitData(viewLifecycleOwner.lifecycle, it)
//                adapter.notifyDataSetChanged()
//                Log.d("Result",it.toString())
//        }
        //loadImages(page, perPage)
//        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == photoList.size - 1) {
//                    //bottom of list!
//                    if (!isLoading) {
//                        if (img!!.nextPage != null) {
//                            page++
//                            loadImages(page, perPage)
//                        } else {
//                            Toast.makeText(context, "No more result available", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//                }
//            }
//        })
        return binding.root
    }

//    private fun loadImages(page: Int, perPage: Int) {
//        binding!!.progressBar.visibility = View.VISIBLE
//        isLoading = true
//        ApiInstance.getApi().getCuratedImages(page, perPage)!!
//            .enqueue(object : Callback<ImageModelClass?> {
//                override fun onResponse(
//                    call: Call<ImageModelClass?>,
//                    response: Response<ImageModelClass?>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            photoList.addAll(response.body()!!.photos)
//                            img = response.body()
//                            adapter!!.notifyDataSetChanged()
//                            isLoading = false
//                            binding!!.progressBar.visibility = View.INVISIBLE
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<ImageModelClass?>, t: Throwable) {
//                    isLoading = false
//                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
//                    binding!!.progressBar.visibility = View.INVISIBLE
//                }
//            })
//    }
}