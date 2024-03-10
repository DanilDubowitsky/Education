package com.testeducation.ui.listener

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class QuestionItemTouchHelperCallback(
    private val updateResultMove: (Int, Int) -> Unit,
    private val onClearView: (ViewHolder) -> Unit,
    private val onDragStateChanged: ((Boolean, Int, Int) -> Unit)? = null,
    private val onSelectChanged: ((ViewHolder) -> Unit)? = null
) : ItemTouchHelper.Callback() {

    private var targetPosition: Int = 0
    private var oldPosition: Int = 0
    private var isItemMoved: Boolean = false

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        onClearView(viewHolder)
        super.clearView(recyclerView, viewHolder)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ACTION_STATE_IDLE
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        targetPosition = target.absoluteAdapterPosition
        oldPosition = viewHolder.absoluteAdapterPosition
        updateResultMove(viewHolder.absoluteAdapterPosition, target.absoluteAdapterPosition)
        isItemMoved = true
        return true
    }

    override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (viewHolder != null) {
            onSelectChanged?.invoke(viewHolder)
        }
        if (actionState == ACTION_STATE_IDLE && isItemMoved) {
            onDragStateChanged?.invoke(false, oldPosition, targetPosition)
            isItemMoved = false
        }
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) = Unit

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

}