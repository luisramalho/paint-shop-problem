package com.luisramalho.pf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * A class representing a Paint Factory.
 *
 * Created by Luís Ramalho on 19/11/16.
 * <info@luisramalho.com>
 */

public class PaintFactory {
    public interface StatusCallback {
        void onStatusUpdate(String status);
    }

    private StatusCallback statusCallback;
    private ArrayList<Order> orders;

    private static Solution solution;

    public PaintFactory(URL url, StatusCallback statusCallback)
            throws IOException, NumberFormatException {
        this.statusCallback = statusCallback;
        this.orders = parse(url);
    }

    /**
     * Solves the challenge.
     *
     * @return the output for the solution.
     */
    public String solve() {
        StringBuilder sb = new StringBuilder();
        if (orders != null) {
            for (Order order : orders) {
                statusCallback.onStatusUpdate(
                        "Solving Order #" + order.getId());
                solution = null;
                PaintFactory.bt(order, root(order));
                sb.append("Case #").append(order.getId()).append(": ");
                if (solution != null) {
                    sb.append(solution);
                } else {
                    sb.append("IMPOSSIBLE");
                }
                sb.append("\n");
            }
        } else {
            sb.append("An error occurred!");
        }
        return sb.toString();
    }

    /**
     * The backtracking recursive method to look for solutions.
     *
     * @param order the problem.
     * @param candidate the solution candidate to be checked.
     */
    private static void bt(Order order, Solution candidate) {
        if (solution != null) {
            return;
        }

        if (reject(order, candidate)) {
            return;
        }

        if (accept(order, candidate)) {
            output(candidate);
        }

        Solution solution = first(candidate);
        while (solution != null) {
            bt(order, solution);
            solution = next(solution);
        }
    }

    /**
     * Checks if all customers are satisfied with the solution candidate.
     *
     * @param order the problem.
     * @param candidate the solution candidate to be checked.
     * @return <code>true</code> if they are all satisfied;
     * <code>false</code> otherwise.
     */
    private static boolean reject(Order order, Solution candidate) {
        for (Customer customer : order.getCustomers()) {
            if (!customer.happyWith(candidate)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the candidate solution should be accepted.
     *
     * @param order the problem.
     * @param candidate the solution candidate to be checked.
     * @return <code>true</code> if they are all satisfied;
     * <code>false</code> otherwise.
     */
    private static boolean accept(Order order, Solution candidate) {
        return isEveryoneHappy(order, candidate);
    }

    private static void output(Solution candidate) {
        solution = candidate;
    }

    /**
     * Generates the next possible subtree for the problem.
     *
     * @param root the root of the tree.
     * @return the next possible solution candidate.
     */
    private static Solution first(Solution root) {
        if (!isFinalized(root)) {
            ArrayList<Solution> tree = new ArrayList<>();
            Solution glossy, matte;
            for (int i = 0; i < root.size(); i++) {
                if (root.getSolution().get(i) == -1) {
                    glossy = new Solution(root);
                    glossy.setGlossy(i);
                    tree.add(glossy);

                    matte = new Solution(root);
                    matte.setMatte(i);
                    tree.add(matte);
                    break; // only a subtree at a time
                }
            }
            for (int i = 1; i < tree.size(); i++) {
                tree.get(i - 1).setNextSolution(tree.get(i));
            }

            if (tree.size() > 0) {
                tree.get(0).setNextSolution(tree.get(1));
                return tree.get(0);
            }
        }

        return null;
    }

    /**
     * Returns the next solution candidate.
     *
     * @param solution the current solution candidate.
     * @return the next solution.
     */
    private static Solution next(Solution solution) {
        return solution.getNextSolution();
    }

    private static Solution root(Order order) {
        return new Solution(order.getNumberOfPaintColors(), -1);
    }

    /**
     * Checks if all customers are satisfied with a given solution candidate.
     *
     * @param order the problem.
     * @param candidate the solution candidate.
     * @return <code>true</code> if they are all satisfied;
     * <code>false</code> otherwise.
     */
    private static boolean isEveryoneHappy(Order order, Solution candidate) {
        if (!isFinalized(candidate)) {
            return false;
        }

        for (Customer customer : order.getCustomers()) {
            if (!customer.happyWith(candidate)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the current solution is final.
     *
     * @param candidate the solution candidate.
     * @return <code>true</code> if there are no more -1;
     * <code>false</code> otherwise.
     */
    private static boolean isFinalized(Solution candidate) {
        for (int type : candidate.getSolution()) {
            if (type == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses text from the given URL and populates the order.
     *
     * @param url the URL of the raw text file.
     * @return a list of orders (problems).
     *
     * @throws IOException
     * @throws NumberFormatException
     */
    private ArrayList<Order> parse(URL url) throws IOException,
            NumberFormatException {
        ArrayList<Order> orders = new ArrayList<>();
        statusCallback.onStatusUpdate("Opening URL...");
        InputStream stream = url.openStream();
        statusCallback.onStatusUpdate("Reading file...");
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(streamReader);
        int numberOfTestCases = Integer.parseInt(in.readLine());
        for (int i = 0; i < numberOfTestCases; i++) {
            Order order = new Order();
            order.setId(i);
            order.setNumberOfPaintColors(in.readLine());
            order.setNumberOfCustomers(in.readLine());
            ArrayList<Customer> customers = new ArrayList<>();
            for (int c = 0; c < order.getNumberOfCustomers(); c++) {
                statusCallback.onStatusUpdate(
                        "Processing Order #" + order.getId() + " | " +
                                "Customer #" + c);
                String[] customerPreferences = in.readLine().split(" ");
                ArrayList<Paint> preferences = new ArrayList<>();
                for (int j = 1; j < customerPreferences.length; j++) {
                    preferences.add(new Paint(
                            customerPreferences[j],
                            customerPreferences[++j]));
                }
                customers.add(new Customer(preferences));
                order.setCustomers(customers);
            }
            orders.add(order);
        }
        in.close();
        return orders;
    }
}
