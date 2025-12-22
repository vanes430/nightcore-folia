package su.nightexpress.nightcore.bridge.registry;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class NightRegistryKey {

    private final String id;

    public NightRegistryKey(@NotNull String id) {
        this.id = id.toLowerCase();
    }

    @NotNull
    public final String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NightRegistryKey that = (NightRegistryKey) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
