package partner.its.com.retrofitproject.view;

import partner.its.com.retrofitproject.view.adapter.RepoListAdapter;

/**
 * Created by roman on 12.6.17.
 */

public interface MainView {
    void showProgress();
    void hideProgress();
    void setRepos(RepoListAdapter repoListAdapter);
    void showMessage(int resMessage);
    String getRequest();
}
