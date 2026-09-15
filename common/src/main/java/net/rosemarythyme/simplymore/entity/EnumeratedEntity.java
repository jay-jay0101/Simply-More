package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;

public interface EnumeratedEntity {
    TrackedData<Integer> getDataType();

    default void initEnumeratedDataTracker(DataTracker.Builder builder) {
        builder.add(getDataType(), 0);
    }

    default void writeEnumeratedCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("ordinal", getDataTrackerBridge().get(getDataType()));
    }

    default void readEnumeratedCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("ordinal")) {
            getDataTrackerBridge().set(getDataType(), nbt.getInt("ordinal"));
        } else {
            getDataTrackerBridge().set(getDataType(), 0);
        }
    }

    boolean shouldEnumerate();

    DataTracker getDataTrackerBridge();

    default void setOrdinal(int ordinal) {
        getDataTrackerBridge().set(getDataType(), ordinal);
    }

    default int getOrdinal() {
        return getDataTrackerBridge().get(getDataType());
    }
}
