package com.example.user_repository_infos.scenes.findRespository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.user_repository_infos.scenes.gitHubService.ApiClient
import com.example.user_repository_infos.scenes.gitHubService.GitHubService
import com.example.user_repository_infos.scenes.gitHubService.models.Repository
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindRepositoryViewModel : ViewModel(), KoinComponent {
    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    private val _event: MutableLiveData<FindRepositoryEvent> =
        MutableLiveData<FindRepositoryEvent>()
    val event: LiveData<FindRepositoryEvent> = _event

    val uiState = FindRepositoryUIState()

    fun loadAllRepositories(context: Context, userName: String) {
        uiState.showLoadIndicator(true)
        val retrofitClient = ApiClient.getRetrofitInstance()
        val endpoint = retrofitClient.create(GitHubService::class.java)
        val callback =
            if (userName.isEmpty()) endpoint.getRepositories() else endpoint.getRepository(userName)

        callback.enqueue(object : Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                uiState.closeLoadDialog(true)
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<Repository>>, response: Response<List<Repository>>
            ) {
                uiState.closeLoadDialog(true)

                if (response.isSuccessful) {
                    val repositories = response.body()
                    if (!repositories.isNullOrEmpty()) {
                        val repositories = response.body()
                        _repositories.value = repositories ?: listOf()
                    } else {
                        _event.postValue(
                            FindRepositoryEvent.HandleError(
                                errorMessage = "Repositório não encontrado"
                            )
                        )
                    }
                } else {
                    _event.postValue(
                        FindRepositoryEvent.HandleError(
                            errorMessage = "Repositório não encontrado"
                        )
                    )
                }
            }
        })
    }

    sealed interface FindRepositoryEvent {
        class HandleError(val errorMessage: String) : FindRepositoryEvent
    }

    class FindRepositoryUIState(
        private val _enableLoadIndicator: MutableLiveData<Boolean> = MutableLiveData(),
        private val _closeLoadDialog: MutableLiveData<Boolean> = MutableLiveData(),

        ) {
        val enableLoadIndicator: LiveData<Boolean> = _enableLoadIndicator
        val closeLoadDialog: LiveData<Boolean> = _closeLoadDialog

        fun showLoadIndicator(enable: Boolean) = _enableLoadIndicator.run { postValue(enable) }
        fun closeLoadDialog(enable: Boolean) = _closeLoadDialog.run { postValue(enable) }
    }
}