package me.immathan.stockersample.adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import me.immathan.stocker.Stocker
import me.immathan.stockersample.R
import me.immathan.stockersample.model.PinModel



/**
 * @author Mathan on 25/11/18
 */
class PinsListAdapter(private val stocker: Stocker): RecyclerView.Adapter<PinsListAdapter.PinsViewHolder>() {

    var pinsList: List<PinModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PinsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pin_board_list_item, parent, false)
        return PinsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pinsList?.size ?: 0
    }

    override fun onBindViewHolder(pinsViewHolder: PinsViewHolder, position: Int) {
        val pin = pinsList!![position]
        stocker.fetch(pin.urls.regular) {(body, status, error) ->
            if(status && body != null) {
                val bitmap = BitmapFactory.decodeByteArray(body, 0, body.size)
                pinsViewHolder.image.setImageBitmap(bitmap)
            }
        }
    }

    class PinsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)

    }

}