package com.manualcheg.ktscourse.presentation.ui.screens.onboarding

import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.ic_favourites_100
import ktscourse.composeapp.generated.resources.ic_notification
import ktscourse.composeapp.generated.resources.ic_stat_black
import ktscourse.composeapp.generated.resources.onboarding_favourites_desc
import ktscourse.composeapp.generated.resources.onboarding_favourites_title
import ktscourse.composeapp.generated.resources.onboarding_notification_desc
import ktscourse.composeapp.generated.resources.onboarding_notification_title
import ktscourse.composeapp.generated.resources.onboarding_stat_desc
import ktscourse.composeapp.generated.resources.onboarding_stat_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

class OnboardingItems(
    val image: DrawableResource,
    val title: StringResource,
    val desc: StringResource
) {
    companion object {
        fun getData(): List<OnboardingItems> {
            return listOf(
                OnboardingItems(
                    image = Res.drawable.ic_favourites_100,
                    title = Res.string.onboarding_favourites_title,
                    desc = Res.string.onboarding_favourites_desc
                ),
                OnboardingItems(
                    image = Res.drawable.ic_stat_black,
                    title = Res.string.onboarding_stat_title,
                    desc = Res.string.onboarding_stat_desc
                ),
                OnboardingItems(
                    image = Res.drawable.ic_notification,
                    title = Res.string.onboarding_notification_title,
                    desc = Res.string.onboarding_notification_desc
                ),
            )
        }
    }
}
