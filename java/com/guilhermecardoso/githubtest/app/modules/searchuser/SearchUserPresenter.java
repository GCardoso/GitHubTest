package com.guilhermecardoso.githubtest.app.modules.searchuser;

import com.guilhermecardoso.githubtest.domain.network.entity.Repo;
import com.guilhermecardoso.githubtest.domain.network.services.GitHubService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guilhermecardoso on 11/27/17.
 */

class SearchUserPresenter implements SearchUserContract.Actions {

    private SearchUserContract.View view;
    private CompositeDisposable compositeDisposable;
    private final GitHubService service;

    public SearchUserPresenter(SearchUserContract.View view, CompositeDisposable compositeDisposable, GitHubService service) {
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        this.service = service;
    }

    @Override
    public void searchUser(String username) {
        view.showLoading();

        compositeDisposable.add(service.getUserRepos(username)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new BiConsumer<List<Repo>, Throwable>() {
            @Override
            public void accept(List<Repo> repos, Throwable throwable) throws Exception {
                view.hideLoading();

                if (repos != null) {
                    if (repos.size() > 0) {
                        view.navigateToUserRepos(repos);
                    } else {
                        view.showUserNotFoundError();
                    }
                } else {
                    view.showNetworkError();
                }
            }
        }));
    }

    @Override
    public void attachView(SearchUserContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void dispose() {
        if (this.compositeDisposable!= null && !this.compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }
    }
}
