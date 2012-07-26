/*
 * KeyValuePairMatcher.java
 *
 * Copyright 2012 Andres Quiroz Hernandez
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

import programming5.code.ObjectMatcher;

/**
 * Used to match a key pair string by using its key, regardless of the value. For example, the pair "color:blue" matches the key "color".
 * Note that the matcher is not reflexive: "color" does not match "color:blue"
 * @author andresqh
 */
public class KeyValuePairMatcher implements ObjectMatcher<String> {

    protected String separator = ":";

    /**
     * Creates a matcher that expects key/value pair strings with a colon (:) separator
     */
    public KeyValuePairMatcher() {}

    /**
     * Creates a matcher that expects key/value pair strings with the given separator string (i.e. key+separator+value)
     */
    public KeyValuePairMatcher(String pairSeparator) {
        separator = pairSeparator;
    }
    
    /**
     * @param pair first parameter, "key+separator+value" (e.g. "color:blue")
     * @param key second parameter, search key (e.g. "color")
     * @return true if the keys match, false otherwise
     */
    public boolean matches(String pair, String key) {
        return pair.split(separator)[0].trim().equals(key);
    }

}
