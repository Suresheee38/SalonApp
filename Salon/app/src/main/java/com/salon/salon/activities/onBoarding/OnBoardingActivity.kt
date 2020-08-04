package com.salon.salon.activities.onBoarding

import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.salon.salon.R
import com.salon.salon.interfaces.ViewPageChangeListener
import com.salon.salon.presenters.OnBoardingPresenter

class OnBoardingActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null //sfdd
    private var page = 0
    var onBoardingViewPager: ViewPager? = null
    var onBoardingPresenter= OnBoardingPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_actitvity)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        initViews()
        onBoardingViewPager?.addOnPageChangeListener(ViewPageChangeListener{ currentPage ->
            //on page changed in view Pager
            onBoardingViewPager?.currentItem = currentPage
        })

    }

    private fun initViews() {
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        onBoardingViewPager = findViewById(R.id.container)
        onBoardingViewPager?.let {
            it.adapter = mSectionsPagerAdapter
            it.currentItem = page
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_on_boarding_actitvity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return OnBoardingFragment.newInstance(position + 1, presenter = onBoardingPresenter)
        }

        override fun getCount(): Int {
            return onBoardingPresenter.screenCounts
        }
    }

    /**
     * OnBoardingFragment
     */
    class OnBoardingFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_on_boarding_actitvity, parent, false)
            val onBoardingNextButton = rootView.findViewById<Button>(R.id.onBoardingNextButton)
            val sectionNumber =  arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
            onBoardingNextButton.setOnClickListener {
                val currentActivity = activity as OnBoardingActivity
                currentActivity.onBoardingViewPager?.currentItem = sectionNumber
                presenter?.finishActivity(sectionNumber -1 )
            }
            onBoardingNextButton.text = presenter?.onBoardingButtonText(sectionNumber - 1)
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private const val ARG_SECTION_NUMBER = "sectionNumber"
            var presenter: OnBoardingPresenter? = null

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int, presenter: OnBoardingPresenter): OnBoardingFragment {
                val fragment = OnBoardingFragment()
                Companion.presenter = presenter
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
