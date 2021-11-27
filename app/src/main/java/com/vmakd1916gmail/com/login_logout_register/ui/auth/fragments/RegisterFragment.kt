package com.vmakd1916gmail.com.login_logout_register.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vmakd1916gmail.com.login_logout_register.R
import com.vmakd1916gmail.com.login_logout_register.databinding.FragmentRegisterBinding
import com.vmakd1916gmail.com.login_logout_register.models.network.RefreshTokenResponse
import com.vmakd1916gmail.com.login_logout_register.models.network.UserResponse
import com.vmakd1916gmail.com.login_logout_register.models.network.VerifyTokenResponse
import com.vmakd1916gmail.com.login_logout_register.other.APP_AUTH_ACTIVITY
import com.vmakd1916gmail.com.login_logout_register.other.EventObserver
import com.vmakd1916gmail.com.login_logout_register.other.TokenPreferences
import com.vmakd1916gmail.com.login_logout_register.ui.auth.VM.AuthViewModel
import com.vmakd1916gmail.com.login_logout_register.ui.main.MainActivity
import com.vmakd1916gmail.com.login_logout_register.ui.snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentRegisterBinding? = null
    val mBinding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private var userResponse: UserResponse? = null

    @Inject
    lateinit var tokenPreferences: TokenPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.goToLoginBtnId.setOnClickListener {
            APP_AUTH_ACTIVITY.navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        mBinding.registerBtnId.setOnClickListener(this)
        mBinding.noLoginButtonId.setOnClickListener(this)

        authViewModel.registerStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                mBinding.progressBar.visibility = View.GONE
                snackbar(it)
            },
            onLoading = {
                mBinding.progressBar.visibility = View.VISIBLE
            }
        ) {
            mBinding.progressBar.visibility = View.GONE
            authViewModel.authUser(userResponse!!)

        })

        authViewModel.authStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                mBinding.progressBar.visibility = View.GONE
                snackbar(it)
            },
            onLoading = {}
        ) {
            mBinding.progressBar.visibility = View.GONE
            tokenPreferences.setStoredToken(it.token)
            Intent(requireActivity(), MainActivity::class.java).also {
                startActivity(it)
                requireActivity().finish()
            }

        })

//        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.fall_animation)
//        val fromDownMoveAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_down_move_animation)
//        val fromLeftMoveAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_left_move_animation)
//        val fromRightMoveAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_right_animation)
//        mBinding.registerLabelId.startAnimation(anim)
//        mBinding.goToLoginBtnId.startAnimation(fromRightMoveAnim)
//        mBinding.registerBtnId.startAnimation(fromDownMoveAnim)
//        mBinding.noLoginButtonId.startAnimation(fromLeftMoveAnim)

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onClick(v: View) {
        if (v.id == R.id.register_btn_id) {

            val userName = mBinding.loginEditTextTextPersonName.text.toString()
            val userPassword = mBinding.loginEditTextTextPassword.text.toString()

            userResponse = authViewModel.createUserResponse(userName, userPassword)

            authViewModel.registerUser(userResponse!!)


        }
        if (v.id == R.id.no_login_button_id) {
            Intent(requireContext(), MainActivity::class.java).also {
                startActivity(it)
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}