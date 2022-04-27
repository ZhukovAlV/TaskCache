package com.company.exception;

public class LengthMasException extends Exception {
    private static final String MESSAGE = "В первой строке файла должно быть 2 числа";

    public LengthMasException(){
        super(MESSAGE);
    }
}
