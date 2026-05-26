package com.mooji.cod.cryptorank.features.marketActivity

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mooji.cod.cryptorank.R
import com.mooji.cod.cryptorank.apiManager.BASE_URL_IMAGE
import com.mooji.cod.cryptorank.apiManager.model.CoinsData
import com.mooji.cod.cryptorank.databinding.ItemRecyclerMarketBinding
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class MarketAdapter(
    private val recyclerCallback: RecyclerCallback,
    private val data:ArrayList<CoinsData.Data>) :RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    lateinit var binding: ItemRecyclerMarketBinding
    inner class MarketViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindViews(dataCoin: CoinsData.Data)  {

            if (dataCoin.dISPLAY != null && dataCoin.rAW != null) {

                binding.txtConName.text = dataCoin.coinInfo.fullName

                val price = dataCoin.rAW.uSD.pRICE
                if (price > 0) {
                    val change = "%,.2f".format(dataCoin.rAW.uSD.pRICE)
                    binding.txtPrice.text = "$ " + change
                } else if (price < 0) {
                    val change = String.format("%.4f", dataCoin.rAW.uSD.pRICE.toString())
                    binding.txtPrice.text = "$ " + change


                }

                binding.txtMarketCap.text =
                    "$ " + String.format("%.2f", dataCoin.rAW.uSD.mKTCAP / 1000000000) + " B"

                val change = dataCoin.rAW.uSD.cHANGEPCT24HOUR
                if (change > 0) {
                    binding.txtChange.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorGain
                        )
                    )
                    val repair = dataCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4)
                    binding.txtChange.text = "+" + repair + "%"
                } else if (change < 0) {
                    binding.txtChange.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorLoss
                        )
                    )
                    val repair = dataCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 5)
                    binding.txtChange.text = repair + "%"
                } else {
                    binding.txtChange.text = "0.00%"
                }



                Glide
                    .with(itemView)
                    .load(BASE_URL_IMAGE + dataCoin.coinInfo.imageUrl)
                    .into(binding.imgItem)

                itemView.setOnClickListener {
                    recyclerCallback.onCoinItemClicked(dataCoin)
                }
            }
        }



    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {

        binding = ItemRecyclerMarketBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MarketViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
            holder.bindViews(data[position])

    }

    override fun getItemCount(): Int = data.size

    interface RecyclerCallback {

        fun onCoinItemClicked(dataCoin:CoinsData.Data)
    }
}