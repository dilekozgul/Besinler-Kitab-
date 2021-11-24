package com.dilekozgul.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dilekozgul.besinlerkitabi.R
import com.dilekozgul.besinlerkitabi.model.Food
import com.dilekozgul.besinlerkitabi.util.downloadImage
import com.dilekozgul.besinlerkitabi.util.makePlaceholder
import com.dilekozgul.besinlerkitabi.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*
import kotlinx.android.synthetic.main.fragment_food_detail.view.*

class FoodAdapter(val FoodList : ArrayList<Food>): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_recycler_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemView.txtFoodName.text = FoodList.get(position).name
        holder.itemView.txtFoodCalorie.text= FoodList.get(position).calorie
        holder.itemView.recyclerFoodImage.downloadImage(FoodList.get(position).foodImage, makePlaceholder(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(FoodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
      return FoodList.size
    }

    fun foodListUpdate(newFoodList: List<Food>)
    {
        FoodList.clear()
        FoodList.addAll(newFoodList)
        notifyDataSetChanged()

    }

}