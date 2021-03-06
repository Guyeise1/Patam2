package Interpreter.Commands.Fundation;

import Expression.Expression;
import Interpreter.CalcExpresion;
import Interpreter.Commands.Exceptions.InterpreterException;
import Interpreter.Commands.Exceptions.InvalidConditionFormatException;
import Interpreter.Commands.Exceptions.ParseException;
import test.MyInterpreter;

public class Condition {
    private String text;

    private String left;
    private String right;
    private Operator operator;

    public Boolean calculate() throws ParseException {
        double c_left = assignExpression(left).calculate();
        double c_right = assignExpression(right).calculate();
        return switch (operator) {
            case GT -> c_left > c_right;
            case LT -> c_left < c_right;
            case EQ -> c_left == c_right;
            case NE -> c_left != c_right;
            case GE -> c_left >= c_right;
            case LE -> c_left <= c_right;
        };
    }

    /**
     *
     * @param text - 3 + 2 < 6
     * @return
     * @throws InvalidConditionFormatException
     * @throws ParseException
     */
    public static Condition parse(String text) throws InvalidConditionFormatException, ParseException {
        Operator operator  = null;
        for (Operator opr: Operator.values() ) {
            if(text.contains(opr.value)) {
                operator = opr;
                break;
            }
        }

        if(operator == null) {
            throw new InvalidConditionFormatException();
        }

        String[] expression = text.split(operator.getValue());

        Condition ret = new Condition();
        ret.left = expression[0];
        // ret.left = CalcExpresion.parseExpression(expression[0]);
        ret.right = expression[1];
        ret.operator = operator;

        return ret;
    }

    /**
     *
     * @param txt - (x + 3) * 2
     * @return - ( 5 + 3 ) * 2
     */
    private static Expression assignExpression(String txt) throws ParseException {
        String simpleExpression = MyInterpreter.getInstance().assignVariableValues(txt); // without variables (after assign)
        return CalcExpresion.parseExpression(simpleExpression);

    }

    // The operators are sorted meaning
    // you don't have to worry about
    // find '<' before '<='.
    private enum Operator {
        GE(">="),
        LE("<="),
        EQ("=="),
        NE("!="),
        GT(">"),
        LT("<");


        private final String value;
        Operator(String s) {
            this.value = s;
        }

        public String getValue() {
            return this.value;
        }
    }
    
}
