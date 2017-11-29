package com.guilhermecardoso.githubtest.app.modules.searchuser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.guilhermecardoso.githubtest.R;
import com.guilhermecardoso.githubtest.app.modules.userrepos.UserReposActivity;
import com.guilhermecardoso.githubtest.domain.network.entity.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by guilhermecardoso on 11/27/17.
 */

public class SearchUserFragment extends Fragment implements SearchUserContract.View {

    private SearchUserContract.Actions presenter;
    private SweetAlertDialog dialog;
    private static String TAG = "SearchUserFragment";

    @BindView(R.id.edit_username)
    EditText editUsername;

    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_search_user, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void setPresenter(SearchUserContract.Actions presenter) {
        this.presenter = presenter;
        presenter.subscribe();
    }



    @Override
    public void navigateToUserRepos(List<Repo> repos) {
        Intent intent = new Intent(getActivity(), UserReposActivity.class);

        intent.putParcelableArrayListExtra(UserReposActivity.EXTRA_REPOS_LIST, new ArrayList<Repo>(repos));
        intent.putExtra(UserReposActivity.EXTRA_USERNAME, editUsername.getText().toString().toLowerCase());

        startActivity(intent);
    }

    @OnClick(R.id.button_search_user)
    @Override
    public void searchUser() {
        presenter.searchUser(editUsername.getText().toString().toLowerCase());
    }

    @Override
    public void showLoading() {
        FragmentActivity activity = getActivity();
        if (dialog == null && activity != null) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading");
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismissWithAnimation();
            dialog = null;
        }
    }

    @Override
    public void showNetworkError() {
        FragmentActivity activity = getActivity();
        if (dialog == null && activity != null) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText("Some error occurred");
            dialog.setContentText("A network error has occurred. Check your Internet connection and try again later");
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        presenter.dispose();
        super.onDestroyView();
    }

    @Override
    public void showUserNotFoundError() {
        FragmentActivity activity = getActivity();
        if (dialog == null && activity != null) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText("Some error occurred");
            dialog.setContentText("User not found. Please enter another name");
            dialog.setCancelable(true);
            dialog.show();
        }
    }
}
