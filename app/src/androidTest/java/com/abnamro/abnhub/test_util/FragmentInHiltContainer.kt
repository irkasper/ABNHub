package com.abnamro.abnhub.test_util

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.abnamro.abnhub.ui.main.HiltTestActivity

/**
 * to test fragments without it's parent activity
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
//    @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    crossinline action: Fragment.() -> Unit = {}
): ActivityScenario<HiltTestActivity> {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    )
    val activityScenario =
        ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
            val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(T::class.java.classLoader),
                T::class.java.name
            )
            fragment.arguments = fragmentArgs
            activity.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment, "")
                .commitNow()
            fragment.action()
        }
    return activityScenario
}
