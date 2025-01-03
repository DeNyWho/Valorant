plugins {
    alias(libs.plugins.valorant.android.feature)
    alias(libs.plugins.valorant.android.library.compose)
    alias(libs.plugins.valorant.android.library.jacoco)
}

android {
    namespace = "com.example.valorant.feature.tournaments"
}

dependencies {
    implementation(libs.toolbar.compose)
    testImplementation(libs.hilt.android.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
