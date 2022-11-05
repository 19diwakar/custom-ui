package co.diwakar.customui.presentation.second_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.diwakar.customui.R
import co.diwakar.customui.domain.model.UiData
import co.diwakar.customui.presentation.first_activity.BaseViewHolder
import kotlinx.android.synthetic.main.layout_item_ui_data.view.*

class UIDataAdapter : RecyclerView.Adapter<BaseViewHolder<UiData>>() {
    private val uiDataList = mutableListOf<UiData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UiData> {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_ui_data, parent, false)
        return UiDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<UiData>, position: Int) {
        val uiData = uiDataList[position]
        (holder as UiDataViewHolder).onBind(uiData)
    }

    override fun getItemCount(): Int {
        return uiDataList.size
    }

    fun addUiDataList(toAdd: List<UiData>) {
        uiDataList.clear()
        uiDataList.addAll(toAdd)
    }
}

class UiDataViewHolder(itemView: View) :
    BaseViewHolder<UiData>(itemView) {

    override fun onBind(value: UiData) {
        itemView.labelView.text = value.label ?: ""
        itemView.outputView.text = value.userInput ?: ""
    }
}