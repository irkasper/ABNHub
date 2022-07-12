package com.abnamro.abnhub.ui.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * used for forcing UITests to wait for background job
 */
class EspressoIdlingResource {

    companion object {
        private val mCountingIdlingResource: CountingIdlingResource =
            CountingIdlingResource("idl")

        fun increment() {
            mCountingIdlingResource.increment()
        }

        fun decrement() {
            if (!mCountingIdlingResource.isIdleNow)
                mCountingIdlingResource.decrement()
        }

        fun getIdlingResource(): IdlingResource {
            return mCountingIdlingResource
        }
    }
}
