package west

import org.audiveris.proxymusic.PartList
import org.audiveris.proxymusic.PartName
import org.audiveris.proxymusic.ScorePart
import org.audiveris.proxymusic.ScorePartwise
import org.audiveris.proxymusic.ScorePartwise.Part.Measure

fun newScorePartWise(vararg parts: ScorePartwise.Part) = ScorePartwise().apply {
    partList = PartList().also { it.addScoreParts(*parts) }
    part += parts
}

fun newPart(id: String, partName: String, measures: List<Measure>) = ScorePartwise.Part().apply {
    this.scorePart = newScorePart(id, partName)
    this.measure += measures
//    this.measure.add(newMeasure(divisions, 1, newClef(ClefSign.G, 2), noteList = noteList))
}

var ScorePartwise.Part.scorePart: ScorePart
    get() = id as ScorePart
    set(value) { id = value }

fun newScorePart(id: String, partName: String) = ScorePart().apply {
    this.id = id
    this.partName = PartName().also { it.value = partName }
}

fun PartList.addScoreParts(vararg parts: ScorePartwise.Part) =
        partGroupOrScorePart.addAll(parts.map { it.scorePart })