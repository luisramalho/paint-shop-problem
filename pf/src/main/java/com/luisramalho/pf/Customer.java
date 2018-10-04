package com.luisramalho.pf;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a customer.
 *
 * Created by Luís Ramalho on 19/11/16.
 * <info@luisramalho.com>
 */

class Customer {
    private ArrayList<Paint> preferences;

    Customer(ArrayList<Paint> preferences) {
        this.preferences = preferences;
    }

    /**
     * Checks if the customer is happy with the candidate solution.
     *
     * @param candidate the candidate solution.
     * @return <code>true</code> if they are happy;
     * <code>false</code> otherwise.
     */
    boolean happyWith(Solution candidate) {
        if (!candidate.isComplete()) {
            return isThereHope(candidate);
        }
        return likesAtLeastOnePaintType(candidate);
    }

    /**
     * Checks if the current candidate solution offers any possibility of
     * being accepted by the customer. In other words, it checks if there could
     * a final solution out of the candidate solution for this customer.
     *
     * @param candidate the candidate solution.
     * @return <code>true</code> if there is hope;
     * <code>false</code> otherwise.
     */
    private boolean isThereHope(Solution candidate) {
        for (Paint paint : preferences) {
            int type = candidate.getSolution().get(paint.getColorIndex());
            if (type == -1 || type == paint.getType()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the customer is satisfied with at least one paint type.
     *
     * @param candidate the candidate solution.
     * @return <code>true</code> if the customer is satisfied with at least one
     * paint type; <code>false</code> otherwise.
     */
    private boolean likesAtLeastOnePaintType(Solution candidate) {
        List<Integer> types = candidate.getSolution();
        for (Paint paint : preferences) {
            if (types.get(paint.getColorIndex()) == paint.getType()) {
                return true;
            }
        }
        return false;
    }
}
