package org.example;

public interface Expression {
}
record Literal(double value) implements Expression {}

record Variable(String name) implements Expression {}

record Sum(Expression left, Expression right) implements Expression {}

record Product(Expression left, Expression right) implements Expression {}

record Power(Expression base, Literal exponent) implements Expression {}