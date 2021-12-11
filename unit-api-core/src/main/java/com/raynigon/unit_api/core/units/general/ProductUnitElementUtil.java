package com.raynigon.unit_api.core.units.general;

import javax.measure.Unit;
import java.util.Arrays;

// Element specific algorithms provided locally to this class
final class ProductUnitElementUtil {

    // -- returns a defensive sorted copy, unless size <= 1
    static ProductUnitElement[] copyAndSort(final ProductUnitElement[] elements) {
        if (elements == null || elements.length <= 1) {
            return elements;
        }
        final ProductUnitElement[] elementsSorted = Arrays.copyOf(elements, elements.length);
        Arrays.sort(elementsSorted, ProductUnitElementUtil::compare);
        return elementsSorted;
    }

    static int compare(final ProductUnitElement e0, final ProductUnitElement e1) {
        final Unit<?> sysUnit0 = e0.getUnit().getSystemUnit();
        final Unit<?> sysUnit1 = e1.getUnit().getSystemUnit();
        final String symbol0 = sysUnit0.getSymbol();
        final String symbol1 = sysUnit1.getSymbol();

        if (symbol0 != null && symbol1 != null) {
            return symbol0.compareTo(symbol1);
        } else {
            return sysUnit0.toString().compareTo(sysUnit1.toString());
        }
    }

    // optimized for the fact, that can only return true, if for each element in e0 there exist a
    // single match in e1
    static boolean arrayEqualsArbitraryOrder(final ProductUnitElement[] e0, final ProductUnitElement[] e1) {
        if (e0.length != e1.length) {
            return false;
        }
        for (ProductUnitElement left : e0) {
            boolean unitFound = false;
            for (ProductUnitElement right : e1) {
                if (left.getUnit().equals(right.getUnit())) {
                    if (left.getPow() != right.getPow() || left.getRoot() != right.getRoot()) {
                        return false;
                    } else {
                        unitFound = true;
                        break;
                    }
                }
            }
            if (!unitFound) {
                return false;
            }
        }
        return true;
    }
}
