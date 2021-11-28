package com.vmakd1916gmail.com.login_logout_register.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vmakd1916gmail.com.login_logout_register.databinding.FragmentSearchBinding

class SearchFragment:Fragment() {

    private var _binding: FragmentSearchBinding? = null
    val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }
}