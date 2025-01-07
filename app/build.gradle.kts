plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)



}



android {
    namespace = "com.smcdeveloper.nobinoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.smcdeveloper.nobinoapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    //Retrofit For Remote Api Call


    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.intercepter)

    //ButtomNavigation

    implementation(libs.androidx.navigation.compose)

    implementation (libs.androidx.material.icons.extended)

    





   // Hilt Di

    implementation(libs.hilt.android)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.espresso.core)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navcompose)








    // Data Store
    implementation(libs.data.store)

    // Coil Image Library
   // implementation(libs.coile.image)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.compose)


// Slider With View Pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

// Pagination 3

    implementation (libs.androidx.paging.compose)




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}