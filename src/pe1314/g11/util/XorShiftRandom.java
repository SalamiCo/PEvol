package pe1314.g11.util;

import java.util.Random;

/**
 * Implementation of an XOR-Shift random number generator, faster and better than the regular Java {@link Random}.
 * 
 * @author Daniel Escoz Solana
 * @author Pedro Morgado Alarc&oacute;n
 * @see http://www.javamex.com/tutorials/random_numbers/xorshift.shtml
 */
public final class XorShiftRandom extends Random {

    /** Generated by Eclipse */
    private static final long serialVersionUID = -8081310134191538335L;

    /** Current random state */
    private long state;

    public XorShiftRandom () {
        setSeed(System.nanoTime());
    }

    public XorShiftRandom (long seed) {
        setSeed(seed);
    }

    @Override
    protected int next (int bits) {
        state ^= (state << 21);
        state ^= (state >>> 35);
        state ^= (state << 4);
        return (int) (state & ((1L << bits) - 1));
    }

    @Override
    public synchronized void setSeed (long seed) {
        if (seed == 0) {
            state = Long.MIN_VALUE;
        } else {
            state = seed;
        }
    }
}
