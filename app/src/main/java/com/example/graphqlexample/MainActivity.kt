package com.example.graphqlexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.FeedResultQuery
import okhttp3.OkHttpClient
import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var apolloClient: ApolloClient
    val BASE_URL = "https://rickandmortyapi.com/graphql"
    lateinit var response: ApolloResponse<FeedResultQuery.Data>
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        apolloClient = ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
        GlobalScope.launch {
            val start = System.currentTimeMillis()
            response = fetchdata()
            Log.d("CharactersList","${response.data}")
            val finish = System.currentTimeMillis()
            val timeElapsed = finish - start
            Log.d("asd", timeElapsed.toString())
        }
    }
    suspend fun fetchdata() : ApolloResponse<FeedResultQuery.Data> {
        return apolloClient.query(FeedResultQuery()).execute()
    }
}