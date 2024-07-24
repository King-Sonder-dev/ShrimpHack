package me.alpha432.oyvey.event.impl;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class StartBreakingBlockEvent {
    public BlockPos blockPos;
    public Direction direction;

    public StartBreakingBlockEvent(BlockPos blockPos, Direction direction) {
        this.blockPos = blockPos;
        this.direction = direction;
    }
}