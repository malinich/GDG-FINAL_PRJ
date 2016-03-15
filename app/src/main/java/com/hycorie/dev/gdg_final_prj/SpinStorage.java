package com.hycorie.dev.gdg_final_prj;


public class SpinStorage {
    private int mCapacity;
    private FixedSizeList<SpinController> mStorage;
    private SpinHandler mSpinHandler;

    private static SpinStorage ourInstance = new SpinStorage();

    public static SpinStorage getInstance() {
        return ourInstance;
    }

    private SpinStorage() {
        mCapacity = 3;
        mStorage = new FixedSizeList<SpinController>(mCapacity);
    }

    public void add(int index, SpinController sp){
        mStorage.add(index, sp);
        if (mStorage.size() >= mCapacity){
            if(!getStorage().contains(null)){
                mSpinHandler.spinFilled(mStorage);
            };
        }
    }

    public void setSpinHandler(SpinHandler spinHandler) {
        mSpinHandler = spinHandler;
    }

    public FixedSizeList<SpinController> getStorage() {
        return mStorage;
    }
}
