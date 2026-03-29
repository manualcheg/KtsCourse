import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    id("ru.ok.tracer").version("1.1.7")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)
            implementation(project.dependencies.platform("ru.ok.tracer:tracer-platform:1.1.7"))

            // Плагины независимы друг от друга. Можно подключать только те,
            // которые необходимы в данный момент.

            // Сбор и анализ крешей и ANR
            implementation("ru.ok.tracer:tracer-crash-report")
            // Сбор и анализ нативных крешей
            implementation("ru.ok.tracer:tracer-crash-report-native")
            // Сбор и анализ хипдапмов при OOM
            implementation("ru.ok.tracer:tracer-heap-dumps")
            // Анализ потребления дискового места на устройстве
            implementation("ru.ok.tracer:tracer-disk-usage")
            // Семплирующий профайлер
            implementation("ru.ok.tracer:tracer-profiler-sampling")
            // Систрейс
            implementation("ru.ok.tracer:tracer-profiler-systrace")
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation.compose)
            implementation(libs.coil.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.coil.network.ktor3)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.napier)
            implementation(libs.material.icons.extended)
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.androidx.datastore.core)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.koin.core)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)
            implementation(libs.insert.koin.koin.compose.viewmodel)
        }

        val iosMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

apply("$rootDir/ktlint.gradle")

android {
    namespace = "com.manualcheg.ktscourse"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.manualcheg.ktscourse"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
    debugImplementation(libs.leakcanary.android)

    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
}

tracer {
    create("defaultConfig") {
        // См. в разделе _«Настройки»_
        pluginToken = "rsurXfuFU2p8xslo8c8nM9Q5wRKzkazmWxxk3r6EkNL"
        appToken = "huahvSoIj4AIKwAhNVe8KIiP437CrhXPnCLC7vI55061"

        // Ваши параметры конфигурации (подробее см. выше)
        // Например:
        uploadMapping = true
//        uploadNativeSymbols = true
        uploadRetryCount = 2
        additionalLibrariesPath = projectDir.toString() + "/aVeryNonstandardLibsDirectory"
    }

    // Также можно задавать конфигурацию для каждого flavor, buildType, buildVariant.
    // Конфигурации наследуют defaultConfig.
    create("debug") {
        // Параметры...
    }
    create("demoDebug") {
        // Параметры...
    }
}
