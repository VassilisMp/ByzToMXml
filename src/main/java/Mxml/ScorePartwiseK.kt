package Mxml

class ScorePartwiseK : org.audiveris.proxymusic.ScorePartwise() {
    init {
        if (partList != null) throw ExceptionInInitializerError("This ScorePartwise already has a partList")
        partList = org.audiveris.proxymusic.PartList()
    }
    inner class PartK : org.audiveris.proxymusic.ScorePartwise.Part() {
        init {
            ScorePartK().apply {
                this.id = "P" + (getPart().size+1)
                partName = PartNameK().apply { value = "Music" }
            }.also {
                // add in partList
                partList.partGroupOrScorePart.add(it)
                this.id = it
                getPart().add(this@PartK)
            }
        }
        fun getScorePartwise(): ScorePartwiseK {
            return this@ScorePartwiseK
        }
        inner class ScorePartK : org.audiveris.proxymusic.ScorePart() {
            inner class PartNameK : org.audiveris.proxymusic.PartName()
        }
    }
    companion object {
        fun PartOf(): ScorePartwiseK.PartK {
            return ScorePartwiseK().PartK()
        }
    }
}