package com.example.twitterclone

import com.google.gson.annotations.SerializedName
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class Tweet(
    @SerializedName("")
    var Image: String,
    @SerializedName("")
    var Name: String,
    @SerializedName("")
    var Login: String,
    @SerializedName("")
    var Time: String,
    @SerializedName("")
    var Description: String,
    @SerializedName("")
    var countComents: String,
    @SerializedName("")
    var countRetweets: String,
    @SerializedName("")
    var countFavoriteds: String
        )

class TweetBuilder{

    var Image: String = ""
    var Name: String = ""
    var Login: String = ""
    var Time: String = ""
    var Description: String = ""
    var countComents: String = ""
    var countRetweets: String = ""
    var countFavoriteds: String = ""

    fun build(): Tweet = Tweet(
        Image,
        Name,
        Login,
        Time,
        Description,
        countComents,
        countRetweets,
        countFavoriteds
    )
}

object TwitterKeys {
    val consumerKey = "3SrIURfBF6QqTTOoFb6eJqM0i"
    val consumerSecret = "PxryF7AmbQQRDlCAORKyOK2zDA5L2aT5tIEatQohan9Ru2LdVy"
    val CALLBACKURL = "josebiasneto-android:///"
}