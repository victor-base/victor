package victor;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import exception.VictorError;
import models.MatchResult;

public class Victor implements AutoCloseable {

    private Pointer nativePtr;
    private MatchResult[] resultPtr;

    private final VictorNative victorNative = VictorNative.INSTANCE;

    public Victor(int type, int method, int dims) {
        this(type, method, dims, 0); // Default value of context is 0
    }

    public Victor(int type, int method, int dims, int context) {
        nativePtr = victorNative.alloc_index(type, method, dims, new Pointer(context));
        if (isDestroyed()) {
            throw new RuntimeException("Unable to create index.");
        }
    }

    public void insert(long id, float[] vector, int dims) {
        checkIfDestroyedAndThrow();

        int code = victorNative.insert(nativePtr, id, vector, dims);
        VictorError.check(code, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void delete(int id) {
        checkIfDestroyedAndThrow();
        int code = victorNative.delete(nativePtr, id);
        VictorError.check(code, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public MatchResult search(float[] vector, int dims) {
       checkIfDestroyedAndThrow();
       MatchResult result = (resultPtr == null || resultPtr.length == 0)
               ? new MatchResult()
               : resultPtr[0];
        Pointer resultPointer = new Memory(result.size());

        try {
           int code = victorNative.search(nativePtr, vector, dims, resultPointer);
           VictorError.check(code, Thread.currentThread().getStackTrace()[1].getMethodName());
           return new MatchResult(resultPointer);
       }
        finally
        {
            resultPtr = null; //todo check
        }
    }

    public MatchResult[] search_n(float[] vector, int dims, int n) {
       checkIfDestroyedAndThrow();
        MatchResult result = (resultPtr == null || resultPtr.length == 0)
                ? new MatchResult()
                : resultPtr[0];
       Pointer resultsPointer = new Memory((long) result.size() * n);

       try {
           int code = victorNative.search_n(nativePtr, vector, dims, resultsPointer, n);
           VictorError.check(code, Thread.currentThread().getStackTrace()[1].getMethodName());
           return resultPtr; // todo cast pointer to MatchResult[] type
       }
        finally
        {
            resultPtr = null; //todo check
        }
    }

    @Override
    public void close() {
        checkIfDestroyedAndThrow();
        int code = (int) victorNative.destroy_index(new PointerByReference(nativePtr));
        VictorError.check(code, Thread.currentThread().getStackTrace()[1].getMethodName());
        nativePtr = new Pointer(0);
    }

    public boolean isDestroyed()  {
        return nativePtr.getInt(0) == 0;
    }

    private void checkIfDestroyedAndThrow() throws IllegalStateException {
        if (isDestroyed()) {
            throw new IllegalStateException("Index has been destroyed.");
        }
    }
}
