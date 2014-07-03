package drunkmafia.thaumicinfusion.common.lib;

/**
 * Created by DrunkMafia on 16/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class BlockInfo {

    public static final String infusedBlock_UnlocalizedName = "local_infused";
    public static final String infusedBlock_RegistryName = "reg_infused";

    public static final String essentiaBlock_UnlocalizedName = "local_essentia";
    public static final String essentiaBlock_RegistryName = "reg_essentia";

    public static final String essentiaConstructorBlock_UnlocalizedName = "local_essentiaConstructor";
    public static final String essentiaConstructorBlock_RegistryName = "reg_essentiaConstructor";
    public static final String essentiaConstructorBlock_TileEntity = "tile_essentiaConstructor";

    private static final String TEXTUREBASE = ModInfo.MODID_INFO + ":";
    public static final String essentiaBlock_BlockTexture = TEXTUREBASE + "essentiablock";
    public static final String essentiaBlock_BrickTexture = TEXTUREBASE + "bricks_essentiablock";
    public static final String essentiaBlock_SquareTexture = TEXTUREBASE + "squarebrick_essentiablock";
}
