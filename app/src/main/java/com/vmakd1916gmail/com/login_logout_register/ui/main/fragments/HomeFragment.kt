package com.vmakd1916gmail.com.login_logout_register.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmakd1916gmail.com.login_logout_register.databinding.FragmentHomeBinding
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import com.vmakd1916gmail.com.login_logout_register.other.EventObserver
import com.vmakd1916gmail.com.login_logout_register.ui.main.VM.HomeViewModel
import com.vmakd1916gmail.com.login_logout_register.ui.main.adapters.AdapterActionListener
import com.vmakd1916gmail.com.login_logout_register.ui.main.adapters.PostAdapter
import com.vmakd1916gmail.com.login_logout_register.ui.snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val mBinding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postAdapter = PostAdapter(object : AdapterActionListener {

            override fun expandItem(item: PostResponse) {

            }

            override fun collapseItem(item: PostResponse) {

            }

            override fun itemClick(item: PostResponse) {
//                val bundle = Bundle()
//                bundle.putSerializable("event", item)
//                APP_ACTIVITY.navController.navigate(
//                    R.id.action_crimeListFragment_to_addedCrimeFragment,
//                    bundle
//                )
            }
        })

        mBinding.allPostRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
            itemAnimator = SlideInLeftAnimator()
        }

        (mBinding.allPostRecyclerView.itemAnimator as SlideInLeftAnimator).apply {
            addDuration = 400
            removeDuration = 400
            moveDuration = 300
            changeDuration = 600
        }

        homeViewModel.post.observe(viewLifecycleOwner, EventObserver(
            onError = {
                snackbar(it)
            },
            onLoading = {
                Log.d(TAG, "onViewCreated: loading")
            }

        ) {
            Log.d(TAG, "onViewCreated: ${it}")
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                it.collectLatest {
                    Log.d(TAG, "onViewCreated: $it")
                    postAdapter.submitData(it)
                }
            }
        }
        )

        homeViewModel.getPost()

    }
}