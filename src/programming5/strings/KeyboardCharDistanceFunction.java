/*
 * KeyboardCharDistanceFunction.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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

package programming5.strings;

import java.util.Map.Entry;
import programming5.collections.EntryObject;
import programming5.collections.HashTable;
import programming5.math.DistanceFunction;

/**
 * TODO: Play with this
 * @author andresqh
 */
public class KeyboardCharDistanceFunction implements DistanceFunction<Character> {

    private static final char[][] KEYBOARD1 = {{'`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '='},
                                              {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'},
                                              {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\''},
                                              {'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/'}};
    
    private static final char[][] KEYBOARD2 = {{'~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+'},
                                              {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', '{', '}', '|'},
                                              {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', ':', '\"'},
                                              {'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<', '>', '?'}};
    
    private static final HashTable<Character, Entry<Float, Float>> COORDINATES = new HashTable<Character, Entry<Float, Float>>();
    
    static {
        for (int i = 0; i < KEYBOARD1.length; i++) {
            for (int j = 0; j < KEYBOARD1[i].length; j++) {
                COORDINATES.put(KEYBOARD1[i][j], new EntryObject<Float, Float>((float) i, (float) j));
            }
        }
        for (int i = 0; i < KEYBOARD2.length; i++) {
            for (int j = 0; j < KEYBOARD2[i].length; j++) {
                COORDINATES.put(KEYBOARD2[i][j], new EntryObject<Float, Float>(0.5f + i, 0.5f + j));    // TODO: Good way to separate k1 from k2?
            }
        }
    }
    
    protected final double distanceLevel1;
    protected final double distanceLevel2;
    protected final double distanceLevel3;
    
    public KeyboardCharDistanceFunction() {
        distanceLevel1 = 0.2;
        distanceLevel2 = 0.4;
        distanceLevel3 = 0.8;
    }

    public KeyboardCharDistanceFunction(double proximity1Distance, double proximity2Distance, double proximity3Distance) {
        distanceLevel1 = proximity1Distance;
        distanceLevel2 = proximity2Distance;
        distanceLevel3 = proximity3Distance;
    }

    public double distance(Character obj1, Character obj2) {
        if (obj1 == obj2) {
            return 0;   // Trivial case
        }
        else {
            Entry<Float, Float> coord1 = COORDINATES.safeGet(obj1, new EntryObject<Float, Float>(Float.MAX_VALUE, Float.MAX_VALUE));
            Entry<Float, Float> coord2 = COORDINATES.safeGet(obj2, new EntryObject<Float, Float>(Float.MAX_VALUE, Float.MAX_VALUE));
            if (obj1 == ' ' && obj2 != ' ') {
                coord1 = new EntryObject<Float, Float>(coord2.getKey()+1, coord2.getValue());   // Simplification with space bar on entire bottom row
            }
            else if(obj2 == ' ' && obj1 != ' ') {
                coord2 = new EntryObject<Float, Float>(coord1.getKey()+1, coord1.getValue());   // Simplification with space bar on entire bottom row
            }
            float proximity = Math.max(Math.abs(coord1.getKey()-coord2.getKey()), Math.abs(coord1.getValue()-coord2.getValue()));
            if (proximity <= 1) {
                return distanceLevel1;
            }
            else if (proximity <= 2) {
                return distanceLevel2;
            }
            else if (proximity <=3) {
                return distanceLevel3;
            }
            else {
                return 1;
            }
        }
    }

}
