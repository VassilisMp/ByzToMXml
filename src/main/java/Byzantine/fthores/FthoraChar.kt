package Byzantine.fthores

import Byzantine.ByzStep
import Byzantine.fthores.ByzScale.Companion.NEANES
import Byzantine.fthores.ByzScale.Companion.NEXEANES
import Byzantine.fthores.ByzScale.Companion.SOFT_DIATONIC
import Byzantine.fthores.Type.*
import parser.Engine
import west.Note
import west.cast
import java.util.function.Consumer

class FthoraChar internal constructor(
        val type: Type? = null,
        val step: ByzStep? = null,
        val commas: Int? = null
) : Consumer<Engine> {
    /*override fun accept(engine: Engine) {
        // get relative Byzantine step from Step
        val lastPitch = QuantityChar.getLastNonRestNote(engine.noteList)
        val step = lastPitch.step
        val byzStep = Engine.stepToByzStep(step)
        // get relative byzantine octave
        val octave = lastPitch.octave!!
        var byzOctave = octave - 5
        var byzScale: ByzScale? = null
        when (type) {
            Type.S_D -> {
                if (this.step == ByzStep.NH && commas == 53) byzOctave = 1
                byzScale = ByzScale.SOFT_DIATONIC.getByStep(this.step, byzOctave)
            }
            Type.H_C -> byzScale = ByzScale.NEXEANES.getByStep(this.step, null)
            Type.S_C -> byzScale = ByzScale.NEANES.getByStep(this.step, null)
        }
        if (byzScale != null) {
            val scale = engine.currentByzScale.getByStep(byzStep, byzOctave)
            scale.applyFthora(byzScale)
            engine.putFthoraScale(ByzScale(scale), engine.getNoteListSize())
        }
    }*/

    override fun accept(engine: Engine) {
        // get relative Byzantine step from Step
        val lastNote = with(engine.noteList) {
            subList(0, indexOf(this@FthoraChar)+1).findLast { it is Note }!!.cast<Note>() }!!
        if (type != null) {
            var byzScale: ByzScale? = null
            when (type) {
                S_D -> {
                    val byzOctave = if (this.step == ByzStep.NH && commas == 53) 1 else lastNote.byzOctave
                    byzScale = SOFT_DIATONIC.getByStep(step!!, byzOctave)
                }
                H_C -> byzScale = NEXEANES[step!!]
                S_C -> byzScale = NEANES[step!!]
                H_D -> TODO()
                HENARMONIC -> TODO()
                ZYGOS -> TODO()
                SPATHI -> TODO()
                KLITON -> TODO()
                PERMANENT_SHARP -> TODO()
                PERMANENT_FLAT -> TODO()
                SHARP -> TODO()
                FLAT -> TODO()
            }
            val scale = engine.currentByzScale.getByStep(lastNote.byzStep!!, lastNote.byzOctave)
            scale.applyFthora(byzScale)
            engine.noteList.add(engine.noteList.indexOf(this), scale.copy())
        }
        if (commas != null) {
            lastNote.accidentalCommas = commas
            return
        }
    }

    override fun toString(): String {
        return "FthoraChar{" +
                super.toString() +
                "type=" + type +
                ", step=" + step +
                ", commas=" + commas +
                "} "
    }
}