package com.example.twitterclone.model.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitterclone.Tweet
import com.example.twitterclone.data.NetworkUtils
import com.example.twitterclone.data.TweetsResponse
import com.example.twitterclone.data.TwitterAPI
import com.example.twitterclone.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tweetsAdapter: TweetsAdapter
    private lateinit var tweetsList: MutableList<Tweet>
    private lateinit var mViewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tweets = arrayListOf<Tweet>()
        tweetsAdapter = TweetsAdapter(tweets)
        binding.rvTweets.adapter = tweetsAdapter
        binding.rvTweets.layoutManager = LinearLayoutManager(applicationContext)

        mViewModel._tweetStream.startTweetStream()



    }

    fun getData() {
        val retrofitClient = NetworkUtils()
            .getRetrofitInstance("https://api.twitter.com/2/")
        val endpoint = retrofitClient.create(TwitterAPI::class.java)
        val callback = endpoint.getTweets()

        callback.enqueue(object : Callback<TweetsResponse> {
            override fun onFailure(call: Call<TweetsResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<TweetsResponse>,
                response: Response<TweetsResponse>
            ) {
                if (response.body() != null) {
                    tweetsList = response.body()?.tweets!!
                    binding.rvTweets.adapter = TweetsAdapter(tweetsList)
                    binding.rvTweets.layoutManager = LinearLayoutManager(applicationContext)
                }
            }

        })
    }

}


