package org.andstatus.game2048

import korlibs.io.concurrent.atomic.KorAtomicRef
import korlibs.time.Stopwatch
import korlibs.time.seconds
fun <T> KorAtomicRef<T>.update(updater: (T) -> T): T {
    var newValue: T
    do {
        val oldValue = value
        newValue = updater(oldValue)
    } while (!this.compareAndSetFixed(oldValue, newValue))
    return newValue
}
