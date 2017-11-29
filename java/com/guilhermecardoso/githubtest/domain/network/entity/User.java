package com.guilhermecardoso.githubtest.domain.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guilhermecardoso on 11/28/17.
 */

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("avatar_url")
    private String avatarURL;

    public User() {
    }

    public User(String name, String avatarURL) {
        this.name = name;
        this.avatarURL = avatarURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
