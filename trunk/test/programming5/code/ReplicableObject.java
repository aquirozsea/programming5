/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.code;

/**
 *
 * @author andresqh
 */
public class ReplicableObject implements Replicable<ReplicableObject> {

    protected int field;

    public ReplicableObject(int myValue) {
        field = myValue;
    }

    public int getField() {
        return field;
    }

    public void setField(int newValue) {
        field = newValue;
    }

    public ReplicableObject replicate() {
        return new ReplicableObject(field);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return this.field == ((ReplicableObject) obj).field;
        }
        catch (ClassCastException cce) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.field;
        return hash;
    }

}
