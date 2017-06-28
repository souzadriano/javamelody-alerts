package com.souzadriano.javamelodyalert;

import java.math.BigDecimal;

class Condition {

    private Integer period;
    private VariableEnum variable;
    private SignEnum sign;
    private BigDecimal value;

    Integer getPeriod() {
        return period;
    }

    void setPeriod(Integer period) {
        this.period = period;
    }

    VariableEnum getVariable() {
        return variable;
    }

    void setVariable(VariableEnum variable) {
        this.variable = variable;
    }

    SignEnum getSign() {
        return sign;
    }

    void setSign(SignEnum sign) {
        this.sign = sign;
    }

    BigDecimal getValue() {
        return value;
    }

    void setValue(BigDecimal value) {
        this.value = value;
    }

}
