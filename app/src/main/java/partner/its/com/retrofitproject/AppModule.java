package partner.its.com.retrofitproject;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 12.6.17.
 */

@Module(
        injects = {
                App.class
        },
        library = true
)

public class AppModule {
    private App mApp;

    public AppModule(App app){
        mApp = app;
    }

    @Provides
    public Application provideApplication(){
        return mApp;
    }
}
