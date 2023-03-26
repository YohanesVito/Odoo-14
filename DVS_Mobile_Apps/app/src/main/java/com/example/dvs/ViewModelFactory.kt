package com.example.dvs

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dvs.ui.notification.NotificationViewModel
import com.example.dvs.viewmodel.*

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> {
                NotificationViewModel(context) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(context) as T
            }
            modelClass.isAssignableFrom(ListProductViewModel::class.java) -> {
                ListProductViewModel(context) as T
            }
//            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
//                RegisterViewModel(Injection.provideRepository(context)) as T
//            }
//            modelClass.isAssignableFrom(MonitorViewModel::class.java) -> {
//                MonitorViewModel(Injection.provideRepository(context)) as T
//            }
//            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
//                MapsViewModel(Injection.provideRepository(context)) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}