package net.capitangolo.maincra.merluzos;

import net.minecraft.core.Direction;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;

/**
 * Assorted common utility code
 *
 * @author Cadiboo
 */
public final class ModUtil {

	/**
	 * Cache all the directions instead of calling Direction.values()
	 * each time (because each call creates a new Direction[] which is wasteful)
	 * TODO: change to Direction.VALUES once it's ATed
	 */
	public static final Direction[] DIRECTIONS = Direction.values();

	/**
	 * Returns null, while claiming to never return null.
	 * Useful for constants with @ObjectHolder who's values are null at compile time, but not at runtime
	 *
	 * @return null
	 */
	@Nonnull
	// Get rid of "Returning null from Nonnull method" warnings
	@SuppressWarnings("ConstantConditions")
	public static <T> T _null() {
		return null;
	}

}
