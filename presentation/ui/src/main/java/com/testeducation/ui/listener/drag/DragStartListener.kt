package com.testeducation.ui.listener.drag

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragStartListener(override var itemTouchHelper: ItemTouchHelper? = null): IDragStartListener {
    override fun oDragStarted(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper?.startDrag(viewHolder)
    }
}