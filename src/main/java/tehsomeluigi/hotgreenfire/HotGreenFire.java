package tehsomeluigi.hotgreenfire;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tehsomeluigi.hotgreenfire.block.BlockHotGreenFire;
import tehsomeluigi.hotgreenfire.block.ItemBlockHotGreenFire;
import tehsomeluigi.hotgreenfire.item.ItemHotGreenFnS;
import tehsomeluigi.hotgreenfire.reference.Reference;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.MOD_VER)
public class HotGreenFire {
    public static BlockHotGreenFire blockHotGreenFire;
    public static ItemHotGreenFnS itemHotGreenFnS;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        HotGreenFire.blockHotGreenFire = new BlockHotGreenFire();
        HotGreenFire.itemHotGreenFnS = new ItemHotGreenFnS();

        GameRegistry.registerBlock(blockHotGreenFire, ItemBlockHotGreenFire.class, "bHotGreenFire");
        GameRegistry.registerItem(itemHotGreenFnS, "iHotGreenFire");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        GameRegistry.addShapelessRecipe(new ItemStack(itemHotGreenFnS), new ItemStack(Items.emerald), new ItemStack(Items.flint_and_steel, 1, 0), new ItemStack(Items.dye, 1, 2));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {

    }
}
