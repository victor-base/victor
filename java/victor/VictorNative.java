package victor;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * This file serves as the bridge between the managed Java code and the native library Victor coded in c (`libvictor.dll`).
 * It declares the signatures of the native functions using Java Native Access (JNA). These functions
 * include essential operations such as memory management (allocation and destruction of indices), insertion,
 * deletion, search and statistics functionalities.
 */
public interface VictorNative extends Library {
    VictorNative INSTANCE = Native.load("nativeLib/libvictor", VictorNative.class);

    /* Initializes a new index in native memory */
    Pointer alloc_index(int type, int method, int dims, Pointer context);
    /* Releases the allocated memory for an index */
    long destroy_index(PointerByReference ptr);

    /* Inserts a vector into the index */
    int insert(Pointer ptr, long id, float[] vector, int dims);
    /* Removes a vector by its ID from certain index */
    int delete(Pointer ptr, long id);

    /* Executes a query to retrieve the top N results */
    int search_n(Pointer index, float[] vector, int dims, Pointer results, int n);
    /* Performs a single result search using a query vector */
    int search(Pointer index, float[] vector, int dims, Pointer result);

    // TODO add methods update_icontext, stats, size, dump and contains
}