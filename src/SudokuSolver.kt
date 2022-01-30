const val GRID_SIZE = 9
var solved = false

fun main() {
    val board: Array<IntArray> = arrayOf(
        intArrayOf(0,0,9,0,0,0,0,1,5),
        intArrayOf(5,0,0,4,0,9,7,0,0),
        intArrayOf(4,7,3,5,6,1,9,0,0),
        intArrayOf(0,0,0,7,4,0,0,9,6),
        intArrayOf(0,0,0,0,0,0,0,8,0),
        intArrayOf(0,0,4,8,3,0,1,5,0),
        intArrayOf(1,3,5,9,0,0,0,0,2),
        intArrayOf(0,0,6,2,5,7,0,3,0),
        intArrayOf(7,2,0,0,1,0,0,0,9),
    )

//    board.sudokuSolve()
    solveBoard(board)
    board.forEach {
        println(it.contentToString())
    }
}

fun Array<IntArray>.sudokuSolve() {
    solved = false
    solve(this, 0, 0)
}

private fun solve(board: Array<IntArray>, row: Int, column: Int) {
//    if(board[row][column] == 0) {
//        for(i in 1..9) {
//            if (solved) return
//            if (!isNumberInRow(board, i, row) &&
//                !isNumberInColumn(board, i, column) &&
//                !isNumberInBox(board, i, row, column)) {
//
//                board[row][column] = i
//
//                if (row == 8 && column == 8) {
//                    solved = true
//                    return
//                } else if(column == 8) {
//                    solve(board, row + 1, 0)
//                } else {
//                    solve(board, row, column + 1)
//                }
//            }
//        }
//        if(solved) return
//        board[row][column] = 0
//    } else {
//
//        if (row == 8 && column == 8) {
//            solved = true
//            return
//        } else if(column == 8) {
//            solve(board, row + 1, 0)
//        } else {
//            solve(board, row, column + 1)
//        }
//    }

    var row = row
    var column = column
    while(board[row][column] != 0) {
        if(column == 8 && row == 8) {
            solved = true
            return
        } else if(column == 8) {
            row++
            column = 0
        } else {
            column++
        }
    }

    for(i in 1..9) {
        if (solved) return
        if (!isNumberInRow(board, i, row) &&
            !isNumberInColumn(board, i, column) &&
            !isNumberInBox(board, i, row, column)) {

            board[row][column] = i

            if (row == 8 && column == 8) {
                solved = true
                return
            } else if(column == 8) {
                solve(board, row + 1, 0)
            } else {
                solve(board, row, column + 1)
            }
        }
    }
    if(solved) return
    board[row][column] = 0
}

fun solveBoard(board: Array<IntArray>): Boolean {
    for (row in 0 until GRID_SIZE) {
        for (column in 0 until GRID_SIZE) {
            if(board[row][column] == 0) {
                for (numberToTry in 1..GRID_SIZE) {
                    if(!isNumberInRow(board, numberToTry, row) &&
                        !isNumberInColumn(board, numberToTry, column) &&
                        !isNumberInBox(board, numberToTry, row, column)) {

                        board[row][column] = numberToTry

                        if (solveBoard(board)) {
                            return true
                        } else {
                            board[row][column] = 0
                        }
                    }
                }
                return false
            }
        }
    }
    return true
}

fun isNumberInRow(board: Array<IntArray>, num: Int, row: Int): Boolean {
    for(i in 0 until GRID_SIZE) {
        if(num == board[row][i]) return true
    }
    return false
}

fun isNumberInColumn(board: Array<IntArray>, num: Int, column: Int): Boolean {
    for(i in 0 until GRID_SIZE) {
        if(num == board[i][column]) return true
    }
    return false
}

fun isNumberInBox(board: Array<IntArray>, num: Int, row: Int, column: Int): Boolean {
    val topLeft: IntArray = intArrayOf(row - row % 3, column - column % 3)

    for(i in 0..2) {
        for(k in 0..2){
            if(num == board[topLeft[0] + i][topLeft[1] + k]) return true
        }
    }
    return false
}