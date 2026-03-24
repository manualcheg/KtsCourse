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
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.manualcheg.ktscourse.common.LocalDimensions
import com.manualcheg.ktscourse.screenOnboarding.domain.model.OnboardingItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.ic_stat_black
import ktscourse.composeapp.generated.resources.onboard_screen_button_next_text
import ktscourse.composeapp.generated.resources.onboarding_skip_text
import ktscourse.composeapp.generated.resources.onboarding_stat_desc
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Onboarding(
    moveToLoginScreen: () -> Unit,
    viewModel: ViewModelOnboardingScreen = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    OnboardingContent(
        uiState = uiState,
        events = viewModel.events,
        onPageChanged = viewModel::onPageChanged,
        onNextClick = viewModel::onNextClick,
        onBackClick = viewModel::onBackClick,
        onSkipClick = viewModel::onSkipClick,
        moveToLoginScreen = moveToLoginScreen
    )
}

@Composable
fun OnboardingContent(
    uiState: OnboardingUiState,
    onPageChanged: (Int) -> Unit,
    events: SharedFlow<OnboardingEvent>,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onSkipClick: () -> Unit,
    moveToLoginScreen: () -> Unit
) {
    val pageState = rememberPagerState(pageCount = { uiState.items.size })
    val dimensions = LocalDimensions.current

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                OnboardingEvent.NextPage -> pageState.animateScrollToPage(pageState.currentPage + 1)
                OnboardingEvent.BackPage -> pageState.animateScrollToPage(pageState.currentPage - 1)
                OnboardingEvent.MoveToLogin -> moveToLoginScreen.invoke()
            }
        }
    }

    LaunchedEffect(pageState.currentPage) {
        onPageChanged(pageState.currentPage)
    }

    Scaffold { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            TopSection(
                onBackClick = { onBackClick() },
                onSkipClick = { onSkipClick() },
                showBackButton = uiState.canGoBack
            )

            HorizontalPager(
                state = pageState,
                modifier = Modifier
                    .fillMaxHeight(dimensions.onboardingPagerMaxHeight)
                    .fillMaxWidth()
            ) { page -> OnboardingItem(item = uiState.items[page]) }

            BottomSection(
                size = uiState.items.size,
                index = uiState.currentPage,
                onButtonClick = { onNextClick() },
                isLastPage = uiState.isLastPage,
            )
        }
    }
}

@Composable
fun TopSection(
    onBackClick: () -> Unit,
    onSkipClick: () -> Unit,
    showBackButton: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalDimensions.current.paddingLarge)
    ) {
        if (showBackButton) {
            IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = null
                )
            }
        }

        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.CenterEnd),
            contentPadding = PaddingValues(LocalDimensions.current.zeroPadding)
        ) {
            Text(
                text = stringResource(Res.string.onboarding_skip_text),
                color = MaterialTheme.colorScheme.primary/*MaterialTheme.colorScheme.onBackground*/
            )
        }
    }
}

@Composable
fun BottomSection(
    size: Int,
    index: Int,
    onButtonClick: () -> Unit = {},
    isLastPage: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalDimensions.current.paddingLarge)
    ) {
        Indicators(size, index)

        FloatingActionButton(
            onClick = onButtonClick,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(LocalDimensions.current.roundCornerSize))
        ) {
            Icon(
                imageVector = if (isLastPage) {
                    Icons.Default.Done
                } else {
                    Icons.AutoMirrored.Outlined.KeyboardArrowRight
                },
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = stringResource(Res.string.onboard_screen_button_next_text)
            )
        }
    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.boxScopeArrangementSize),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val dimensions = LocalDimensions.current
    val width = animateDpAsState(
        targetValue = if (isSelected) dimensions.targetSelectedSize else dimensions.targetSize,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(dimensions.boxIndicatorSize)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
            )
    )
}

@Composable
fun OnboardingItem(item: OnboardingItem) {
    val dimensions = LocalDimensions.current
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
            modifier = Modifier.size(dimensions.onboardingImageSize)
                .padding(horizontal = dimensions.onboardingImageHorizontalPadding)
        )

        Spacer(modifier = Modifier.height(dimensions.onboardingBigSpacerHeight))

        Text(
            text = stringResource(item.title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = dimensions.onboardingLetterSpacing,
        )
        Spacer(modifier = Modifier.height(dimensions.onboardingSmallSpacerHeight))

        Text(
            text = stringResource(item.desc),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensions.onboardingTextPadding),
            letterSpacing = dimensions.onboardingLetterSpacing,
        )
    }
}

@Preview
@Composable
fun PreviewScreen() {
    OnboardingContent(
        uiState = OnboardingUiState(
            items = listOf(
                OnboardingItem(
                    Res.drawable.ic_stat_black,
                    Res.string.onboarding_skip_text,
                    Res.string.onboarding_stat_desc
                )
            ), 0
        ),
        onPageChanged = {},
        events = MutableSharedFlow<OnboardingEvent>().asSharedFlow(),
        onNextClick = {},
        onBackClick = {},
        onSkipClick = {},
        moveToLoginScreen = {}
    )
}
