/**
 * Representa a divisao generica das adjacencias do cubo.
 * Cada aresta descreve uma transferencia origem -> destino
 * com opcao de inversao, permitindo processar todas as
 * rotacoes com uma unica funcao generica.
 */

enum class TipoFatia {
    LINHA,
    COLUNA
}

data class Fatia(
    val face: String,
    val tipo: TipoFatia,
    val indice: Int
)

data class Aresta(
    val origem: Fatia,
    val destino: Fatia,
    val inverter: Boolean = false
)

private fun CuboMagico.obterFace(nome: String): FaceCubo = when (nome) {
    "cima" -> this.cima
    "baixo" -> this.baixo
    "frente" -> this.frente
    "tras" -> this.tras
    "esquerda" -> this.esquerda
    "direita" -> this.direita
    else -> throw IllegalArgumentException("Face invalida: $nome")
}

fun obterFatia(cubo: CuboMagico, fatia: Fatia): MutableList<String> {
    val face = cubo.obterFace(fatia.face)
    return when (fatia.tipo) {
        TipoFatia.LINHA -> face.obterLinha(fatia.indice)
        TipoFatia.COLUNA -> face.obterColuna(fatia.indice)
    }
}

fun definirFatia(cubo: CuboMagico, fatia: Fatia, valores: List<String>) {
    val face = cubo.obterFace(fatia.face)
    val array = valores.toTypedArray()
    when (fatia.tipo) {
        TipoFatia.LINHA -> face.definirLinha(fatia.indice, array)
        TipoFatia.COLUNA -> face.definirColuna(fatia.indice, array)
    }
}

fun processarArestas(cubo: CuboMagico, arestas: List<Aresta>): CuboMagico {
    val captura = arestas.associate { it.origem to obterFatia(cubo, it.origem).toMutableList() }
    for (aresta in arestas) {
        val valores = captura[aresta.origem]!!
        val finais = if (aresta.inverter) valores.reversed() else valores
        definirFatia(cubo, aresta.destino, finais)
    }
    return cubo
}
