/*
 * BigIntegerGenerator.java
 *
 * Copyright 2009 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package programming5.collections.keyGenerators;

import java.math.BigInteger;
import java.util.Random;
import programming5.collections.MapKeyGenerator;

/**
 * Implementation of the MapKeyGenerator interface, meant to be used with Map objects to generate the keys
 * associated with objects to store. Utilizes the BigInteger random generation distribution to generate new
 * keys of a given bit length.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class BigIntegerGenerator implements MapKeyGenerator<BigInteger> {

    private Random random;
    private int bits;

    public BigIntegerGenerator(int bitLength) {
        random = new Random(System.currentTimeMillis());
        bits = bitLength;
    }

    public BigIntegerGenerator(int bitLength, long randomSeed) {
        random = new Random(randomSeed);
        bits = bitLength;
    }

    /**
     * @return a non-zero big integer (zero is reserved for special-case indicator)
     */
    @Override
    public BigInteger generateKey() {
        BigInteger ret = new BigInteger(bits, random);
        while (ret.equals(BigInteger.ZERO)) {
            ret = new BigInteger(bits, random);
        }
        return ret;
    }

}
