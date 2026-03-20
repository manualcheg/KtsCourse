package com.manualcheg.ktscourse.screenOnboarding.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manualcheg.ktscourse.data.datastore.DataStorePreferencesProvider
import com.manualcheg.ktscourse.data.repository.UserPreferencesRepository
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Onboarding(moveToLoginScreen: () -> Unit) {
    val viewModel = ViewModelOnboardingScreen(
        userPreferencesRepository = UserPreferencesRepository(DataStorePreferencesProvider.datastore)
    )

    val items by viewModel.items.collectAsState()
    val pageState = rememberPagerState(pageCount = { items.size })

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                OnboardingEvent.NextPage -> pageState.scrollToPage(pageState.currentPage + 1)
                OnboardingEvent.BackPage -> pageState.scrollToPage(pageState.currentPage - 1)
                OnboardingEvent.MoveToLogin -> moveToLoginScreen.invoke()
            }
        }
    }

    Scaffold { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            TopSection(
                onBackClick = { viewModel.onBackClick(pageState.currentPage) },
                onSkipClick = { viewModel.onSkipClick() }
            )

            HorizontalPager(
                state = pageState,
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
            ) { page -> OnboardingItem(item = items[page]) }

            BottomSection(size = items.size, index = pageState.currentPage) {
                viewModel.onNextClick(pageState.currentPage)
            }
        }
    }
}

@Composable
fun TopSection(onBackClick: () -> Unit = {}, onSkipClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.CenterEnd),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Skip", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun BottomSection(size: Int, index: Int, onButtonClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Indicators(size, index)

        FloatingActionButton(
            onClick = onButtonClick,
            containerColor = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(15.dp))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                tint = Color.White,
                contentDescription = "Next"
            )
        }
    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0XFFF8E2E7)
            )
    )
}

@Composable
fun OnboardingItem(item: OnboardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.size(200.dp).padding(horizontal = 50.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = stringResource(item.title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(item.desc),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
            letterSpacing = 1.sp,
        )
    }
}

@Preview
@Composable
fun PreviewScreen() {
    Onboarding({ println("Login") })
}
