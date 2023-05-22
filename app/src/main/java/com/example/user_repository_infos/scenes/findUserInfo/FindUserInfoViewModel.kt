import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.user_repository_infos.scenes.gitHubService.ApiClient
import com.example.user_repository_infos.scenes.gitHubService.GitHubService
import com.example.user_repository_infos.scenes.gitHubService.models.UserModel
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindUserInfoViewModel : ViewModel(), KoinComponent {
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> get() = _user

    private val _event: MutableLiveData<FindUserEvent> = MutableLiveData<FindUserEvent>()
    val event: LiveData<FindUserEvent> = _event

    private val _shouldShowUserInfos: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val shouldShowUserInfos: LiveData<Boolean> = _shouldShowUserInfos

    val uiState = FindUserInfoUIState()

    fun loadUser(userName: String) {
        uiState.showLoadIndicator(true)
        val retrofitClient = ApiClient.getRetrofitInstance()
        val endpoint = retrofitClient.create(GitHubService::class.java)
        val callback = endpoint.getUser(userName)

        callback.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                uiState.closeLoadDialog(true)
                _event.postValue(
                    FindUserEvent.HandleError(
                        errorMessage = t.message.toString()
                    )
                )
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                uiState.closeLoadDialog(true)
                _shouldShowUserInfos.postValue(false)
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        _shouldShowUserInfos.postValue(true)
                        _user.value = user!!
                    } else {
                        _event.postValue(
                            FindUserEvent.HandleError(
                                errorMessage = "Usuário não encontrado"
                            )
                        )
                    }
                } else {
                    _event.postValue(
                        FindUserEvent.HandleError(
                            errorMessage = "Usuário não encontrado"
                        )
                    )
                }
            }
        })
    }

    sealed interface FindUserEvent {
        class HandleError(val errorMessage: String) : FindUserEvent
    }

    class FindUserInfoUIState(
        private val _enableLoadIndicator: MutableLiveData<Boolean> = MutableLiveData(),
        private val _closeLoadDialog: MutableLiveData<Boolean> = MutableLiveData(),

        ) {
        val enableLoadIndicator: LiveData<Boolean> = _enableLoadIndicator
        val closeLoadDialog: LiveData<Boolean> = _closeLoadDialog

        fun showLoadIndicator(enable: Boolean) = _enableLoadIndicator.run { postValue(enable) }
        fun closeLoadDialog(enable: Boolean) = _closeLoadDialog.run { postValue(enable) }
    }
}
