package by.it.arekhava.calc;

import by.it.arekhava.calc.Patterns;
import by.it.arekhava.calc.Var;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public Var calc(String expression) throws CalcException{

        //2+3;
        Var reult;
        expression = expression.replaceAll("\\s+", "");
        String[] part = expression.split(Patterns.OPERATION, 2);
        if (part.length < 2) {
            return Var.createVar(expression);
        }

        Var right = Var.createVar(part[1]);
        if (expression.contains("=")) {
            return Var.save(part[0], right);

        }

            Var left = Var.createVar(part[0]);
            if (Objects.nonNull(left) && Objects.nonNull(right)) {
                Pattern compile = Pattern.compile(Patterns.OPERATION);
                Matcher matcher = compile.matcher(expression);
                if (matcher.find()) {
                    String operation = matcher.group();
                    switch (operation) {
                        case "+":
                            return left.add(right);
                        case "-":
                            return left.sub(right);
                        case "*":
                            return left.mul(right);
                        case "/":
                            return left.div(right);
                    }
                }
            }
            return null;
        }


}
