package parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class InMusicSyllableTest {

    private val inMusicSyllable = InMusicSyllable("μας")

    @Test
    fun getStart() {
        assertEquals("μα", inMusicSyllable.start)
    }

    @Test
    fun getMiddle() {
        assertEquals("α", inMusicSyllable.middle)
    }

    @Test
    fun getEnd() {
        assertEquals("ας", inMusicSyllable.end)
    }
}