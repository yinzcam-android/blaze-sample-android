package com.wscsports.android.blaze.sampleapp.moments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blaze.blazesdk.data_source.BlazeDataSourceType
import com.blaze.blazesdk.data_source.BlazeWidgetLabel
import com.blaze.blazesdk.style.players.moments.BlazeMomentsPlayerFooterGradientStyle
import com.blaze.blazesdk.style.players.moments.BlazeMomentsPlayerStyle
import com.blaze.blazesdk.style.widgets.BlazeWidgetLayout
import com.wscsports.android.blaze.sampleapp.R
import com.wscsports.android.blaze.sampleapp.core.Delegates
import com.wscsports.android.blaze.sampleapp.databinding.FragmentMomentsBinding

class MomentsFragment : Fragment(R.layout.fragment_moments) {
    val MOMENT_ID = "all-access"

    private var binding: FragmentMomentsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMomentsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRowWidget()
        initGridWidget()
    }

    override fun onResume() {
        super.onResume()
        binding?.momentsPullToRefresh?.isRefreshing = false
    }

    /**
     * set and update the pullToRefresh layout Listener
     */
    private fun initListeners() {
        binding?.apply {
            momentsPullToRefresh.setOnRefreshListener {
                updateDataSource()
                momentsPullToRefresh.isRefreshing = false
            }
        }
    }

    /**
     * Used to showcase options to change data source type and or refresh data for current data source.
     */
    private fun updateDataSource() {
        binding?.apply {

            momentsRowWidget.updateDataSource(
                dataSourceType = BlazeDataSourceType.Labels(
                    BlazeWidgetLabel.singleLabel(MOMENT_ID)
                )
            )

            momentsGridWidget.updateDataSource(
                dataSourceType = BlazeDataSourceType.Labels(
                    BlazeWidgetLabel.singleLabel(MOMENT_ID)
                )
            )

            momentsRowWidget.reloadData()
            momentsGridWidget.reloadData()
        }
    }


    private fun initRowWidget() {
        // Using rectangles Preset
        val momentsWidgetRowPreset = BlazeWidgetLayout.Presets.MomentsWidget.Row.verticalRectangles.apply{
            widgetItemStyle.statusIndicator.unreadState.apply{
                textStyle.textColor  = android.graphics.Color.GREEN
                backgroundColor = android.graphics.Color.RED
                isVisible = true
            }
        }



        // Using default player Preset
        val momentsPlayerPreset = BlazeMomentsPlayerStyle.base()

        //You can modify onboarding experience by setting firstTimeSlide in player style
        // momentsPlayerPreset.firstTimeSlide.mainTitle.text ="Moments First Time Slide Title"

        //You can modify player buttons experience by setting buttons scaleType in player style
        // momentsPlayerPreset.buttons.exit.scaleType = ImageView.ScaleType.FIT_XY

        binding?.momentsRowWidget?.initWidget(
            widgetLayout = momentsWidgetRowPreset,
            playerStyle = momentsPlayerPreset,
            dataSource = BlazeDataSourceType.Labels(BlazeWidgetLabel.singleLabel(MOMENT_ID)),
            widgetId = "moments-row",
            widgetDelegate = Delegates.widgetDelegate,
            shouldOrderWidgetByReadStatus = true
        )
    }

    private fun initGridWidget() {
        // Using 2 columns  Preset
        val momentsWidgetGridPreset = BlazeWidgetLayout.Presets.MomentsWidget.Grid.twoColumnsVerticalRectangles.apply{
            widgetItemStyle.statusIndicator.unreadState.apply{
                textStyle.textColor  = android.graphics.Color.GREEN
                backgroundColor = android.graphics.Color.RED
                isVisible = true
            }
        }

        // Using default player Preset
        val momentsPlayerPreset = BlazeMomentsPlayerStyle.base()

        // You can modify onboarding experience by setting firstTimeSlide in player style
        // momentsPlayerPreset.firstTimeSlide.mainTitle.text ="Moments First Time Slide Title"

        // You can modify player buttons experience by setting buttons scaleType in player style
         // momentsPlayerPreset.buttons.exit.scaleType = ImageView.ScaleType.FIT_XY

        // You can modify the end positioning of the bottom gradient.
        momentsPlayerPreset.footerGradient.endPositioning = BlazeMomentsPlayerFooterGradientStyle.BlazeEndPositioning.BOTTOM_TO_CONTAINER

        // We can modify the given presets. i.e.,
        // Limit the amount of items shown on the widget level
        momentsWidgetGridPreset.maxDisplayItemsCount = 4

        binding?.momentsGridWidget?.initWidget(
            widgetLayout = momentsWidgetGridPreset,
            playerStyle = momentsPlayerPreset,
            dataSource = BlazeDataSourceType.Labels(BlazeWidgetLabel.singleLabel(MOMENT_ID)),
            widgetId = "moments-grid",
            widgetDelegate = Delegates.widgetDelegate,
            shouldOrderWidgetByReadStatus = true
        )
    }

}