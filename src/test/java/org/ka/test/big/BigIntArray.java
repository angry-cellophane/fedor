package org.ka.test.big;

import java.io.Serializable;
import java.util.Arrays;

public class BigIntArray implements Serializable {
    private final int[] array1;
    private final int[] array2;

    public BigIntArray(int[] array1, int[] array2) {
        this.array1 = array1;
        this.array2 = array2;
    }

    public int[] getArray1() {
        return array1;
    }

    public int[] getArray2() {
        return array2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BigIntArray that = (BigIntArray) o;

        if (!Arrays.equals(array1, that.array1)) return false;
        return Arrays.equals(array2, that.array2);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(array1);
        result = 31 * result + Arrays.hashCode(array2);
        return result;
    }
}
