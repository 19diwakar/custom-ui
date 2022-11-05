package co.diwakar.customui.presentation.first_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.diwakar.customui.R
import co.diwakar.customui.constants.Extras
import co.diwakar.customui.domain.model.CustomUI
import co.diwakar.customui.domain.model.UiData
import co.diwakar.customui.presentation.second_activity.SecondActivity
import co.diwakar.customui.utils.gone
import co.diwakar.customui.utils.isUserInputAdded
import co.diwakar.customui.utils.mappedWithLabel
import co.diwakar.customui.utils.visible
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstActivity : AppCompatActivity() {

    private val firstActivityViewModel: FirstActivityViewModel by viewModels()

    private val onEventListener = object : OnEventListener {
        override fun onClick(uiDataList: List<UiData>, uiData: UiData) {
            if (uiDataList.isUserInputAdded()) {
                Intent(
                    this@FirstActivity,
                    SecondActivity::class.java
                ).apply {
                    putParcelableArrayListExtra(
                        Extras.UI_DATA_LIST,
                        uiDataList.mappedWithLabel() as ArrayList<out Parcelable>
                    )
                    startActivity(this)
                }
            } else {
                showMessage("Enter your details first!!!")
            }
        }
    }
    private val customUIAdapter = CustomUIAdapter(onEventListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAdapter()
        lifecycleScope.launch {
            firstActivityViewModel.state.collect(flowCollector)
        }
        firstActivityViewModel.getCustomUi()
    }

    private fun setupAdapter() {
        customUIView.layoutManager = LinearLayoutManager(this)
        customUIView.adapter = customUIAdapter
    }

    private val flowCollector: FlowCollector<FirstActivityState> = FlowCollector { state ->
        setupLoadingUI(state.isLoading)
        showMessage(state.errorMessage)
        setupUI(state.customUI)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupUI(customUI: CustomUI?) {
        customUI?.let { data ->
            data.logoUrl?.let {
                logoView.load(it) {
                    crossfade(true)
                }
            }
            data.headingText?.let { header ->
                headerView.text = header
            }
            data.uidata?.let { uiDataList ->
                customUIAdapter.addUiDataList(uiDataList)
                customUIAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupLoadingUI(isLoading: Boolean) {
        if (isLoading) {
            progressOverlay.visible()
        } else {
            progressOverlay.gone()
        }
    }

    private fun showMessage(message: String?) {
        message?.let {
            Snackbar.make(rootView, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}