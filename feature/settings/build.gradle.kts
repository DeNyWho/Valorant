plugins {
    alias(libs.plugins.valorant.android.feature)
    alias(libs.plugins.valorant.android.library.compose)
    alias(libs.plugins.valorant.android.library.jacoco)
}

android {
    namespace = "com.example.settings"
}

dependencies {
    testImplementation(libs.hilt.android.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
