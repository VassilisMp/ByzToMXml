package parser

import Byzantine.ByzStep.*
import grammar.ByzBaseVisitor
import grammar.ByzParser.*
import org.audiveris.proxymusic.AccidentalValue
import org.audiveris.proxymusic.Key
import org.audiveris.proxymusic.Pitch
import org.audiveris.proxymusic.Step
import org.audiveris.proxymusic.Step.*
import parser.fthores.ByzScale
import parser.fthores.Martyria.Companion.ACCIDENTALS_MAP
import west.Note
import java.math.BigDecimal
import java.math.RoundingMode

class ArktikiMartyriaVisitor : ByzBaseVisitor<ArktikiMartyriaVisitor.PitchnKey>() {

    override fun visitPrwtosArktikiMartyria(ctx: PrwtosArktikiMartyriaContext?) = PitchnKey(PitchOf(A, 4), ussakKey())

    override fun visitPrwtosTetrafwnos(ctx: PrwtosTetrafwnosContext?) = PitchnKey(PitchOf(A, 4), huseyniKey())

    override fun visitPrwtosXrwmatikosArktikiMartyria(ctx: PrwtosXrwmatikosArktikiMartyriaContext?) = PitchnKey(PitchOf(B, 5), prwtosXrwmatikosKey())

    override fun visitPrwtosDifwnosArktikiMartyria(ctx: PrwtosDifwnosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(A, 4), createKey(keyAccidental(B, -1), keyAccidental(D, -3)))

    override fun visitDeuterosArktikiMartyria(ctx: DeuterosArktikiMartyriaContext?) = PitchnKey(PitchOf(D, 5), deuterosKey())

    override fun visitDeuterosVouArktikiMartyria(ctx: DeuterosVouArktikiMartyriaContext?) = PitchnKey(PitchOf(B, 4), deuterosKey())

    override fun visitDeuterosVouSkliroXroma(ctx: DeuterosVouSkliroXromaContext?): PitchnKey =
            PitchnKey(PitchOf(B, 4), createKey(
                    keyAccidental(B, -1),
                    keyAccidental(E, -1),
                    keyAccidental(F, 4),
                    keyAccidental(G, 4),
                    keyAccidental(D, 3),
                    keyAccidental(A, 3)
            ))

    override fun visitDeuterosPaSkliroXromaArktikiMartyria(ctx: DeuterosPaSkliroXromaArktikiMartyriaContext?) =
            PitchnKey(PitchOf(A, 4), hicazKey())

    override fun visitTritosGa(ctx: TritosGaContext?) = PitchnKey(PitchOf(C, 5), ussakKey())

    override fun visitTritosPaArktikiMartyria(ctx: TritosPaArktikiMartyriaContext?) = PitchnKey(PitchOf(A, 4), ussakKey())

    override fun visitTritosFthoraNhArktikiMartyria(ctx: TritosFthoraNhArktikiMartyriaContext?) =
            PitchnKey(PitchOf(C, 5), gaDiatonicKey())

    override fun visitTetartosDiArktikiMartyria(ctx: TetartosDiArktikiMartyriaContext?) =
            PitchnKey(PitchOf(D, 5), huseyniKey())

    override fun visitTetartosPaArktikiMartyria(ctx: TetartosPaArktikiMartyriaContext?) =
            PitchnKey(PitchOf(A, 4), huseyniKey())

    override fun visitLegetos(ctx: LegetosContext?) = PitchnKey(PitchOf(B, 4), createKey(
            keyAccidental(B, -1),
            keyAccidental(E, -1),
            keyAccidental(F, 4)
    ))

    override fun visitLegetosMalakoXrwmaArktikiMartyria(ctx: LegetosMalakoXrwmaArktikiMartyriaContext?) = PitchnKey(PitchOf(B, 4), deuterosKey())

    override fun visitTetartosMalakoXrwmaArktikiMartyria(ctx: TetartosMalakoXrwmaArktikiMartyriaContext?) = PitchnKey(PitchOf(D, 5), deuterosKey())

    override fun visitTetartosNenanwArktikiMartyria(ctx: TetartosNenanwArktikiMartyriaContext?) = PitchnKey(PitchOf(D, 5), hicazKey())

    override fun visitTetartosKlitonArktikiMartyria(ctx: TetartosKlitonArktikiMartyriaContext?) =
            PitchnKey(PitchOf(D, 5), createKey(keyAccidental(F, 4), keyAccidental(C, 4)))

    override fun visitPlagiosPrwtouArktikiMartyria(ctx: PlagiosPrwtouArktikiMartyriaContext?) = PitchnKey(PitchOf(A, 4), huseyniKey())

    override fun visitPlagiosPrwtouKe(ctx: PlagiosPrwtouKeContext?) =
            PitchnKey(
                    PitchOf(E, 5),
                    ByzScale.get2OctavesScale()
                            .getByStep(KE)
                            .apply { initAccidentalCommas(Note.relativeStandardStep) }
                            .applyFthora(ByzScale.SOFT_DIATONIC[PA])
                            .getKey(KE)
            )


    override fun visitPlagiosPrwtouPentafwnosArktikiMartyria(ctx: PlagiosPrwtouPentafwnosArktikiMartyriaContext?) = PitchnKey(PitchOf(A, 4), ussakKey())

    override fun visitPlagiosDeuteroyArktikiMartyria(ctx: PlagiosDeuteroyArktikiMartyriaContext?) = PitchnKey(PitchOf(A, 4), hicazKey())

    override fun visitPlagiosDeuteroyNenanwArktikiMartyria(ctx: PlagiosDeuteroyNenanwArktikiMartyriaContext?) = PitchnKey(PitchOf(D, 5), hicazKey())

    override fun visitPlagiosDeuteroyVouMalakoArktikiMartyria(ctx: PlagiosDeuteroyVouMalakoArktikiMartyriaContext?) = PitchnKey(PitchOf(B, 4), deuterosKey())

    override fun visitPlagiosDeuteroyDiMalakoArktikiMartyria(ctx: PlagiosDeuteroyDiMalakoArktikiMartyriaContext?) = PitchnKey(PitchOf(D, 5), deuterosKey())

    override fun visitPlagiosDeuteroyKeMalakoArktikiMartyria(ctx: PlagiosDeuteroyKeMalakoArktikiMartyriaContext?) =
            PitchnKey(PitchOf(E, 5), malakoXrwmaPa())

    // TODO change to PlagiosDeuteroyPaMalakoDifwniaArktikiMartyria
    override fun visitPlagiosDeuteroyKeMalakoDifwniaArktikiMartyria(ctx: PlagiosDeuteroyKeMalakoDifwniaArktikiMartyriaContext?) =
            PitchnKey(PitchOf(A, 4), malakoXrwmaPa())

    override fun visitPlagiosDeuteroyNhArktikiMartyria(ctx: PlagiosDeuteroyNhArktikiMartyriaContext?) =
            PitchnKey(PitchOf(G, 4), skliroXrwmaNh())

    override fun visitPlagiosDeuteroyNhEptafwnosArktikiMartyria(ctx: PlagiosDeuteroyNhEptafwnosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(G, 5), skliroXrwmaNh())

    override fun visitVarysGaArktikiMartyria(ctx: VarysGaArktikiMartyriaContext?) =
            PitchnKey(PitchOf(C, 5), ussakKey())

    override fun visitVarysZwSklirosArktikiMArtyria(ctx: VarysZwSklirosArktikiMArtyriaContext?) =
            PitchnKey(PitchOf(F, 4), acemAsiran())

    override fun visitVarysZwSklirosEptafwnosArktikiMArtyria(ctx: VarysZwSklirosEptafwnosArktikiMArtyriaContext?) =
            PitchnKey(PitchOf(F, 5), acemAsiran())

    override fun visitVarysDiatonikosArktikiMartyria(ctx: VarysDiatonikosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(F, 4), huseyniKey())

    override fun visitVarysDiatonikosEptafwnos(ctx: VarysDiatonikosEptafwnosContext?) =
            PitchnKey(PitchOf(F, 5), huseyniKey())

    override fun visitVarysDiatonikosTetrafwnosArktikiMartyria(ctx: VarysDiatonikosTetrafwnosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(F, 4), createKey(keyAccidental(B, -1), keyAccidental(F, 4), keyAccidental(C, 4)))

    override fun visitVarysDiatonikosPentafwnosArktikiMartyria(ctx: VarysDiatonikosPentafwnosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(F, 4), huseyniKey())

    override fun visitPlagiosTetartoy(ctx: PlagiosTetartoyContext?) =
            PitchnKey(PitchOf(G, 4), huseyniKey())

    override fun visitPlagiosTetartouTrifwnos(ctx: PlagiosTetartouTrifwnosContext?) =
            PitchnKey(PitchOf(C, 5), gaDiatonicKey())

    override fun visitPlagiosTetartouEptafwnosArktikiMartyria(ctx: PlagiosTetartouEptafwnosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(G, 5), huseyniKey())

    override fun visitPlagiosTetartouEptafwnosXrwmatikosArktikiMartyria(ctx: PlagiosTetartouEptafwnosXrwmatikosArktikiMartyriaContext?) =
            PitchnKey(PitchOf(G, 5), skliroXrwmaNh())

    data class PitchnKey(val pitch: Pitch, val key: Key)

    companion object {
        private fun keyAccidental(step: Step, commas: Int) = arrayOf(step, accidentalAlter(commas), accidental(commas))

        private fun createKey(vararg elements: Array<Any>): Key = proxyMusicFactory.createKey().apply {
            elements.forEach { nonTraditionalKey.addAll(it) }
        }

        private fun accidental(commas: Int) = ACCIDENTALS_MAP[commas] ?: throw Error("wrong accidental commas")
        private fun accidentalAlter(commas: Int) = (commas * 2.0 / 9).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

        // TODO use static values instead of function calls
        private fun ussakKey() = createKey(keyAccidental(B, -1))
        private fun huseyniKey() = createKey(keyAccidental(B, -1), keyAccidental(F, 4))
        private fun prwtosXrwmatikosKey() = createKey(
                keyAccidental(F, 2),
                keyAccidental(C, 4),
                keyAccidental(G, 4)
        )

        private fun deuterosKey() = createKey(
                keyAccidental(B, -1),
                keyAccidental(E, -3),
                keyAccidental(F, 4)
        )

        private fun hicazKey() = ByzScale.get2OctavesScale().run {
            initAccidentalCommas(Note.relativeStandardStep)
            applyChord(ByzScale.NEXEANES, 4, PA)
                    .applyChord(ByzScale.SOFT_DIATONIC, 5, DI, 2)
            getKey(PA)
        }

        private fun gaDiatonicKey() = createKey(keyAccidental(B, -1), keyAccidental(E, -1))
        private fun malakoXrwmaPa() = ByzScale.get2OctavesScale()
                .getByStep(PA)
                .apply { initAccidentalCommas(Note.relativeStandardStep) }
                .applyFthora(ByzScale.NEANES)
                .getKey(PA)

        private fun skliroXrwmaNh() = ByzScale.get2OctavesScale()
                .getByStep(NH)
                .apply { initAccidentalCommas(Note.relativeStandardStep) }
                .applyFthora(ByzScale.NEXEANES)
                .getKey(NH)

        private fun acemAsiran() = createKey(keyAccidental(B, -5))
    }
}