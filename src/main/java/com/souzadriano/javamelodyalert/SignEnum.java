package com.souzadriano.javamelodyalert;

enum SignEnum {
    GREATER_THAN("Greater than"),
    LESS_THAN("Less than"),
    EQUAL("Equal");

    private SignEnum(String sign) {
        this.sign = sign;
    }

    private String sign;

    public String getSign() {
        return sign;
    }

    static SignEnum parseSign(String sign) {
        for (SignEnum signEnum : SignEnum.values()) {
            if (signEnum.getSign().equals(sign)) {
                return signEnum;
            }
        }
        return null;
    }

}
