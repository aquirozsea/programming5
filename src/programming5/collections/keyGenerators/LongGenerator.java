/*
 * LongGenerator.java
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

import programming5.collections.MapKeyGenerator;

import java.util.Random;

/**
 * Implementation of the MapKeyGenerator interface, meant to be used with Map objects to generate the keys
 * associated with objects to store. Utilizes the random java utility class to generate uniformly distributed
 * long values.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class LongGenerator implements MapKeyGenerator<Long> {

    private Random random;
    private Long min = null;
    private Long max = null;

    public LongGenerator() {
        random = new Random(System.currentTimeMillis());
    }

    public LongGenerator(long randomSeed) {
        random = new Random(randomSeed);
    }

    public LongGenerator(long minValue, long maxValue) {
        random = new Random(System.currentTimeMillis());
        min = Math.min(minValue, maxValue);
        max = Math.max(minValue, maxValue);
    }

    public LongGenerator(long minValue, long maxValue, long randomSeed) {
        random = new Random(randomSeed);
        min = Math.min(minValue, maxValue);
        max = Math.max(minValue, maxValue);
    }

    @Override
    public Long generateKey() {
        long ret;
        if (min == null) {
            ret = random.nextLong();
        }
        else {
            ret = min + (random.nextLong() % (max - min));
        }
        return ret;
    }

}
