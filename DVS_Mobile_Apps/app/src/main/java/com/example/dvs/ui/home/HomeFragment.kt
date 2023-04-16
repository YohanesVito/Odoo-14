package com.example.dvs.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dvs.R
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.FragmentHomeBinding
import com.example.dvs.model.UserModel
import com.example.dvs.ui.listproduct.ListProductActivity
import com.example.dvs.ui.login.LoginActivity
import com.example.dvs.viewmodel.HomeViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mUser: UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        setupViewModel()
        setupAction()
        return binding.root
    }

    private fun setupAction() {
        binding.ivProduct.setOnClickListener {
            startActivity(Intent(requireActivity(),ListProductActivity::class.java))
        }

    }

    private fun setupViewModel() {
        homeViewModel= ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireContext())
        )[HomeViewModel::class.java]

        //get user
        homeViewModel.getUser().observe(viewLifecycleOwner) {
            if (it == null) {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            else{
                mUser = it
                binding.tvNama.text = mUser.username
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_home, fragment,fragment.javaClass.simpleName)
        transaction?.disallowAddToBackStack()
        transaction?.commit()

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}