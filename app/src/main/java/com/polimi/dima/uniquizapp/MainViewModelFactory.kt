package com.polimi.dima.uniquizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polimi.dima.uniquizapp.data.repository.Repository
import com.polimi.dima.uniquizapp.data.repository.UserRepository


class MainViewModelFactory(
    private val repository: UserRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}