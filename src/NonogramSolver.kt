private var nonogramSolved = false

fun main() {

    val rowConstraints = arrayOf(
        intArrayOf(2,1),
        intArrayOf(2,2),
        intArrayOf(1,1,1),
        intArrayOf(1),
        intArrayOf(1,1)
    )

    val columnConstraints = arrayOf(
        intArrayOf(3,1),
        intArrayOf(2),
        intArrayOf(3),
        intArrayOf(1),
        intArrayOf(3)
    )

    val board = arrayOf(
        intArrayOf(1, 1, 1, 0, 0),
        intArrayOf(1, 1, 0, 1, 1),
        intArrayOf(1, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0),
        intArrayOf(1, 0, 0, 0, 0)
    )


    val solution = solveNonogram(rowConstraints, columnConstraints)

    solution.forEach {
        println(it.contentToString())
    }
}

fun solveNonogram(rowConstraints: Array<IntArray>, columnConstraints: Array<IntArray>): Array<IntArray> {
    nonogramSolved = false
    val board = Array(rowConstraints.size) {IntArray(columnConstraints.size) {0} }
    solve(board, rowConstraints, columnConstraints, 0, 0)
    for(i in board.indices) {
        for(k in board[i].indices) {
            if(board[i][k] == -1) board[i][k] = 0
        }
    }
    return board
}

private fun solve(board: Array<IntArray>, rowConstraints: Array<IntArray>, columnConstraints: Array<IntArray>, row: Int, column: Int) {
    board[row][column] = 1
    if(isSpaceValid(board, rowConstraints[row], columnConstraints[column], row, column)) {
        if(row + 1 == rowConstraints.size && column + 1 == columnConstraints.size) {
            nonogramSolved = true
            return
        } else if (column == 4) {
            solve(board, rowConstraints, columnConstraints, row + 1, 0)
        } else {
            solve(board, rowConstraints, columnConstraints, row, column + 1)
        }
    }
    if (nonogramSolved) return

    board[row][column] = -1
    if(isSpaceValid(board, rowConstraints[row], columnConstraints[column], row, column)) {
        if(row + 1 == rowConstraints.size && column + 1 == columnConstraints.size) {
            nonogramSolved = true
            return
        } else if (column == 4) {
            solve(board, rowConstraints, columnConstraints, row + 1, 0)
        } else {
            solve(board, rowConstraints, columnConstraints, row, column + 1)
        }
    }
    if(nonogramSolved) return

    board[row][column] = 0
}

private fun isSpaceValid(board: Array<IntArray>, rowConstraints: IntArray, columnConstraints: IntArray, row: Int, column: Int): Boolean {
    return isRowValid(board[row], column, rowConstraints) && isColumnValid(board, column, row, columnConstraints)
}

private fun isRowValid(row: IntArray, column: Int, rowConstraints: IntArray): Boolean {
    return isRowValid(0, row, column, rowConstraints, 0, 0)
}

private fun isRowValid(curr: Int, row: IntArray, x: Int, rowConstraints: IntArray, pointer: Int, iter: Int): Boolean {
    if(pointer >= rowConstraints.size && iter > 0) return false

    if(x == 0) {
        return if(curr + 1 == row.size) {
            isEntireRowValid(row, rowConstraints)
        } else if(row[curr] == 1) {
            iter != -1
        } else if(row[curr] == -1) {
            iter <= 0
        } else {
            true
        }
    }

    val bool =
        if(row[curr] == 1) {
            if(iter + 1 == rowConstraints[pointer]) {
                isRowValid(curr + 1, row, x - 1, rowConstraints, pointer + 1, -1)
            } else {
                isRowValid(curr + 1, row, x - 1, rowConstraints, pointer, iter + 1)
            }
        } else {
            if(row[curr] == -1 && iter > 0) {
                false
            } else {
                isRowValid(curr + 1, row, x - 1, rowConstraints, pointer, 0)
            }
        }

    return bool
}

private fun isColumnValid(board: Array<IntArray>, column: Int, row: Int, columnConstraints: IntArray): Boolean {
    return isColumnValid(0, board, column, row, columnConstraints, 0, 0)
}

private fun isColumnValid(curr: Int, board: Array<IntArray>, column: Int, y: Int, columnConstraints: IntArray, pointer: Int, iter: Int): Boolean {
    if(pointer >= columnConstraints.size && iter > 0) return false

    if(y == 0) {
        return if(curr + 1 == board.size) {
            isEntireColumnValid(board, column, columnConstraints)
        } else if (board[curr][column] == 1) {
            iter != -1
        } else if(board[curr][column] == -1) {
            iter <= 0
        } else {
            true
        }
    }

    val bool =
        if(board[curr][column] == 1) {
            if(iter + 1 == columnConstraints[pointer]) {
                isColumnValid(curr + 1, board, column, y - 1, columnConstraints, pointer + 1, -1)
            } else {
                isColumnValid(curr + 1, board, column, y - 1,  columnConstraints, pointer, iter + 1)
            }
        } else {
            if(board[curr][column] == -1 && iter > 0) {
                false
            } else {
                isColumnValid(curr + 1, board, column, y - 1,  columnConstraints, pointer, 0)
            }
        }

    return bool
}

private fun isEntireRowValid(row: IntArray, rowConstraints: IntArray): Boolean {
    val viewedConstraints = arrayListOf(0)

    for (space in row) {
        if(space == 1) {
            viewedConstraints[viewedConstraints.size-1]++
        } else {
            viewedConstraints.add(0)
        }
    }

    viewedConstraints.removeIf { it == 0 }
    return viewedConstraints.toString() == rowConstraints.contentToString()
}

private fun isEntireColumnValid(board: Array<IntArray>, column: Int, columnConstraints: IntArray): Boolean {
    val viewedConstraints = arrayListOf(0)

    for (row in board.indices) {
        if(board[row][column] == 1) {
            viewedConstraints[viewedConstraints.size-1]++
        } else {
            viewedConstraints.add(0)
        }
    }

    viewedConstraints.removeIf { it == 0 }
    return viewedConstraints.toString() == columnConstraints.contentToString()
}