package com.vmakd1916gmail.com.login_logout_register.ui.main.VM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.vmakd1916gmail.com.login_logout_register.models.local.Post
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import com.vmakd1916gmail.com.login_logout_register.other.Event
import com.vmakd1916gmail.com.login_logout_register.other.Resource
import com.vmakd1916gmail.com.login_logout_register.repositories.main.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepositoryImpl
) : ViewModel() {
    private val _post = MutableLiveData<Event<Resource<Flow<PagingData<PostResponse>>>>>()
    val post: LiveData<Event<Resource<Flow<PagingData<PostResponse>>>>> = _post

    fun getPost() {
        _post.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPost()
            if (response.data!=null) {
                _post.postValue(Event(Resource.Success(response.data)))
            }
            else {
                _post.postValue(Event(Resource.Error(response.message!!)))
            }
        }
    }

    fun createPost(postResponse: PostResponse) =
        Post(
            authorUid = postResponse.authorId,
            text = postResponse.postTextContent,
            imageUrl = "",
            date = postResponse.postDate
        )
}