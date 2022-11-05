package co.diwakar.customui.presentation.second_activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.diwakar.customui.R
import co.diwakar.customui.constants.Extras
import co.diwakar.customui.domain.model.UiData
import co.diwakar.customui.utils.getParcelableArrayList
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    private val uiDataAdapter = UIDataAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setupAdapter()
        setupData()
    }

    private fun setupAdapter() {
        uiDataView.layoutManager = LinearLayoutManager(this)
        uiDataView.adapter = uiDataAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupData() {
        intent.getParcelableArrayList<UiData>(Extras.UI_DATA_LIST)?.let {
            uiDataAdapter.addUiDataList(it)
            uiDataAdapter.notifyDataSetChanged()
        }
    }
}