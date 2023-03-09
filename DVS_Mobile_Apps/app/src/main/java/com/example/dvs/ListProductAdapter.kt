package com.example.dvs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListProductAdapter(private val productList: List<Product>) : RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productDescription.text = product.description
        holder.productPrice.text = "$${product.price}"
        holder.productImage.setImageResource(product.image)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.iv_product)
        var productName: TextView = itemView.findViewById(R.id.tv_product_name)
        var productDescription: TextView = itemView.findViewById(R.id.tv_product_desc)
        var productPrice: TextView = itemView.findViewById(R.id.tv_product_desc)
    }
}
