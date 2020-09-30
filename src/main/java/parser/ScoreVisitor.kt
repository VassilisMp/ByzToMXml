package parser

import grammar.ByzBaseVisitor
import grammar.ByzParser.*
import org.antlr.v4.runtime.tree.ParseTree
import org.audiveris.proxymusic.Step
import west.Note.Companion.RestNote

// replace all characters with the simplest ones
class ScoreVisitor : ByzBaseVisitor<Unit>() {

    val text: StringBuilder = StringBuilder()
    var missingLetter: String? = null
    val elements: MutableList<Any> = mutableListOf()
    val lastPitch = PitchOf(Step.A, 4)
    private val quantityCharVisitor = QuantityCharVisitor(lastPitch)

    override fun visitClusterType2(ctx: ClusterType2Context): Unit = with(ctx) {
        fun getArxigramma() = ARXIGRAMMA()?.text?.drop(1) ?: ""
        // concatenate arxigramma and syllables and possible missing letter that was on the previous cluster by mistake
        var syllable: String = getArxigramma() +
                (missingLetter ?: "") +
                syllable().joinToString(separator = "", transform = { it.text })
        if (missingLetter != null) missingLetter = null
        // if two last letters of the syllable are the same it means, the last belongs to the next note
        if (syllable.length>1) syllable.takeLast(2).let {
            if (it[0] == it[1]) {
                syllable = syllable.dropLast(1)
                missingLetter = it[0].toString()
            }
        }
        val gorgotita = tChar().mapNotNull { visitGorgotita(it) }.firstOrNull()
        val argia = tChar().mapNotNull { visitArgia(it) }.firstOrNull()
        elements.addAll(quantityCharVisitor.visit(syllable = syllable, gorgotita = gorgotita, argia = argia, tree = ctx.qChar()))
        // visit pause or return if null
        if (pause() != null) elements.addAll(visitPause(ctx))
    }

    companion object {
        private val argiesVisitor = ArgiesVisitor()
        private fun visitArgia(ctx: TCharContext): Tchar? = argiesVisitor.visit(ctx)
        private val gorgotitesVisitor = GorgotitesVisitor()
        private fun visitGorgotita(ctx: TCharContext) = gorgotitesVisitor.visit(ctx)
        private val pauseVisitor = object : ByzBaseVisitor<List<Any>>() {
            override fun visit(tree: ParseTree?): List<Any> = super.visit(tree) ?: emptyList()
            override fun visitPause(ctx: PauseContext): List<Any> {
                val times = when {
                    ctx.APLI().isNotEmpty() -> -2
                    ctx.DIPLI().isNotEmpty() -> -3
                    ctx.TRIPLI().isNotEmpty() -> -4
                    else -> -1
                }
                return listOfNotNull(RestNote(), Tchar(division = times), gorgotitesVisitor.visit(ctx.gorgotita()))
            }
        }
        private fun visitPause(ctx: ClusterType2Context) = pauseVisitor.visit(ctx)
        private val arktikiMartyriaVisitor = object: ByzBaseVisitor<Unit>() {
            override fun visitNewArktikiMartyria(ctx: NewArktikiMartyriaContext?) = null
        }
    }
}