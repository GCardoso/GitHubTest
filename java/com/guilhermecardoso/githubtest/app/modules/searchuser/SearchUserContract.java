package com.guilhermecardoso.githubtest.app.modules.searchuser;

import com.guilhermecardoso.githubtest.BaseActions;
import com.guilhermecardoso.githubtest.domain.network.entity.Repo;

import java.util.List;

/**
 * Created by guilhermecardoso on 11/27/17.
 */

public interface SearchUserContract {

    interface View {
        void navigateToUserRepos(List<Repo> repos);
        void searchUser();
        void showUserNotFoundError();
        void showNetworkError();
        void showLoading();
        void hideLoading();
        void setPresenter(Actions presenter);
    }

    interface Actions extends BaseActions {
        void searchUser(String username);
        void attachView(View view);
    }
}
