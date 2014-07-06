package tehsomeluigi.hotgreenfire.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tehsomeluigi.hotgreenfire.reference.Reference;

import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.*;

public class BlockHotGreenFire extends BlockFire {
    private IIcon[] field_149850_M;

    public BlockHotGreenFire() {
        super();
        // this.setCreativeTab(CreativeTabs.tabMisc);
        // We shall not make this available
        this.setBlockName(Reference.MOD_ID + ":hotgreenfire");
        this.setLightLevel(1F);
        this.setHardness(0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149850_M = new IIcon[] {p_149651_1_.registerIcon(Reference.MOD_ID + ":fire_layer_0"), p_149651_1_.registerIcon(Reference.MOD_ID + ":fire_layer_1")};
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int i1, int i2) {
        return this.field_149850_M[0];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getFireIcon(int i1) {
        return this.field_149850_M[i1];
    }

    // Nicked from BlockFire
    private boolean canNeighborBurn(World world, int x, int y, int z) {

        return Blocks.fire.canCatchFire(world, x + 1, y, z, WEST) ||
               Blocks.fire.canCatchFire(world, x - 1, y, z, EAST) ||
               Blocks.fire.canCatchFire(world, x, y - 1, z, UP) ||
               Blocks.fire.canCatchFire(world, x, y + 1, z, DOWN) ||
               Blocks.fire.canCatchFire(world, x, y, z - 1, SOUTH) ||
               Blocks.fire.canCatchFire(world, x, y, z + 1, NORTH);
    }

    // Mostly Nicked from BlockFire - some changed to make fire not extinguish itself unless it has nothing to burn
    private void tryCatchFire(World world, int x, int y, int z, int i1, Random rand, int i2, ForgeDirection face) {
        int j1 = world.getBlock(x, y, z).getFlammability(world, x, y, z, face);

        if (rand.nextInt(i1) < j1) {
            boolean flag = world.getBlock(x, y, z) == Blocks.tnt;

            int k1 = i2 + rand.nextInt(5) / 4;

            if (k1 > 15) {
                k1 = 15;
            }

            world.setBlock(x, y, z, this, k1, 3);

            if (flag) {
                Blocks.tnt.onBlockDestroyedByPlayer(world, x, y, z, 1);
            }
        }
    }

    // Mostly Nicked from BlockFire - changed to light Netherrack as poor fire (vanilla fire)
    private void tryCatchPoorFire(World world, int x, int y, int z) {
        if (world.getBlock(x, y - 1, z).isFireSource(world, x, y - 1, z, UP) && world.isAirBlock(x, y, z)) {
            world.setBlock(x, y, z, Blocks.fire, 15, 3);
        }
    }

    // Nicked from BlockFire
    private int getChanceOfNeighborsEncouragingFire(World world, int x, int y, int z)
    {
        byte b0 = 0;

        if (! world.isAirBlock(x, y, z)) {
            return 0;
        } else {
            int l = b0;
            l = this.getChanceToEncourageFire(world, x + 1, y, z, l, WEST);
            l = this.getChanceToEncourageFire(world, x - 1, y, z, l, EAST);
            l = this.getChanceToEncourageFire(world, x, y - 1, z, l, UP);
            l = this.getChanceToEncourageFire(world, x, y + 1, z, l, DOWN);
            l = this.getChanceToEncourageFire(world, x, y, z - 1, l, SOUTH);
            l = this.getChanceToEncourageFire(world, x, y, z + 1, l, NORTH);
            return l;
        }
    }


    /**
     * Ticks the block if it's been scheduled
     *
     * Mostly nicked from BlockFire - some tweaks have been made
     */
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick")) {
            boolean flag = world.getBlock(x, y - 1, z).isFireSource(world, x, y - 1, z, UP);

            if (! this.canPlaceBlockAt(world, x, y, z)) {
                world.setBlockToAir(x, y, z);
            }

            if (! flag && world.isRaining() && (world.canLightningStrikeAt(x, y, z) || world.canLightningStrikeAt(x - 1, y, z) || world.canLightningStrikeAt(x + 1, y, z) || world.canLightningStrikeAt(x, y, z - 1) || world.canLightningStrikeAt(x, y, z + 1))) {
                // get extinguished by rain
                world.setBlockToAir(x, y, z);
            } else {
                int l = world.getBlockMetadata(x, y, z);

                if (l < 15) {
                    world.setBlockMetadataWithNotify(x, y, z, l + rand.nextInt(3) / 2, 4);
                }

                world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + rand.nextInt(15));

                if (rand.nextInt(10) < 5) {
                    this.tryCatchPoorFire(world, x + 1, y, z);
                    this.tryCatchPoorFire(world, x - 1, y, z);
                    this.tryCatchPoorFire(world, x, y - 2, z);
                    this.tryCatchPoorFire(world, x, y + 2, z);
                    this.tryCatchPoorFire(world, x, y, z - 1);
                    this.tryCatchPoorFire(world, x, y, z + 1);
                }


                if (! flag && ! this.canNeighborBurn(world, x, y, z)) {
                    if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || l > 3) {
                        world.setBlockToAir(x, y, z);
                    }
                } else {
                    boolean flag1 = world.isBlockHighHumidity(x, y, z);
                    byte b0 = 0;

                    if (flag1)
                    {
                        b0 = -50;
                    }

                    this.tryCatchFire(world, x + 1, y, z, 300 + b0, rand, l, WEST );
                    this.tryCatchFire(world, x - 1, y, z, 300 + b0, rand, l, EAST );
                    this.tryCatchFire(world, x, y - 1, z, 250 + b0, rand, l, UP   );
                    this.tryCatchFire(world, x, y + 1, z, 250 + b0, rand, l, DOWN );
                    this.tryCatchFire(world, x, y, z - 1, 300 + b0, rand, l, SOUTH);
                    this.tryCatchFire(world, x, y, z + 1, 300 + b0, rand, l, NORTH);

                    for (int i1 = x - 1; i1 <= x + 1; ++i1) {
                        for (int j1 = z - 1; j1 <= z + 1; ++j1) {
                            for (int k1 = y - 1; k1 <= y + 4; ++k1) {
                                if (i1 != x || k1 != y || j1 != z) {
                                    int l1 = 100;

                                    if (k1 > y + 1) {
                                        l1 += (k1 - (y + 1)) * 100;
                                    }

                                    int i2 = this.getChanceOfNeighborsEncouragingFire(world, i1, k1, j1);

                                    if (i2 > 0) {
                                        int j2 = (i2 + 40 + world.difficultySetting.getDifficultyId() * 7) / (l + 30);

                                        if (flag1) {
                                            j2 /= 2;
                                        }

                                        if (j2 > 0 && rand.nextInt(l1) <= j2 && (!world.isRaining() || !world.canLightningStrikeAt(i1, k1, j1)) && !world.canLightningStrikeAt(i1 - 1, k1, z) && !world.canLightningStrikeAt(i1 + 1, k1, j1) && !world.canLightningStrikeAt(i1, k1, j1 - 1) && !world.canLightningStrikeAt(i1, k1, j1 + 1)) {
                                            int k2 = l + rand.nextInt(5) / 4;

                                            if (k2 > 15) {
                                                k2 = 15;
                                            }

                                            world.setBlock(i1, k1, j1, this, k2, 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
