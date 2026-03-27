package com.manualcheg.ktscourse.presentation.ui

@OptIn(InternalComposeUiApi::class)
actual object LocalAppTheme {
    actual val current: Boolean
        @Composable get() = LocalSystemTheme.current == SystemTheme.Dark

    @Composable
    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
        val new =
            when (value) {
                true -> SystemTheme.Dark
                false -> SystemTheme.Light
                null -> LocalSystemTheme.current
            }

        return LocalSystemTheme.provides(new)
    }
}
