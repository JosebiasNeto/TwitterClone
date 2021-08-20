package com.example.twitterclone.model.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth2
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.tweets.lookup.TweetsLookup
import com.tycz.tweedle.lib.tweets.stream.TweetsStream
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val oauth2 = OAuth2(token = "")
    private val _tweetLookup: TweetsLookup = TweetsLookup(oauth2)
    val _tweetStream = TweetsStream(oauth2)


    @ExperimentalApi
    fun getTweet(tweetId:Long): LiveData<Response<SingleTweetPayload?>> {
        val liveData:MutableLiveData<Response<SingleTweetPayload?>> = MutableLiveData<Response<SingleTweetPayload?>>()
        viewModelScope.launch{
            val response = _tweetLookup.getTweet(tweetId)
            liveData.postValue(response)
        }
        return liveData
    }



}