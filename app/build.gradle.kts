plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleDaggerHiltAndroid)
    alias(libs.plugins.jetbrainsKotlinAndroid)// support kotlin
}

android {
    namespace = "net.jkcats.simplemvvm"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.jkcats.simplemvvm"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release_signing") {
            keyAlias = "OneTapBUY"
            keyPassword = "s0K7H1VM"
            storeFile = file(path = "onetapbuy.jks")
            storePassword = "s0K7H1VM"
        }
    }

    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
//            )
//        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
            signingConfig = signingConfigs.getByName("release_signing")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)

    // LiveData
    implementation(libs.androidx.lifecycle.livedata)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // rxjava
    implementation(libs.adapter.rxjava3)
    implementation(libs.rxandroid)
    implementation(libs.rxjava)

    // kotlin
    implementation(libs.androidx.core.ktx)

    // WorkManager
    implementation(libs.androidx.work.runtime)// (Java only)
    implementation(libs.androidx.work.runtime.ktx)//Kotlin + coroutines
    implementation(libs.androidx.work.rxjava3)// optional - RxJava3 support

    // ###### 努瓦机器人 start ####
    // MUST use lib
    implementation (libs.gson)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.core)
    implementation(files("libs/NuwaSDK-2021-07-08_1058_2.1.0.08_e21fe7.aar"))
    // ###### 努瓦机器人 end ####
}