package me.creepsterlgc.core.utils;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

public class BlockUtils {
	
	public static boolean isUsable(BlockType type) {
		
		if(!type.equals(BlockTypes.LEVER)
		&& !type.equals(BlockTypes.WOODEN_BUTTON)
		&& !type.equals(BlockTypes.STONE_BUTTON)
		&& !type.equals(BlockTypes.WOODEN_DOOR)
		&& !type.equals(BlockTypes.FENCE_GATE)
		&& !type.equals(BlockTypes.WOODEN_PRESSURE_PLATE)
		&& !type.equals(BlockTypes.STONE_PRESSURE_PLATE)
		&& !type.equals(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE)
		&& !type.equals(BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE)
		&& !type.equals(BlockTypes.TRAPDOOR)
		&& !type.equals(BlockTypes.FURNACE)
		&& !type.equals(BlockTypes.LIT_FURNACE)
		&& !type.equals(BlockTypes.CHEST)
		&& !type.equals(BlockTypes.ENDER_CHEST)
		&& !type.equals(BlockTypes.ANVIL)
		&& !type.equals(BlockTypes.ENCHANTING_TABLE)
		&& !type.equals(BlockTypes.BED)
		&& !type.equals(BlockTypes.CRAFTING_TABLE)
		&& !type.equals(BlockTypes.DISPENSER)
		&& !type.equals(BlockTypes.HOPPER)
		&& !type.equals(BlockTypes.DROPPER)
		&& !type.equals(BlockTypes.BEACON)
		&& !type.equals(BlockTypes.BREWING_STAND)
		&& !type.equals(BlockTypes.CAKE)
		&& !type.equals(BlockTypes.DAYLIGHT_DETECTOR)
		&& !type.equals(BlockTypes.DAYLIGHT_DETECTOR_INVERTED)
		&& !type.equals(BlockTypes.NOTEBLOCK)
		&& !type.equals(BlockTypes.JUKEBOX)
		&& !type.equals(BlockTypes.UNPOWERED_COMPARATOR)
		&& !type.equals(BlockTypes.UNPOWERED_REPEATER)
		&& !type.equals(BlockTypes.POWERED_COMPARATOR)
		&& !type.equals(BlockTypes.POWERED_REPEATER)
		&& !type.equals(BlockTypes.TNT)
		&& !type.equals(BlockTypes.ACACIA_DOOR)
		&& !type.equals(BlockTypes.ACACIA_FENCE_GATE)
		&& !type.equals(BlockTypes.DARK_OAK_DOOR)
		&& !type.equals(BlockTypes.DARK_OAK_FENCE_GATE)
		&& !type.equals(BlockTypes.SPRUCE_DOOR)
		&& !type.equals(BlockTypes.SPRUCE_FENCE_GATE)
		&& !type.equals(BlockTypes.BIRCH_DOOR)
		&& !type.equals(BlockTypes.BIRCH_FENCE_GATE)
		&& !type.equals(BlockTypes.JUNGLE_DOOR)
		&& !type.equals(BlockTypes.JUNGLE_FENCE_GATE)) return false; return true;
		
	}

}
