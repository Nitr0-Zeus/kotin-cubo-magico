/**
 * Representa uma face individual do cubo mágico.
 * Cada face tem um tamanho padrão de 3x3 adesivos.
 * 
 * Padrão de cores (convenção ocidental):
 * - W: Branco
 * - Y: Amarelo  
 * - G: Verde
 * - B: Azul
 * - O: Laranja
 * - R: Vermelho
 */
class FaceCubo(val tamanho: Int = 3) {
    // Matriz que armazena as cores de cada adesivo da face
    val adesivos: Array<Array<String>>

    init {
        // Inicializa a matriz com espaços vazios
        adesivos = Array(tamanho) { Array(tamanho) { " " } }
    }

    // Define uma cor específica para todos os adesivos da face
    fun definirCor(cor: String) {
        for (i in 0 until tamanho) {
            for (j in 0 until tamanho) {
                adesivos[i][j] = cor
            }
        }
    }

    // Obtém a cor de um adesivo específico
    fun obterAdesivo(linha: Int, coluna: Int): String {
        return adesivos[linha][coluna]
    }

    // Define a cor de um adesivo específico
    fun definirAdesivo(linha: Int, coluna: Int, cor: String) {
        adesivos[linha][coluna] = cor
    }

    // Obtém uma linha completa de adesivos
    fun obterLinha(linha: Int): MutableList<String> {
        return adesivos[linha].toMutableList()
    }

    // Obtém uma coluna completa de adesivos
    fun obterColuna(coluna: Int): MutableList<String> {
        var resultado = mutableListOf<String>()
        for (i in 0 until tamanho) {
            resultado.add(adesivos[i][coluna])
        }
        return resultado
    }

    // Define uma linha completa de adesivos
    fun definirLinha(linha: Int, cores: Array<String>) {
        for (j in 0 until tamanho) {
            adesivos[linha][j] = cores[j]
        }
    }

    // Define uma coluna completa de adesivos
    fun definirColuna(coluna: Int, cores: Array<String>) {
        for (i in 0 until tamanho) {
            adesivos[i][coluna] = cores[i]
        }
    }

    override fun toString(): String {
        var sb = StringBuilder()
        for (i in 0 until tamanho) {
            for (j in 0 until tamanho) {
                sb.append(adesivos[i][j]).append(" ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}