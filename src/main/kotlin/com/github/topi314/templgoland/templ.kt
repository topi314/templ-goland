@file:Suppress("UnstableApiUsage")

package com.github.topi314.templgoland

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor

const val TEMPL_LANGUAGE_ID = "templ"
const val TEMPL_LANGUAGE_NAME = "templ"
const val TEMPL_LANGUAGE_DESCRIPTION = "A language for writing HTML user interfaces in Go."
const val TEMPL_FILE_EXTENSION = "templ"

class TemplLspServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        if (file.extension == TEMPL_FILE_EXTENSION) {
            serverStarter.ensureServerStarted(TemplLspServerDescriptor(project))
        }
    }
}

private class TemplLspServerDescriptor(project: Project) :
    ProjectWideLspServerDescriptor(project, TEMPL_LANGUAGE_NAME) {
    override fun isSupportedFile(file: VirtualFile) = file.extension == TEMPL_FILE_EXTENSION
    override fun createCommandLine() = GeneralCommandLine("templ", "lsp")
}

object TemplLanguage : Language(TEMPL_LANGUAGE_ID)

class TemplFileType : LanguageFileType(TemplLanguage) {
    override fun getName() = TEMPL_LANGUAGE_NAME
    override fun getDescription() = TEMPL_LANGUAGE_DESCRIPTION
    override fun getDefaultExtension() = TEMPL_FILE_EXTENSION
    override fun getIcon() = IconLoader.getIcon("/templ.svg", TemplFileType::class.java)
}