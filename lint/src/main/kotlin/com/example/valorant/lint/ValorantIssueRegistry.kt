package com.example.valorant.lint

import com.example.valorant.lint.designsystem.DesignSystemDetector
import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

class ValorantIssueRegistry : IssueRegistry() {

    override val issues = listOf(
        DesignSystemDetector.ISSUE,
        TestMethodNameDetector.FORMAT,
        TestMethodNameDetector.PREFIX,
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = 13

    override val vendor: Vendor = Vendor(
        vendorName = "Valorant",
        feedbackUrl = "https://github.com/DeNyWho/Valorant/issues",
        contact = "https://github.com/DeNyWho/Valorant",
    )
}