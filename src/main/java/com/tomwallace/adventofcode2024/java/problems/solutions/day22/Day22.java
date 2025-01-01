package com.tomwallace.adventofcode2024.java.problems.solutions.day22;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.*;

public class Day22 implements IAdventProblemSet {

    private final Integer generations = 2000;

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Monkey Market";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 22;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day22Input.txt";
        var sum = sumRegeneratedSecrets(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day22Input.txt";
        var most = getMostBananas(filePath);
        return most.toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() { return Difficulty.MEDIUM; }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }

    protected Long sumRegeneratedSecrets(String filePath) {
        var secrets = FileUtility.parseFileToList(filePath, line -> regenerateSecretMultipleTimes(Long.parseLong(line), generations));
        return secrets.stream().mapToLong(secret -> secret).sum();
    }

    protected Integer getMostBananas(String filePath) {
        var priceChanges = FileUtility.parseFileToList(filePath, line -> findPriceChangesSecretMultipleTimes(Long.parseLong(line)));
        var prices = FileUtility.parseFileToList(filePath, line -> findPricesSecretMultipleTimes(Long.parseLong(line)));
        var salesPrices = new HashMap<String, Integer>();

        for (int i = 0; i < prices.size(); i++) {
            var salesPriceMap = createSalesPriceMap(prices.get(i), priceChanges.get(i));
            salesPriceMap.forEach((key, value) -> salesPrices.merge(key, value, Integer::sum));
        }

        var mostBananas = salesPrices.values().stream()
                .max(Comparator.naturalOrder());

        //noinspection OptionalGetWithoutIsPresent
        return mostBananas.get();
    }

    protected Long regenerateSecretMultipleTimes(Long initial, Integer generations) {
        var current = initial;
        for (var i = 0; i < generations; i++) {
            current = getNewSecret(current);
        }
        return current;
    }

    private Map<String, Integer> createSalesPriceMap(List<Integer> prices, List<Integer> priceChanges) {
        var result = new HashMap<String, Integer>();
        for (int i = 0; i < priceChanges.size() - 3; i++) {
            var key = String.format("%d,%d,%d,%d", priceChanges.get(i), priceChanges.get(i + 1), priceChanges.get(i + 2), priceChanges.get(i + 3));
            if (!result.containsKey(key)) {
                result.put(key, prices.get(i + 3));
            }
        }
        return result;
    }

    protected List<Integer> findPriceChangesSecretMultipleTimes(Long initial) {
        var lastOne = (int) (initial % 10);
        var result = new ArrayList<>(List.of(lastOne));
        var currentSecret = initial;
        for (var i = 0; i < generations; i++) {
            currentSecret = getNewSecret(currentSecret);
            var currentOne = (int) (currentSecret % 10);
            result.add(currentOne - lastOne);
            lastOne = currentOne;
        }
        return result;
    }

    protected List<Integer> findPricesSecretMultipleTimes(Long initial) {
        var result = new ArrayList<>(List.of((int) (initial % 10)));
        var currentSecret = initial;
        for (var i = 0; i < generations; i++) {
            currentSecret = getNewSecret(currentSecret);
            var currentOne = (int) (currentSecret % 10);
            result.add(currentOne);
        }
        return result;
    }

    private Long getNewSecret(Long initial) {
        var step1 = prune(mix(initial,initial * 64L));
        var step2 = prune(mix(step1, step1 / 32));
        return prune(mix(step2, step2 * 2048));
    }

    protected Long mix(Long secret, Long mixer) {
        return mixer ^ secret;
    }

    protected Long prune(Long secret) {
        return secret % 16777216;
    }
}


