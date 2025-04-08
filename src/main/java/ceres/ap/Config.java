package ceres.ap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@AllArgsConstructor
public class Config {

    private final boolean isRando;
    private final boolean sendToConsole;
    @Nullable
    private final List<String> announcements;
    @Nullable
    private final String prefix;
    private final int frequency;

}
