package drunkmafia.thaumicinfusion.common.block;

import drunkmafia.thaumicinfusion.common.block.tile.EssentiaConstructorTile;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 02/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class EssentiaConstructor extends Block implements ITileEntityProvider {
    protected EssentiaConstructor() {
        super(Material.rock);
        setBlockName(essentiaConstructorBlock_UnlocalizedName);
        setCreativeTab(TITab.tab);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new EssentiaConstructorTile();
    }
}
