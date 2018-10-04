package com.luisramalho.pf;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a Solution to the problem.
 *
 * Created by Luís Ramalho on 19/11/16.
 * <info@luisramalho.com>
 */

class Solution {

    private List<Integer> solution;
    private Solution nextSolution;

    Solution(int size, int value) {
        this.solution = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            solution.add(i, value);
        }
    }

    Solution(Solution candidate) {
        this.solution = new ArrayList<>(candidate.getSolution());
    }

    List<Integer> getSolution() {
        return solution;
    }

    Solution getNextSolution() {
        return nextSolution;
    }

    void setNextSolution(Solution nextSolution) {
        this.nextSolution = nextSolution;
    }

    int size() {
        return solution.size();
    }

    void setMatte(int index) {
        solution.set(index, 1);
    }

    void setGlossy(int index) {
        solution.set(index, 0);
    }

    /**
     * Checks if a solution candidate is complete.
     *
     * @return <code>true</code> if it is a complete solution;
     * <code>false</code> otherwise.
     */
    boolean isComplete() {
        for (Integer type : solution) {
            if (type == -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : solution) {
            sb.append(i).append(" ");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
