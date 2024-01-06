package org.andstatus.game2048.view

/** sortOrder < 0 for left-aligned buttons, >= 0 - for right-aligned */
enum class AppBarButtonsEnum(val icon: String, val row: Int, val sortOrder: Int) {

    TRY_AGAIN("try_again", 0, 9),
    GAME_MENU("menu", 0, 10),

    PAUSE("pause", 1, -3),
    UNDO("undo", 1, 3),
    REDO("redo", 1, 5),
    REDO_PLACEHOLDER("", 1, 5),

    WATCH("watch", 0, -5),
    TO_START("skip_previous", 1, 2),
    BACKWARDS("backwards", 1, 3),
    STOP("stop", 1, 4),
    STOP_PLACEHOLDER("", 1, 4),
    FORWARD("forward", 1, 5),
    FORWARD_PLACEHOLDER("", 1, 5),
    TO_CURRENT("skip_next", 1, 6),
}