public class Victor extends VictorNative {

    private long nativePtr;

    public Victor(int type, int method, int dims) {
        nativePtr = allocIndex(type, method, dims);
        if (isDestroyed()) {
            throw new RuntimeException("Unable to create index.");
        }
    }

    public int insert(long id, float[] vector, int dims) {
        checkIfDestroyedAndThrow();

        int code = nativeInsert(nativePtr, id, vector, dims);
        VictorError.check(code, Thread.currentThread().getStackTrace()[1].getMethodName());
        return code;
    }

    public int delete(long id) {
        checkIfDestroyedAndThrow();
        return nativeDelete(nativePtr, id);
    }

    @Override
    public void close() {
        checkIfDestroyedAndThrow();
        int code = (int) nativeDestroy(nativePtr);
        VictorError.check(code, "close()");
        nativePtr = 0;
    }

    public boolean isDestroyed()  {
        return nativePtr == 0;
    }

    private void checkIfDestroyedAndThrow() throws IllegalStateException {
        if (isDestroyed()) {
            throw new IllegalStateException("Index has been destroyed.");
        }
    }
}
