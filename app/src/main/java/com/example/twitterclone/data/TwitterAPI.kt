package com.example.twitterclone.data

import retrofit2.http.GET
import twitter4j.ResponseList
import twitter4j.Status
import twitter4j.TwitterFactory


interface TwitterAPI {

    @GET("tweets/search/recent?query=from:twitterdev")
    fun getTweets(): retrofit2.Call<TweetsResponse>

    val twitter: ResponseList<Status>?
        get() = TwitterFactory().instance
            .userTimeline


}

