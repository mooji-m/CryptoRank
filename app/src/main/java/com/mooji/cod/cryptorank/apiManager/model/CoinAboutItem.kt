package com.mooji.cod.cryptorank.apiManager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinAboutItem(

    var coinWebsite:String? = "N/A",
    var coinGithub:String? = "N/A",
    var coinTwitter:String? = "N/A",
    var coinReddit:String? = "N/A",
    var coinDesc:String? = "No information found"


) : Parcelable