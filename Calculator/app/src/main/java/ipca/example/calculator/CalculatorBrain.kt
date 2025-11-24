package ipca.example.calculator

class CalculatorBrain {

    enum class Operation(val op: String) {
        ADD("+"), SUBTRACT("-"), MULTIPLY("×"), DIVIDE("÷"), EQUAL("="), SQRT("√"), PERCENTAGE("%");

        companion object {
            fun parseOperation(opString: String): Operation {
                return entries.find { it.op == opString } ?: EQUAL
            }
        }
    }

    var operand = 0.0
    var operation: Operation? = null

    fun doOperation(newOperand: Double, newOperation: Operation): Double {

        when (newOperation) {
            Operation.SQRT -> {
                operand = if (newOperand >= 0) kotlin.math.sqrt(newOperand) else Double.NaN
                operation = null
                return operand
            }
            Operation.PERCENTAGE -> {
                operand = newOperand / 100.0
                operation = null
                return operand
            }
            else -> {}
        }

        if (operation != null) {
            operand = when (operation) {
                Operation.ADD -> operand + newOperand
                Operation.SUBTRACT -> operand - newOperand
                Operation.MULTIPLY -> operand * newOperand
                Operation.DIVIDE -> if (newOperand != 0.0) operand / newOperand else Double.NaN
                else -> operand
            }
        } else {
            operand = newOperand
        }

        operation = if (newOperation != Operation.EQUAL) newOperation else null
        return operand
    }

    fun reset() {
        operand = 0.0
        operation = null
    }
}