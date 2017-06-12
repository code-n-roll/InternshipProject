package partner.its.com.retrofitproject.view;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import partner.its.com.retrofitproject.AppModule;
import partner.its.com.retrofitproject.presenter.RepoListPresenter;
import partner.its.com.retrofitproject.view.activity.MainActivity;

/**
 * Created by roman on 12.6.17.
 */

@Module(
        injects = MainActivity.class,
        addsTo = AppModule.class
)

public class MainModule {
    private MainView mView;

    public MainModule(MainView view){
        mView = view;
    }

    @Provides
    @Singleton
    public MainView provideView(){
        return mView;
    }

    @Provides
    @Singleton
    public RepoListPresenter providePresenter(MainView mainView){
        return new RepoListPresenter(mainView);
    }
}
