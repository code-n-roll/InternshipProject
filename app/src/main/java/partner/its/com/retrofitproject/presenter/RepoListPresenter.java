package partner.its.com.retrofitproject.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import partner.its.com.retrofitproject.R;
import partner.its.com.retrofitproject.model.RepoModel;
import partner.its.com.retrofitproject.view.MainView;
import partner.its.com.retrofitproject.view.activity.MainActivity;
import partner.its.com.retrofitproject.view.adapter.RepoListAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roman on 12.6.17.
 */

public class RepoListPresenter implements Presenter{
    public final String LOG_TAG = "myLogs";
    public final String REPO_LIST_KEY = "REPO_LIST";

    private MainView mMainView;
    private CompositeDisposable mCompositeDisposable;
    private GitHubService mGitHubService;
    private ArrayList<RepoModel> mRepoModels;
    private RepoListAdapter mRepoListAdapter;

    public RepoListPresenter(MainView mainView) {
        mMainView = mainView;
        mCompositeDisposable = new CompositeDisposable();

        initGitHubService();
    }

    @Override
    public void clickOnSendButton() {
        if (mMainView != null) {
            mCompositeDisposable.add(mGitHubService.getRepos(mMainView.getRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
        }
    }

    @Override
    public void initRepoListRecycler(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mRepoModels = savedInstanceState.getParcelableArrayList(REPO_LIST_KEY);
        } else {
            mRepoModels = new ArrayList<>();
        }
        mRepoListAdapter = new RepoListAdapter(mRepoModels);
        if (mMainView != null){
            mMainView.setRepos(mRepoListAdapter);
        }
    }

    @Override
    public Bundle onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(REPO_LIST_KEY, mRepoModels);
        return outState;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
        mCompositeDisposable.clear();
    }


    private void initGitHubService(){
        final String BASE_URL = "https://api.github.com/";
        mGitHubService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GitHubService.class);
    }

    private void handleResponse(List<RepoModel> repoModels){
        if (mMainView != null){
            mMainView.hideProgress();
        }

        mRepoModels.clear();
        mRepoModels.addAll(repoModels);
        mRepoListAdapter.notifyDataSetChanged();

        Log.d(LOG_TAG, repoModels.toString());
    }

    public boolean isOnline() {
        MainActivity mainActivity = (MainActivity)mMainView;
        ConnectivityManager cm =
                (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void handleError(Throwable error){
        if (mMainView != null){
            mMainView.hideProgress();
            if (isOnline()){
                mMainView.showMessage(R.string.error_message_validation);
            } else {
                mMainView.showMessage(R.string.error_internet_connection);
            }
        }

        mRepoModels.clear();
        mRepoListAdapter.notifyDataSetChanged();
    }
}
