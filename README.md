# Ala2 - Rubik's Cube Simulator

## Visão Geral
Simulador de cubo mágico 3x3 em Kotlin com todas as rotações e embaralhamento.

## Funcionalidades
- Inicialização com cores padrão (W=Branco, Y=Amarelo, G=Verde, B=Azul, O=Laranja, R=Vermelho)
- Todas as rotações possíveis: U, D, F, B, L, R e variações (', 2)
- Embaralhamento aleatório com número configurable de movimentos
- Verificação se o cubo está resolvido
- Representação textual do cubo via `toString()`

## Classes Principais
- **`FaceCubo`** (`src/cube/CubeFace.kt`): Representa uma face individual 3x3
  - Matriz de adesivos `Array<Array<String>>`
  - Operações: `definirCor()`, `obterAdesivo()`, `definirAdesivo()`, linhas e colunas

- **`CuboMagico`** (`src/cube/CubeMagico.kt`): Representa o cubo completo com 6 faces
  - `data class` com faces: cima, baixo, frente, tras, esquerda, direita
  - Método `resolvido()`: Verifica se todas as faces estão uniformes
  - Método `embaralhar()`: Embaralha com N movimentos aleatórios

## Rotações Disponíveis
- **U** (cima): Giro no sentido horário
- **U'** (cima inversa): Giro no sentido anti-horário
- **U2** (cima dois graus): Dois giros de 90 graus
- **D** (baixo), **D', D2**: Igual ao U
- **F** (frente), **F', F2**: Giro na face frontal
- **B** (trás), **B', B2**: Giro na face traseira
- **L** (esquerda), **L', L2**: Giro na face esquerda
- **R** (direita), **R', R2**: Giro na face direita

## Padrões de Cores
| Código | Cor |
|--------|-----|
| W | Branco |
| Y | Amarelo |
| G | Verde |
| B | Azul |
| O | Laranja |
| R | Vermelho |

## Exemplos de Uso
```kotlin
import ...

fun main() {
    val cubo = inicializarCubo()
    println(cubo)  // Exibe cubo resolvido
    println("Esta resolvido: ${CuboMagico.resolvido(cubo)}")

    // Embaralha o cubo com 20 movimentos aleatorios
    val embaralhado = CuboMagico.embaralhar(cubo, 20)
    println(embaralhado)
    println("Esta resolvido: ${CuboMagico.resolvido(embaralhado)}")

    // Gira a face Frente do cubo embaralhado
    val depoisDeGirar = girarF(embaralhado)
    println(depoisDeGirar)
    println("Esta resolvido: ${CuboMagico.resolvido(depoisDeGirar)}")
}
```

## Estrutura do Projeto
```
magic-cube-kotlin/
├── .gitignore
├── magic-cube-kotlin.iml
├── magiccube.jar
├── src/
│   └── cube/
│       ├── CubeFace.kt    // FaceCubo - face individual 3x3
│       ├── CubeMagico.kt  // CuboMagico - cubo com 6 faces + todas as rotações
│       └── Main.kt        // Ponto de entrada do simulador
└── README.md  // Este arquivo
```