package com.vmakd1916gmail.com.login_logout_register.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vmakd1916gmail.com.login_logout_register.R
import com.vmakd1916gmail.com.login_logout_register.databinding.FragmentLoginBinding
import com.vmakd1916gmail.com.login_logout_register.other.APP_AUTH_ACTIVITY
import com.vmakd1916gmail.com.login_logout_register.other.EventObserver
import com.vmakd1916gmail.com.login_logout_register.ui.auth.VM.AuthViewModel
import com.vmakd1916gmail.com.login_logout_register.ui.main.MainActivity
import com.vmakd1916gmail.com.login_logout_register.ui.snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentLoginBinding? = null
    val mBinding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.goToRegisterBtnId.setOnClickListener {

                APP_AUTH_ACTIVITY.navController.navigate(R.id.action_loginFragment_to_registerFragment)

        }
        mBinding.loginBtnId.setOnClickListener(this)
        authViewModel.authStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                mBinding.loginProgressBar.visibility = View.GONE
                snackbar(it)
            },
            onLoading = {
                mBinding.loginProgressBar.visibility = View.VISIBLE
            }

        ){
            Intent(requireContext(), MainActivity::class.java).also {
                startActivity(it)
                requireActivity().finish()
            }
        })

    }


    override fun onClick(v: View) {
        if (v.id == R.id.login_btn_id) {
            val userName = mBinding.registerEditTextTextPersonName.text.toString()
            val userPassword = mBinding.registerEditTextTextPassword.text.toString()
            val userResponse = authViewModel.createUserResponse(userName, userPassword)
            authViewModel.authUser(userResponse)

        }
    }
}