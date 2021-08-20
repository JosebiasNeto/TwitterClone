package com.example.twitterclone.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitterclone.Tweet
import com.example.twitterclone.TwitterKeys.CALLBACKURL
import com.example.twitterclone.TwitterKeys.consumerKey
import com.example.twitterclone.TwitterKeys.consumerSecret
import com.example.twitterclone.data.NetworkUtils
import com.example.twitterclone.data.TweetsResponse
import com.example.twitterclone.data.TwitterAPI
import com.example.twitterclone.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tweetsAdapter: TweetsAdapter
    private lateinit var tweetsList: MutableList<Tweet>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tweets = arrayListOf<Tweet>()
        tweetsAdapter = TweetsAdapter(tweets)
        binding.rvTweets.adapter = tweetsAdapter
        binding.rvTweets.layoutManager = LinearLayoutManager(applicationContext)



        val twitter = TwitterFactory().instance
        twitter.setOAuthConsumer(consumerKey, consumerSecret)


    }

    fun clickLogin(v: View?) {
        try {
            val accessToken: AccessToken = loadAccessToken()
            if (accessToken == null) {
                val twitter = TwitterFactory().instance
                twitter.setOAuthConsumer(consumerKey, consumerSecret)
                val requestToken: RequestToken = twitter.getOAuthRequestToken(CALLBACKURL)
                val url = requestToken.authenticationURL
                val it = Intent(
                    Intent.ACTION_VIEW, Uri.parse(url)
                )
                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(it)
                saveRequestData(
                    requestToken.token,
                    requestToken.tokenSecret
                )
            } else {
                val twitter = TwitterFactory().instance
                twitter.setOAuthConsumer(consumerKey, consumerSecret)
                twitter.oAuthAccessToken
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext,"Error", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onResume() {
        super.onResume()
        val uri = intent.data
        if (uri != null) {
            val oauthVerifier = uri.getQueryParameter("oauth_verifier")
            try {
                val requestToken: RequestToken = loadRequestToken()
                val twitter = TwitterFactory().getInstance()
                twitter.setOAuthConsumer(consumerKey,consumerSecret)
                val at: AccessToken = twitter.getOAuthAccessToken(
                    requestToken, oauthVerifier
                )
                saveAccessToken(
                    at.token, at.tokenSecret
                )
            } catch (e: TwitterException) {
                e.printStackTrace()
                Toast.makeText(applicationContext,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun getData(){
        val retrofitClient = NetworkUtils()
            .getRetrofitInstance("https://api.twitter.com/2/")
        val endpoint = retrofitClient.create(TwitterAPI::class.java)
        val callback = endpoint.getTweets()

        callback.enqueue(object : Callback<TweetsResponse> {
            override fun onFailure(call: Call<TweetsResponse>, t: Throwable) {
            }
            override fun onResponse(call: Call<TweetsResponse>, response: Response<TweetsResponse>) {
                if(response.body() != null){
                    tweetsList = response.body()?.tweets!!
                    binding.rvTweets.adapter = TweetsAdapter(tweetsList)
                    binding.rvTweets.layoutManager = LinearLayoutManager(applicationContext)
                }
            }

        })
    }

    private fun loadRequestToken(): RequestToken {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val reqToken = prefs.getString("request_token", null)
        val reqTokenSecret = prefs.getString("request_tokensecret", null)
        return RequestToken(reqToken, reqTokenSecret)
    }

    private fun saveRequestData(
        requestToken: String, requestTokenSecret: String
    ) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        editor.putString(
            "request_token", requestToken
        )
        editor.putString(
            "request_tokensecret", requestTokenSecret
        )
        editor.apply()
    }

    private fun loadAccessToken(): AccessToken {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val acToken = prefs.getString("access_token", null)
        val acTokenSecret = prefs.getString("access_tokensecret", null)
        return AccessToken(acToken, acTokenSecret)

    }

    private fun saveAccessToken(
        accessToken: String, accessTokenSecret: String
    ) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        editor.putString(
            "access_token", accessToken
        )
        editor.putString(
            "access_tokensecret", accessTokenSecret
        )
        editor.apply()
    }

}
