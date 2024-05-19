package org.example;

public class Main {
    public static void main(String[] args) {
        Expression poly = new Sum(new Product(new Literal(3), new Power(new Variable("x"), new Literal(2))),new Literal(5));
        System.out.println(ExpressionModel.prettyPrint(poly));


        Expression expr = new Sum(new Product(new Literal(3), new Power(new Variable("x"), new Literal(2))), new Literal(5));
        System.out.println(ExpressionModel.prettyPrint(expr));

        Expression expr1 = new Sum(new Literal(3),new Sum(new Literal(2), new Literal(5)));


        Expression simplified = ExpressionModel.simplify(expr1);
        System.out.println(ExpressionModel.prettyPrint(simplified));

        Expression expr2 = new Sum(new Product(new Literal(3), new Power(new Variable("x"), new Literal(2))), new Literal(5));


        double result = ExpressionModel.evaluate(expr2, "x", 2.0);
        System.out.println(result);


        Expression expr3 = new Sum(new Product(new Literal(3), new Power(new Variable("x"), new Literal(2))),new Literal(5));

        Expression derivative = ExpressionModel.derivative(expr3, "x");
        System.out.println(ExpressionModel.prettyPrint(derivative));
    }
}