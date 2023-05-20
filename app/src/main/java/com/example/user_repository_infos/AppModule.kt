import com.example.user_repository_infos.scenes.findRespository.FindRepositoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FindRepositoryViewModel() }
}
