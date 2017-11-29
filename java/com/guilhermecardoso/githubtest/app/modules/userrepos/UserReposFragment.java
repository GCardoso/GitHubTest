package com.guilhermecardoso.githubtest.app.modules.userrepos;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guilhermecardoso.githubtest.R;
import com.guilhermecardoso.githubtest.app.modules.userrepos.adapter.RepoAdapter;
import com.guilhermecardoso.githubtest.domain.network.entity.Repo;
import com.guilhermecardoso.githubtest.domain.network.entity.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by guilhermecardoso on 11/28/17.
 */

public class UserReposFragment extends Fragment implements UserReposContract.View {

    private static final String EXTRA_REPOS = "EXTRA_REPOS";
    private static final String EXTRA_USERNAME = "EXTRA_USERNAME";
    private UserReposContract.Actions presenter;
    private SweetAlertDialog dialog;
    private RepoAdapter adapter;

    @BindView(R.id.drawee_user_img)
    SimpleDraweeView draweeUserImg;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_collapsing)
    CollapsingToolbarLayout toolbarLayout;

    public static Fragment newInstance(ArrayList<Repo> repos, String username) {
        UserReposFragment instance = new UserReposFragment();

        if (repos != null) {
            Bundle bundle = new Bundle();

            bundle.putParcelableArrayList(EXTRA_REPOS, repos);
            bundle.putString(EXTRA_USERNAME, username);

            instance.setArguments(bundle);
        }

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_user_repos, container, false);
        ButterKnife.bind(this, rootView);

        RecyclerView recyclerView = ButterKnife.findById(rootView, R.id.recycler_view);
        adapter = new RepoAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Bundle args = getArguments();

        if (args != null && args.containsKey(EXTRA_REPOS)) {
            adapter.addRepos(args.<Repo>getParcelableArrayList(EXTRA_REPOS));
        }

        if (args != null && args.containsKey(EXTRA_USERNAME)) {
            presenter.loadUserInfo(args.getString(EXTRA_USERNAME));
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return rootView;
    }

    @Override
    public void showUserInfo(User user) {
        Uri uri = Uri.parse(user.getAvatarURL());
        draweeUserImg.setImageURI(uri);

        toolbarLayout.setTitle(user.getName());
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
    public void showError() {
        FragmentActivity activity = getActivity();
        if (dialog == null && activity != null) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText("Some error occurred");
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    @Override
    public void setPresenter(UserReposContract.Actions presenter) {
        this.presenter = presenter;
        presenter.subscribe();
    }
}
