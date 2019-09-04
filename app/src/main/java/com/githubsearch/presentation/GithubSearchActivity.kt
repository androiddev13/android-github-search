package com.githubsearch.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.githubsearch.R
import com.githubsearch.data.RepoRepository
import dagger.android.AndroidInjection
import javax.inject.Inject

class GithubSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
