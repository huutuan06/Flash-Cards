package com.flashcards.flashcards.ui.notification

import android.os.Bundle
import android.view.View
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentNotificationBinding
import com.flashcards.flashcards.notification.NotificationUtils
import kotlin.reflect.KClass

class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_notification

    override val viewModelClass: KClass<NotificationViewModel>
        get() = NotificationViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTest.setOnClickListener {
            test()
        }
    }

    fun test() {
        NotificationUtils.showDemoNotification1(
            context!!,
            "Congratulations",
            "You got a legendary sword!", null
        )
    }
}
