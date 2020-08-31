package io.github.queerbric.queerbrics.registry;

import io.github.queerbric.queerbrics.QueerbricsMain;
import io.github.queerbric.queerbrics.datatypes.item.QueerBrickItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class QueerbricsItems {
	// Yes I know that this is a silly way to name colours, but it's consistent! - Caroline
	public enum Colour { // The 16 vanilla-supported dye colours.
		BLACK, WHITE, // Black, White
		DARK_GREY, LIGHT_GREY, // Grey, Light Grey
		DARK_BLUE, LIGHT_BLUE, // Blue, Light Blue
		DARK_RED, LIGHT_RED, // Red, Pink
		DARK_GREEN, LIGHT_GREEN, // Green, Lime Green
		DARK_PURPLE, LIGHT_PURPLE, // Purple, Magenta
//		BROWN, // Brown
		YELLOW, CYAN, ORANGE; // Yellow, Cyan, Orange
		
		private final String registryKey;
		
		public final Item brick;
		
		Colour() {
			this.registryKey = this.name().toLowerCase().trim();
			this.brick = registerItem( this.registryKey + "_brick", new QueerBrickItem());
//			this.fabric = registerItem(this.registryKey + "_fabric", new QueerBricItem());
		}
		
		public static void load() { System.out.println("Pride bricks should now init."); }
	}
	
	public enum Pride {
		RAINBOW, // General pride
		GAY, LESBIAN, BI, PAN, // Attraction pride
		TRANS, ENBY, // Gender-identity pride
		ANARCHY;
		
		public final Item brick;
		private final String registryKey;
//		public final Item fabric;
		
		Pride() {
			this.registryKey = this.name().toLowerCase().trim();
			this.brick = registerItem( this.registryKey + "_brick", new QueerBrickItem());
		}
		
		public static void load() { System.out.println("Coloured bricks should now init."); }
	}
	
	public static void init() {
		Pride.load();
		Colour.load();
	}
	
	public static Item registerItem(String key, Item item) {
		return Registry.register(Registry.ITEM, QueerbricsMain.getId(key), item);
	}
}
