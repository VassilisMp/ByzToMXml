object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        /*val docx = XWPFDocument(FileInputStream("elpiza.docx"))
        val name = docx.document.body.newCursor().execQuery("w:r").name
        println(name)*/
//        -classpath /home/vassilis/IdeaProjects/ByzToWes/libs/SaxonHE10-1J/*

        /*val grammarName = "generatedGrammar"

        val s = (0x1D046..0x1D0F5).joinToString(separator = "\n") {
            val name = Character.getName(it)
                    .replaceFirst("BYZANTINE MUSICAL SYMBOL ", "")
                    .replace(' ', '_')
            val char = String(Character.toChars(it))
            "$name : \'$char\' ;"
        }
        println(s)
        File("$grammarName.g4").writeText("lexer grammar $grammarName;\n\n$s")*/
        val parser = Parser()
        parser.parse()
    }
}