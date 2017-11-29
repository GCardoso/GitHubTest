package com.guilhermecardoso.githubtest.app.modules.userrepos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.guilhermecardoso.githubtest.R;
import com.guilhermecardoso.githubtest.domain.network.ServiceFactory;
import com.guilhermecardoso.githubtest.domain.network.entity.Repo;
import com.guilhermecardoso.githubtest.domain.network.services.GitHubService;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by guilhermecardoso on 11/28/17.
 */

public class UserReposActivity extends AppCompatActivity {
    public static final String EXTRA_REPOS_LIST = "EXTRA_REPOS_LIST";
    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";
    private static final String TAG = "UserReposActivity";
    UserReposContract.Actions presenter;

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
            Intent extras = getIntent();
            ArrayList<Repo> repos = extras.hasExtra(EXTRA_REPOS_LIST) ? extras.getParcelableArrayListExtra(EXTRA_REPOS_LIST) : new ArrayList();
            String username = extras.hasExtra(EXTRA_USERNAME) ? extras.getStringExtra(EXTRA_USERNAME) : "";
            fragment = UserReposFragment.newInstance(repos, username);
        }

        presenter = (UserReposContract.Actions) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new UserReposPresenter((UserReposFragment) fragment,
                    new CompositeDisposable(),
                    ServiceFactory.createService(GitHubService.class));
        } else {
            presenter.attachView((UserReposFragment) fragment);
        }

        ((UserReposFragment) fragment).setPresenter(presenter);
        fragmentTransaction.replace(R.id.fragment, fragment, TAG);
        fragmentTransaction.commit();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

}
