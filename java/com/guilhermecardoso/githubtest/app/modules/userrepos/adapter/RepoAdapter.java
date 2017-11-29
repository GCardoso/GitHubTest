package com.guilhermecardoso.githubtest.app.modules.userrepos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guilhermecardoso.githubtest.R;
import com.guilhermecardoso.githubtest.domain.network.entity.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guilhermecardoso on 11/28/17.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder>{

    List<Repo> repos;

    public RepoAdapter() {
        repos = new ArrayList<>();
    }

    public void addRepos(List<Repo> repos) {
        this.repos = repos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_repo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo = this.repos.get(position);

        holder.textviewRepoName.setText(repo.getName());
        holder.textviewRepoProgLanguage.setText(repo.getLanguage());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textview_repo_name)
        TextView textviewRepoName;
        @BindView(R.id.textview_repo_prog_language)
        TextView textviewRepoProgLanguage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
