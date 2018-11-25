package me.immathan.stockersample.utils

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemOffsetDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int) : this(context.resources.getDimensionPixelSize(itemOffsetId)) {}

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if(position == 0) {
            outRect.set(mItemOffset, mItemOffset / 2, mItemOffset / 2, mItemOffset / 2)
            return
        }
        if(position == 1) {
            outRect.set(mItemOffset / 2 , mItemOffset / 2, mItemOffset, mItemOffset / 2)
            return
        }
        if(position % 2 == 0) {
            outRect.set(mItemOffset, mItemOffset / 2, mItemOffset / 2, mItemOffset / 2)
        } else {
            outRect.set(mItemOffset / 2, mItemOffset / 2, mItemOffset, mItemOffset / 2)
        }
    }
}