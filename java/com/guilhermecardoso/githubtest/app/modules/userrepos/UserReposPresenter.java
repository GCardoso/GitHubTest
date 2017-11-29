package com.guilhermecardoso.githubtest.app.modules.userrepos;

import com.guilhermecardoso.githubtest.domain.network.entity.User;
import com.guilhermecardoso.githubtest.domain.network.services.GitHubService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guilhermecardoso on 11/28/17.
 */

public class UserReposPresenter implements UserReposContract.Actions {

    private final GitHubService service;
    private UserReposContract.View view;
    private CompositeDisposable compositeDisposable;

    public UserReposPresenter(UserReposContract.View view, CompositeDisposable compositeDisposable, GitHubService service) {
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        this.service = service;
    }

    @Override
    public void dispose() {
        if (this.compositeDisposable != null && !this.compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void loadUserInfo(String username) {
        this.compositeDisposable.add(service.getUser(username)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new BiConsumer<User, Throwable>() {
            @Override
            public void accept(User user, Throwable throwable) throws Exception {
                if (user != null ) {
                    view.showUserInfo(user);
                } else {
                    view.showError();
                }
            }
        }));
    }

    @Override
    public void attachView(UserReposContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        this.compositeDisposable = new CompositeDisposable();
    }

}
