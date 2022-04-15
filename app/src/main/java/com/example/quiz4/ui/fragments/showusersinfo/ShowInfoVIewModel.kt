package com.example.quiz4.ui.fragments.showusersinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz4.data.UserRepository
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody


class ShowInfoVIewModel(private val userRepository: UserRepository):ViewModel() {

    private val _showInfo = MutableStateFlow<Result<UsersListItem>>(Result.Success(null))
    val showInfo = _showInfo.asStateFlow()

    fun showInfoUser(id: String) {
        viewModelScope.launch {
            userRepository.showInfoUser(id).collect {
                _showInfo.emit(it)
            }
        }
    }

    fun uploadImage(id: String, image: ByteArray) {
        val multipartBody = MultipartBody.create(MediaType.parse("image/*"), image)
        val req = MultipartBody.Part.createFormData("image", "image.jpg", multipartBody)
        viewModelScope.launch {
            userRepository.uploadImage(id, req)
        }
    }
}