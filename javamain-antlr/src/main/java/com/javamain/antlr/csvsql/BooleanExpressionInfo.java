package com.javamain.antlr.csvsql;

public class BooleanExpressionInfo {
    String leftOperand;
    String operator;
    String rightOperand;
    BooleanExpressionInfo left;
    BooleanExpressionInfo right;

    public String getLeftOperand() {
        return leftOperand;
    }

    public void setLeftOperand(String leftOperand) {
        this.leftOperand = leftOperand;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(String rightOperand) {
        this.rightOperand = rightOperand;
    }

    public BooleanExpressionInfo getLeft() {
        return left;
    }

    public void setLeft(BooleanExpressionInfo left) {
        this.left = left;
    }

    public BooleanExpressionInfo getRight() {
        return right;
    }

    public void setRight(BooleanExpressionInfo right) {
        this.right = right;
    }
}
