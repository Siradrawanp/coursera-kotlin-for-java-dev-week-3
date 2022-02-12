package nicestring

fun String.isNice(): Boolean {
    var jumlahSyarat = 0

    val noSubstring = listOf<Pair<Char,Char>>(Pair('b','u'), Pair('b','a'), Pair('b','e'))
    val charVowel   = listOf<Char>('a','i','u','e','o')

    var dobeLetter  = this.zipWithNext()

    if (dobeLetter.filter { noSubstring.contains(it) }.count() == 0) jumlahSyarat++
    if (this.filter { charVowel.contains(it) }.count() >= 3) jumlahSyarat++
    if (dobeLetter.filter { it.first == it.second }.count() > 0) jumlahSyarat++

    return (jumlahSyarat >= 2)
}