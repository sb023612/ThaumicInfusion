package drunkmafia.thaumicinfusion.common.world;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrunkMafia on 27/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class WorldEventHandler {
    /**
    private HashMap<World, TIWorldData> worlDatas;

    public WorldEventHandler(){
        worlDatas = new HashMap<World, TIWorldData>();
    }

    public TIWorldData getWorldData(World world){
        return worlDatas.get(world);
    }

    public void addWorldData(World world, TIWorldData data){
        worlDatas.put(world, data);
    }
     **/

    @SubscribeEvent
    public void load(WorldEvent.Load loadEvent){

        loadEvent.world.perWorldStorage.loadData(TIWorldData.class, loadEvent.world.getWorldInfo().getWorldName() + "_TIDATA");

        /**((ISaveHandler saveHandler = loadEvent.world.getSaveHandler();
        try {
            File file = new File(saveHandler.getWorldDirectory().getAbsoluteFile(), "TIData.dat");
            if(file.exists()) {
                NBTTagCompound fileTag = CompressedStreamTools.read(file);

                if (fileTag != null && fileTag.hasKey(loadEvent.world.getProviderName())) {
                    //TIWorldData data = new TIWorldData();

                    System.out.println(fileTag.getCompoundTag(loadEvent.world.getSaveHandler().loadWorldInfo().getWorldName()).hasKey("Hello"));

                    //data.readFromNBT(fileTag.getCompoundTag(loadEvent.world.getSaveHandler().loadWorldInfo().getWorldName()));
                    //worlDatas.put(loadEvent.world, data);
                }
            }

        } catch (Exception e) {
            if(!loadCrash) {
                e.printStackTrace();
                loadCrash =true;
            }else FMLLog.warning("ERROR, Prevented stacktrace from printing to stop spam");
        }**/
    }
    /**
    private boolean saveCrash = false, loadCrash = false;

    @SubscribeEvent
    public void save(WorldEvent.Save saveEvent){
        ISaveHandler saveHandler = saveEvent.world.getSaveHandler();
        try {
            File file = new File(saveHandler.getWorldDirectory().getAbsoluteFile(), "TIData.dat");
            NBTTagCompound fileTag;
            if(file.exists()){
                fileTag = CompressedStreamTools.read(file);
            }else{
                file.createNewFile();
                fileTag = new NBTTagCompound();
            }

            NBTTagCompound dataTag = new NBTTagCompound();
            dataTag.setString("Hello", "World");
            //TIWorldData data = worlDatas.get(saveEvent.world);
            //data.writeToNBT(dataTag);
            fileTag.setTag(saveEvent.world.getSaveHandler().loadWorldInfo().getWorldName(), dataTag);

            CompressedStreamTools.write(fileTag, file);

        } catch (Exception e) {
            if(!saveCrash) {
                e.printStackTrace();
                saveCrash =true;
            }else FMLLog.warning("ERROR, Prevented stacktrace from printing to stop spam");
        }
    }

    public Byte[] readFile(FileInputStream stream) throws IOException {
        ArrayList<Byte> byteArrayList = new ArrayList<Byte>();
        int content;
        while ((content = stream.read()) != -1) {
            // convert to char and display it
            System.out.print((char) content);
        }
        return (Byte[])byteArrayList.toArray();
    }

    public void writeFile(FileOutputStream stream) throws IOException {
    }**/
}
