package pe1314.g11.pr2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pe1314.g11.Problem;
import pe1314.g11.SolverStep;
import pe1314.g11.sga.PermutationChromosome;

public final class OrderPriorityOrderCombinationStep<V> implements SolverStep<V,PermutationChromosome> {

    private static final List<Integer> NUMS = new ArrayList<>();

    private final double probability;

    public OrderPriorityOrderCombinationStep (double probability) {
        if (probability < 0.0 || probability > 1.0 || Double.isInfinite(probability) || Double.isNaN(probability)) {
            throw new IllegalArgumentException("invalid probability: " + probability);
        }

        this.probability = probability;
    }

    @Override
    public void apply (
        Problem<V,PermutationChromosome> problem, List<PermutationChromosome> input, Random random, int generation,
        List<PermutationChromosome> output)
    {
        Iterator<PermutationChromosome> it = input.iterator();
        while (it.hasNext()) {
            PermutationChromosome a = it.next();

            if (it.hasNext()) {
                PermutationChromosome b = it.next();

                if (random.nextDouble() < probability) {
                    int place = random.nextInt(a.getCombinationPlaces());
                    int p2 = place;
                    while (place == p2) {
                        p2 = random.nextInt(a.getCombinationPlaces());
                    }

                    List<Integer> selected =
                        selectRandom(a.getPermutation().size(), a.getPermutation().size() / 3, random);
                    output.add(performCombination(a, b, random, selected));
                    output.add(performCombination(b, a, random, selected));

                } else {
                    output.add(a);
                    output.add(b);
                }

            } else {
                output.add(a);
            }
        }
    }

    private PermutationChromosome performCombination (
        PermutationChromosome a, PermutationChromosome b, Random random, List<Integer> selected)
    {
        List<Integer> newPerm = new ArrayList<>(a.getPermutation());
        Collections.sort(selected);
        
        // Null out the numbers
        for (Integer idx : selected) {
            newPerm.set(a.getPermutation().indexOf(b.getPermutation().get(idx.intValue())), null);
        }
        
        // Fill them again
        for (Integer idx : selected) {
            newPerm.set(newPerm.indexOf(null), b.getPermutation().get(idx.intValue()));
        }
        
        return new PermutationChromosome(newPerm);
    }

    private static List<Integer> selectRandom (int size, int spsize, Random random) {
        while (NUMS.size() < size) {
            NUMS.add(Integer.valueOf(NUMS.size()));
        }

        List<Integer> nums = new ArrayList<>(NUMS.subList(0, size));
        Collections.shuffle(nums, random);
        return nums.subList(0, spsize);
    }
}