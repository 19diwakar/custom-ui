package co.diwakar.customui.presentation.first_activity

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import co.diwakar.customui.R
import co.diwakar.customui.domain.model.UiData
import co.diwakar.customui.utils.UIType
import kotlinx.android.synthetic.main.layout_item_button.view.*
import kotlinx.android.synthetic.main.layout_item_editfield.view.*
import kotlinx.android.synthetic.main.layout_item_label.view.*

class CustomUIAdapter(
    private val onEventListener: OnEventListener
) : RecyclerView.Adapter<BaseViewHolder<UiData>>() {
    private val uiDataList = mutableListOf<UiData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UiData> {
        return when (viewType) {
            ITEM_LABEL -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_item_label, parent, false)
                LabelViewHolder(view)
            }
            ITEM_EDITFIELD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_item_editfield, parent, false)
                EditFieldViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_item_button, parent, false)
                ButtonViewHolder(view, onClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<UiData>, position: Int) {
        val uiData = uiDataList[position]
        when (getItemViewType(position)) {
            ITEM_LABEL -> {
                (holder as LabelViewHolder).onBind(uiData)
            }
            ITEM_EDITFIELD -> {
                (holder as EditFieldViewHolder).onBind(uiData)
            }
            else -> {
                (holder as ButtonViewHolder).onBind(uiData)
            }
        }
    }

    override fun getItemCount(): Int {
        return uiDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (uiDataList[position].getUIType()) {
            UIType.LABEL -> ITEM_LABEL
            UIType.EDITTEXT -> ITEM_EDITFIELD
            else -> ITEM_BUTTON
        }
    }

    private val onClickListener = OnClickListener {
        val data = it.getTag(R.id.uiData) as UiData
        onEventListener.onClick(uiDataList, data)
    }

    fun addUiDataList(toAdd: List<UiData>) {
        uiDataList.clear()
        uiDataList.addAll(toAdd)
    }

    companion object {
        const val ITEM_LABEL = 1
        const val ITEM_EDITFIELD = 2
        const val ITEM_BUTTON = 3
    }
}

class ButtonViewHolder(itemView: View, private val onClickListener: OnClickListener) :
    BaseViewHolder<UiData>(itemView) {

    override fun onBind(value: UiData) {
        val button = itemView.buttonView
        button.text = value.value ?: "Button"
        button.setTag(R.id.uiData, value)
        button.setOnClickListener(onClickListener)
    }
}

class LabelViewHolder(itemView: View) : BaseViewHolder<UiData>(itemView) {

    override fun onBind(value: UiData) {
        itemView.label.text = value.value
    }
}

class EditFieldViewHolder(itemView: View) :
    BaseViewHolder<UiData>(itemView) {

    override fun onBind(value: UiData) {
        val editText = itemView.editTextView
        editText.hint = value.hint ?: ""
        editText.addTextChangedListener {
            value.userInput = it.toString()
        }
    }
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(value: T)
}

interface OnEventListener {
    fun onClick(uiDataList: List<UiData>, uiData: UiData)
}