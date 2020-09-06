grammar fonitika;

qChar :
        /*kentimataAboveOligonAndGorgon
        | kentimataAboveOligonAndDigorgon
        | yporroiAndGorgon
        | yporroiAndGorgonLeftDot
        | yporroiAndGorgonRightDot
        | yporroiAndDigorgon
        | yporroiAndDigorgonLeftDot
        | yporroiAndTrigorgon
        | yporroiAndTrigorgonLeftDot
        | yporroiAndKentimataOverOligonAndGorgon
        | yporroiAndKentimataOverOligonAndDigorgon
        | yporroiAndKentimataOverOligonAndTrigorgon
        | yporroiAndKentimataOverOligonAndGorgonLDot
        | yporroiAndKentimataOverOligonAndGorgonRDot
        | yporroiAndKentimataOverOligonAndDigorgonLDot
        | yporroiAndKentimataOverOligonAndDigorgonRDot
        | yporroiAndKentimataOverOligonAndTrigorgonLDot
        | yporroiAndKentimataOverOligonAndTrigorgonRDot*/
        kentimaToTheRightOfOligon
        | OLIGON_NEO
        | PETASTI
        | KENTIMATA_NEO_MESO
        | KENTIMA_NEO_MESO
        | OLIGON_OVER_PETASTI
        | KENTIMA_UNDER_OLIGON
        | OLIGON_ABOVE_KENTIMATA
        | KENTIMATA_ABOVE_OLIGON
//        | kentimataAboveOligonAndGorgon
//        | kentimataAboveOligonAndDigorgon
        | ANTIKENOMA_UNDER_KENTIMA_UNDER_OLIGON
//        | APLI_UNDER_ANTIKENOMA_UNDER_KENTIMA_UNDER_OLIGON
//        | APLI_UNDER_ANTIKENOMA_UNDER_OLIGON_ABOVE_KENTIMATA
        | KENTIMA_OVER_OLIGON
        | KENTIMA_OVER_PETASTI
        | YPSILI_AT_RIGHT_END_OF_OLIGON
        | YPSILI_AT_RIGHT_END_OF_PETASTI
        | YPSILI_AT_LEFT_END_OF_OLIGON
        | YPSILI_AT_LEFT_END_OF_PETASTI
        | YPSILI_AT_RIGHT_END_OF_OLIGON_AND_KENTIMATA_AT_LEFT
        | YPSILI_AT_LEFT_END_OF_OLIGON_AND_KENTIMATA_AT_RIGHT
        | YPSILI_NEXT_TO_KENTIMA_OVER_OLIGON
        | YPSILI_NEXT_TO_KENTIMA_OVER_PETASTI
        | YPSILI_OVER_KENTIMA_OVER_OLIGON
        | YPSILI_OVER_KENTIMA_OVER_PETASTI
        | TWO_IPSILES_OVER_OLIGON
        | TWO_IPSILES_OVER_PETASTI
        | TWO_IPSILES_OVER_KETNIMATA_OVER_OLIGON
        | TWO_IPSILES_OVER_KETNIMATA_OVER_PETASTI
        | YPSILI_OVER_KENTIMA_OVER_OLIGON_AND_YPSILI_TO_LEFT
        | YPSILI_OVER_KENTIMA_OVER_PETASTI_AND_YPSILI_RIGHT
        | THREE_YPSILES_OVER_OLIGON
        | THREE_YPSILES_OVER_PETASTI
        | THREE_YPSILES_OVER_OLIGON_KENTIMATA_IN_MIDDLE
        | THREE_YPSILES_OVER_PETASTI_KENTIMATA_IN_MIDDLE
        | THREE_YPSILES_OVER_OLIGON_KENTIMA_IN_MIDDLE
        | APOSTROFOS_NEO
        | CONTINUOUS_ELAFRON
        | ELAFRON
        | ELAPHRON_OVER_APOSTROPHOS
        | CHAMILI
        | HAMILI_OVER_APOSTROPHOS
        | HAMILI_OVER_ELAPHRON
        | HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS
        | APOSTROPHOS_OVER_PETASTI
        | CONTINUOUS_ELAFRON_OVER_PETASTI
        | ELAPHRON_OVER_PETASTI
        | ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI
//        | YPORROI
//        | yporroiAndGorgon
//        | yporroiAndGorgonLeftDot
//        | yporroiAndGorgonRightDot
        | TWO_APOSTROPHOI_IN_A_ROW
        | APOSTROPHOS_OVER_OLIGON
        | ELAPHRON_OVER_OLIGON
        | ELAPHRON_OVER_APOSTROPHOS_OVER_ISON
        | HAMILI_OVER_OLIGON
//        | YPORROI_OVER_OLIGON
//        | YPORROI_OVER_PETASTI
        | HAMILI_OVER_HAMILI
        | HAMILI_OVER_PETASTI
        | HAMILI_OVER_APOSTROPHOS_OVER_PETASTI
        | HAMILI_OVER_ELAPHRON_OVER_PETASTI
        | HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI
        | HAMILI_OVER_HAMILI_OVER_PETASTI
        | HAMILI_OVER_HAMILI_OVER_APOSTROPHOS
        | HAMILI_OVER_HAMILI_OVER_APOSTROPHOS_OVER_PETASTI
        | HAMILI_OVER_HAMILI_OVER_ELAPHRON
        | HAMILI_OVER_HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS
        | HAMILI_OVER_HAMILI_OVER_HAMILI
        | ISON_NEO
        | ISON_OVER_PETASTI
        | ISON_OVER_OLIGON
        | APOSTROPHOS_UNDER_ISON
        | APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON
        | ISON_AND_KENTIMATA_OVER_OLIGON
        | ELAPHRON_AND_KENTIMATA_OVER_OLIGON
        | CONTINUOUS_ELAFRON_AND_KENTIMATA_OVER_OLIGON
        | ELAPHRON_OVER_APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON
        | HAMILI_AND_KENTIMATA_OVER_OLIGON
        | yporroi
//        | YPORROI_AND_KENTIMATA_OVER_OLIGON
        // with vareia
        // TODO this is wrong, because it can accept many varias in a row
//        | VAREIA_NEO qChar
        ;

kentimaToTheRightOfOligon : OLIGON_NEO KENTIMA_NEO_MESO | KENTIMA_TO_THE_RIGHT_OF_OLIGON ;
/*kentimataAboveOligonAndGorgon : KENTIMATA_ABOVE_OLIGON GORGON_USED_ON_KENTIMATA_ABOVE_OLIGON ;
kentimataAboveOligonAndDigorgon : KENTIMATA_ABOVE_OLIGON DIGORGON_USED_ON_KENTIMATA_ABOVE_OLIGON ;*/
/*yporroiAndGorgon : YPORROI GORGON_USED_ON_YPORROI;
yporroiAndGorgonLeftDot : YPORROI GORGON_LEFT_DOT_USED_ON_YPORROI;
yporroiAndGorgonRightDot : YPORROI GORGON_RIGHT_DOT_USED_ON_YPORROI;
yporroiAndDigorgon : YPORROI DIGORGON_USED_ON_YPORROI;
yporroiAndDigorgonLeftDot : YPORROI DIGORGON_LEFT_DOT_USED_ON_YPORROI;
yporroiAndTrigorgon : YPORROI TRIGORGON_USED_ON_YPORROI;
yporroiAndTrigorgonLeftDot : YPORROI TRIGORGON_LEFT_DOT_USED_ON_YPORROI;*/
/*yporroiAndKentimataOverOligonAndGorgon : YPORROI_AND_KENTIMATA_OVER_OLIGON GORGON_USED_ON_L116_YPORROI ;
yporroiAndKentimataOverOligonAndDigorgon : YPORROI_AND_KENTIMATA_OVER_OLIGON DIGORGON_USED_ON_L116_YPORROI ;
yporroiAndKentimataOverOligonAndTrigorgon : YPORROI_AND_KENTIMATA_OVER_OLIGON TRIGORGON_USED_ON_L116_YPORROI ;
yporroiAndKentimataOverOligonAndGorgonLDot : YPORROI_AND_KENTIMATA_OVER_OLIGON GORGON_LEFT_DOT_USED_ON_L116_YPORROI ;
yporroiAndKentimataOverOligonAndGorgonRDot : YPORROI_AND_KENTIMATA_OVER_OLIGON GORGON_PARESTIGMENON_DEXIA_STA_DEXIA ;
yporroiAndKentimataOverOligonAndDigorgonLDot : YPORROI_AND_KENTIMATA_OVER_OLIGON DIGORGON_PARESTIGMENON_ARISTERA_KATO_STA_DEXIA ;
yporroiAndKentimataOverOligonAndDigorgonRDot : YPORROI_AND_KENTIMATA_OVER_OLIGON DIGORGON_PARESTIGMENON_DEXIA_STA_DEXIA ;
yporroiAndKentimataOverOligonAndTrigorgonLDot : YPORROI_AND_KENTIMATA_OVER_OLIGON TRIGORGON_PARESTIGMENON_ARISTERA_KATO_STA_DEXIA ;
yporroiAndKentimataOverOligonAndTrigorgonRDot : YPORROI_AND_KENTIMATA_OVER_OLIGON TRIGORGON_PARESTIGMENON_DEXIA_STA_DEXIA ;*/

yporroi : /*yporroiAndGorgon
        | yporroiAndGorgonLeftDot
        | yporroiAndGorgonRightDot
        | yporroiAndDigorgon
        | yporroiAndDigorgonLeftDot
        | yporroiAndTrigorgon
        | yporroiAndTrigorgonLeftDot
        | yporroiAndKentimataOverOligonAndGorgon
        | yporroiAndKentimataOverOligonAndDigorgon
        | yporroiAndKentimataOverOligonAndTrigorgon
        | yporroiAndKentimataOverOligonAndGorgonLDot
        | yporroiAndKentimataOverOligonAndGorgonRDot
        | yporroiAndKentimataOverOligonAndDigorgonLDot
        | yporroiAndKentimataOverOligonAndDigorgonRDot
        | yporroiAndKentimataOverOligonAndTrigorgonLDot
        | yporroiAndKentimataOverOligonAndTrigorgonRDot*/
        YPORROI
        | YPORROI_OVER_OLIGON
        | YPORROI_OVER_PETASTI
        | YPORROI_AND_KENTIMATA_OVER_OLIGON
        ;

//yporroi_tChar : yporroi ;


//yporroiAndTCharAndSylabble: yporroi tChar? SYLLABLE;

// Fonitika (Vocals)
ISON_NEO : '𝁆' | 'B097' ;
OLIGON_NEO : '𝁇' | 'B115' ;
OXEIA_NEO : '𝁈' ;
PETASTI : '𝁉' | 'B083';
//KOUFISMA : '𝁊' ;
//PETASTOKOUFISMA : '𝁋' ;
//KRATIMOKOUFISMA : '𝁌' ;
//PELASTON_NEO : '𝁍' ;
KENTIMATA_NEO_ANO : '𝁎' -> skip ;
KENTIMA_NEO_ANO : '𝁏' -> skip ;
YPSILI : '𝁐' -> skip ;
APOSTROFOS_NEO : '𝁑' | 'B106' ;
APOSTROFOI_SYNDESMOS_NEO : '𝁒' -> skip ;
YPORROI : '𝁓' | 'B039' | 'L164' ;
KRATIMOYPORROON : '𝁔' -> skip ;
ELAFRON : '𝁕' | 'B107' ;            // -β
CHAMILI : '𝁖' | 'B059' ;            // -δ

// Specials
KENTIMATA_NEO_MESO : '𝃰' | 'B120' ;
KENTIMA_NEO_MESO : '𝃱' | 'B067';
KENTIMATA_NEO_KATO : '𝃲' ;
KENTIMA_NEO_KATO : '𝃳' ;

// MK only
OLIGON_OVER_PETASTI : 'B068' ;                                  // β
KENTIMA_UNDER_OLIGON : 'B100' ;                                 // β
OLIGON_ABOVE_KENTIMATA : 'B099' | 'B206' ;                      // 2
KENTIMATA_ABOVE_OLIGON : 'B118' ;                               // 2

ANTIKENOMA_UNDER_KENTIMA_UNDER_OLIGON : 'L063' ;                // β
APLI_UNDER_ANTIKENOMA_UNDER_KENTIMA_UNDER_OLIGON : 'B090' ;     // β.
APLI_UNDER_ANTIKENOMA_UNDER_OLIGON_ABOVE_KENTIMATA : 'B122' ;   // 2.
KENTIMA_OVER_OLIGON : 'B102' ;                                  // γ
KENTIMA_OVER_PETASTI : 'B070' ;                                 // γ
YPSILI_AT_RIGHT_END_OF_OLIGON : 'B103' ;                        // δ
YPSILI_AT_RIGHT_END_OF_PETASTI : 'B071' ;                       // δ
YPSILI_AT_LEFT_END_OF_OLIGON : 'L115' ;                         // ε
YPSILI_AT_LEFT_END_OF_PETASTI : 'L083' ;                        // ε
YPSILI_AT_RIGHT_END_OF_OLIGON_AND_KENTIMATA_AT_LEFT : 'L097' ;  // δ α
YPSILI_AT_LEFT_END_OF_OLIGON_AND_KENTIMATA_AT_RIGHT : 'L065' ;  // ε α
YPSILI_NEXT_TO_KENTIMA_OVER_OLIGON : 'L100' ;                   // στ
YPSILI_NEXT_TO_KENTIMA_OVER_PETASTI : 'L068' ;                  // στ
YPSILI_OVER_KENTIMA_OVER_OLIGON : 'L102' ;                      // ζ
YPSILI_OVER_KENTIMA_OVER_PETASTI : 'L070' ;                     // ζ
TWO_IPSILES_OVER_OLIGON : 'L103' ;                              // η
TWO_IPSILES_OVER_PETASTI : 'L071' ;                             // η
TWO_IPSILES_OVER_KETNIMATA_OVER_OLIGON : 'L104' ;               // θ
TWO_IPSILES_OVER_KETNIMATA_OVER_PETASTI : 'L072' ;              // θ
YPSILI_OVER_KENTIMA_OVER_OLIGON_AND_YPSILI_TO_LEFT : 'L107' ;   // ια
YPSILI_OVER_KENTIMA_OVER_PETASTI_AND_YPSILI_RIGHT : 'L074' ;    // ι
THREE_YPSILES_OVER_OLIGON : 'L108' ;                            // ιβ
THREE_YPSILES_OVER_PETASTI : 'L076' ;                           // ιβ
THREE_YPSILES_OVER_OLIGON_KENTIMATA_IN_MIDDLE : 'L059' ;        // ιγ
THREE_YPSILES_OVER_PETASTI_KENTIMATA_IN_MIDDLE : 'L058' ;       // ιγ
THREE_YPSILES_OVER_OLIGON_KENTIMA_IN_MIDDLE : 'L039' ;          // ιδ
CONTINUOUS_ELAFRON : 'B104' ;                                   // /-2
ELAPHRON_OVER_APOSTROPHOS : 'B108' ;                            // -γ
HAMILI_OVER_APOSTROPHOS : 'L120' ;                              // -ε
HAMILI_OVER_ELAPHRON : 'L099' ;                                 // -στ
HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS : 'L118' ;                // -ζ
APOSTROPHOS_OVER_PETASTI : 'B074' ;                             // -α
CONTINUOUS_ELAFRON_OVER_PETASTI : 'B072' ;                      // /-2
ELAPHRON_OVER_PETASTI : 'B075' ;                                // -β
ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI : 'B076' ;               // -γ

TWO_APOSTROPHOI_IN_A_ROW : 'B061' ;                             // -2
APOSTROPHOS_OVER_OLIGON : 'L041' ;                              // -α
ELAPHRON_OVER_OLIGON : 'L045' ;                                 // -β
ELAPHRON_OVER_APOSTROPHOS_OVER_ISON : 'L061' ;                  // -γ
HAMILI_OVER_OLIGON : 'L043' ;                                   // -δ
YPORROI_OVER_OLIGON : 'L095' ;                                  // -2
YPORROI_OVER_PETASTI : 'B034' ;                                 // -2
HAMILI_OVER_HAMILI : 'L098' ;                                   // -η
HAMILI_OVER_PETASTI : 'L090' ;                                  // -δ
HAMILI_OVER_APOSTROPHOS_OVER_PETASTI : 'L088' ;                 // -ε
HAMILI_OVER_ELAPHRON_OVER_PETASTI : 'L067' ;                    // -στ
HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS_OVER_PETASTI : 'L086' ;   // -ζ
HAMILI_OVER_HAMILI_OVER_PETASTI : 'L066' ;                      // -η
HAMILI_OVER_HAMILI_OVER_APOSTROPHOS : 'L110' ;                  // -θ
HAMILI_OVER_HAMILI_OVER_APOSTROPHOS_OVER_PETASTI : 'L078' ;     // -θ
HAMILI_OVER_HAMILI_OVER_ELAPHRON : 'L109' ;                     // -ι
HAMILI_OVER_HAMILI_OVER_ELAPHRON_OVER_APOSTROPHOS : 'L044' ;    // -ια
HAMILI_OVER_HAMILI_OVER_HAMILI : 'L046' ;                       // -ιβ
ISON_OVER_PETASTI : 'B065' ;                                    // 0
ISON_OVER_OLIGON : 'L048' ;                                     // 0
APOSTROPHOS_UNDER_ISON : 'B043' ;                               // 0 -α
APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON : 'B045' ;                // -α α
ISON_AND_KENTIMATA_OVER_OLIGON : 'B095' ;                       // 0 α
ELAPHRON_AND_KENTIMATA_OVER_OLIGON : 'L112' ;                   // -β α
CONTINUOUS_ELAFRON_AND_KENTIMATA_OVER_OLIGON : 'L080' ;         // / -2 α
ELAPHRON_OVER_APOSTROPHOS_AND_KENTIMATA_OVER_OLIGON : 'L091' ;  // -γ α
HAMILI_AND_KENTIMATA_OVER_OLIGON : 'L123' ;                     // -δ α
YPORROI_AND_KENTIMATA_OVER_OLIGON : 'L116' ;                    // -2 a
KENTIMA_TO_THE_RIGHT_OF_OLIGON : 'B162' ;