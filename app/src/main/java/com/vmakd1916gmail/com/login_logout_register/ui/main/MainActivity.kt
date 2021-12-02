package com.vmakd1916gmail.com.login_logout_register.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.vmakd1916gmail.com.login_logout_register.R
import com.vmakd1916gmail.com.login_logout_register.databinding.ActivityMainBinding
import com.vmakd1916gmail.com.login_logout_register.other.EventObserver
import com.vmakd1916gmail.com.login_logout_register.other.TokenPreferences
import com.vmakd1916gmail.com.login_logout_register.ui.auth.AuthActivity
import com.vmakd1916gmail.com.login_logout_register.ui.auth.VM.AuthViewModel
import com.vmakd1916gmail.com.login_logout_register.ui.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var tokenPreferences: TokenPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        mBinding.bottomNavigationView.apply {
            background = null
            menu.getItem(2).isEnabled = false
            setupWithNavController(navController)

        }

        mBinding.fabNewPost.setOnClickListener {
            navController.navigate(R.id.globalActionToCreatePostFragment)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miLogout->{
                authViewModel.logoutUser()
                authViewModel.logoutStatus.observe(this, EventObserver(
                    onError = {
                        mBinding.mainProgressBar.visibility = View.GONE
                    },
                    onLoading = {
                        mBinding.mainProgressBar.visibility = View.VISIBLE
                    }
                ) {
                    tokenPreferences.setStoredToken("")
                    Intent(this, AuthActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}