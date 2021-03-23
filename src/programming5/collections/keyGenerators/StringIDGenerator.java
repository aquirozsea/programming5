/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.collections.keyGenerators;

import programming5.collections.MapKeyGenerator;

import java.util.Random;

/**
 *
 * @author andresqh
 */
public class StringIDGenerator implements MapKeyGenerator {

    protected Random random;
    protected int length;

    public StringIDGenerator() {
        random = new Random(System.currentTimeMillis());
        length = 8;
    }

    public StringIDGenerator(long randomSeed) {
        random = new Random(randomSeed);
        length = 8;
    }

    public StringIDGenerator(int idLength) {
        random = new Random(System.currentTimeMillis());
        length = idLength;
    }
    
    public StringIDGenerator(int idLength, long randomSeed) {
        random = new Random(randomSeed);
        length = idLength;
    }
    
    public Object generateKey() {
        char[] stringBytes = new char[length];
        for (int i = 0; i < stringBytes.length; i++) {
            stringBytes[i] = (char) (random.nextInt(26) + 97);
        }
        return new String(stringBytes);
    }
}
