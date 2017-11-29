package com.guilhermecardoso.githubtest.app.modules.userrepos;

import com.guilhermecardoso.githubtest.BaseActions;
import com.guilhermecardoso.githubtest.domain.network.entity.User;

/**
 * Created by guilhermecardoso on 11/28/17.
 */

public interface UserReposContract {

    interface View {
        void setPresenter(Actions presenter);
        void showError();
        void showLoading();
        void hideLoading();
        void showUserInfo(User user);
    }

    interface Actions extends BaseActions {
        void attachView(View view);
        void loadUserInfo(String username);
    }
}
