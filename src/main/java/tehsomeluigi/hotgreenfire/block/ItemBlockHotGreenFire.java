package tehsomeluigi.hotgreenfire.block;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockHotGreenFire extends ItemBlock {
    public ItemBlockHotGreenFire(Block par1) {
        super(par1);
        this.setHasSubtypes(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack iStack, EntityPlayer player, List list, boolean someBool) {
        list.add(LanguageRegistry.instance().getStringLocalization(iStack.getUnlocalizedName() + ".tooltip"));
    }
}
