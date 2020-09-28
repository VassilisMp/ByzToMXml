package parser

import grammar.ByzBaseVisitor
import grammar.ByzParser
import grammar.ByzParser.ClusterType2Context
import grammar.ByzParser.NewArktikiMartyriaContext
import org.audiveris.proxymusic.Step
import west.Note.Companion.RestNote

// replace all characters with the simplest ones
class ScoreVisitor : ByzBaseVisitor<Unit>() {

    val text: StringBuilder = StringBuilder()
    var missingLetter: String? = null
    val elements: MutableList<Any> = mutableListOf()
    val lastPitch = PitchOf(Step.G, 4)
    private val quantityCharVisitor = QuantityCharVisitor(lastPitch)

    override fun visitClusterType2(ctx: ClusterType2Context) {
//        print(ctx.text + "  ")
        // concatenate arxigramma and syllables and possible missing letter that was on the previous cluster by mistake
        var syllable: String = ctx.getArxigramma() +
                (missingLetter ?: "") +
                ctx.syllable().joinToString(separator = "", transform = { it.text })
        if (missingLetter != null) missingLetter = null
        // if two last letters of the syllable are the same it means, the last belongs to the next note
        if (syllable.length>1) syllable.takeLast(2).let {
            if (it[0] == it[1]) {
                syllable = syllable.dropLast(1)
                missingLetter = it[0].toString()
            }
        }
        val gorgotita = ctx.gorgotita()
//        println(gorgotita)
        val argia = ctx.argies()
//        println(argia)
        elements.addAll(quantityCharVisitor.visit(syllable = syllable, gorgotita = gorgotita, argia = argia, tree = ctx.qChar()))
        // visit pause or return if null
        elements.addAll(pauseVisitor.visit(ctx.pause() ?: return))
    }

    private fun ClusterType2Context.getArxigramma(): String = (this.ARXIGRAMMA()?.text?.drop(1) ?: "")
    private fun ClusterType2Context.gorgotita(): Tchar? = tChar().map { gorgotitesVisitor.visit(it) }.firstOrNull()
    private fun ClusterType2Context.argies(): Tchar? = tChar().map { argiesVisitor.visit(it) }.firstOrNull()

    companion object {
        private val argiesVisitor = ArgiesVisitor()
        internal val gorgotitesVisitor = GorgotitesVisitor()
        private val pauseVisitor = object : ByzBaseVisitor<List<Any>>() {
            override fun visitPause(ctx: ByzParser.PauseContext): List<Any> {
                var times = 1
                when {
                    ctx.APLI().size > 0 -> times += 1
                    ctx.DIPLI().size > 0 -> times += 2
                    ctx.TRIPLI().size > 0 -> times += 3
                }
                return listOfNotNull(RestNote(), Tchar(0, -times, false), gorgotitesVisitor.visit(ctx.gorgotita()))
            }
        }
        private val arktikiMartyriaVisitor = object: ByzBaseVisitor<Unit>() {
            override fun visitNewArktikiMartyria(ctx: NewArktikiMartyriaContext?) = null
        }
    }
}