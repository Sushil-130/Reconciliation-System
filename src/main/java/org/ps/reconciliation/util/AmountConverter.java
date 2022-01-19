package org.ps.reconciliation.util;

import java.text.DecimalFormat;
import java.util.Currency;

public class AmountConverter {

    private AmountConverter() {
    }

    public static String getAmount(double amount, Currency currency) {
        if (currency.equals(Currency.getInstance("JOD"))) {
            DecimalFormat df = new DecimalFormat(".000");
            return df.format(amount);
        } else {
            DecimalFormat df = new DecimalFormat(".00");
            return df.format(amount);
        }
    }
}
