package org.andstatus.game2048.view

import korlibs.korge.view.addTo
import korlibs.korge.view.position

fun ViewData.showGameMenu() = myWindow("game_actions") {
    var xInd = 0
    var yInd = 0

    suspend fun button(buttonEnum: GameMenuButtonsEnum, handler: () -> Unit) {
        if (isPortrait) {
            yInd++
            if (yInd > 9) return
        } else {
            yInd++
            if (yInd > 4 && xInd == 0) {
                xInd = 5
                yInd = 1
            }
            if (yInd > 4) return
        }

        wideButton(buttonEnum.icon, buttonEnum.labelKey) {
            handler()
            window.removeFromParent()
        }.apply {
            position(buttonXs[xInd], buttonYs[yInd])
            addTo(window)
        }
    }

    button(GameMenuButtonsEnum.TRY_AGAIN, presenter::onTryAgainClick)
    button(GameMenuButtonsEnum.SHARE, presenter::onShareClick)
    button(GameMenuButtonsEnum.LOAD, presenter::onLoadClick)
    button(GameMenuButtonsEnum.EXIT, presenter::onExitAppClick)
}
