grammar argies;

argia : klasma
        | apli
        | dipli
        | tripli
        ;

klasma : KLASMA_ANO
        | KLASMA_KATO
        | KLASMA_ANO_STA_DEXIA
        | KLASMA_ANO_STA_ARISTERA
        ;
apli : APLI | APLI_STA_DEXIA ;
dipli : DIPLI | DIPLI_STA_DEXIA ;
tripli : TRIPLI | TRIPLI_STA_DEXIA ;

/*koronis :
        KORONIS
        | KORONIS_HAMILA
        | KORONIS_HAMILA_STA_DEXIA
        | KORONIS_STA_DEXIA
        ;
stavros : STAVROS | STAVROS_PSILA ;*/

// Argies (Retards)
KLASMA_ANO : '𝁿' | 'B117' ;
DIPLI_ARCHAION : '𝂀' -> skip ;
KRATIMA_ARCHAION : '𝂁' -> skip ;
KRATIMA_ALLO : '𝂂' -> skip ;
KRATIMA_NEO : '𝂃' -> skip ;
APODERMA_NEO : '𝂄' -> skip ;
APLI : '𝂅' | 'B056' ;
DIPLI : '𝂆' | 'B057' ;
TRIPLI : '𝂇' | 'B048' ;
TETRAPLI : '𝂈' -> skip ;
KORONIS : ('𝂉' | 'L077') -> skip ;
// Specials
KLASMA_KATO : '𝃴' | 'B073' | 'B145' ; // B145 is not in the catalog, but it's been found in scores

KLASMA_ANO_STA_DEXIA : 'B105' ;
KLASMA_ANO_STA_ARISTERA : 'B085' ;
APLI_STA_DEXIA : 'B042' ;
DIPLI_STA_DEXIA : 'B040' ;
TRIPLI_STA_DEXIA : 'B041' ;

// Afona or Ypostaseis (Mutes or Hypostases)
STAVROS : ('𝁾' | 'F039') -> skip ;

KORONIS_HAMILA : 'L060' -> skip ;
KORONIS_HAMILA_STA_DEXIA : 'L062' -> skip ;
KORONIS_STA_DEXIA : 'B169' -> skip ;
STAVROS_PSILA : 'F034' -> skip ;
BREATH_MARK : 'F169' -> skip ;