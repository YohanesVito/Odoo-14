package com.example.dvs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListProductActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityListProductBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listProductAdapter: ListProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.product_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create example product list
        val productList = listOf(
            Product("Product 1", "Description 1", 9.99, R.drawable.product1),
            Product("Product 2", "Description 2", 19.99, R.drawable.product2),
            Product("Product 3", "Description 3", 29.99, R.drawable.product3),
        )

        // Set up adapter
        listProductAdapter = ListProductAdapter(productList)
        recyclerView.adapter = listProductAdapter
    }
}
