package Byzantine.fthores

import Byzantine.ByzStep
import Byzantine.ByzStep.*
import Byzantine.fthores.Martyria.Companion.FLATS_FOURTHS
import Byzantine.fthores.Martyria.Companion.SHARP_FIFTHS
import org.audiveris.proxymusic.Key
import org.audiveris.proxymusic.ObjectFactory
import org.audiveris.proxymusic.Step
import org.jetbrains.annotations.Contract
import parser.addMany
import west.Note.Companion.toStep
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Consumer
import java.util.function.Supplier

class ByzScale {
    /**
     * list holding martyrias
     */
    private val scale: MutableList<Martyria>

    /**
     * cursor current position
     */
    private var cursorPos = 0
    // TODO write code for fthorikoSimio in FthoraChar
    /**
     * the last fthora that was applied
     */
    private var fthorikoSimio: FthorikoSimio?

    /**
     * martyria on which the last fthora was applied
     */
    private var fthoraHolder: Martyria? = null

    private constructor(collection: Collection<Martyria>?, fthorikoSimio: FthorikoSimio?, step: ByzStep?, octave: Int) {
        scale = if (collection != null) ArrayList(collection) else ArrayList()
        this.fthorikoSimio = fthorikoSimio
        fthoraHolder = getMartyria(step, octave)
    }

    internal constructor(byzScale: ByzScale) {
        scale = byzScale.scale.map { it.copy() }.toMutableList()
        cursorPos = byzScale.cursorPos
        fthorikoSimio = byzScale.fthorikoSimio
        if (byzScale.fthoraHolder != null) {
            val index = byzScale.scale.indexOf(byzScale.fthoraHolder!!)
            fthoraHolder = scale[index]
        }
    }

    fun copy() = ByzScale(this)

    fun getByStep(step: ByzStep, octave: Int? = null): ByzScale {
        if (octave != null) {
            if (octave !in scale[0].octave..scale.last().octave) return this
            scale.indexOfFirst { it.step == step && it.octave == octave }.let { if (it >= 0) cursorPos = it }
        } else scale.indexOfFirst { it.step == step }.let { if (it >= 0) cursorPos = it }
        return this
    }

    private fun indexOf(step: ByzStep, octave: Int?): Int {
        val minOctave = scale[0].octave
        val maxOctave = scale[scale.size - 1].octave
        val martyria = arrayOfNulls<Martyria>(1)
        val booleanSupplier: Supplier<Boolean>
        booleanSupplier = when (octave) {
            in minOctave..maxOctave -> Supplier { martyria[0]!!.step == step && martyria[0]!!.octave == octave }
            else -> Supplier { martyria[0]!!.step == step }
        }
        for (i in scale.indices) {
            martyria[0] = scale[i]
            if (booleanSupplier.get()) return i
        }
        return -1
    }

    /**
     * @param byzStep the step to search for.
     * @return the index of the step if exists, or else -1.
     */
    private fun indexOfStep(byzStep: ByzStep): Int = scale.indexOfFirst { it.step == byzStep }

    fun getNextIfExists(): Martyria? = if (cursorPos < scale.size - 1) scale[++cursorPos] else null
    fun getPrevIfExists(): Martyria? = if (cursorPos > 0) scale[--cursorPos] else null
    fun getNext(): Martyria? = getItemToRight(1)
    fun getPrev(): Martyria? = getItemToLeft(1)

    // TODO use cursorPos as one Element array parameter to be able to iterate static fthora scales on many threads
    fun getItemToRight(num: Int): Martyria? {
        cursorPos = (cursorPos + num) % scale.size
        return scale[cursorPos]
    }

    fun getItemToLeft(num: Int): Martyria? {
        cursorPos = indexOfLeftItem(num)
        return scale[cursorPos]
    }

    fun indexOfLeftItem(num: Int): Int {
        var index = (cursorPos - num) % scale.size
        if (index < 0) index += scale.size
        return index
    }

    private fun calcAbsPos() {
        val martyria1 = getMartyria(DI, 0)
        val indexOfMesoDI = scale.indexOf(martyria1)
        var i = 0
        val thisSize = scale.size
        while (i < thisSize) {
            val martyria = scale[i]
            var commasCounter = 0
            if (i < indexOfMesoDI) for (j in i until indexOfMesoDI) commasCounter -= scale[j].commasToNext
            else if (i > indexOfMesoDI) for (j in i downTo indexOfMesoDI + 1) commasCounter += scale[j].commasToPrev
            martyria.absolPos = commasCounter
            i++
        }
    }

    fun getMartyria(step: ByzStep?, octave: Int) = scale.firstOrNull { it.step == step && it.octave == octave }

    fun getMartyriaByStepOctave(martyria: Martyria) = getMartyria(martyria.step, martyria.octave)

    fun resetCursor() {
        cursorPos = 0
    }

    fun applyFthora(fthora: ByzScale): ByzScale {
        //            HARD_DIATONIC.indexOfStep(relativeStep)
        // save cursor position for both scales
        val fthoraCursorPos = fthora.cursorPos
        val cursorPos = cursorPos
        fthoraHolder = scale[cursorPos]
        var a = getPrevIfExists()
        var b = fthora.getPrev()
        // iterate scale to left first
        var diff = 0
        while (a != null) {
            diff = a.commasToNext + diff - b!!.commasToNext
            a.accidentalCommas += diff
            a.commasToNext = b.commasToNext
            a.commasToPrev = b.commasToPrev
            a.simio = b.simio
            a = getPrevIfExists()
            b = fthora.getPrev()
            //                System.out.println(String.format("%s, %s", martyria, fthoraMart));
        }
        // reset cursor positions
        fthora.cursorPos = fthoraCursorPos
        this.cursorPos = cursorPos
        // iterate scale to right
        diff = 0
        a = this[this.cursorPos]
        b = fthora[fthora.cursorPos]
        while (a != null) {
            a.accidentalCommas += diff
            diff = b!!.commasToNext + diff - a.commasToNext
            a.commasToNext = b.commasToNext
            a.commasToPrev = b.commasToPrev
            a.simio = b.simio
            a = getNextIfExists()
            b = fthora.getNext()
        }
        calcAbsPos()
        return this
    }

    fun applyChord(chord: ByzScale, notes: Int = 4, step: ByzStep, octave: Int? = null): ByzScale {
        this.getByStep(step, octave)
        // save cursor position for both scales
//        val fthoraCursorPos = tetrachord.cursorPos
        val cursorPos = cursorPos
        fthoraHolder = scale[cursorPos]
//        var a = getPrevIfExists()
//        var b = tetrachord.getPrev()
        // iterate scale to right
        var diff = 0
        var a = this[this.cursorPos]
        var b = chord[chord.cursorPos]
        for (i in 1..notes) {
            a!!.accidentalCommas += diff
            diff = b!!.commasToNext + diff - a!!.commasToNext
            a.commasToNext = b.commasToNext
            a.commasToPrev = b.commasToPrev
            a.simio = b.simio
            a = getNextIfExists()
            b = chord.getNext()
        }
        calcAbsPos()
        return this
    }

    operator fun get(i: Int): Martyria? = scale[i]

    operator fun get(step: ByzStep): ByzScale {
        scale.indexOfFirst { it.step == step }.let { if (it >= 0) cursorPos = it }
        return this
    }

    fun size(): Int = scale.size

    override fun toString(): String {
        return "ByzScale{" +
                "cursorPos=" + cursorPos +
                ", fthorikoSimio=" + fthorikoSimio +
                ", fthoraHolder=" + fthoraHolder +
                ", scale=" + scale +
                '}'
    }

    fun getKey(startStep: ByzStep?, octave: Int? = null): Key {
        var scale: List<Martyria> = scale
        if (startStep != null) {
            val start = this.indexOf(startStep, octave)
            scale = this.scale.subList(start, start + 7)
        }
        val key = ObjectFactory().createKey()
        val nonTraditionalKey = key.nonTraditionalKey
        val funRef = AtomicReference { martyria: Martyria -> martyria.accidentalCommas < 0 }
        val addAccidentals = Consumer { step: Step ->
            scale.firstOrNull { it.step.toStep() == step && funRef.get().invoke(it) }
                    ?.let { martyria ->
                        nonTraditionalKey.addMany(
                                step,
                                martyria.alter,
                                martyria.accidental
                        )
                    }
        }
        FLATS_FOURTHS.forEach(addAccidentals)
        funRef.set { martyria -> martyria.accidentalCommas > 0 }
        SHARP_FIFTHS.forEach(addAccidentals)
        return key
    }

    fun initAccidentalCommas(relativeStandardStep: ByzStep) {
        val HARD_DIATONIC = HARD_DIATONIC.getByStep(relativeStandardStep, null)
        val HARD_DIATONIC_cursorPos = HARD_DIATONIC.cursorPos
        HARD_DIATONIC.cursorPos = HARD_DIATONIC_cursorPos - 1
        getByStep(NH, null)
        val currentByzScaleCursorPos = cursorPos
        run {
            var i = currentByzScaleCursorPos
            var difference = 0
            while (i < size()) {
                val a = this[i]
                val b = HARD_DIATONIC.getNext()
                difference += a!!.commasToNext - b!!.commasToNext
                val setIndex = if (i + 1 == size()) 0 else i + 1
                this[setIndex]!!.accidentalCommas = difference
                i++
            }
        }
        HARD_DIATONIC.cursorPos = HARD_DIATONIC_cursorPos
        var i = currentByzScaleCursorPos - 1
        var difference = 0
        while (i >= 0) {
            val a = this[i]
            val b = HARD_DIATONIC.getPrev()
            difference += a!!.commasToNext - b!!.commasToNext
            a.accidentalCommas = -difference
            i--
        }
        resetCursor()
    }

    companion object {
        // TODO add the missing scales
        val NEXEANES = createScale(
                Martyria(0, PA, MartirikoSimio.NEXEANESx, 5),
                Martyria(0, BOU, MartirikoSimio.NENANO, 12),
                Martyria(0, GA, MartirikoSimio.NEXEANESx, 5),
                Martyria(0, DI, MartirikoSimio.NENANO, 9)
        )
        val NEANES = createScale(
                Martyria(0, DI, MartirikoSimio.NEANES, 6),
                Martyria(0, KE, MartirikoSimio.NEANES2, 11),
                Martyria(0, ZW, MartirikoSimio.NEANES, 5),
                Martyria(1, NH, MartirikoSimio.NEANES2, 9)
        )
        val SOFT_DIATONIC = createScale(
                Martyria(-1, DI, MartirikoSimio.AGIA, 9),
                Martyria(-1, KE, MartirikoSimio.ANANES, 8),
                Martyria(-1, ZW, MartirikoSimio.AANES, 5),
                Martyria(0, NH, MartirikoSimio.NEAGIE, 9),
                Martyria(0, PA, MartirikoSimio.ANEANES, 8),
                Martyria(0, BOU, MartirikoSimio.NEHEANES, 5),
                Martyria(0, GA, MartirikoSimio.NANA, 9)
        )
        val HARD_DIATONIC = createScale(
                Martyria(0, NH, MartirikoSimio.NEAGIE, 9),
                Martyria(0, PA, MartirikoSimio.ANEANES, 9),
                Martyria(0, BOU, MartirikoSimio.NEHEANES, 4),
                Martyria(0, GA, MartirikoSimio.NANA, 9),
                Martyria(0, DI, MartirikoSimio.AGIA, 9),
                Martyria(0, KE, MartirikoSimio.ANANES, 9),
                Martyria(0, ZW, MartirikoSimio.AANES, 4)
        )

        private fun createScale(vararg martyriasParam: Martyria): ByzScale {
            val byzScale = ByzScale(martyriasParam.toList(), null, null, 0)
            byzScale.getItemToLeft(2)
            // set commasToPrev using commasToNext value of the previous martyria
            byzScale.scale.forEach{ it.commasToPrev = byzScale.getNext()!!.commasToNext }
            byzScale.calcAbsPos()
            byzScale.resetCursor()
            return byzScale
        }

        @Contract("_, _, _, _ -> new")
        fun ByzScaleOf(
                collection: Collection<Martyria>,
                fthorikoSimio: FthorikoSimio?, step: ByzStep?, octave: Int
        ): ByzScale {
            return ByzScale(collection, fthorikoSimio, step, octave)
        }

        fun get2OctavesScale(): ByzScale {
            // create first octave
            // clone SOFT_DIATONIC scale
            // wrap in ByzScale
            val diatonicByzScale = ByzScale(SOFT_DIATONIC)
            diatonicByzScale.fthorikoSimio = FthorikoSimio.NH_D
            diatonicByzScale.fthoraHolder = diatonicByzScale.getMartyria(NH, 0)
            // go two positions to left, to call getNext().commasToNext on the previous martyria
            diatonicByzScale.getItemToLeft(2)
            // set commasToPrev using commasToNext value of the previous martyria
            diatonicByzScale.scale.forEach{ it.commasToPrev = diatonicByzScale.getNext()!!.commasToNext }
            // deep copy the first octave, increasing the octave number by 1
            val copy = diatonicByzScale.scale
                    .map { martyria1 ->
                        val martyria = martyria1.copy()
                        martyria.octave++
                        martyria
                    }
                    .toList()
            // copy last element, DI''
            val martyria = diatonicByzScale.scale[0].copy()
            martyria.octave += 2
            diatonicByzScale.scale.addAll(copy)
            diatonicByzScale.scale.add(martyria)
            diatonicByzScale.calcAbsPos()
            return diatonicByzScale
        }
        /*val NEANES_KEY = get2OctavesScale()
                .getByStep(NH)
                .apply { initAccidentalCommas(Note.relativeStandardStep) }
                .applyFthora(NEANES)
                .getKey(NH)*/
    }
}