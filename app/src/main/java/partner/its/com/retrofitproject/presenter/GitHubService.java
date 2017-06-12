package partner.its.com.retrofitproject.presenter;


import java.util.List;

import io.reactivex.Observable;
import partner.its.com.retrofitproject.model.RepoModel;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by roman on 9.6.17.
 */

public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<List<RepoModel>> getRepos(@Path("user") String user);
}
