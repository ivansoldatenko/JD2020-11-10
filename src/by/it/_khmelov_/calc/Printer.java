package by.it._khmelov_.calc;

import java.util.Objects;

public class Printer {
    public void print(Var result) {
        if (Objects.nonNull(result)){
            System.out.println(result);
        }
    }

    public void printErr(CalcException e) {
            System.out.println(e.getMessage());
    }
}
