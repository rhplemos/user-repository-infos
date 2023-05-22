import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.user_repository_infos.scenes.gitHubService.GitHubService
import com.example.user_repository_infos.scenes.gitHubService.models.UserModel
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FindRepositoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var gitHubService: GitHubService

    @Mock
    private lateinit var userObserver: Observer<UserModel>

    @Mock
    private lateinit var eventObserver: Observer<FindUserInfoViewModel.FindUserEvent>

    @Mock
    private lateinit var shouldShowUserInfosObserver: Observer<Boolean>

    private lateinit var viewModel: FindUserInfoViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = FindUserInfoViewModel()
        viewModel.user.observeForever(userObserver)
        viewModel.event.observeForever(eventObserver)
        viewModel.shouldShowUserInfos.observeForever(shouldShowUserInfosObserver)
    }

    @Test
    fun `loadUser should update user and shouldShowUserInfos when API call is successful`() {
        val userName = "exampleUser"
        val user = UserModel(
            login = "Test",
            id = 11,
            name = "exampleUser",
            bio = "Teste Teste",
            location = "Teste",
            publicRepos = "11",
            company = "Teste"
        )

        val call = createSuccessCall(user)
        `when`(gitHubService.getUser(userName)).thenReturn(call)

        viewModel.loadUser(userName)

        verify(shouldShowUserInfosObserver).onChanged(true)
        verify(userObserver).onChanged(user)
    }

    @Test
    fun `loadUser should post HandleError event when API call fails`() {
        val userName = "exampleUser"
        val errorMessage = "Error message"
        val call = createFailureCall(errorMessage)
        `when`(gitHubService.getUser(userName)).thenReturn(call)

        viewModel.loadUser(userName)

        verify(eventObserver).onChanged(FindUserInfoViewModel.FindUserEvent.HandleError(errorMessage))
    }

    private fun createSuccessCall(user: UserModel): Call<UserModel> {
        return object : Call<UserModel> {
            override fun enqueue(callback: Callback<UserModel>) {
                callback.onResponse(this, Response.success(user))
            }

            override fun isExecuted(): Boolean = false

            override fun clone(): Call<UserModel> = this

            override fun isCanceled(): Boolean = false

            override fun cancel() {}

            override fun execute(): Response<UserModel> = Response.success(user)

            override fun request(): Request? = null

            override fun timeout(): Timeout = Timeout.NONE
        }
    }

    private fun createFailureCall(errorMessage: String): Call<UserModel> {
        val errorResponseBody =
            ResponseBody.create(MediaType.parse("application/json"), errorMessage)
        return object : Call<UserModel> {
            override fun enqueue(callback: Callback<UserModel>) {
                callback.onFailure(this, IOException(errorMessage))
            }

            override fun isExecuted(): Boolean = false

            override fun clone(): Call<UserModel> = this

            override fun isCanceled(): Boolean = false

            override fun cancel() {}

            override fun execute(): Response<UserModel> = Response.error(400, errorResponseBody)

            override fun request(): Request? = null

            override fun timeout(): Timeout = Timeout.NONE
        }
    }
}
