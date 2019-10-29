package com.example.newapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_character.view.*
import android.content.Intent


class RecyclerViewAdapter(
    val context: Context,
    val data: MutableList<Character>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_character, parent, false)
        var holder: ViewHolder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*Glide
            .with(context)
            .load(data[position].)
            .into(holder.image)*/

        val currentItem = data[position]

        // put the data
        if (currentItem.gender.equals("Male")) {
            Glide
                .with(context)
                .load("")
                .placeholder(R.drawable.male)
                .error(R.drawable.male)
                .fallback(R.drawable.male)
                .into(holder.image)
        } else {
            Glide
                .with(context)
                .load("")
                .placeholder(R.drawable.femelle)
                .error(R.drawable.femelle)
                .fallback(R.drawable.femelle)
                .into(holder.image)
        }
        holder!!.name.text = currentItem.name
        holder.itemView.setOnClickListener {


                val explicitIntent = Intent(context, SecondActivity::class.java)
                explicitIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                // Insert extra data in the intent
                explicitIntent.putExtra("ID", data[position].id)
                context.startActivity(explicitIntent)

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.gender_character;
        val name: TextView = itemView.char_name;
    }
}