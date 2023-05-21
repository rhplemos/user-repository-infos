import android.content.Context
import android.widget.Toast
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

    fun loadUser(context: Context, userName: String) {
        val retrofitClient = ApiClient.getRetrofitInstance()
        val endpoint = retrofitClient.create(GitHubService::class.java)
        val callback = endpoint.getUser(userName)

        callback.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                val user = response.body()
                _user.value = user!!
            }
        })
    }
}
