/**
 * Representa o cubo magico completo com 6 faces.
 * Suporta todas as rotacoes possiveis (U, D, F, B, L, R e variacoes).
 *
 * Cada face do cubo mantem sua propria matriz de adesivos,
 * e as rotacoes movem os adesivos entre faces adjacentes
 * atraves da lista generica de [Aresta] (origem -> destino).
 */
data class CuboMagico(
    val cima: FaceCubo = FaceCubo(),
    val baixo: FaceCubo = FaceCubo(),
    val frente: FaceCubo = FaceCubo(),
    val tras: FaceCubo = FaceCubo(),
    val esquerda: FaceCubo = FaceCubo(),
    val direita: FaceCubo = FaceCubo()
) {
    companion object {
        fun resolvido(cubo: CuboMagico): Boolean {
            val faces = arrayOf(cubo.cima, cubo.baixo, cubo.frente, cubo.tras, cubo.esquerda, cubo.direita)
            for (face in faces) {
                val cor = face.obterAdesivo(0, 0)
                for (i in 0 until 3) {
                    for (j in 0 until 3) {
                        if (face.obterAdesivo(i, j) != cor) return false
                    }
                }
            }
            return true
        }

        fun embaralhar(cubo: CuboMagico, movimentos: Int = 20): CuboMagico {
            var c = cubo
            val listaMovimentos = arrayOf("U", "U'", "U2", "D", "D'", "D2", "F", "F'", "F2", "B", "B'", "B2", "L", "L'", "L2", "R", "R'", "R2")
            val random = java.util.Random()
            for (i in 1..movimentos) {
                val m = listaMovimentos[random.nextInt(listaMovimentos.size)]
                c = when (m) {
                    "U" -> girarU(c)
                    "U'" -> girarUp(c)
                    "U2" -> girarU2(c)
                    "D" -> girarD(c)
                    "D'" -> girarDp(c)
                    "D2" -> girarD2(c)
                    "F" -> girarF(c)
                    "F'" -> girarFp(c)
                    "F2" -> girarF2(c)
                    "B" -> girarB(c)
                    "B'" -> girarBp(c)
                    "B2" -> girarB2(c)
                    "L" -> girarL(c)
                    "L'" -> girarLp(c)
                    "L2" -> girarL2(c)
                    "R" -> girarR(c)
                    "R'" -> girarRp(c)
                    "R2" -> girarR2(c)
                    else -> c
                }
            }
            return c
        }
    }
}

fun girarFace90(face: FaceCubo): FaceCubo {
    val novaFace = FaceCubo(face.tamanho)
    for (i in 0 until face.tamanho) {
        for (j in 0 until face.tamanho) {
            novaFace.definirAdesivo(j, face.tamanho - 1 - i, face.obterAdesivo(i, j))
        }
    }
    return novaFace
}

// Listas de transferencia origem -> destino para cada face.
// Cada Aresta carrega a fatia de origem, a fatia de destino e se deve inverter.

val arestasCima = listOf(
    Aresta(Fatia("frente", TipoFatia.COLUNA, 0), Fatia("frente", TipoFatia.COLUNA, 0), inverter = true),
    Aresta(Fatia("direita", TipoFatia.COLUNA, 0), Fatia("direita", TipoFatia.COLUNA, 0), inverter = false),
    Aresta(Fatia("tras", TipoFatia.COLUNA, 2), Fatia("tras", TipoFatia.COLUNA, 2), inverter = true),
    Aresta(Fatia("esquerda", TipoFatia.COLUNA, 2), Fatia("esquerda", TipoFatia.COLUNA, 2), inverter = true)
)

val arestasBaixo = listOf(
    Aresta(Fatia("direita", TipoFatia.COLUNA, 2), Fatia("frente", TipoFatia.COLUNA, 2), inverter = true),
    Aresta(Fatia("tras", TipoFatia.COLUNA, 0), Fatia("direita", TipoFatia.COLUNA, 2), inverter = true),
    Aresta(Fatia("esquerda", TipoFatia.COLUNA, 0), Fatia("tras", TipoFatia.COLUNA, 0), inverter = true),
    Aresta(Fatia("frente", TipoFatia.COLUNA, 2), Fatia("esquerda", TipoFatia.COLUNA, 0), inverter = false)
)

val arestasFrente = listOf(
    Aresta(Fatia("esquerda", TipoFatia.COLUNA, 0), Fatia("cima", TipoFatia.LINHA, 2), inverter = true),
    Aresta(Fatia("cima", TipoFatia.LINHA, 2), Fatia("direita", TipoFatia.COLUNA, 2), inverter = false),
    Aresta(Fatia("direita", TipoFatia.COLUNA, 2), Fatia("baixo", TipoFatia.LINHA, 0), inverter = true),
    Aresta(Fatia("baixo", TipoFatia.LINHA, 0), Fatia("esquerda", TipoFatia.COLUNA, 0), inverter = false)
)

val arestasTras = listOf(
    Aresta(Fatia("direita", TipoFatia.COLUNA, 0), Fatia("cima", TipoFatia.LINHA, 0), inverter = true),
    Aresta(Fatia("baixo", TipoFatia.LINHA, 2), Fatia("direita", TipoFatia.COLUNA, 0), inverter = true),
    Aresta(Fatia("esquerda", TipoFatia.COLUNA, 2), Fatia("baixo", TipoFatia.LINHA, 2), inverter = true),
    Aresta(Fatia("cima", TipoFatia.LINHA, 0), Fatia("esquerda", TipoFatia.COLUNA, 2), inverter = false)
)

val arestasEsquerda = listOf(
    Aresta(Fatia("tras", TipoFatia.COLUNA, 2), Fatia("cima", TipoFatia.COLUNA, 0), inverter = true),
    Aresta(Fatia("cima", TipoFatia.COLUNA, 0), Fatia("frente", TipoFatia.COLUNA, 0), inverter = false),
    Aresta(Fatia("frente", TipoFatia.COLUNA, 0), Fatia("baixo", TipoFatia.COLUNA, 0), inverter = false),
    Aresta(Fatia("baixo", TipoFatia.COLUNA, 0), Fatia("tras", TipoFatia.COLUNA, 2), inverter = false)
)

val arestasDireita = listOf(
    Aresta(Fatia("frente", TipoFatia.COLUNA, 2), Fatia("cima", TipoFatia.COLUNA, 2), inverter = false),
    Aresta(Fatia("cima", TipoFatia.COLUNA, 2), Fatia("tras", TipoFatia.COLUNA, 0), inverter = true),
    Aresta(Fatia("tras", TipoFatia.COLUNA, 0), Fatia("baixo", TipoFatia.COLUNA, 2), inverter = true),
    Aresta(Fatia("baixo", TipoFatia.COLUNA, 2), Fatia("frente", TipoFatia.COLUNA, 2), inverter = false)
)

// Rotacoes usando a funcao generica processarArestas.
// Cada girar captura e aplica a lista de Arestas correspondente.

fun girarU(cubo: CuboMagico): CuboMagico {
    processarArestas(cubo, arestasCima)
    val novaCima = girarFace90(cubo.cima)
    return cubo.copy(cima = novaCima)
}

fun girarUp(cubo: CuboMagico): CuboMagico = girarU(girarU(girarU(cubo)))

fun girarU2(cubo: CuboMagico): CuboMagico = girarU(girarU(cubo))

fun girarD(cubo: CuboMagico): CuboMagico {
    processarArestas(cubo, arestasBaixo)
    val novoBaixo = girarFace90(cubo.baixo)
    return cubo.copy(baixo = novoBaixo)
}

fun girarDp(cubo: CuboMagico): CuboMagico = girarD(girarD(girarD(cubo)))

fun girarD2(cubo: CuboMagico): CuboMagico = girarD(girarD(cubo))

fun girarF(cubo: CuboMagico): CuboMagico {
    processarArestas(cubo, arestasFrente)
    val novaFrente = girarFace90(cubo.frente)
    return cubo.copy(frente = novaFrente)
}

fun girarFp(cubo: CuboMagico): CuboMagico = girarF(girarF(girarF(cubo)))

fun girarF2(cubo: CuboMagico): CuboMagico = girarF(girarF(cubo))

fun girarB(cubo: CuboMagico): CuboMagico {
    processarArestas(cubo, arestasTras)
    val novaTras = girarFace90(cubo.tras)
    return cubo.copy(tras = novaTras)
}

fun girarBp(cubo: CuboMagico): CuboMagico = girarB(girarB(girarB(cubo)))

fun girarB2(cubo: CuboMagico): CuboMagico = girarB(girarB(cubo))

fun girarL(cubo: CuboMagico): CuboMagico {
    processarArestas(cubo, arestasEsquerda)
    val novaEsquerda = girarFace90(cubo.esquerda)
    return cubo.copy(esquerda = novaEsquerda)
}

fun girarLp(cubo: CuboMagico): CuboMagico = girarL(girarL(girarL(cubo)))

fun girarL2(cubo: CuboMagico): CuboMagico = girarL(girarL(cubo))

fun girarR(cubo: CuboMagico): CuboMagico {
    processarArestas(cubo, arestasDireita)
    val novaDireita = girarFace90(cubo.direita)
    return cubo.copy(direita = novaDireita)
}

fun girarRp(cubo: CuboMagico): CuboMagico = girarR(girarR(girarR(cubo)))

fun girarR2(cubo: CuboMagico): CuboMagico = girarR(girarR(cubo))
