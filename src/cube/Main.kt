/**
 * Ponto de entrada do simulador de cubo magico em Kotlin.
 * Este programa demonstra as capabilidades do simulador:
 * - Inicializar um cubo resolvido com cores padrao
 * - Embaralhar o cubo com movimentos aleatorios
 * - Girar faces do cubo
 * - Verificar se o cubo esta resolvido
 *
 * Funcionamento:
 * 1. Cria um cubo ja resolvido com as cores padrao (W=Branco, Y=Amarelo, G=Verde, B=Azul, O=Laranja, R=Vermelho)
 * 2. Exibe o cubo resolvido
 * 3. Embaralha o cubo com 20 movimentos aleatorios
 * 4. Exibe o cubo embaralhado (nao estara mais resolvido)
 * 5. Gira a face Frente (F) do cubo embaralhado
 * 6. Exibe o resultado final
 */
import java.util.Random

// Funcao para inicializar o cubo com cores padrao
fun inicializarCubo(): CuboMagico {
    val cubo = CuboMagico()
    // Define a cor de cada face
    cubo.cima.definirCor("W")
    cubo.baixo.definirCor("Y")
    cubo.frente.definirCor("G")
    cubo.tras.definirCor("B")
    cubo.esquerda.definirCor("O")
    cubo.direita.definirCor("R")
    return cubo
}

fun main() {
    // Cria o cubo inicial resolvido
    val cubo = inicializarCubo()
    println("=== Cubo Resolvido ===")
    println(cubo)
    println("Esta resolvido: ${CuboMagico.resolvido(cubo)}")

    // Embaralha o cubo com 20 movimentos aleatorios
    println("\n=== Apos Embaralhar ===")
    val embaralhado = CuboMagico.embaralhar(cubo, 20)
    println(embaralhado)
    println("Esta resolvido: ${CuboMagico.resolvido(embaralhado)}")

    // Gira a face Frente do cubo embaralhado
    println("\n=== Girando Face Frente ===")
    val depoisDeGirar = girarF(embaralhado)
    println(depoisDeGirar)
    println("Esta resolvido: ${CuboMagico.resolvido(depoisDeGirar)}")
}