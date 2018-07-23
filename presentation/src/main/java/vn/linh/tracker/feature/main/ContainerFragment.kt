package vn.linh.tracker.feature.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment
import vn.linh.tracker.feature.other.OtherFragment
import vn.linh.tracker.feature.step.StepFragment
import vn.linh.tracker.feature.weather.WeatherFragment

class ContainerFragment : BaseFragment() {
    private var mUserVisibleHint: Boolean = false
    private var tabPosition: Int = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.let {
            tabPosition = it.getInt(TAB_POSITION)
        }
        when (tabPosition) {
            Tab.WEATHER.position -> goNextChildFragmentWithoutAddToBackStack(WeatherFragment())
            Tab.STEP.position -> goNextChildFragmentWithoutAddToBackStack(StepFragment())
            Tab.OTHER.position -> goNextChildFragmentWithoutAddToBackStack(OtherFragment())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    private fun goNextChildFragmentWithoutAddToBackStack(fragment: Fragment) {
        childFragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mUserVisibleHint = isVisibleToUser
        if (!isAdded) {
            return
        }
        val fragment = childFragmentManager.findFragmentById(R.id.frame_container)
        if (fragment != null) {
            fragment.userVisibleHint = isVisibleToUser
        }
    }

    override fun getUserVisibleHint(): Boolean {
        return mUserVisibleHint
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(
                R.id.frame_container) as BaseFragment
        return fragment.onBackPressed()
    }

    companion object {
        var TAB_POSITION = "tab_position"
        fun newInstance(tabPosition: Int): ContainerFragment {
            val bundle = Bundle()
            bundle.putInt(TAB_POSITION, tabPosition)
            val baseFrag = ContainerFragment()
            baseFrag.arguments = bundle
            return baseFrag
        }
    }
}