package com.example.dvs.ui.listproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityListProductBinding
import com.example.dvs.model.UserModel
import com.example.dvs.remote.response.ProductsResponseItem
import com.example.dvs.viewmodel.ListProductViewModel

class ListProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListProductBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listProductAdapter: ListProductAdapter
    private lateinit var listProductViewModel: ListProductViewModel
    private var user: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()
        setupAction()

    }

    private fun setupAction() {
        binding.ibSearch.setOnClickListener {
            val token = "Bearer ${user?.token}"
            val search = binding.etSearch.text.toString()
            val offset = 0
            val limit = 10
            listProductViewModel.getProductsRequest(token,search,offset,limit).observe(this){
                setupRecyclerView(it)
            }
        }

    }

    private fun setupRecyclerView(products: ArrayList<ProductsResponseItem>) {

        // Set up RecyclerView
        recyclerView = binding.productList
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up adapter
        listProductAdapter = ListProductAdapter(products)
        recyclerView.adapter = listProductAdapter

    }

    private fun setupViewModel() {
        listProductViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[ListProductViewModel::class.java]

        listProductViewModel.getUser().observe(this) { user ->
            this.user = user
        }
    }
}
