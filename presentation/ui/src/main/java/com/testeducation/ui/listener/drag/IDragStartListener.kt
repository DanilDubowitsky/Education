package com.testeducation.ui.listener.drag

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView.ViewHolder

interface IDragStartListener {
    var itemTouchHelper : ItemTouchHelper?
    fun oDragStarted(viewHolder: ViewHolder)
}