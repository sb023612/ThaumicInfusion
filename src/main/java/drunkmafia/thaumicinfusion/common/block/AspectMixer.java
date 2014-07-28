package drunkmafia.thaumicinfusion.common.block;

import drunkmafia.thaumicinfusion.common.block.tile.MixerTile;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by DrunkMafia on 28/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class AspectMixer extends TIBlock implements ITileEntityProvider {
    protected AspectMixer() {
        super(Material.rock);
        setBlockName("Mixer");
        setCreativeTab(TITab.tab);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new MixerTile();
    }
}
