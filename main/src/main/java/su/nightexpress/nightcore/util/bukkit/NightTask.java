package su.nightexpress.nightcore.util.bukkit;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.nightcore.NightCorePlugin;
import su.nightexpress.nightcore.util.TimeUtil;

import java.util.function.Supplier;

public class NightTask {

    private final NightCorePlugin plugin;
    private final WrappedTask     wrappedTask;

    public NightTask(@NotNull NightCorePlugin plugin, @Nullable WrappedTask wrappedTask) {
        this.plugin = plugin;
        this.wrappedTask = wrappedTask;
    }

    @NotNull
    public static NightTask create(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, int interval) {
        return create(plugin, runnable, TimeUtil.secondsToTicks(interval));
    }

    @NotNull
    public static NightTask create(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, long interval) {
        return createTask(plugin, () -> interval <= 0 ? null : plugin.getFoliaLib().getScheduler().runTimer(runnable, 0L, interval));
    }

    @NotNull
    public static NightTask createAsync(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, int interval) {
        return createAsync(plugin, runnable, TimeUtil.secondsToTicks(interval));
    }

    @NotNull
    public static NightTask createAsync(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, long interval) {
        return createTask(plugin, () -> interval <= 0 ? null : plugin.getFoliaLib().getScheduler().runTimerAsync(runnable, 0L, interval));
    }

    @NotNull
    private static NightTask createTask(@NotNull NightCorePlugin plugin, @NotNull Supplier<WrappedTask> supplier) {
        WrappedTask wrappedTask = supplier.get();
        return new NightTask(plugin, wrappedTask);
    }

    @Nullable
    public WrappedTask getWrappedTask() {
        return this.wrappedTask;
    }

    public boolean isValid() {
        return this.wrappedTask != null;
    }

    @Deprecated
    public boolean isRunning() {
        return this.isValid();
    }

    public boolean stop() {
        if (this.wrappedTask == null) return false;

        this.wrappedTask.cancel();
        return true;
    }
}
