package com.example.movieapp.ui.feature.colllection

import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import kotlin.math.abs

class SliderCard : ViewPager2.PageTransformer {

    private val nextItemVisibleWidth: Float
    private val currentItemMargin: Float
    private val pageTranslation: Float

    constructor(nextItemVisibleWidth: Float, currentItemMargin: Float) {
        this.nextItemVisibleWidth = nextItemVisibleWidth
        this.currentItemMargin = currentItemMargin
        this.pageTranslation = nextItemVisibleWidth + currentItemMargin
    }

    override fun transformPage(page: View, position: Float) {
        page.translationX = -pageTranslation * position
        page.scaleY = 1 - (0.25f * abs(position))
        page.alpha = 0.25f + (1 - abs(position))
    }
}
