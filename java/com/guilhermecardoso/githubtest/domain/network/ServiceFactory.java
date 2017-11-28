package com.guilhermecardoso.githubtest.domain.network;

import com.guilhermecardoso.githubtest.domain.network.interceptor.AuthenticationInterceptor;
import com.guilhermecardoso.githubtest.domain.network.interceptor.LoggingInterceptor;
import com.guilhermecardoso.githubtest.domain.network.services.GitHubService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(GitHubService.SERVICE_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    public static <S> S createService(
            Class<S> serviceClass) {
        AuthenticationInterceptor interceptor;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (GitHubService.SERVICE_TOKEN != null) {
            interceptor = new AuthenticationInterceptor(GitHubService.SERVICE_TOKEN);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
            }
        }

        httpClient.addInterceptor(new LoggingInterceptor());

        builder.client(httpClient.build());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit = builder.build();


        return retrofit.create(serviceClass);
    }

}