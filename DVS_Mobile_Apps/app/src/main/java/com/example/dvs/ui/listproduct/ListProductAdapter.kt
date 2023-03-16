package com.example.dvs.ui.listproduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dvs.Product
import com.example.dvs.R
import com.example.dvs.remote.response.ProductsResponseItem

class ListProductAdapter(private val productList: ArrayList<ProductsResponseItem>) : RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productId.text = product.id.toString()
        holder.productName.text = product.name
//        holder.productDescription.text = product.id
//        holder.productPrice.text = "$${product.price}"
//        holder.productImage.setImageResource(product.image)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var productImage: ImageView = itemView.findViewById(R.id.iv_product)

        var productId: TextView = itemView.findViewById(R.id.tv_product_id)
        var productName: TextView = itemView.findViewById(R.id.tv_product_name)
//
//        var productDescription: TextView = itemView.findViewById(R.id.tv_product_desc)
//        var productPrice: TextView = itemView.findViewById(R.id.tv_product_desc)
    }
}
