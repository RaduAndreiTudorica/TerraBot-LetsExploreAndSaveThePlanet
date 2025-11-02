package main.core;

import fileio.CommandInput;
import main.output.BaseOutput;

@FunctionalInterface
public interface CommandExecutor {
    BaseOutput execute(CommandInput command);
}
