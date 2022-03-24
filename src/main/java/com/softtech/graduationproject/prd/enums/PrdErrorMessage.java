package com.softtech.graduationproject.prd.enums;

import com.softtech.graduationproject.gen.enums.BaseErrorMessage;

public enum PrdErrorMessage implements BaseErrorMessage {
    PRODUCT_NOT_FOUND("Product not found!"),
    VAT_RATE_CANNOT_BE_NEGATIVE("Vat Rate cannot be negative!"),
    PRICE_CANNOT_BE_NEGATIVE("Product price cannot be negative or zero!"),
    NAME_CANNOT_BE_NULL("Name cannot be null!"),
    PRICE_CANNOT_BE_NULL("Price cannot be null!"),
    PRODUCT_TYPE_ID_CANNOT_BE_NULL("Product type id cannot be null!")
    ;

    private String message;

    PrdErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
