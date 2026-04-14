package com.manualcheg.ktscourse.screenMain.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.common.LocalDimensions
import com.manualcheg.ktscourse.domain.model.Launch
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.main_screen_launch_status_failed_text
import ktscourse.composeapp.generated.resources.main_screen_launch_status_success_text
import ktscourse.composeapp.generated.resources.main_screen_launch_status_upcoming_text
import ktscourse.composeapp.generated.resources.main_screen_mission_image_cont_descript_launch
import ktscourse.composeapp.generated.resources.rocket_launch_128
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Preview
@Composable
fun PreviewLaunchItem() {
    LaunchItem(
        Launch(
            id = "1",
            name = "Starship",
            rocketId = "",
            launchDate = "15.02.2012",
            details = "kdfjbfjk kjbjkb hbhh jbjdfns,k 345 cbklkd fgf kjned!!!",
            imageUrl = "https://images2.imgbox.com/85/43/6VSgldkO_o.png",
            status = LaunchStatus.SUCCESS,
            flightNumber = 333,
            launchpad = "dd",
        ),
        { println("") },
    )
}

@Composable
fun LaunchItem(launch: Launch, onItemClick: () -> Unit) {
    val dimensions = LocalDimensions.current
    val statusColor = when (launch.status) {
        LaunchStatus.SUCCESS -> Color(dimensions.successColor)
        LaunchStatus.FAILURE -> MaterialTheme.colorScheme.error
        LaunchStatus.UPCOMING -> Color(dimensions.upcomingColor)
    }

    val statusText = when (launch.status) {
        LaunchStatus.SUCCESS -> stringResource(Res.string.main_screen_launch_status_success_text)
        LaunchStatus.FAILURE -> stringResource(Res.string.main_screen_launch_status_failed_text)
        LaunchStatus.UPCOMING -> stringResource(Res.string.main_screen_launch_status_upcoming_text)
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.outline,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.paddingLarge)
            .clickable { onItemClick.invoke() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.paddingLarge),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (launch.imageUrl != "") {
                AsyncImage(
                    model = launch.imageUrl,
                    contentDescription = stringResource(Res.string.main_screen_mission_image_cont_descript_launch),
                    placeholder = painterResource(Res.drawable.rocket_launch_128),
                    error = painterResource(Res.drawable.rocket_launch_128),
                    modifier = Modifier
                        .size(dimensions.imageSize),
                )
            }

            Spacer(modifier = Modifier.width(dimensions.paddingMedium))

            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = launch.name,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "#${launch.flightNumber}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                if (launch.details.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(dimensions.spacerHeight))
                    Text(
                        text = launch.details,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                    )
                }

                Spacer(modifier = Modifier.height(dimensions.spacerHeight))

                Text(
                    text = launch.launchDate,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall,
                )

                Spacer(modifier = Modifier.height(dimensions.spacerHeight))

                Row {
                    Box(
                        modifier = Modifier
                            .size(
                                dimensions.successIndicatorSize,
                            ).background(statusColor, shape = CircleShape)
                            .align(Alignment.CenterVertically),
                    )

                    Spacer(modifier = Modifier.width(dimensions.paddingSmall))

                    Text(text = statusText, fontSize = dimensions.textSizeSmallest)
                }
            }
        }
    }
}
