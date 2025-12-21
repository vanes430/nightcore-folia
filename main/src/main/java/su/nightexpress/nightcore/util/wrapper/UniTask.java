package su.nightexpress.nightcore.util.wrapper;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.nightcore.NightCorePlugin;

@Deprecated
public class UniTask {

    private final NightCorePlugin plugin;
    private final Runnable        runnable;

    private long        interval;
    private boolean     async;
    private WrappedTask task;

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable) {
        this(plugin, runnable, 0L);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, int interval) {
        this(plugin, runnable, interval, false);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, int interval, boolean async) {
        this(plugin, runnable, interval * 20L, async);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, long interval) {
        this(plugin, runnable, interval, false);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, long interval, boolean async) {
        this.plugin = plugin;
        this.runnable = runnable;
        this.interval = interval;
        this.async = async;
    }

    @Deprecated
    public UniTask setSecondsInterval(int interval) {
        return this.setTicksInterval(interval * 20L);
    }

    @Deprecated
    public UniTask setTicksInterval(long interval) {
        this.interval = interval;
        return this;
    }

    @Deprecated
    public UniTask setAsync() {
        return this.setAsync(true);
    }

    @Deprecated
    public UniTask setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public boolean isRunning() {
        return this.task != null;
    }

    public final void restart() {
        this.stop();
        this.start();
    }

    public UniTask start() {
        if (this.task != null || this.interval <= 0L) return this;

        if (this.async) {
            this.task = plugin.getFoliaLib().getScheduler().runTimerAsync(runnable, 0L, interval);
        }
        else {
            this.task = plugin.getFoliaLib().getScheduler().runTimer(runnable, 0L, interval);
        }
        return this;
    }

    public boolean stop() {
        if (this.task == null) return false;

        this.task.cancel();
        this.task = null;
        return true;
    }
}
