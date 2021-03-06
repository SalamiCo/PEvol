package pe1314.g11.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pe1314.g11.Chromosome;
import pe1314.g11.Problem;
import pe1314.g11.SolverStep;

public final class ElitismStepPair<V, C extends Chromosome<C>> {

    /** The elite saved by the save step */
    /* protected */final List<C> elite = new ArrayList<C>();

    /** Percent of chromosomes to be saved */
    /* protected */final double percent;

    public ElitismStepPair (final double percent) {
        this.percent = percent;
    }

    /**
     * Returns a {@link ProblemStep} that saves the best chromosomes of the input population. The output is returned
     * sorted as a side effect.
     * 
     * @return The saving step of the elitism implementation
     */
    public SolverStep<V,C> getSaveStep () {
        return new SaveStep();
    }

    /**
     * Returns a {@ProbemStep} that resets the previously saved chromosomes to the output population,
     * removing the worst of them. The output is returned partially sorted as a side effect.
     * 
     * @return The restoring step of the elitism implementation
     */
    public SolverStep<V,C> getRestoreStep () {
        return new RestoreStep();
    }

    private class SaveStep implements SolverStep<V,C> {

        /* package */SaveStep () {
        }

        @Override
        public void apply (
            final Problem<V,C> problem, final List<C> input, final Random random, final int generation,
            final List<C> output)
        {
            output.addAll(input);
            Collections.sort(output, new FitnessComparator<C>(problem));

            final int eliteNum = (int) (output.size() * percent);

            elite.clear();
            for (int i = 0; i < eliteNum; i++) {
                elite.add(output.get(i));
            }
        }

    }

    private class RestoreStep implements SolverStep<V,C> {

        /* package */RestoreStep () {
        }

        @Override
        public void apply (
            final Problem<V,C> problem, final List<C> input, final Random random, final int generation,
            final List<C> output)
        {
            output.addAll(input);
            Collections.sort(output, new FitnessComparator<C>(problem));

            final int eliteNum = (int) (output.size() * percent);

            for (int i = 0; i < eliteNum; i++) {
                output.set(output.size() - i - 1, elite.get(i));
            }
        }

    }
}
