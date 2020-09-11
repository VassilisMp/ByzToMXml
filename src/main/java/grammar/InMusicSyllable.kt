package grammar

class InMusicSyllable(val syllable: String) {
    val start: String
    val middle: String
    val end: String

    init {
        val groups = regex.find(syllable)?.groups
        if (groups != null) {
            start = (groups[1]?.value?: "") + (groups[2]?.value?: "")
            middle = groups[2]?.value?: ""
            end = (groups[2]?.value?: "") + (groups[3]?.value?: "")
        } else {
            start = syllable
            middle = syllable
            end = syllable
        }
    }

    companion object {
        private val regex = ("(γκ|µπ|ντ|τσ|τζ|Γκ|Μπ|Ντ|Τσ|Τζ|[βγδζθκλμνξπρστφχψςΒΓΔΖΘΚΛΜΝΞΠΡΣΤΦΧΨΣ])?" +
                "(αι|ει|οι|ου|υι|αυ|ευ|Αι|Ει|Οι|Ου|Υι|Αυ|Ευ|[αεηιουωΑΕΗΙΟΥΩ])" +
                "(γκ|µπ|ντ|τσ|τζ|[βγδζθκλμνξπρστφχψς])?").toRegex()
    }
}