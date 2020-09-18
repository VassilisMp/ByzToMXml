package parser

import Mxml.Note.NoteTypeEnum.*
import org.audiveris.proxymusic.Note
import west.cast
import java.util.function.Consumer

class Gorgon: Consumer<MutableList<Any>>{

    override fun accept(elements: MutableList<Any>) {
        val index = elements.indexOf(this)
        val prevNote: Note = elements[index - 1] as Note
        val nextNote: Note = elements.subList(index, elements.lastIndex).reversed().first { it is Note }.cast()!!
        prevNote.apply {
            duration_ -= EIGHTH.duration
            // this might happen if there is an argia after a gorgotita
            if (duration_ == 0) dot.clear()
            when(val noteTypeEnum = noteTypeByDuration(duration)) {
                HALFD -> {
                    noteType = EIGHTH.noteType
                    dot.clear()
                    val complementaryNote = copy().also { it.setType(HALF) }
                    lyricText?.let {
                        InMusicSyllable(it).let { syllable ->
                            lyricText = syllable.start
                            complementaryNote.lyricText = syllable.end
                        }
                    }
                }
                WHOLE -> {}
                null -> {}
                else -> {
                    type.value = noteTypeEnum.noteType
                    dot.clear(); dot += noteTypeEnum.dot; dot += noteTypeEnum.dot2
                }
            }
        }
//        elements.subList(0, index).reversed().
    }
}
