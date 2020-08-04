package com.salon.salon.interfaces

import android.support.v4.view.ViewPager

class ViewPageChangeListener(private val completion: (Int)-> (Unit)): ViewPager.OnPageChangeListener {


    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageSelected(p0: Int) {
        completion(p0)
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }
}