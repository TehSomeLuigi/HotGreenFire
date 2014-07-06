package tehsomeluigi.hotgreenfire.item;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tehsomeluigi.hotgreenfire.HotGreenFire;
import tehsomeluigi.hotgreenfire.reference.Reference;

import java.util.List;

public class ItemHotGreenFnS extends Item {

    public ItemHotGreenFnS() {
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName(Reference.MOD_ID + ":hotgreenfns");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iIcon) {
        itemIcon = iIcon.registerIcon(Reference.MOD_ID + ":hotgreenfns");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack iStack, EntityPlayer player, List list, boolean someBool) {
        list.add(LanguageRegistry.instance().getStringLocalization(iStack.getUnlocalizedName() + ".tooltip"));
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack iStack, EntityPlayer p, World world, int i1, int i2, int i3, int i4, float f1, float f2, float f3) {
        if (i4 == 0) {
            --i2;
        }

        if (i4 == 1) {
            ++i2;
        }

        if (i4 == 2) {
            --i3;
        }

        if (i4 == 3) {
            ++i3;
        }

        if (i4 == 4) {
            --i1;
        }

        if (i4 == 5) {
            ++i1;
        }

        if (!p.canPlayerEdit(i1, i2, i3, i4, iStack)) {
            return false;
        } else {
            if (world.isAirBlock(i1, i2, i3)) {
                world.playSoundEffect((double) i1 + 0.5D, (double) i2 + 0.5D, (double) i3 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlock(i1, i2, i3, HotGreenFire.blockHotGreenFire);
            }

            iStack.damageItem(1, p);
            return true;
        }
    }
}
