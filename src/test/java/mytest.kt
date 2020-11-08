import Byzantine.ByzClass
import Byzantine.ByzStep
import grammar.ByzLexer
import grammar.ByzParser
/*import gen.convertBaseListener
import gen.convertLexer
import gen.convertParser*/
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.InputMismatchException
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.Fraction
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.audiveris.proxymusic.Step
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import parser.Engine
import parser.InMusicSyllable
import parser.Parser
import parser.fontToByzClass
import parser.fthores.ByzScale
import west.Note
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.stream.Collectors


class ParseTest {
    //    private val title = "elpiza"
    private val title = "makarios_anir_syntoma_fokaeos_simple"
    private val docx = XWPFDocument(FileInputStream("$title.docx"))

    @Test
    fun testParse() {
        val regex = Regex("[α-ωΑ-Ω]+")
        val nonPrintable = "\\P{Print}"
        try {
            XWPFDocument().use { newDocument ->
                for (paragraph in docx.paragraphs) {
                    val newParagraph = newDocument.createParagraph()
                    for (run in paragraph.runs) {
                        val fontName = run.fontName
                        val isByzantine = fontToByzClass.containsKey(fontName)
                        var text = run.text()
                        val match = regex.containsMatchIn(text)
                        text = if (isByzantine || match) StringUtils.stripAccents(text) else continue
                        /*if (!isByzantine) {
                       text = StringUtils.stripAccents(text.replace(" ", ""));
//                                .replaceAll(nonPrintable, "");
                       if (text.length() == 0) continue;
                    }*/
                        val newRun = newParagraph.createRun()
                        newRun.setText(text)
                        newRun.fontFamily = run.fontFamily
                        println("text = " + run.text() + ", font = " + run.fontFamily)
                        val codePointsStr = text.codePoints()
                                .mapToObj { i: Int -> i.toString() }
                                .collect(Collectors.joining(", "))
                        println("codePoints = $codePointsStr")
                        //                    }
                    }
                }
                newDocument.write(FileOutputStream("dynamisPhase1.docx"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun findCodepoints() {
        for (paragraph in docx.paragraphs) {
            for (run in paragraph.runs) {
                val codePointsStr = run.text().codePoints()
                        .mapToObj { i: Int -> i.toString() }
                        .collect(Collectors.joining(", "))
                println("text = " + run.text() + ", font = " + run.fontFamily + ", codePoints = " + codePointsStr)
            }
        }
    }

    @Test
    fun stage1Transform() {
        val joiner = StringJoiner(", ")
        XWPFDocument().use { newDocument ->
            for (paragraph in docx.paragraphs) {
                val newParagraph = newDocument.createParagraph()
                for (run in paragraph.runs) {
                    val fontName = run.fontName
                    val clazz = fontToByzClass[fontName]
                    val text = run.text().run<String, String> {
                        // else keep only [α-ωΑ-Ω], which is greek text
                        if (clazz != null && clazz !in listOf(Byzantine.ByzClass.A, ByzClass.T)) filter { it.toInt() in 0xE000..0xF8FF }
                        else filter { it.toInt() in 0x0391..0x03A9 || it.toInt() in 0x03B1..0x03C9 }
                        // if is Byzantine font then keep only chars in unicode private use area
                    }
                    if (text.isEmpty()) continue
                    val newRun = newParagraph.createRun()
                    newRun.setText(text)
                    newRun.fontFamily = run.fontFamily
                    newParagraph.createRun().apply {
                        fontFamily = "Tahoma"
                        setText("   ")
                    }
//                    newRun.fontSize = run.fontSize
//                    println("text = " + newRun.text() + ", font = " + newRun.fontFamily)
                    val codePointsStr = text.codePoints()
                            .mapToObj { i: Int -> i.toString() }
                            .collect(Collectors.joining(", "))
//                    println("codePoints = $codePointsStr")
                    joiner.run {
                        if (clazz == null || clazz in listOf(ByzClass.A, ByzClass.T)) {
                            add(text.toCharArray().joinToString(separator = " "))
                        } else {
                            val byzClass: ByzClass = fontToByzClass[fontName] ?: error("couldn 't find font")
                            val str = text.toCharArray().toList().stream()
                                    .map { byzClass.toString() + String.format("%03d", (it.toInt() - 0xF000)) }
                                    .collect(Collectors.joining(" "))
                            add(str)
                        }
                    }
                }
            }
//            newDocument.write(FileOutputStream(title + "1.docx"))
            println(joiner.toString())
        }
    }

    @Test
    fun newTry() {
        val runs: List<XWPFRun> = mutableListOf<XWPFRun>().apply {
            for (par in docx.paragraphs)
                for (run in par.runs)
                    add(run)
        }

        val str: String = runs.joinToString(separator = "") { run ->
            val byzClass = fontToByzClass[run.fontName]
            run.text().run {
                when {
                    // translate special byzantine letters
                    byzClass != null && byzClass == ByzClass.N -> {
                        when (this) {
                            "u" -> "ου"
                            "s" -> "στ"
                            "n" -> "ν"
                            "z" -> "ζ" // TODO this is the wrong letter translation
                            else -> ""
                        }
                    }
                    // Arxigramma case
                    byzClass != null && byzClass == ByzClass.A -> {
                        "@$this"
                    }
                    // if is Byzantine font then keep only chars in unicode private use area
                    byzClass != null && byzClass != ByzClass.T -> {
                        filter { it.toInt() in 0xE000..0xF8FF }
                                .map { byzClass.toString() + String.format("%03d", (it.toInt() - 0xF000)) }
                                .joinToString(separator = "") { it }
                    }
                    // else keep only [α-ωΑ-Ω], which is greek text
                    else -> this
                }
            }
        }
                .replace(".", "")
                .splitToSequence(' ')
                .filter { it.isNotEmpty() }
                .map { "($it)" }
                .joinToString(separator = "")
        println(str)
    }

    @Test
    fun clearNonAscii() {
        val strValue = "Ã string çöntäining nön äsçii çhäräçters"

//        System.out.println( strValue.replaceAll( "[^\\x00-\\x7F]", "" ) );
        println(StringUtils.stripAccents(strValue))
    }

    @Test
    fun textToFile() {
        var str = "Fonitika (Vocals)1D046\uD834\uDC46BYZANTINE MUSICAL SYMBOL ISON NEO1D047\uD834\uDC47BYZANTINE MUSICAL SYMBOL OLIGON NEO1D048\uD834\uDC48BYZANTINE MUSICAL SYMBOL OXEIA NEO1D049\uD834\uDC49BYZANTINE MUSICAL SYMBOL PETASTI1D04A\uD834\uDC4ABYZANTINE MUSICAL SYMBOL KOUFISMA1D04B\uD834\uDC4BBYZANTINE MUSICAL SYMBOLPETASTOKOUFISMA1D04C\uD834\uDC4CBYZANTINE MUSICAL SYMBOLKRATIMOKOUFISMA1D04D\uD834\uDC4DBYZANTINE MUSICAL SYMBOL PELASTON NEO1D04E\uD834\uDC4EBYZANTINE MUSICAL SYMBOL KENTIMATANEO ANO1D04F\uD834\uDC4FBYZANTINE MUSICAL SYMBOL KENTIMA NEOANO1D050\uD834\uDC50BYZANTINE MUSICAL SYMBOL YPSILI1D051\uD834\uDC51BYZANTINE MUSICAL SYMBOL APOSTROFOSNEO1D052\uD834\uDC52BYZANTINE MUSICAL SYMBOL APOSTROFOISYNDESMOS NEO1D053\uD834\uDC53BYZANTINE MUSICAL SYMBOL YPORROI1D054\uD834\uDC54BYZANTINE MUSICAL SYMBOLKRATIMOYPORROON" +
                "1D055\uD834\uDC55BYZANTINE MUSICAL SYMBOL ELAFRON1D056\uD834\uDC56BYZANTINE MUSICAL SYMBOL CHAMILI" +
                "Afona or Ypostaseis (Mutes or Hypostases)1D057\uD834\uDC57BYZANTINE MUSICAL SYMBOL MIKRON ISON1D058\uD834\uDC58BYZANTINE MUSICAL SYMBOL VAREIA NEO1D059\uD834\uDC59BYZANTINE MUSICAL SYMBOL PIASMA NEO1D05A\uD834\uDC5ABYZANTINE MUSICAL SYMBOL PSIFISTON NEO1D05B\uD834\uDC5BBYZANTINE MUSICAL SYMBOL OMALON1D05C\uD834\uDC5CBYZANTINE MUSICAL SYMBOL ANTIKENOMA1D05D\uD834\uDC5DBYZANTINE MUSICAL SYMBOL LYGISMA1D05E\uD834\uDC5EBYZANTINE MUSICAL SYMBOL PARAKLITIKINEO1D05F\uD834\uDC5FBYZANTINE MUSICAL SYMBOL PARAKALESMANEO1D060\uD834\uDC60BYZANTINE MUSICAL SYMBOL ETERONPARAKALESMA1D061\uD834\uDC61BYZANTINE MUSICAL SYMBOL KYLISMA1D062\uD834\uDC62BYZANTINE MUSICAL SYMBOLANTIKENOKYLISMA1D063\uD834\uDC63BYZANTINE MUSICAL SYMBOL TROMIKON NEO1D064\uD834\uDC64BYZANTINE MUSICAL SYMBOL EKSTREPTON1D065\uD834\uDC65BYZANTINE MUSICAL SYMBOL SYNAGMA NEO1D066\uD834\uDC66BYZANTINE MUSICAL SYMBOL SYRMA1D067\uD834\uDC67BYZANTINE MUSICAL SYMBOL CHOREVMANEO1D068\uD834\uDC68BYZANTINE MUSICAL SYMBOL EPEGERMA1D069\uD834\uDC69BYZANTINE MUSICAL SYMBOL SEISMA NEO1D06A\uD834\uDC6ABYZANTINE MUSICAL SYMBOL XIRON KLASMA1D06B\uD834\uDC6BBYZANTINE MUSICAL SYMBOLTROMIKOPSIFISTON1D06C\uD834\uDC6CBYZANTINE MUSICAL SYMBOLPSIFISTOLYGISMA1D06D\uD834\uDC6DBYZANTINE MUSICAL SYMBOLTROMIKOLYGISMA1D06E\uD834\uDC6EBYZANTINE MUSICAL SYMBOLTROMIKOPARAKALESMA1D06F\uD834\uDC6FBYZANTINE MUSICAL SYMBOLPSIFISTOPARAKALESMA1D070\uD834\uDC70BYZANTINE MUSICAL SYMBOLTROMIKOSYNAGMA1D071\uD834\uDC71BYZANTINE MUSICAL SYMBOLPSIFISTOSYNAGMA1D072\uD834\uDC72BYZANTINE MUSICAL SYMBOLGORGOSYNTHETON1D073\uD834\uDC73BYZANTINE MUSICAL SYMBOLARGOSYNTHETON1D074\uD834\uDC74BYZANTINE MUSICAL SYMBOL ETERONARGOSYNTHETON1D075\uD834\uDC75BYZANTINE MUSICAL SYMBOL OYRANISMANEO1D076\uD834\uDC76BYZANTINE MUSICAL SYMBOL THEMATISMOSESO1D077\uD834\uDC77BYZANTINE MUSICAL SYMBOL THEMATISMOSEXO1D078\uD834\uDC78BYZANTINE MUSICAL SYMBOL THEMAAPLOUN1D079\uD834\uDC79BYZANTINE MUSICAL SYMBOL THES KAIAPOTHES1D07A\uD834\uDC7ABYZANTINE MUSICAL SYMBOL KATAVASMA1D07B\uD834\uDC7BBYZANTINE MUSICAL SYMBOL ENDOFONON1D07C\uD834\uDC7CBYZANTINE MUSICAL SYMBOL YFEN KATO1D07D\uD834\uDC7DBYZANTINE MUSICAL SYMBOL YFEN ANO1D07E\uD834\uDC7EBYZANTINE MUSICAL SYMBOL STAVROS" +
                "Argies (Retards)1D07F\uD834\uDC7FBYZANTINE MUSICAL SYMBOL KLASMA ANO1D080\uD834\uDC80BYZANTINE MUSICAL SYMBOL DIPLIARCHAION1D081\uD834\uDC81BYZANTINE MUSICAL SYMBOL KRATIMAARCHAION1D082\uD834\uDC82BYZANTINE MUSICAL SYMBOL KRATIMA ALLO1D083\uD834\uDC83BYZANTINE MUSICAL SYMBOL KRATIMA NEO1D084\uD834\uDC84BYZANTINE MUSICAL SYMBOL APODERMANEO1D085\uD834\uDC85BYZANTINE MUSICAL SYMBOL APLI1D086\uD834\uDC86BYZANTINE MUSICAL SYMBOL DIPLI1D087\uD834\uDC87BYZANTINE MUSICAL SYMBOL TRIPLI1D088\uD834\uDC88BYZANTINE MUSICAL SYMBOL TETRAPLI1D089\uD834\uDC89BYZANTINE MUSICAL SYMBOL KORONISLeimmata or Siopes (Leimmas or Silencers)1D08A\uD834\uDC8ABYZANTINE MUSICAL SYMBOL LEIMMA ENOSCHRONOU1D08B\uD834\uDC8BBYZANTINE MUSICAL SYMBOL LEIMMA DYOCHRONON1D08C\uD834\uDC8CBYZANTINE MUSICAL SYMBOL LEIMMA TRIONCHRONON1D08D\uD834\uDC8DBYZANTINE MUSICAL SYMBOL LEIMMATESSARON CHRONON1D08E\uD834\uDC8EBYZANTINE MUSICAL SYMBOL LEIMMAIMISEOS CHRONOUSynagmata or Gorgotites (Synagmas or Quickeners)1D08F\uD834\uDC8FBYZANTINE MUSICAL SYMBOL GORGON NEOANO1D090\uD834\uDC90BYZANTINE MUSICAL SYMBOL GORGONPARESTIGMENON ARISTERA1D091\uD834\uDC91BYZANTINE MUSICAL SYMBOL GORGONPARESTIGMENON DEXIA1D092\uD834\uDC92BYZANTINE MUSICAL SYMBOL DIGORGON1D093\uD834\uDC93BYZANTINE MUSICAL SYMBOL DIGORGONPARESTIGMENON ARISTERA KATO1D094\uD834\uDC94BYZANTINE MUSICAL SYMBOL DIGORGONPARESTIGMENON ARISTERA ANO1D095\uD834\uDC95BYZANTINE MUSICAL SYMBOL DIGORGONPARESTIGMENON DEXIA1D096\uD834\uDC96BYZANTINE MUSICAL SYMBOL TRIGORGON1D097\uD834\uDC97BYZANTINE MUSICAL SYMBOL ARGON1D098\uD834\uDC98BYZANTINE MUSICAL SYMBOL IMIDIARGON• called diargon by some authorities1D099\uD834\uDC99BYZANTINE MUSICAL SYMBOL DIARGON• called triargon by some authoritiesAgogika (Conduits)Glyphs shown for conduits reflect Greek practice, with chi asthe base letter; different national traditions use glyphs withdifferent base letters.1D09A\uD834\uDC9ABYZANTINE MUSICAL SYMBOL AGOGI POLIARGI1D09B\uD834\uDC9BBYZANTINE MUSICAL SYMBOL AGOGIARGOTERI1D09C\uD834\uDC9CBYZANTINE MUSICAL SYMBOL AGOGI ARGI1D09D\uD834\uDC9DBYZANTINE MUSICAL SYMBOL AGOGI METRIA1D09E\uD834\uDC9EBYZANTINE MUSICAL SYMBOL AGOGI MESI1D09F\uD834\uDC9FBYZANTINE MUSICAL SYMBOL AGOGI GORGI1D0A0\uD834\uDCA0BYZANTINE MUSICAL SYMBOL AGOGIGORGOTERI1D0A1\uD834\uDCA1BYZANTINE MUSICAL SYMBOL AGOGI POLIGORGI" +
                "Ichimata and Martyrika (Ichimas and Evidentials)1D0A2\uD834\uDCA2BYZANTINE MUSICAL SYMBOL MARTYRIAPROTOS ICHOS1D0A3\uD834\uDCA3BYZANTINE MUSICAL SYMBOL MARTYRIA ALLIPROTOS ICHOS1D0A4\uD834\uDCA4BYZANTINE MUSICAL SYMBOL MARTYRIADEYTEROS ICHOS1D0A5\uD834\uDCA5BYZANTINE MUSICAL SYMBOL MARTYRIA ALLIDEYTEROS ICHOS1D0A6\uD834\uDCA6BYZANTINE MUSICAL SYMBOL MARTYRIATRITOS ICHOS1D0A7\uD834\uDCA7BYZANTINE MUSICAL SYMBOL MARTYRIATRIFONIAS1D0A8\uD834\uDCA8BYZANTINE MUSICAL SYMBOL MARTYRIATETARTOS ICHOS1D0A9\uD834\uDCA9BYZANTINE MUSICAL SYMBOL MARTYRIATETARTOS LEGETOS ICHOS1D0AA\uD834\uDCAABYZANTINE MUSICAL SYMBOL MARTYRIALEGETOS ICHOS1D0AB\uD834\uDCABBYZANTINE MUSICAL SYMBOL MARTYRIAPLAGIOS ICHOS1D0AC\uD834\uDCACBYZANTINE MUSICAL SYMBOL ISAKIA TELOUSICHIMATOS1D0AD\uD834\uDCADBYZANTINE MUSICAL SYMBOL APOSTROFOITELOUS ICHIMATOS1D0AE\uD834\uDCAEBYZANTINE MUSICAL SYMBOL FANEROSISTETRAFONIAS1D0AF\uD834\uDCAFBYZANTINE MUSICAL SYMBOL FANEROSISMONOFONIAS1D0B0\uD834\uDCB0BYZANTINE MUSICAL SYMBOL FANEROSISDIFONIAS1D0B1\uD834\uDCB1BYZANTINE MUSICAL SYMBOL MARTYRIAVARYS ICHOS1D0B2\uD834\uDCB2BYZANTINE MUSICAL SYMBOL MARTYRIAPROTOVARYS ICHOS1D0B3\uD834\uDCB3BYZANTINE MUSICAL SYMBOL MARTYRIAPLAGIOS TETARTOS ICHOS1D0B4\uD834\uDCB4BYZANTINE MUSICAL SYMBOL GORTHMIKONN APLOUN• used in intonation formulas instead of nu,before phonemes a, i, o, u→03BDν  greek small letter nu1D0B5\uD834\uDCB5BYZANTINE MUSICAL SYMBOL GORTHMIKONN DIPLOUN• used in intonation formulas instead of nu,before phoneme e→03BDν  greek small letter nuFthores (Destroyers)1D0B6\uD834\uDCB6BYZANTINE MUSICAL SYMBOL ENARXIS KAIFTHORA VOU1D0B7\uD834\uDCB7BYZANTINE MUSICAL SYMBOL IMIFONON1D0B8\uD834\uDCB8BYZANTINE MUSICAL SYMBOL IMIFTHORON1D0B9\uD834\uDCB9BYZANTINE MUSICAL SYMBOL FTHORAARCHAION DEYTEROU ICHOU1D0BA\uD834\uDCBABYZANTINE MUSICAL SYMBOL FTHORADIATONIKI PA1D0BB\uD834\uDCBBBYZANTINE MUSICAL SYMBOL FTHORADIATONIKI NANA1D0BC\uD834\uDCBCBYZANTINE MUSICAL SYMBOL FTHORA NAOSICHOS1D0BD\uD834\uDCBDBYZANTINE MUSICAL SYMBOL FTHORADIATONIKI DI1D0BE\uD834\uDCBEBYZANTINE MUSICAL SYMBOL FTHORASKLIRON DIATONON DI" +
                "1D0BF\uD834\uDCBFBYZANTINE MUSICAL SYMBOL FTHORADIATONIKI KE1D0C0\uD834\uDCC0BYZANTINE MUSICAL SYMBOL FTHORADIATONIKI ZO1D0C1\uD834\uDCC1BYZANTINE MUSICAL SYMBOL FTHORADIATONIKI NI KATO1D0C2\uD834\uDCC2BYZANTINE MUSICAL SYMBOL FTHORADIATONIKI NI ANO1D0C3\uD834\uDCC3BYZANTINE MUSICAL SYMBOL FTHORAMALAKON CHROMA DIFONIAS1D0C4\uD834\uDCC4BYZANTINE MUSICAL SYMBOL FTHORAMALAKON CHROMA MONOFONIAS1D0C5\uD834\uDCC5BYZANTINE MUSICAL SYMBOL FHTORASKLIRON CHROMA VASIS※ BYZANTINE MUSICAL SYMBOL FTHORASKLIRON CHROMA VASIS• misspelling of “FTHORA” in character name is aknown defect1D0C6\uD834\uDCC6BYZANTINE MUSICAL SYMBOL FTHORASKLIRON CHROMA SYNAFI1D0C7\uD834\uDCC7BYZANTINE MUSICAL SYMBOL FTHORANENANO1D0C8\uD834\uDCC8BYZANTINE MUSICAL SYMBOL CHROA ZYGOS1D0C9\uD834\uDCC9BYZANTINE MUSICAL SYMBOL CHROA KLITON1D0CA\uD834\uDCCABYZANTINE MUSICAL SYMBOL CHROA SPATHIAlloioseis (Differentiators)1D0CB\uD834\uDCCBBYZANTINE MUSICAL SYMBOL FTHORA IYFESIS TETARTIMORION1D0CC\uD834\uDCCCBYZANTINE MUSICAL SYMBOL FTHORAENARMONIOS ANTIFONIA1D0CD\uD834\uDCCDBYZANTINE MUSICAL SYMBOL YFESISTRITIMORION1D0CE\uD834\uDCCEBYZANTINE MUSICAL SYMBOL DIESISTRITIMORION1D0CF\uD834\uDCCFBYZANTINE MUSICAL SYMBOL DIESISTETARTIMORION1D0D0\uD834\uDCD0BYZANTINE MUSICAL SYMBOL DIESIS APLIDYO DODEKATA1D0D1\uD834\uDCD1BYZANTINE MUSICAL SYMBOL DIESISMONOGRAMMOS TESSERA DODEKATA1D0D2\uD834\uDCD2BYZANTINE MUSICAL SYMBOL DIESISDIGRAMMOS EX DODEKATA1D0D3\uD834\uDCD3BYZANTINE MUSICAL SYMBOL DIESISTRIGRAMMOS OKTO DODEKATA1D0D4\uD834\uDCD4BYZANTINE MUSICAL SYMBOL YFESIS APLIDYO DODEKATA1D0D5\uD834\uDCD5BYZANTINE MUSICAL SYMBOL YFESISMONOGRAMMOS TESSERA DODEKATA1D0D6\uD834\uDCD6BYZANTINE MUSICAL SYMBOL YFESISDIGRAMMOS EX DODEKATA1D0D7\uD834\uDCD7BYZANTINE MUSICAL SYMBOL YFESISTRIGRAMMOS OKTO DODEKATA1D0D8\uD834\uDCD8BYZANTINE MUSICAL SYMBOL GENIKI DIESIS1D0D9\uD834\uDCD9BYZANTINE MUSICAL SYMBOL GENIKI YFESISRythmika (Rhythmics)1D0DA\uD834\uDCDABYZANTINE MUSICAL SYMBOL DIASTOLI APLIMIKRI→1D105\uD834\uDD05  musical symbol short barline1D0DB\uD834\uDCDBBYZANTINE MUSICAL SYMBOL DIASTOLI APLIMEGALI→1D100\uD834\uDD00  musical symbol single barline1D0DC\uD834\uDCDCBYZANTINE MUSICAL SYMBOL DIASTOLI DIPLI1D0DD\uD834\uDCDDBYZANTINE MUSICAL SYMBOL DIASTOLITHESEOS" +
                "1D0DE\uD834\uDCDEBYZANTINE MUSICAL SYMBOL SIMANSISTHESEOS1D0DF\uD834\uDCDFBYZANTINE MUSICAL SYMBOL SIMANSISTHESEOS DISIMOU1D0E0\uD834\uDCE0BYZANTINE MUSICAL SYMBOL SIMANSISTHESEOS TRISIMOU1D0E1\uD834\uDCE1BYZANTINE MUSICAL SYMBOL SIMANSISTHESEOS TETRASIMOU1D0E2\uD834\uDCE2BYZANTINE MUSICAL SYMBOL SIMANSISARSEOS1D0E3\uD834\uDCE3BYZANTINE MUSICAL SYMBOL SIMANSISARSEOS DISIMOU1D0E4\uD834\uDCE4BYZANTINE MUSICAL SYMBOL SIMANSISARSEOS TRISIMOU1D0E5\uD834\uDCE5BYZANTINE MUSICAL SYMBOL SIMANSISARSEOS TETRASIMOUGrammata (Letters)The first three characters are not actually attested in musicalcontexts.1D0E6\uD834\uDCE6BYZANTINE MUSICAL SYMBOL DIGRAMMA GG1D0E7\uD834\uDCE7BYZANTINE MUSICAL SYMBOL DIFTOGGOS OU→0223ȣ  latin small letter ou1D0E8\uD834\uDCE8BYZANTINE MUSICAL SYMBOL STIGMA→03DBϛ  greek small letter stigma1D0E9\uD834\uDCE9BYZANTINE MUSICAL SYMBOL ARKTIKO PA1D0EA\uD834\uDCEABYZANTINE MUSICAL SYMBOL ARKTIKO VOU1D0EB\uD834\uDCEBBYZANTINE MUSICAL SYMBOL ARKTIKO GA1D0EC\uD834\uDCECBYZANTINE MUSICAL SYMBOL ARKTIKO DI1D0ED\uD834\uDCEDBYZANTINE MUSICAL SYMBOL ARKTIKO KE1D0EE\uD834\uDCEEBYZANTINE MUSICAL SYMBOL ARKTIKO ZO1D0EF\uD834\uDCEFBYZANTINE MUSICAL SYMBOL ARKTIKO NISpecials1D0F0\uD834\uDCF0BYZANTINE MUSICAL SYMBOL KENTIMATANEO MESO1D0F1\uD834\uDCF1BYZANTINE MUSICAL SYMBOL KENTIMA NEOMESO1D0F2\uD834\uDCF2BYZANTINE MUSICAL SYMBOL KENTIMATANEO KATO1D0F3\uD834\uDCF3BYZANTINE MUSICAL SYMBOL KENTIMA NEOKATO1D0F4\uD834\uDCF4BYZANTINE MUSICAL SYMBOL KLASMA KATO1D0F5\uD834\uDCF5BYZANTINE MUSICAL SYMBOL GORGON NEOKATO"
        str = str.replace(Regex("1D0.."), "\n")
                .replace(Regex("BYZANTINE MUSICAL SYMBOL(' ')*"), "")
        /*println(str.filter {
            val unicodeBlock: Character.UnicodeBlock = Character.UnicodeBlock.of(it.toInt())
            println(it.toInt())
            unicodeBlock != Character.UnicodeBlock.BYZANTINE_MUSICAL_SYMBOLS
        })*/
        println(Character.UnicodeBlock.of("\uD834\uDC46".codePointAt(0)))
//        println(str)
        println(str.lines().size)
        val hasSurrogatePairAt = str.lines()[0].hasSurrogatePairAt(0)
        println(hasSurrogatePairAt)
        val codePointAt = str.lines()[1].codePointAt(0)
        println(codePointAt)

        str = str.codePoints()
                .filter { Character.UnicodeBlock.of(it) != Character.UnicodeBlock.BYZANTINE_MUSICAL_SYMBOLS }
                .collect(::StringBuilder,
                        { obj: StringBuilder, i: Int -> obj.appendCodePoint(i) },
                        { obj: StringBuilder, charSequence: StringBuilder -> obj.append(charSequence) })
                .lineSequence()
                .joinToString(separator = "\n", transform = String::trim)
        println(str)
        println("\n\n")
        println(Character.getName(119027).replaceFirst("BYZANTINE MUSICAL SYMBOL ", ""))
//        org.apache.commons.io.FileUtils.writeStringToFile(File("char.txt"), "\uD834\uDC47", "utf-16")
    }

    @Test
    fun strToUnicodeCodepointsString() {
        println("𝁈".surrogatesToHex())
        println("B123".surrogatesToHex())
    }

    /*@Test
    fun convert() {
        Listener().run()
    }*/

    @Test
    fun inMusicSyllableTest() {
        InMusicSyllable("μας").run {
            println(start)
            println(middle)
            println(end)
        }
    }

    @Test
    fun addAllTest() {
        val list = mutableListOf<Any>()
        list.addAll("0", "1", listOf("2", "3"))
        println(list)
        assertAll(
                { list[0] == "0" },
                { list[1] == "1" },
                { list[2] == "2" },
                { list[3] == "3" }
        )
    }

    fun MutableList<Any>.addAll(vararg elements: Any) {
        for (it in elements) {
            if(it is Iterable<*>) {
                it.forEach {iterableIt ->
                    if (iterableIt != null) add(iterableIt)
                }
            }
            else add(it)
        }
    }

    @Test
    fun newEngineKtTest() {
        val engine = Engine("makarios_anir_syntoma_fokaeos_simple.docx")
        try {
            engine.run()
        } catch (e: InputMismatchException) {
            println("caught")
        }
    }

    @Test
    fun myyyy() {
        val note = Note(
                step = Step.C,
                octave = 4,
                duration = 1,
                noteType = Note.NoteTypeEnum.QUARTER,
                syllable = ""
        )

        note.rationalDuration = Fraction.getFraction(4, 3)

        println(note)
    }

    @Test
    fun parseArkt() {
        val parser = Parser(XWPFDocument(FileInputStream("arktikesMartyries.docx")), false)
//        parser.parse()
        println(parser.byzCharsStr)
    }

    @Test
    fun makeArkt() {
        val str = "I096I055I045I091F070B115B067F125 I096I042I043 I096I042I061 I096B050B096B037F033 I096I042I093F033 I096I042I095 I096I041I092F072 I096I051I092 I096I057I092F072B104 I096I057I092F068 I096I054I045I080F074 I096I054I045I091F070 I096I054I045B104 I096B051B064I037I093 I096I054I045B104I061 I096I054I045I043 I096I054I045I080F036 I096I036I080F095 I096I126I055I091 I096I126I055I123I087 I096I126I055I091F070I123I087 I096I126I055I091F070L115F041 I096I126I056I095 I096I126I056B102F036 I096I126I056I061I048 I096I126I056B102I043 I096I126I056B103I123F064 I096I126I056I091F064I048 I096I126I056I112F033 I096I126I056L102I112F033 I096I040I092F041 I096I040I125F041 I096I040L102F041 I096I040I125 I096I040L102F076 I096B054B094L102 I096B054B094B103 I096B054B094L115 I096I126I054I112F068 I096I126I054B102F068 I096I126I054I092 I096I126I054I112L102F058 I096I126I054I112L102F036"
        val lexer = ByzLexer(CharStreams.fromString(str))
        val tokenStream = CommonTokenStream(lexer)
        val parser = ByzParser(tokenStream)
        parser.score()
        val namesList = tokenStream.tokens.joinToString(" ") {
            ByzLexer.VOCABULARY.getSymbolicName(it.type)
        }.replace("HXOS_WORD", "\nHXOS_WORD")
        println(namesList)
    }

    @Test
    fun scaleToKey() {
        val scale = ByzScale.get2OctavesScale()
        scale.initAccidentalCommas(Note.relativeStandardStep)
        scale.applyChord(ByzScale.NEXEANES, 4, ByzStep.BOU)
                .applyChord(ByzScale.SOFT_DIATONIC, 5, ByzStep.KE, 2)
        val key = scale.getKey(ByzStep.BOU)
        println(key.nonTraditionalKey)
    }

    @Test
    fun PaHardToKey() {
        val scale = ByzScale.get2OctavesScale()
        scale.initAccidentalCommas(Note.relativeStandardStep)
        scale.applyChord(ByzScale.NEXEANES, 4, ByzStep.PA)
                .applyChord(ByzScale.SOFT_DIATONIC, 5, ByzStep.DI, 2)
        val key = scale.getKey(ByzStep.PA)
        println(key.nonTraditionalKey)
    }
}

fun CharSequence.surrogatesToHex(): CharSequence = when{
    this.hasSurrogatePairAt(0) -> map {
        """\u${Integer.toHexString(it.toInt()).toUpperCase()}"""
    }.joinToString(separator = "", prefix = "'", postfix = "'")
    else -> "'$this'"
}

/*
class Listener : convertBaseListener() {
    override fun enterRuleLine(ctx: convertParser.RuleLineContext?) {
        val rule: convertParser.MyRuleContext? = ctx?.getChild(convertParser.MyRuleContext::class.java, 0)
        println(rule?.LITERAL(0)?.text?.removeSurrounding("'")?.surrogatesToHex())
    }

    // create frm byzLexer.g4
    fun run() {
        */
/*val file = File("/grammarSrc/byzLexer.g4")
        val str = "// Argies (Retards)\n" +
                "KLASMA_ANO : '\uD834\uDC7F' | 'B117' ;\n" +
                "DIPLI_ARCHAION : '\uD834\uDC80' ;\n" +
                "KRATIMA_ARCHAION : '\uD834\uDC81' ;\n" +
                "KRATIMA_ALLO : '\uD834\uDC82' ;\n" +
                "KRATIMA_NEO : '\uD834\uDC83' ;\n" +
                "APODERMA_NEO : '\uD834\uDC84' ;\n"*//*

        val lexer = convertLexer(CharStreams.fromStream(File("grammarSrc/byzLexer.g4").inputStream()))
        val tokenStream = CommonTokenStream(lexer)
        val parser = convertParser(tokenStream)
        val tree = parser.lines()
        val walker = ParseTreeWalker()
        walker.walk(this, tree)
        println(tree.toStringTree(parser));
    }
}*/
