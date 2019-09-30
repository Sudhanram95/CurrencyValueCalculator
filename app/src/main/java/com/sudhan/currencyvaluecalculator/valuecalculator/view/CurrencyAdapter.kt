package com.sudhan.currencyvaluecalculator.valuecalculator.view

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sudhan.currencyvaluecalculator.R
import com.sudhan.currencyvaluecalculator.valuecalculator.presenter.IValueCalculatorPresenter
import java.lang.Exception

class CurrencyAdapter(var iValueCalculatorPresenter: IValueCalculatorPresenter, var ratesMap: LinkedHashMap<String, Double>) : RecyclerView.Adapter<CurrencyAdapter.Companion.MyViewHolder>() {

    private val flagAndCurrencyMap = iValueCalculatorPresenter.onGetFlagAndCurrencyMap()

    companion object {
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var txtCurrencyAbbreviation: TextView
            var txtCurrency: TextView
            var imgFlag: ImageView
            var edtEquivalentAmount: EditText
            var relRoot: RelativeLayout
            init {
                txtCurrencyAbbreviation = view.findViewById(R.id.txt_currency_abbreviation)
                txtCurrency = view.findViewById(R.id.txt_currency)
                imgFlag = view.findViewById(R.id.img_flag)
                edtEquivalentAmount = view.findViewById(R.id.edt_equivalent_amount)
                relRoot = view.findViewById(R.id.rel_root)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        val myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun getItemCount(): Int {
        return ratesMap.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val currencyList = ArrayList<String>(ratesMap.keys)
        val valueList = ArrayList<Double>(ratesMap.values)
        holder.txtCurrencyAbbreviation.text = currencyList.get(position)
        holder.txtCurrency.text = flagAndCurrencyMap.get(currencyList.get(position))!!.currencyFullName
        Picasso.get().load(flagAndCurrencyMap.get(currencyList.get(position))!!.flag)
            .placeholder(R.drawable.ic_flag_loading)
            .error(R.drawable.ic_default_flag)
            .into(holder.imgFlag)
        holder.edtEquivalentAmount.tag = "converter tag"
        holder.edtEquivalentAmount.setText(iValueCalculatorPresenter.onRoundCurrencyValue(valueList.get(position)))
        holder.edtEquivalentAmount.tag = null

        if (position == 0) {
            holder.edtEquivalentAmount.isFocusable = true
            holder.edtEquivalentAmount.isFocusableInTouchMode = true
        }

        holder.relRoot.setOnClickListener {
            iValueCalculatorPresenter.onDisposeApiCall()
            iValueCalculatorPresenter.onChangeBaseCurrency(currencyList.get(position), valueList.get(position))
        }

        holder.edtEquivalentAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (holder.edtEquivalentAmount.tag == null) {
                    Log.e("Adapter", "Position: $position")
                    val baseAmount = try {holder.edtEquivalentAmount.text.toString().toDouble()}
                        catch (e: Exception) {0.00}
                    Handler().postDelayed({
                        iValueCalculatorPresenter.onDisposeApiCall()
                        iValueCalculatorPresenter.onGetContinuousCurrencyRates(currencyList.get(position), baseAmount)
                    }, 600)
                }
            }
        })
    }

    fun updateCurrencyValue(updatedRatesMap: LinkedHashMap<String, Double>, doNotifiyBaseAdapter: Boolean) {
        ratesMap = updatedRatesMap
        if (doNotifiyBaseAdapter)
            notifyDataSetChanged()
        else
            notifyItemRangeChanged(1, ratesMap.size)
    }
}