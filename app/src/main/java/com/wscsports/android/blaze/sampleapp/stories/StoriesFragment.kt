package com.wscsports.android.blaze.sampleapp.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blaze.blazesdk.ads.models.ui.BlazeStoriesAdsConfigType
import com.blaze.blazesdk.data_source.BlazeDataSourceType
import com.blaze.blazesdk.data_source.BlazeWidgetLabel
import com.blaze.blazesdk.style.players.stories.BlazeStoryPlayerStyle
import com.blaze.blazesdk.style.widgets.BlazeWidgetLayout
import com.wscsports.android.blaze.sampleapp.R
import com.wscsports.android.blaze.sampleapp.core.Delegates
import com.wscsports.android.blaze.sampleapp.databinding.FragmentStoriesBinding

class StoriesFragment : Fragment(R.layout.fragment_stories) {
    private val STORY_ID = "game-stories"

    private var binding: FragmentStoriesBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
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
        binding?.storiesPullToRefresh?.isRefreshing = false
    }

    /**
     * set and update the pullToRefresh layout Listener
     */
    private fun initListeners() {
        binding?.apply {
            storiesPullToRefresh.setOnRefreshListener {
                updateDataSource()
                storiesPullToRefresh.isRefreshing = false
            }
        }
    }

    /**
     * Used to showcase options to change data source type and or refresh data for current data source.
     */
    private fun updateDataSource() {
        binding?.apply {

            storyRowWidget.updateDataSource(
                dataSourceType = BlazeDataSourceType.Labels(
                    BlazeWidgetLabel.singleLabel(STORY_ID)
                )
            )

            storyGridWidget.updateDataSource(
                dataSourceType = BlazeDataSourceType.Labels(
                    BlazeWidgetLabel.singleLabel(STORY_ID)
                )
            )

            storyRowWidget.reloadData()
            storyGridWidget.reloadData()
        }
    }


    private fun initRowWidget() {
        // Using default Preset
        val storiesWidgetRowPreset = BlazeWidgetLayout.Presets.StoriesWidget.Row.circles

        // Using default player Preset
        val storiesPlayerPreset = BlazeStoryPlayerStyle.base()

        // You can modify onboarding experience by setting firstTimeSlide in player style
//         storiesPlayerPreset.firstTimeSlide.mainTitle.text ="Stories First Time Slide Title"

        // You can modify player buttons experience by setting buttons scaleType in player style
//         storiesPlayerPreset.buttons.exit.scaleType = ImageView.ScaleType.FIT_XY

        // You can modify the format of the last update text.
//         storiesPlayerPreset.lastUpdate.textCase = BlazeTextCase.UPPERCASE

        // Example of customizing ads configuration
        binding?.storyRowWidget?.updateAdsConfigType(
            BlazeStoriesAdsConfigType.EVERY_X_STORIES
        )

        binding?.storyRowWidget?.initWidget(
            widgetLayout = storiesWidgetRowPreset,
            playerStyle = storiesPlayerPreset,
            dataSource = BlazeDataSourceType.Labels(BlazeWidgetLabel.singleLabel(STORY_ID)),
            widgetId = "live-stories-row",
            widgetDelegate = Delegates.widgetDelegate,
            shouldOrderWidgetByReadStatus = true
        )
    }

    private fun initGridWidget() {
        // Using default Preset
        val storiesWidgetGridPreset = BlazeWidgetLayout.Presets.StoriesWidget.Grid.twoColumnsVerticalRectangles

        // Using default player Preset
        val storiesPlayerPreset = BlazeStoryPlayerStyle.base()

        //You can modify onboarding experience by setting firstTimeSlide in playerTheme
//        storiesPlayerPreset.firstTimeSlide.mainTitle.text ="Stories First Time Slide Title"

        //You can modify player buttons experience by setting buttons scaleType in playerTheme
//        storiesPlayerPreset.buttons.exit.scaleType = ImageView.ScaleType.FIT_XY

        // We can modify the given presets. i.e.,
        // Limit the amount of items shown on the widget level
//        storiesWidgetGridPreset.maxDisplayItemsCount = 4

        // Example of customizing ads configuration
        binding?.storyRowWidget?.updateAdsConfigType(
            BlazeStoriesAdsConfigType.FIXED_PAGES_INDEX
        )

        binding?.storyGridWidget?.initWidget(
            widgetLayout = storiesWidgetGridPreset,
            playerStyle = storiesPlayerPreset,
            dataSource = BlazeDataSourceType.Labels(BlazeWidgetLabel.singleLabel(STORY_ID)),
            widgetId = "top-stories-grid",
            widgetDelegate = Delegates.widgetDelegate,
            shouldOrderWidgetByReadStatus = true
        )
    }

}