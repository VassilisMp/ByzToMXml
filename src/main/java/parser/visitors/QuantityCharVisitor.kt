package parser.visitors

import grammar.ByzBaseVisitor
import grammar.ByzParser
import org.antlr.v4.runtime.tree.ParseTree
import org.audiveris.proxymusic.Note
import org.audiveris.proxymusic.Pitch
import org.audiveris.proxymusic.Step
import parser.InMusicSyllable
import parser.Tchar
import parser.visitors.ArgiesVisitor.Companion.Apli
import parser.visitors.GorgotitesVisitor.Companion.gorgon

class QuantityCharVisitor(var lastPitch: Pitch = PitchOf(Step.G, 4)) : ByzBaseVisitor<List<Any>>() {

    private var gorgotita: Tchar? = null
    private var argia: Tchar? = null
    private var yfesodiesi: String? = null
    private var monimi: String? = null
    private var syllable: String? = null
    private var prevSyllable: String? = null

    fun visit(
            prevSyllable: String? = null,
            syllable: String? = null,
            gorgotita: Tchar? = null,
            argia: Tchar? = null,
            yfesodiesi: String? = null,
            monimi: String? = null,
            tree: ParseTree
    ): List<Any> {
        this.prevSyllable = prevSyllable
        this.syllable = syllable
        this.gorgotita = gorgotita
        this.argia = argia
        this.yfesodiesi = yfesodiesi
        this.monimi = monimi
        return super.visit(tree)
    }

    private fun standard(num: Int): List<Any> {
        println("$num $syllable")
        return listOfNotNull(nextNote(num, syllable), argia, gorgotita, yfesodiesi, monimi)
    }
    private fun dual(num1: Int, num2: Int): List<Any> {
        println("$num1 $syllable $num2")
        return with(InMusicSyllable(syllable)) { listOfNotNull(nextNote(num1, start), monimi, nextNote(num2, end), argia, gorgotita, yfesodiesi) }
    }

    override fun visitZeroV(ctx: ByzParser.ZeroVContext?) = standard(0)
    override fun visitAlphaV(ctx: ByzParser.AlphaVContext?) = standard(1)
    override fun visitBetaV(ctx: ByzParser.BetaVContext?) = standard(2)
    override fun visitGammaV(ctx: ByzParser.GammaVContext?) = standard(3)
    override fun visitDeltaV(ctx: ByzParser.DeltaVContext?) = standard(4)
    override fun visitEpsilonV(ctx: ByzParser.EpsilonVContext?) = standard(5)
    override fun visitSigmaTafV(ctx: ByzParser.SigmaTafVContext?) = standard(6)
    override fun visitZetaV(ctx: ByzParser.ZetaVContext?) = standard(7)
    override fun visitHetaV(ctx: ByzParser.HetaVContext?) = standard(8)
    override fun visitThetaV(ctx: ByzParser.ThetaVContext?) = standard(9)
    override fun visitIotaV(ctx: ByzParser.IotaVContext?) = standard(10)
    override fun visitIotaAlphaV(ctx: ByzParser.IotaAlphaVContext?) = standard(11)
    override fun visitIotaBetaV(ctx: ByzParser.IotaBetaVContext?) = standard(12)
    override fun visitIotaGammaV(ctx: ByzParser.IotaGammaVContext?) = standard(13)
    override fun visitIotaDeltaV(ctx: ByzParser.IotaDeltaVContext?) = standard(14)
    override fun visitMAlphaV(ctx: ByzParser.MAlphaVContext?) = standard(-1)
    override fun visitMBetaV(ctx: ByzParser.MBetaVContext?) = standard(-2)
    override fun visitMGammaV(ctx: ByzParser.MGammaVContext?) = standard(-3)
    override fun visitMDeltaV(ctx: ByzParser.MDeltaVContext?) = standard(-4)
    override fun visitMEpsilonV(ctx: ByzParser.MEpsilonVContext?) = standard(-5)
    override fun visitMSigmaTafV(ctx: ByzParser.MSigmaTafVContext?) = standard(-6)
    override fun visitMZetaV(ctx: ByzParser.MZetaVContext?) = standard(-7)
    override fun visitMThetaV(ctx: ByzParser.MThetaVContext?) = standard(-9)
    override fun visitMIotaV(ctx: ByzParser.MIotaVContext?) = standard(-10)
    override fun visitMIotaAlphaV(ctx: ByzParser.MIotaAlphaVContext?) = standard(-11)
    override fun visitMIotaBetaV(ctx: ByzParser.MIotaBetaVContext?) = standard(-12)
    override fun visitZeroAndAlphaV(ctx: ByzParser.ZeroAndAlphaVContext?) = dual(0, 1)
    override fun visitBetaVAndApli(ctx: ByzParser.BetaVAndApliContext?) =
            listOfNotNull(nextNote(2, syllable), argia, gorgotita, Apli(), yfesodiesi, monimi)
    override fun visitDeltaAndAlphaV(ctx: ByzParser.DeltaAndAlphaVContext?) = dual(4, 1)
    override fun visitEpsilonAndAlphaV(ctx: ByzParser.EpsilonAndAlphaVContext?) = dual(5, 1)
    override fun visitZeroAndmAlphaV(ctx: ByzParser.ZeroAndmAlphaVContext?) = dual(0, -1)
    override fun visitMAlphaAndAlphaV(ctx: ByzParser.MAlphaAndAlphaVContext?) = dual(-1, 1)
    override fun visitMBetaAndAlphaV(ctx: ByzParser.MBetaAndAlphaVContext?) = dual(-2, 1)
    override fun visitMGammaAndAlphaV(ctx: ByzParser.MGammaAndAlphaVContext?) = dual(-3, 1)
    override fun visitMDeltaAndAlphaV(ctx: ByzParser.MDeltaAndAlphaVContext?) = dual(-4, 1)
    override fun visitContinuousElafron(ctx: ByzParser.ContinuousElafronContext?): List<Any> =
            listOfNotNull(nextNote(-1, InMusicSyllable(prevSyllable).end), gorgon(), nextNote(-1, syllable), argia, gorgotita, yfesodiesi, monimi)
    override fun visitTwoApostrofoi(ctx: ByzParser.TwoApostrofoiContext?) = dual(-1, -1)
    override fun visitYporroi(ctx: ByzParser.YporroiContext?) =
            with(InMusicSyllable(syllable)) { listOfNotNull(nextNote(-1, start), gorgotita, monimi, nextNote(-1, end), argia, yfesodiesi) }
    override fun visitContinuousElafronAndKentimata(ctx: ByzParser.ContinuousElafronAndKentimataContext?) =
            with(InMusicSyllable(syllable)) {
                listOfNotNull(nextNote(-1, InMusicSyllable(prevSyllable).end), gorgon(), nextNote(-1, start), monimi, nextNote(1, end), argia, gorgotita, yfesodiesi) }
    override fun visitYporroiAndKentimata(ctx: ByzParser.YporroiAndKentimataContext?) =
            with(InMusicSyllable(syllable)) { listOfNotNull(nextNote(-1, start), gorgotita, monimi, nextNote(-1, middle), argia, nextNote(1, end), yfesodiesi) }
    override fun visitOligonOnKentimata(ctx: ByzParser.OligonOnKentimataContext?) =
            with(InMusicSyllable(syllable)) { listOfNotNull(nextNote(1, start), gorgotita, monimi, nextNote(1, end), argia, yfesodiesi) }
    override fun visitKentimataOnOligon(ctx: ByzParser.KentimataOnOligonContext?) =
            with(InMusicSyllable(syllable)) { listOfNotNull(nextNote(1, start), argia, monimi, nextNote(1, end), gorgotita, yfesodiesi) }
    override fun visitOligonOnKentimataAndApli(ctx: ByzParser.OligonOnKentimataAndApliContext?) =
            with(InMusicSyllable(syllable)) { listOfNotNull(nextNote(1, start), gorgotita, monimi, nextNote(1, end), argia, Apli(), yfesodiesi) }
    override fun visitKentimataOnOligonAndKentima(ctx: ByzParser.KentimataOnOligonAndKentimaContext?): List<Any> {
        return with(InMusicSyllable(syllable)) { listOfNotNull(nextNote(2, start), argia, monimi, nextNote(1, end), gorgotita, yfesodiesi) }
    }

    private fun nextNote(num: Int, syllable: String? = null): Note =
            ("${lastPitch.octave}${lastPitch.step.toNum()}".toInt(7) + num).toString(7).let {
                west.Note(
                        step = it[1].toString().toInt().toStep(),
                        octave = it[0].toString().toInt(),
                        duration = 1,
                        noteType = west.Note.NoteTypeEnum.QUARTER,
                        syllable = syllable
                ).apply { lastPitch = this.pitch }
            }

    private fun Step.toNum(): Int = when (this) {
        Step.A -> 5
        Step.B -> 6
        Step.C -> 0
        Step.D -> 1
        Step.E -> 2
        Step.F -> 3
        Step.G -> 4
    }

    private fun Int.toStep(): Step = when (this) {
        0 -> Step.C
        1 -> Step.D
        2 -> Step.E
        3 -> Step.F
        4 -> Step.G
        5 -> Step.A
        6 -> Step.B
        else -> Step.C // never used
    }
}

fun PitchOf(step: Step, octave: Int): Pitch = Pitch().apply {
    this.step = step
    this.octave = octave
}