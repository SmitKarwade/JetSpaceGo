plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.jetspacego"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jetspacego"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    android {
        sourceSets["main"].assets.srcDirs("src/main/assets")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes.add("META-INF/DEPENDENCIES")
            excludes.add("META-INF/INDEX.LIST")
        }
    }
}

dependencies {

//    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
//    implementation("io.coil-kt.coil:coil-okhttp:3.0.0-rc03")

    implementation("com.google.auth:google-auth-library-oauth2-http:1.18.0")
    implementation("com.google.cloud:google-cloud-texttospeech:2.33.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation("io.github.sceneview:sceneview:2.3.0")

    implementation("androidx.media3:media3-ui-compose:1.7.1")
    implementation("androidx.media3:media3-exoplayer:1.7.1")

    implementation(platform("androidx.compose:compose-bom:2025.06.00"))

    // Required Compose libraries
    implementation("androidx.compose.foundation:foundation")

    implementation("androidx.activity:activity-compose:1.10.1")


    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.android)
    kapt(libs.hilt.android.compiler)


    implementation(libs.compose)

    implementation("androidx.paging:paging-compose:3.3.6")

    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    val nav_version = "2.8.8"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("com.razorpay:checkout:1.6.40")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}