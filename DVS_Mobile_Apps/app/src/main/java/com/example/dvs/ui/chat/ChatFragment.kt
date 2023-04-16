package com.example.dvs.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.FragmentChatBinding
import com.example.dvs.model.ContactModel
import com.example.dvs.util.Constant
import com.example.dvs.viewmodel.ChatViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater,container,false)

        setupViewModel()
        setupAction()

        return binding.root
    }

    private fun setupAction() {
        showLoading(true)
        val recyclerView = binding.rvContact
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        chatViewModel.getContacts().observe(requireActivity()){

            val adapter = ContactAdapter(it)
            recyclerView.adapter = adapter

            showLoading(false)
            adapter.setOnItemClickCallback(object : ContactAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ContactModel) {
                    startChatActivity(data)
                }
            })
        }

    }

    private fun showLoading(loading: Boolean) {
        if (loading){
            binding.progressBar.visibility = View.VISIBLE
        }
        else binding.progressBar.visibility = View.GONE
    }

    private fun startChatActivity(mContact: ContactModel) {
        //kirim bundle
        val bundle = Bundle().apply {
            putParcelable(Constant.CONTACT, mContact)
        }
        val intent = Intent(requireContext(), DetailChatActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    private fun setupViewModel() {
        chatViewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireActivity())
        )[ChatViewModel::class.java]
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}