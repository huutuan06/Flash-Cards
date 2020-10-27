package com.flashcards.flashcards.ui.notification

import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentNotificationBinding
import kotlin.reflect.KClass

class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_notification

    override val viewModelClass: KClass<NotificationViewModel>
        get() = NotificationViewModel::class
}
