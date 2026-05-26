package com.mooji.cod.cryptorank.features.marketActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mooji.cod.cryptorank.apiManager.ApiManager
import com.mooji.cod.cryptorank.apiManager.model.CoinAboutData
import com.mooji.cod.cryptorank.apiManager.model.CoinAboutItem
import com.mooji.cod.cryptorank.apiManager.model.CoinsData
import com.mooji.cod.cryptorank.databinding.ActivityMarketBinding
import com.mooji.cod.cryptorank.features.coinActivity.CoinActivity
import java.util.*
import kotlin.collections.ArrayList


class MarketActivity : AppCompatActivity(), MarketAdapter.RecyclerCallback {
    lateinit var binding: ActivityMarketBinding
    lateinit var dataNews: ArrayList<Pair<String, String>>
    lateinit var aboutDataMap: MutableMap<String, CoinAboutItem>
    val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMarketBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.moduleToolbar.toolbar.title = "Crypto Rank"

        binding.layoutWatchlist.btnShowMore.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }

        binding.swipeRefresh.setOnRefreshListener {
            initUi()


            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefresh.isRefreshing = false
            }, 1000)

        }


        //اینجا گذاشتیمش نه داخل initUI() چون لوکال و محلیه نیاز نیست هربار بروز رسانی بشه :))
        getAboutDataFromAssets()


    }

    //tag92
    override fun onResume() {
        super.onResume()

        initUi()
    }

    private fun initUi() {

        getNewsFromApi()

        getTopCoinsFromApi()


    }

    private fun getAboutDataFromAssets() {

        //اینجا از context معمولی استفاده نکردیم چون اکتیوتی رو میخوایم عوض کنیم ممکنه Null بشه
        //و از context کل اپ استفاده کردیم
        val fileInString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }


        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString, CoinAboutData()::class.java)

        aboutDataMap = mutableMapOf<String, CoinAboutItem>()
        

        dataAboutAll.forEach {
            //tag99
            aboutDataMap[it.currencyName] =
                CoinAboutItem(
                    it.info.web,
                    it.info.github,
                    it.info.twt,
                    it.info.reddit,
                    it.info.desc
                )
        }

    }

    private fun cleanData(data:List<CoinsData.Data>) : List<CoinsData.Data> {
        val newData = mutableListOf<CoinsData.Data>()

        data.forEach {
            if(it.dISPLAY != null && it.rAW != null) {

                newData.add(it)
            }
        }
        return newData
    }

    private fun getTopCoinsFromApi() {
        apiManager.getCoinsList(object : ApiManager.ApiCallback<List<CoinsData.Data>> {
            override fun onSuccess(data: List<CoinsData.Data>) {
                showDataInRecycler(cleanData(data))
            }

            override fun onError(errorMessage: String) {
                Toast.makeText(this@MarketActivity,
                    "error => " + errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun getNewsFromApi() {
        apiManager.getNews(object : ApiManager.ApiCallback<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                dataNews = data
                refreshNews()
            }

            //tag 90
            override fun onError(errorMessage: String) {
                Toast.makeText(this@MarketActivity, "error => " + errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }


        })
    }

    private fun refreshNews() {
        val anim = AlphaAnimation(0f, 1f)
        anim.duration = 1300
        anim.fillAfter = true

        val randomAccess = (0..49).random()
        binding.layoutNews.txtNews.text = dataNews[randomAccess].first
        binding.layoutNews.txtNews.startAnimation(anim)
        binding.layoutNews.imgNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[randomAccess].second))
            startActivity(intent)

        }
        binding.layoutNews.txtNews.setOnClickListener {
            refreshNews()
        }
    }

    private fun showDataInRecycler(data: List<CoinsData.Data>) {
        //tag 91
        val marketAdapter = MarketAdapter(this, ArrayList(data))

        binding.layoutWatchlist.recyclerMain.adapter = marketAdapter
        binding.layoutWatchlist.recyclerMain.layoutManager = LinearLayoutManager(this)
    }

    override fun onCoinItemClicked(dataCoin: CoinsData.Data) {
        val intent = Intent(this, CoinActivity::class.java)
        val bundle = Bundle()


        bundle.putParcelable("bundle1", dataCoin)
        bundle.putParcelable("bundle2", aboutDataMap[dataCoin.coinInfo.name])

        //tag 100
        intent.putExtra("dataToSend", bundle)
        startActivity(intent)
    }


}















