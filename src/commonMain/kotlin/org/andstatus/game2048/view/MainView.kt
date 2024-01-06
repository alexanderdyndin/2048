package org.andstatus.game2048.view

import korlibs.korge.view.Container
import korlibs.korge.view.addTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.andstatus.game2048.view.AppBar.Companion.setupAppBar

class MainView private constructor(
    val appBar: AppBar,
    val scoreBar: ScoreBar,
    val boardView: BoardView,
    val statusBar: StatusBar) : Container() {

    fun show(appBarButtonsToShow: List<AppBarButtonsEnum>, playSpeed: Int) {
        if (appBar.viewData.closed) return

        appBar.show(this, appBarButtonsToShow)
        scoreBar.show(this, playSpeed)
        boardView.setOnTop(this)
        this.addTo(appBar.viewData.gameStage)
    }

    fun hideStatusBar() {
        if (appBar.viewData.closed || this.parent == null) return

        statusBar.removeFromParent()
    }
    companion object {
        suspend fun ViewData.setupMainView(coroutineScope: CoroutineScope): MainView {
            with(coroutineScope) {
                val appBar = async { setupAppBar() }
                val scoreBar = async { setupScoreBar() }
                val boardView = async { BoardView(this@setupMainView) }
                val statusBar = async { setupStatusBar() }

                return MainView(appBar.await(), scoreBar.await(), boardView.await(), statusBar.await())
            }
        }
    }
}