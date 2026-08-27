# Ala2 — Simulador de Cubo Mágico 3x3

## Visão Geral
Simulador de cubo mágico 3x3 escrito em Kotlin. O projeto implementa um cubo completo com 6 faces, todas as rotações no padrão oficial (U, D, F, B, L, R e variações `'`, `2`), embaralhamento aleatório e verificação de estado resolvido. A lógica de rotação foi otimizada com uma abstração genérica de transferência entre fatias, eliminando a duplicação dos antigos `getAdjX`/`setAdjX`.

## Funcionalidades
- Inicialização com esquema de cores ocidental: W (branco), Y (amarelo), G (verde), B (azul), O (laranja), R (vermelho)
- Rotação de qualquer face em 90° horário, anti-horário (`'`) e 180° (`2`)
- Embaralhamento com número configurável de movimentos aleatórios
- Verificação se o cubo está resolvido
- Representação textual via `toString()` em cada face e no cubo

## Requisitos
- JDK 8 ou superior
- Kotlin 1.9+ (ou uso via IntelliJ IDEA com o arquivo `magic-cube-kotlin.iml`)

## Como Executar
Via IntelliJ IDEA: abra o projeto e execute `src/cube/Main.kt`.

Via linha de comando (com `kotlinc` instalado):
```bash
kotlinc src/cube/*.kt -include-runtime -d magiccube.jar
java -jar magiccube.jar
```

Saída esperada: cubo resolvido, cubo embaralhado com 20 movimentos e cubo após giro `F`, cada um com o status `resolvido: true/false`.

## Arquitetura e Otimização

O código está 100% em português, sem aliases e sem mistura de idiomas. Cada classe tem responsabilidade única:

**`FaceCubo` (`src/cube/CubeFace.kt:13`)**
Representa uma face 3x3. Armazena `adesivos: Array<Array<String>>` e expõe apenas operações em português: `definirCor()`, `obterAdesivo()`, `definirAdesivo()`, `obterLinha()`, `obterColuna()`, `definirLinha()`, `definirColuna()`.

**`Fatia` / `Aresta` (`src/cube/Aresta.kt:8`)**
Núcleo da otimização. Antes cada face tinha um par `getAdjX`/`setAdjX` com 4 atribuições manuais repetidas. Agora existe uma única função genérica:

- `Fatia(face, tipo, indice)` — `tipo` é `LINHA` ou `COLUNA` (`Aresta.kt:8`)
- `Aresta(origem, destino, inverter)` — descreve `origem -> destino` com ou sem inversão (`Aresta.kt:19`)
- `obterFatia()` / `definirFatia()` (`Aresta.kt:35`, `Aresta.kt:43`) leem/escrevem linha ou coluna conforme `TipoFatia`
- `processarArestas(cubo, arestas)` (`Aresta.kt:52`) captura todas as origens de uma vez e aplica nos destinos, com `reversed()` quando `inverter = true`

**`CuboMagico` (`src/cube/CubeMagico.kt:9`)**
`data class` com as 6 faces: `cima`, `baixo`, `frente`, `tras`, `esquerda`, `direita`. Contém:

- `companheiro resolvido()` (`CubeMagico.kt:18`) — verifica se cada face é monocromática
- `companheiro embaralhar(movimentos)` (`CubeMagico.kt:31`) — sorteia movimentos em `U/D/F/B/L/R` + `'`/`2`
- `girarFace90()` (`CubeMagico.kt:64`) — rotação de matriz 90° horário
- Listas declarativas `arestasCima`, `arestasBaixo`, `arestasFrente`, `arestasTras`, `arestasEsquerda`, `arestasDireita` (`CubeMagico.kt:77`) — cada uma com 4 `Aresta`s declarando a transferência correta
- Rotações `girarU`, `girarD`, `girarF`, `girarB`, `girarL`, `girarR` (`CubeMagico.kt:122`) — delegam para `processarArestas()` + `girarFace90()` e variações `'` (3× horário) e `2` (2× horário)

Essa divisão elimina a reescrita de cada `getAdjX`/`setAdjX` e facilita manutenção: adicionar ou corrigir uma rotação exige apenas ajustar a lista de `Aresta`, sem duplicar lógica.

## Rotações Disponíveis
| Notação | Face | Efeito |
|---------|------|--------|
| `U`  | `cima` | 90° horário |
| `U'` (`girarUp`) | `cima` | 90° anti-horário (3× `U`) |
| `U2` (`girarU2`) | `cima` | 180° (2× `U`) |
| `D`, `D'`, `D2` | `baixo` | idem |
| `F`, `F'`, `F2` | `frente` | idem |
| `B`, `B'`, `B2` | `tras` | idem |
| `L`, `L'`, `L2` | `esquerda` | idem |
| `R`, `R'`, `R2` | `direita` | idem |

> Observação: no código o identificador é `tras` (sem acento) para manter nomes de variáveis válidos; na documentação usa-se “trás”.

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
fun main() {
    val cubo = inicializarCubo() // cima=W, baixo=Y, frente=G, tras=B, esquerda=O, direita=R
    println(cubo)
    println("Está resolvido: ${CuboMagico.resolvido(cubo)}") // true

    val embaralhado = CuboMagico.embaralhar(cubo, 20)
    println(embaralhado)
    println("Está resolvido: ${CuboMagico.resolvido(embaralhado)}") // false na maioria das vezes

    val depoisDeGirar = girarF(embaralhado) // gira a face frente 90° horário
    println(depoisDeGirar)

    val antiHorario = girarFp(depoisDeGirar) // 90° anti-horário (equivale a 3× girarF)
    val meiaVolta = girarF2(depoisDeGirar)   // 180°
}
```

O exemplo completo está em `src/cube/Main.kt:17` (`inicializarCubo()` e `main()`).

## Estrutura do Projeto
```
magic-cube-kotlin/
├── .gitignore
├── magic-cube-kotlin.iml
├── magiccube.jar
├── README.md
└── src/
    └── cube/
        ├── CubeFace.kt    // FaceCubo — face 3x3
        ├── Aresta.kt      // Fatia, TipoFatia, Aresta, obterFatia, definirFatia, processarArestas
        ├── CubeMagico.kt  // CuboMagico, girarFace90, listas arestasX e girarU/D/F/B/L/R
        └── Main.kt        // inicializarCubo() e main() — demonstração
```

## Convenções
- Idioma único: todo o código e documentação em português (sem aliases e sem mistura com inglês). A notação `U/D/F/B/L/R` é mantida por ser padrão internacional do cubo, não termo de linguagem.
- Sem duplicação: nenhuma rotação reescreve atribuições manuais; toda transferência passa por `processarArestas()`.
