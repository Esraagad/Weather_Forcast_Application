import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.weatherforecastapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherforecastapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.weatherforecastapplication.CustomTestRunner"

    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"${getLocalProperty("API_KEY", project)}\"")
        }
        release {
            buildConfigField("String", "API_KEY", "\"${getLocalProperty("API_KEY", project)}\"")

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
        viewBinding = true
        buildConfig = true
    }
}

// Helper function to read from local.properties
fun getLocalProperty(key: String, project: Project): String {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }
    return properties.getProperty(key, "")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // Coroutines
    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.fragment)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.work.runtime.ktx)
    kapt(libs.androidx.hilt.compiler)

    // Gson for parsing JSON
    implementation(libs.gson)

    // Kotlin JSON converter
    implementation(libs.retrofit2.converter.gson)

    // Glide or Coil for image loading (optional)
    implementation(libs.glide)

    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Truth for testing readability
    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    // Coroutines testing
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)

    // Mockito for mocking
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlin.mockito.kotlin)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.kotlin.mockito.kotlin)
    androidTestImplementation(libs.mockk)

    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
}