package com.testeducation.ui.listener

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView

class QuestionItemTouchHelperCallback(
    private val updateResultMove: (Int, Int) -> Unit,
    private val onClearView: () -> Unit,
    private val onDragStateChanged: ((Boolean, Int, Int) -> Unit)? = null
) : ItemTouchHelper.Callback() {

    private var targetPosition: Int = 0
    private var oldPosition: Int = 0
    private var isItemMoved: Boolean = false

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        onClearView()
        super.clearView(recyclerView, viewHolder)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ACTION_STATE_IDLE
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        targetPosition = target.absoluteAdapterPosition
        oldPosition = viewHolder.absoluteAdapterPosition
        updateResultMove(viewHolder.absoluteAdapterPosition, target.absoluteAdapterPosition)
        isItemMoved = true
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ACTION_STATE_IDLE && isItemMoved) {
            onDragStateChanged?.invoke(false, oldPosition, targetPosition)
            isItemMoved = false
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

}