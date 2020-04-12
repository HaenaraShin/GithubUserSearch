package dev.haenara.githubsearch.api

import dev.haenara.githubsearch.BuildConfig

const val SEARCH_URL = "https://api.github.com"
const val AUTH_TOKEN = BuildConfig.GITHUB_DEVELOPER_TOKEN
const val AUTH_HEADER = BuildConfig.HEADER_AUTH
const val PER_PAGE = 30

const val LOG_TAG = "GSTARS"