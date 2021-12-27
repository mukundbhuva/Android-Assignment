package com.assignment;

import java.util.regex.*;

public class InputValidation
{
    public static final Pattern email_address_validation = Pattern.compile("^[a-z]{1,}[a-z_.]*@[a-z]{2,10}.[a-z]{2,6}$");
    public static final Pattern phone_number_validation = Pattern.compile("^\\d{10}$");
    public static final Pattern name_validation = Pattern.compile("^([a-zA-Z]*)([ ])([a-zA-Z]*)$");
    public static final Pattern username_validation = Pattern.compile("^[a-z[_]]{5,15}$");

    public static boolean validate_input(final String input_data, Pattern pattern)
    {
        return pattern.matcher(input_data).find();
    }
}
