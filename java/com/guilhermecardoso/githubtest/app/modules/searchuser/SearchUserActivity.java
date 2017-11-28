package com.guilhermecardoso.githubtest.app.modules.searchuser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.guilhermecardoso.githubtest.R;
import com.guilhermecardoso.githubtest.domain.network.ServiceFactory;
import com.guilhermecardoso.githubtest.domain.network.services.GitHubService;

import io.reactivex.disposables.CompositeDisposable;

public class SearchUserActivity extends AppCompatActivity {

    private static final String TAG = "SearchUserActivity";
    SearchUserContract.Actions presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment_base);
        setupFragment();
    }

    private void setupFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(TAG);

        if (fragment == null) {
            fragment = SearchUserFragment.newInstance();
        }

        presenter = (SearchUserContract.Actions) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new SearchUserPresenter((SearchUserFragment) fragment,
                    new CompositeDisposable(),
                    ServiceFactory.createService(GitHubService.class));
        } else {
            presenter.attachView((SearchUserFragment) fragment);
        }

        ((SearchUserFragment) fragment).setPresenter(presenter);
        fragmentTransaction.replace(R.id.fragment, fragment, TAG);
        fragmentTransaction.commit();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }


}
