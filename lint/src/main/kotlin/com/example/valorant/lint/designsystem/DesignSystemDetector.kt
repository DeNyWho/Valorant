package com.example.valorant.lint.designsystem

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

class DesignSystemDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(
        UCallExpression::class.java,
        UQualifiedReferenceExpression::class.java,
    )

    override fun createUastHandler(context: JavaContext): UElementHandler =
        object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val preferredName = METHOD_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val name = node.receiver.asRenderString()
                val preferredName = RECEIVER_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }
        }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "DesignSystem",
            briefDescription = "Design system",
            explanation = "This check highlights calls in code that use Compose Material " +
                    "composables instead of equivalents from the Valorant design system " +
                    "module.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        val METHOD_NAMES = mapOf(
            "MaterialTheme" to "ValorantTheme",
            "Button" to "ValorantButton",
            "OutlinedButton" to "ValorantOutlinedButton",
            "TextButton" to "ValorantTextButton",
            "FilterChip" to "ValorantFilterChip",
            "ElevatedFilterChip" to "ValorantFilterChip",
            "Icon" to "ValorantIcon",
            "NavigationBar" to "ValorantNavigationBar",
            "NavigationBarItem" to "ValorantNavigationBarItem",
            "NavigationRail" to "ValorantNavigationRail",
            "NavigationRailItem" to "ValorantNavigationRailItem",
            "IconToggleButton" to "ValorantIconToggleButton",
            "FilledIconToggleButton" to "ValorantIconToggleButton",
            "FilledTonalIconToggleButton" to "ValorantIconToggleButton",
            "OutlinedIconToggleButton" to "ValorantIconToggleButton",
            "CenterAlignedTopAppBar" to "ValorantTopAppBar",
            "SmallTopAppBar" to "ValorantTopAppBar",
            "MediumTopAppBar" to "ValorantTopAppBar",
            "LargeTopAppBar" to "ValorantTopAppBar",
        )

        val RECEIVER_NAMES = mapOf(
            "Icons" to "ValorantIcons",
        )

        fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String,
        ) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "Using $name instead of $preferredName",
            )
        }
    }
}