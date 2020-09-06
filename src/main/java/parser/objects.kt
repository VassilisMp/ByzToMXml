import Byzantine.ByzClass

/* TODO Palaia fonts not supported
     * because of different character matching to Byzantine fonts
     * maybe I'll have to make the matching*/
// TODO test all fonts
internal val fontToByzClass = mapOf(
        "PFKonstantinople" to ByzClass.A, //T // TODO Run method for Arxigramma (probably same code with text fonts)
        "BZ Byzantina" to ByzClass.B,
        "BZ Byzantina2" to ByzClass.B,
        "DP_NRByzantina" to ByzClass.B,
        "MKByzantine" to ByzClass.B,
        "BZ Fthores" to ByzClass.F,
        "DP_NRFthores" to ByzClass.F,
        "MKFthores" to ByzClass.F,
        "BZ Ison" to ByzClass.I,
        "DP_NRIson" to ByzClass.I,
        "MK Ison" to ByzClass.I,
        "MK Ison2" to ByzClass.I,
        "MKNewIson" to ByzClass.I,
        "BZ Loipa" to ByzClass.L,
        "DP_NRLoipa" to ByzClass.L,
        "MKLoipa" to ByzClass.L,
        "MK" to ByzClass.N, //N //T // TODO not supported, matching differs to standard font types (example Byzantine, Fthores...)
        "MK2015" to ByzClass.N, //N //T // TODO not supported
        "MK2017 design" to ByzClass.N, //N // T // TODO not supported
        "BZ Palaia" to ByzClass.P, // TODO Palaia not supported
        "DP_NRPalaia" to ByzClass.P,
        "MK Palaia" to ByzClass.P,
        "Genesis" to ByzClass.T, // TODO Run method for text fonts
        "GFSDidotClassic" to ByzClass.T, // same
        "GFSNicefore" to ByzClass.T, // same
        "MgAgiaSofia UC Normal" to ByzClass.T, // same
        "MgByzantine UC Pol Normal" to ByzClass.T, // same
        "BZ Xronos" to ByzClass.X,
        "DP_NRXronos" to ByzClass.X,
        "MK Xronos2016" to ByzClass.X,
        "MKXronos" to ByzClass.X
)