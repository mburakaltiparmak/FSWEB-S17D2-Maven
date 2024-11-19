package com.workintech.s17d2.tax;

import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable{
    public static final Double SIMPLE_TAX_RATE = 15d;
    public static final Double MIDDLE_TAX_RATE = 25d;
    public static final Double UPPER_TAX_RATE = 35d;
    @Override
    public Double getSimpleTaxRate() {
        return SIMPLE_TAX_RATE;
    }

    @Override
    public Double getMiddleTaxRate() {
        return MIDDLE_TAX_RATE;
    }

    @Override
    public Double getUpperTaxRate() {
        return UPPER_TAX_RATE;
    }
}
