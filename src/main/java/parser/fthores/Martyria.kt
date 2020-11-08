package parser.fthores

import Byzantine.ByzStep
import org.audiveris.proxymusic.AccidentalValue
import org.audiveris.proxymusic.AccidentalValue.*
import org.audiveris.proxymusic.Step.*
import java.math.BigDecimal
import java.math.RoundingMode

class Martyria {

    val step: ByzStep

    /**
     * octave number in the byzantine perspective, meaning count of tonoi(´) on a note,
     * new ´ adds after Zw note, backwards are used negative numbers which aren't shown in fact.
     * Humans can hear up to 10 octaves
     */
    var octave: Int
    var simio: MartirikoSimio

    /**
     * absolute position of this Note according to the difference in commas from middle Di
     */
    var absolPos = 0

    /**
     * commas to the next note
     */
    var commasToNext: Int

    /**
     * commas to the previous note
     */
    var commasToPrev = 0
    /*boolean startOfPentachord;
    boolean endOfPentachord;
    boolean startOfTetrachord;
    boolean endOfTetrachord;*/
    /**
     * accidental commas for European notes
     */
    var accidentalCommas = 0

    // AccidentalValue documentation http://usermanuals.musicxml.com/MusicXML/MusicXML.htm#ST-MusicXML-accidental-value.htm
    val accidental: AccidentalValue
        get() = ACCIDENTALS_MAP.getValue(accidentalCommas)

    val alter: BigDecimal
        get() = (accidentalCommas * 2.0 / 9).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

    constructor(octave: Int, step: ByzStep, simio: MartirikoSimio, commasToNext: Int) {
        this.octave = octave
        this.step = step
        this.simio = simio
        this.commasToNext = commasToNext
    }

    private constructor(martyria: Martyria) {
        octave = martyria.octave
        step = martyria.step
        simio = martyria.simio
        absolPos = martyria.absolPos
        commasToNext = martyria.commasToNext
        commasToPrev = martyria.commasToPrev
        /*this.startOfPentachord = martyria.startOfPentachord;
        this.endOfPentachord = martyria.endOfPentachord;
        this.startOfTetrachord = martyria.startOfTetrachord;
        this.endOfTetrachord = martyria.endOfTetrachord;*/
        accidentalCommas = martyria.accidentalCommas
    }

    fun copy() = Martyria(this)

    override fun toString(): String {
        return "Martyria{" +
                "octave=" + octave +
                ", step=" + step +
                ", simio=" + simio +
                ", absolPos=" + absolPos +
                ", commasToNext=" + commasToNext +
                ", commasToPrev=" + commasToPrev +
                ", accidentalCommas=" + accidentalCommas +
                '}'
    }

    companion object {
        val ACCIDENTALS_MAP: Map<Int, AccidentalValue> = mapOf(
                1 to QUARTER_SHARP,
                2 to SHARP_2, // extra
                3 to SHARP_UP, // SHARP_3, // extra
                4 to SHARP,
                5 to SLASH_QUARTER_SHARP,
                7 to THREE_QUARTERS_SHARP, // extra
                8 to SLASH_SHARP,
                9 to DOUBLE_SHARP,

                -1 to QUARTER_FLAT,
                -2 to FLAT_2, // extra
                -3 to FLAT_UP, // extra
                -4 to SLASH_FLAT,
                -5 to FLAT,
                -7 to THREE_QUARTERS_FLAT, // extra
                -8 to DOUBLE_SLASH_FLAT,
                -9 to FLAT_FLAT
        )
        val FLATS_FOURTHS = listOf(B, E, A, D, G, C, F)
        val SHARP_FIFTHS = listOf(F, C, G, D, A, E, B)
    }
}