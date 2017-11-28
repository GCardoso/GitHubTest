package com.guilhermecardoso.githubtest.domain.network.services;

import com.guilhermecardoso.githubtest.domain.network.entity.Repo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by guilhermecardoso on 11/27/17.
 */

public interface GitHubService {
    String SERVICE_ENDPOINT = "https://api.github.com/users/";
    String SERVICE_TOKEN = null;

    @GET("{username}/repos")
    Single<List<Repo>> getUserRepos(@Path("username") String username);
}
