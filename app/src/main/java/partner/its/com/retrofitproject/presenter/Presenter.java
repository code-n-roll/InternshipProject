package partner.its.com.retrofitproject.presenter;

import android.os.Bundle;

/**
 * Created by roman on 12.6.17.
 */

public interface Presenter {
    void onDestroy();
    void clickOnSendButton();
    void initRepoListRecycler(Bundle savedInstanceState);
    Bundle onSaveInstanceState(Bundle outState);
}
