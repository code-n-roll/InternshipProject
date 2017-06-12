package partner.its.com.retrofitproject.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import dagger.ObjectGraph;
import partner.its.com.retrofitproject.App;

/**
 * Created by roman on 12.6.17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ObjectGraph mActivityGraph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGraph = ((App) getApplication()).createScopedGraph(getModules().toArray());
        mActivityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityGraph = null;
    }

    protected abstract List<Object> getModules();
}
