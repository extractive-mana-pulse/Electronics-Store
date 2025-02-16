plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id ("dagger.hilt.android.plugin")
    id ("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
}

android {
    namespace = "com.example.e_shop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.e_shop"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
        }
        release {
            /* this 2 block of code, prepare application for release.
             Basically optimize code and remove unused elements. */
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // type-safe navigation
    implementation(libs.navigation.compose)

    // bottom bar
    implementation (libs.androidx.material)

    // gson
    implementation (libs.gson)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    // mvvm
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // animation
    implementation(libs.androidx.compose.animation)

    // serialization
    implementation(libs.kotlinx.serialization.core)

    // image slider
    implementation (libs.accompanist.pager)
    implementation (libs.accompanist.pager.indicators)

    // room
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.room.paging)

    // paging
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)

    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // firebase
    implementation (libs.firebase.auth.ktx)
    implementation (libs.play.services.auth)

    // credential manager
    implementation("androidx.credentials:credentials:1.5.0-rc01")
}