package org.example;

public class ExpressionModel {
    public static String prettyPrint(Expression expr) {
        if (expr instanceof Literal l) {
            return String.valueOf(l.value());
        } else if (expr instanceof Variable v) {
            return v.name();
        } else if (expr instanceof Sum s) {
            return "(" + prettyPrint(s.left()) + " + " + prettyPrint(s.right()) + ")";
        } else if (expr instanceof Product p) {
            return "(" + prettyPrint(p.left()) + " * " + prettyPrint(p.right()) + ")";
        } else if (expr instanceof Power p) {
            return prettyPrint(p.base()) + "^" + prettyPrint(p.exponent());
        }
        throw new IllegalArgumentException("Unknown expression");
    }
    class ExpressionBracketReduce {
        public static String prettyPrint(Expression expr) {
            return prettyPrint(expr, 0);
        }

        private static String prettyPrint(Expression expr, int parentPrecedence) {
            if (expr instanceof Literal l) {
                return String.valueOf(l.value());
            } else if (expr instanceof Variable v) {
                return v.name();
            } else if (expr instanceof Sum s) {
                int precedence = 1;
                String left = prettyPrint(s.left(), precedence);
                String right = prettyPrint(s.right(), precedence);
                if (parentPrecedence > precedence) {
                    return "(" + left + " + " + right + ")";
                } else {
                    return left + " + " + right;
                }
            } else if (expr instanceof Product p) {
                int precedence = 2;
                String left = prettyPrint(p.left(), precedence);
                String right = prettyPrint(p.right(), precedence);
                if (parentPrecedence > precedence) {
                    return "(" + left + " * " + right + ")";
                } else {
                    return left + " * " + right;
                }
            } else if (expr instanceof Power p) {
                int precedence = 3;
                String base = prettyPrint(p.base(), precedence);
                String exponent = prettyPrint(p.exponent(), precedence);
                if (parentPrecedence > precedence) {
                    return "(" + base + "^" + exponent + ")";
                } else {
                    return base + "^" + exponent;
                }
            }
            throw new IllegalArgumentException("Unknown expression");
        }
    }

        public static Expression simplify(Expression expr) {
            if (expr instanceof Literal l) {
                return l;
            } else if (expr instanceof Variable v) {
                return v;
            } else if (expr instanceof Sum s) {
                Expression left = simplify(s.left());
                Expression right = simplify(s.right());
                if (left instanceof Literal l1 && right instanceof Literal l2) {
                    return new Literal(l1.value() + l2.value());
                } else {
                    return new Sum(left, right);
                }
            } else if (expr instanceof Product p) {
                Expression left = simplify(p.left());
                Expression right = simplify(p.right());
                if (left instanceof Literal l1 && right instanceof Literal l2) {
                    return new Literal(l1.value() * l2.value());
                } else {
                    return new Product(left, right);
                }
            } else if (expr instanceof Power p) {
                Expression base = simplify(p.base());
                Literal exponent = p.exponent();
                if (base instanceof Literal l && exponent.value() == 0) {
                    return new Literal(1);
                } else if (base instanceof Literal l && exponent.value() == 1) {
                    return base;
                } else {
                    return new Power(base, exponent);
                }
            }
            throw new IllegalArgumentException("Unknown expression");
        }


        public static double evaluate(Expression expr, String var, double value) {
            if (expr instanceof Literal l) {
                return l.value();
            } else if (expr instanceof Variable v) {
                if (v.name().equals(var)) {
                    return value;
                } else {
                    throw new IllegalArgumentException("Unknown variable: " + v.name());
                }
            } else if (expr instanceof Sum s) {
                return evaluate(s.left(), var, value) + evaluate(s.right(), var, value);
            } else if (expr instanceof Product p) {
                return evaluate(p.left(), var, value) * evaluate(p.right(), var, value);
            } else if (expr instanceof Power p) {
                return Math.pow(evaluate(p.base(), var, value), evaluate(p.exponent(), var, value));
            }
            throw new IllegalArgumentException("Unknown expression");
        }


        public static Expression derivative(Expression expr, String var) {
            if (expr instanceof Literal l) {
                return new Literal(0);
            } else if (expr instanceof Variable v) {
                if (v.name().equals(var)) {
                    return new Literal(1);
                } else {
                    return new Literal(0);
                }
            } else if (expr instanceof Sum s) {
                return new Sum(derivative(s.left(), var), derivative(s.right(), var));
            } else if (expr instanceof Product p) {
                return new Sum(
                        new Product(derivative(p.left(), var), p.right()),
                        new Product(p.left(), derivative(p.right(), var))
                );
            } else if (expr instanceof Power p) {
                if (p.base() instanceof Variable v && v.name().equals(var)) {
                    return new Product(
                            new Product(p.exponent(), new Power(p.base(), new Literal(p.exponent().value() - 1))),
                            new Literal(1)
                    );
                } else {
                    throw new IllegalArgumentException("unknown variable" + p.base());
                }
            }
            throw new IllegalArgumentException("Unknown expression");
        }



}
