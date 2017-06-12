package partner.its.com.retrofitproject;


import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Collections;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by roman on 12.6.17.
 */

public class App extends Application {
    private ObjectGraph mObjectGraph;
    private RefWatcher mRefWatcher;

    public static RefWatcher getRefWatcher(Context context){
        App application = (App) context.getApplicationContext();
        return application.mRefWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)){
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);
        // Normal app init code...

        mObjectGraph = ObjectGraph.create(getModules().toArray());
        mObjectGraph.inject(this);
    }

    public ObjectGraph createScopedGraph(Object... modules){
        return mObjectGraph.plus(modules);
    }

    private List<Object> getModules(){
        return Collections.singletonList(new AppModule(this));
    }
}
