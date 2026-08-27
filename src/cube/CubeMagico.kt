/**
 * Representa o cubo mágico completo com 6 faces.
 * Suporta todas as rotações possíveis (U, D, F, B, L, R e variações).
 *
 * Cada face do cubo mantém sua própria matriz de adesivos,
 * e as rotações movem os adesivos entre faces adjacentes.
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
        // Verifica se todos os adesivos de cada face têm a mesma cor
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

        // Embaralha o cubo fazendo um numero aleatorio de movimentos
        fun embaralhar(cubo: CuboMagico, movimentos: Int = 20): CuboMagico {
            var c = cubo
            // Lista de todas as possiveis rotacoes (e suas variacoes)
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

// Funcao auxiliar para girar uma face 90 graus no sentido horario
fun girarFace90(face: FaceCubo): FaceCubo {
    var novaFace = FaceCubo(face.tamanho)
    for (i in 0 until face.tamanho) {
        for (j in 0 until face.tamanho) {
            novaFace.definirAdesivo(j, face.tamanho - 1 - i, face.obterAdesivo(i, j))
        }
    }
    return novaFace
}

// Funcoes para obter os adesivos adjacentes na direcao U (cima)
fun getAdjU(cubo: CuboMagico): Map<String, MutableList<String>> {
    return mapOf(
        "frente" to cubo.frente.obterColuna(0),
        "direita" to cubo.direita.obterColuna(0),
        "tras" to cubo.tras.obterColuna(2),
        "esquerda" to cubo.esquerda.obterColuna(2)
    )
}

fun setAdjU(cubo: CuboMagico, adj: Map<String, MutableList<String>>): CuboMagico {
    var c = cubo
    c.frente.definirColuna(0, adj["frente"]!!.reversed().toTypedArray())
    c.direita.definirColuna(0, adj["direita"]!!.toTypedArray())
    c.tras.definirColuna(2, adj["tras"]!!.reversed().toTypedArray())
    c.esquerda.definirColuna(2, adj["esquerda"]!!.reversed().toTypedArray())
    return c
}

// Funcoes para obter os adesivos adjacentes na direcao D (baixo)
fun getAdjD(cubo: CuboMagico): Map<String, MutableList<String>> {
    return mapOf(
        "frente" to cubo.frente.obterColuna(2),
        "direita" to cubo.direita.obterColuna(2),
        "tras" to cubo.tras.obterColuna(0),
        "esquerda" to cubo.esquerda.obterColuna(0)
    )
}

fun setAdjD(cubo: CuboMagico, adj: Map<String, MutableList<String>>): CuboMagico {
    var c = cubo
    c.frente.definirColuna(2, adj["direita"]!!.reversed().toTypedArray())
    c.direita.definirColuna(2, adj["tras"]!!.reversed().toTypedArray())
    c.tras.definirColuna(0, adj["esquerda"]!!.reversed().toTypedArray())
    c.esquerda.definirColuna(0, adj["frente"]!!.toTypedArray())
    return c
}

// Funcoes para obter os adesivos adjacentes na direcao F (frente)
fun getAdjF(cubo: CuboMagico): Map<String, MutableList<String>> {
    return mapOf(
        "cima" to cubo.cima.obterLinha(2),
        "direita" to cubo.direita.obterColuna(2),
        "baixo" to cubo.baixo.obterLinha(0),
        "esquerda" to cubo.esquerda.obterColuna(0)
    )
}

fun setAdjF(cubo: CuboMagico, adj: Map<String, MutableList<String>>): CuboMagico {
    var c = cubo
    c.cima.definirLinha(2, adj["esquerda"]!!.reversed().toTypedArray())
    c.direita.definirColuna(2, adj["cima"]!!.toTypedArray())
    c.baixo.definirLinha(0, adj["direita"]!!.reversed().toTypedArray())
    c.esquerda.definirColuna(0, adj["baixo"]!!.toTypedArray())
    return c
}

// Funcoes para obter os adesivos adjacentes na direcao B (tras)
fun getAdjB(cubo: CuboMagico): Map<String, MutableList<String>> {
    return mapOf(
        "cima" to cubo.cima.obterLinha(0),
        "direita" to cubo.direita.obterColuna(0),
        "baixo" to cubo.baixo.obterLinha(2),
        "esquerda" to cubo.esquerda.obterColuna(2)
    )
}

fun setAdjB(cubo: CuboMagico, adj: Map<String, MutableList<String>>): CuboMagico {
    var c = cubo
    c.cima.definirLinha(0, adj["direita"]!!.reversed().toTypedArray())
    c.direita.definirColuna(0, adj["baixo"]!!.reversed().toTypedArray())
    c.baixo.definirLinha(2, adj["esquerda"]!!.reversed().toTypedArray())
    c.esquerda.definirColuna(2, adj["cima"]!!.toTypedArray())
    return c
}

// Funcoes para obter os adesivos adjacentes na direcao L (esquerda)
fun getAdjL(cubo: CuboMagico): Map<String, MutableList<String>> {
    return mapOf(
        "cima" to cubo.cima.obterColuna(0),
        "frente" to cubo.frente.obterColuna(0),
        "baixo" to cubo.baixo.obterColuna(0),
        "tras" to cubo.tras.obterColuna(2)
    )
}

fun setAdjL(cubo: CuboMagico, adj: Map<String, MutableList<String>>): CuboMagico {
    var c = cubo
    c.cima.definirColuna(0, adj["tras"]!!.reversed().toTypedArray())
    c.frente.definirColuna(0, adj["cima"]!!.toTypedArray())
    c.baixo.definirColuna(0, adj["frente"]!!.toTypedArray())
    c.tras.definirColuna(2, adj["baixo"]!!.toTypedArray())
    return c
}

// Funcoes para obter os adesivos adjacentes na direcao R (direita)
fun getAdjR(cubo: CuboMagico): Map<String, MutableList<String>> {
    return mapOf(
        "cima" to cubo.cima.obterColuna(2),
        "tras" to cubo.tras.obterColuna(0),
        "baixo" to cubo.baixo.obterColuna(2),
        "frente" to cubo.frente.obterColuna(2)
    )
}

fun setAdjR(cubo: CuboMagico, adj: Map<String, MutableList<String>>): CuboMagico {
    var c = cubo
    c.cima.definirColuna(2, adj["frente"]!!.toTypedArray())
    c.tras.definirColuna(0, adj["cima"]!!.reversed().toTypedArray())
    c.baixo.definirColuna(2, adj["tras"]!!.reversed().toTypedArray())
    c.frente.definirColuna(2, adj["baixo"]!!.toTypedArray())
    return c
}

// Rotação da face U (cima)
fun girarU(cubo: CuboMagico): CuboMagico {
    val adj = getAdjU(cubo)
    val novaCima = girarFace90(cubo.cima)
    return cubo.copy(
        cima = novaCima,
        frente = setAdjU(cubo, adj).frente,
        tras = setAdjU(cubo, adj).tras,
        esquerda = setAdjU(cubo, adj).esquerda,
        direita = setAdjU(cubo, adj).direita
    )
}

// Rotação da face U no sentido anti-horário (U')
fun girarUp(cubo: CuboMagico): CuboMagico = girarU(cubo)

// Rotação da face U dois graus (U2)
fun girarU2(cubo: CuboMagico): CuboMagico = girarU(girarU(cubo))

// Rotação da face D (baixo)
fun girarD(cubo: CuboMagico): CuboMagico {
    val adj = getAdjD(cubo)
    val novoBaixo = girarFace90(cubo.baixo)
    return cubo.copy(
        baixo = novoBaixo,
        frente = setAdjD(cubo, adj).frente,
        tras = setAdjD(cubo, adj).tras,
        esquerda = setAdjD(cubo, adj).esquerda,
        direita = setAdjD(cubo, adj).direita
    )
}

// Rotação da face D no sentido anti-horário (D')
fun girarDp(cubo: CuboMagico): CuboMagico = girarD(girarD(cubo))

// Rotação da face D dois graus (D2)
fun girarD2(cubo: CuboMagico): CuboMagico = girarD(girarD(cubo))

// Rotação da face F (frente)
fun girarF(cubo: CuboMagico): CuboMagico {
    val adj = getAdjF(cubo)
    val novaFrente = girarFace90(cubo.frente)
    return cubo.copy(
        cima = setAdjF(cubo, adj).cima,
        direita = setAdjF(cubo, adj).direita,
        baixo = setAdjF(cubo, adj).baixo,
        esquerda = setAdjF(cubo, adj).esquerda,
        frente = novaFrente
    )
}

// Rotação da face F no sentido anti-horário (F')
fun girarFp(cubo: CuboMagico): CuboMagico = girarF(girarF(cubo))

// Rotação da face F dois graus (F2)
fun girarF2(cubo: CuboMagico): CuboMagico = girarF(girarF(cubo))

// Rotação da face B (tras)
fun girarB(cubo: CuboMagico): CuboMagico {
    val adj = getAdjB(cubo)
    val novaTras = girarFace90(cubo.tras)
    return cubo.copy(
        cima = setAdjB(cubo, adj).cima,
        direita = setAdjB(cubo, adj).direita,
        baixo = setAdjB(cubo, adj).baixo,
        esquerda = setAdjB(cubo, adj).esquerda,
        tras = novaTras
    )
}

// Rotação da face B no sentido anti-horário (B')
fun girarBp(cubo: CuboMagico): CuboMagico = girarB(girarB(cubo))

// Rotação da face B dois graus (B2)
fun girarB2(cubo: CuboMagico): CuboMagico = girarB(girarB(cubo))

// Rotação da face L (esquerda)
fun girarL(cubo: CuboMagico): CuboMagico {
    val adj = getAdjL(cubo)
    val novaEsquerda = girarFace90(cubo.esquerda)
    return cubo.copy(
        cima = setAdjL(cubo, adj).cima,
        frente = setAdjL(cubo, adj).frente,
        baixo = setAdjL(cubo, adj).baixo,
        tras = setAdjL(cubo, adj).tras,
        esquerda = novaEsquerda
    )
}

// Rotação da face L no sentido anti-horário (L')
fun girarLp(cubo: CuboMagico): CuboMagico = girarL(girarL(cubo))

// Rotação da face L dois graus (L2)
fun girarL2(cubo: CuboMagico): CuboMagico = girarL(girarL(cubo))

// Rotação da face R (direita)
fun girarR(cubo: CuboMagico): CuboMagico {
    val adj = getAdjR(cubo)
    val novaDireita = girarFace90(cubo.direita)
    return cubo.copy(
        cima = setAdjR(cubo, adj).cima,
        tras = setAdjR(cubo, adj).tras,
        baixo = setAdjR(cubo, adj).baixo,
        frente = setAdjR(cubo, adj).frente,
        direita = novaDireita
    )
}

// Rotação da face R no sentido anti-horário (R')
fun girarRp(cubo: CuboMagico): CuboMagico = girarR(girarR(cubo))

// Rotação da face R dois graus (R2)
fun girarR2(cubo: CuboMagico): CuboMagico = girarR(girarR(cubo))