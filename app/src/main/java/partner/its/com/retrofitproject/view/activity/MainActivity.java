package partner.its.com.retrofitproject.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import partner.its.com.retrofitproject.App;
import partner.its.com.retrofitproject.R;
import partner.its.com.retrofitproject.presenter.RepoListPresenter;
import partner.its.com.retrofitproject.view.MainModule;
import partner.its.com.retrofitproject.view.MainView;
import partner.its.com.retrofitproject.view.adapter.RepoListAdapter;

public class MainActivity extends BaseActivity implements MainView{
    @Inject
    RepoListPresenter mPresenter;

    private Button mSendButton;
    private EditText mRequestEditText;
    private RecyclerView mRepoListRecycler;
    private ProgressBar mProgressBar;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewsListeners();
        initRepoListRecycler(savedInstanceState);
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    protected List<Object> getModules() {
        return Collections.singletonList(new MainModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(mPresenter.onSaveInstanceState(outState));
    }

    private void initRepoListRecycler(Bundle savedInstanceState){
        mPresenter.initRepoListRecycler(savedInstanceState);
        mRepoListRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViewsListeners(){
        mSendButton.setOnClickListener(this::clickOnSendButton);
        mRequestEditText.setOnEditorActionListener(mEditorActionListener);
    }

    private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                mSendButton.callOnClick();
            }
            return false;
        }
    };

    private void initViews(){
        mView = findViewById(android.R.id.content);
        mSendButton = (Button) findViewById(R.id.send_button);
        mRequestEditText = (EditText) findViewById(R.id.text_field);
        mRepoListRecycler = (RecyclerView) findViewById(R.id.repos_list);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    private void clickOnSendButton(View view){
        mPresenter.clickOnSendButton();
        hideKeyboard();
        showProgress();
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void showMessage(int resMessage){
        Toast.makeText(this, resMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public String getRequest() {
        return mRequestEditText.getText().toString();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRepoListRecycler.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRepoListRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRepos(RepoListAdapter repoListAdapter) {
        mRepoListRecycler.setAdapter(repoListAdapter);
    }

}