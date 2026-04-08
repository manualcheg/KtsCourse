package com.manualcheg.ktscourse.screenMain.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.rocket_item_active
import ktscourse.composeapp.generated.resources.rocket_item_cost_template
import ktscourse.composeapp.generated.resources.rocket_item_diameter_template
import ktscourse.composeapp.generated.resources.rocket_item_height_template
import ktscourse.composeapp.generated.resources.rocket_item_inactive
import ktscourse.composeapp.generated.resources.rocket_item_no_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun RocketItem(
    rocket: Rocket,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                AsyncImage(
                    model = rocket.imageUrl,
                    contentDescription = rocket.name,
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = rocket.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = if (rocket.isActive) stringResource(Res.string.rocket_item_active) else stringResource(Res.string.rocket_item_inactive),
                        color = if (rocket.isActive) Color.Green else Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(Res.string.rocket_item_cost_template, rocket.costPerLaunch.toString()),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = rocket.description ?: stringResource(Res.string.rocket_item_no_description),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = stringResource(Res.string.rocket_item_height_template, rocket.height?.toString() ?: "-"),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.rocket_item_diameter_template, rocket.diameter?.toString() ?: "-"),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}
