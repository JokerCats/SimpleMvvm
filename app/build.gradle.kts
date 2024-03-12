plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleDaggerHiltAndroid)
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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

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

    // ######## Support Room db start ########
    // https://developer.android.google.cn/training/data-storage/room?hl=zh-cn
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.rxjava3)               // RxJava3 support for Room
    // 如果使用 Kotlin语言 请按需打开下方注释
    // kapt("androidx.room:room-compiler:$room_version")     // 使用Kotlin标注处理工具(kapt)
    // ksp("androidx.room:room-compiler:$room_version")      // 使用Kotlin符号处理(KSP)
    // implementation("androidx.room:room-ktx:$room_version")// 可选——Kotlin扩展和协程支持Room
    // ######## Support Room db end  ########
}