package com.vmakd1916gmail.com.login_logout_register.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vmakd1916gmail.com.login_logout_register.R
import com.vmakd1916gmail.com.login_logout_register.databinding.ActivityAuthBinding
import com.vmakd1916gmail.com.login_logout_register.models.network.RefreshTokenResponse
import com.vmakd1916gmail.com.login_logout_register.models.network.VerifyTokenResponse
import com.vmakd1916gmail.com.login_logout_register.other.APP_AUTH_ACTIVITY
import com.vmakd1916gmail.com.login_logout_register.other.EventObserver
import com.vmakd1916gmail.com.login_logout_register.other.TokenPreferences
import com.vmakd1916gmail.com.login_logout_register.ui.auth.VM.AuthViewModel
import com.vmakd1916gmail.com.login_logout_register.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


const val TAG = "AuthActivity"

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private var _binding: ActivityAuthBinding? = null
    val mBinding get() = _binding!!

    @Inject
    lateinit var tokenPreferences: TokenPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        APP_AUTH_ACTIVITY=this

        loginIfAuth()
    }
    private fun loginIfAuth() {
        if (tokenPreferences.getStoredToken()!=""){
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        else{
            mBinding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}