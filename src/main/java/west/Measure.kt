package west

import org.audiveris.proxymusic.*

fun newMeasure(
        divisions: Int = 1,
        number: Int,
        clef: Clef? = null,
        timeSignature: Array<Int>? = null,
        noteList: List<Any>
) = ScorePartwise.Part.Measure().apply {
    this.divisions = divisions
    this.number_ = number
    if (clef != null) this.clef = clef
    if (timeSignature != null) this.timeSignature = timeSignature
    noteOrBackupOrForward.addAll(noteList)
}

fun newClef(sign: ClefSign, line: Int): Clef = Clef().apply {
    this.sign = sign
    this.line = line.toBigInteger()
}

var ScorePartwise.Part.Measure.clef: Clef?
    get() = attributes?.clef?.firstOrNull()
    set(value) {
        if (attributes == null) attributes = Attributes()
        attributes!!.clef.add(value)
    }


val factory: ObjectFactory = ObjectFactory()
var ScorePartwise.Part.Measure.timeSignature: Array<Int>?
    get() = attributes?.time?.firstOrNull()?.timeSignature?.map { it.value.toInt() }?.toTypedArray()
    set(value) {
        if (value != null) {
            if (attributes == null) attributes = Attributes()
            if (attributes!!.time.firstOrNull() == null) attributes!!.time.add(Time())
            attributes!!.time.first().timeSignature.let {
                it.add(factory.createTimeBeats(value[0].toString()))
                it.add(factory.createTimeBeatType(value[1].toString()))
            }
        }
    }

/*data class TimeSignature(val numerator: Int, val denominator: Int) : ArrayList<JAXBElement<String>>(2), WestMusicElement {
    init {
        this[0] = factory.createTimeBeats(numerator.toString())
        this[1] = factory.createTimeBeats(denominator.toString())
    }
    companion object {
        val factory: ObjectFactory = ObjectFactory()
    }
}*/

private var ScorePartwise.Part.Measure.number_: Int?
    get() = number?.toInt()
    set(value) { number = value?.toString() }

private var ScorePartwise.Part.Measure.attributes: Attributes?
    get() = noteOrBackupOrForward.firstOrNull()?.cast()
    set(value) { noteOrBackupOrForward.add(0, value) }

private var ScorePartwise.Part.Measure.key: Key?
    get() = attributes?.key?.firstOrNull()
    set(value) {
        if (attributes == null) attributes = Attributes()
        attributes?.key?.add(0, value)
    }

private var ScorePartwise.Part.Measure.divisions: Int?
    get() = attributes?.divisions?.toInt()
    set(value) {
        if (attributes == null) attributes = Attributes()
        attributes?.divisions = value?.toBigDecimal()
    }

inline fun <reified T: Any> Any.cast(): T? = if (this is T) this else null
