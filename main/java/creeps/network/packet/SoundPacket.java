package creeps.network.packet;

import creeps.CreepMain;
import creeps.api.ILocation;
import creeps.network.CodecField;
import creeps.network.PacketCodec;
import creeps.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoundPacket extends PacketCodec {

	private static final int RANGE = 15;
	
	@CodecField
	private double x = 0;
    @CodecField
	private double y = 0;
    @CodecField
	private double z = 0;
    @CodecField
	private String mod = "";
	@CodecField
	private String sound = "";
    @CodecField
	private float volume = 0;
    @CodecField
	private float frequency = 0;
    
    /**
     * Empty packet.
     */
    public SoundPacket() {
    	
    }
    
    /**
	 * Creates a packet with coordinates.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
	 */
	public SoundPacket(double x, double y, double z, String sound, float volume, float frequency) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.sound = sound;
		this.volume = volume;
		this.frequency = frequency;
	}
	
	/**
	 * Creates a packet with coordinates.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
	 * @param mod The mod id that has this sound.
	 */
	public SoundPacket(double x, double y, double z, String sound, float volume, float frequency, String mod) {
		this(x, y, z, sound, volume, frequency);
		this.mod = mod;
	}
	
	/**
	 * Creates a packet which contains the location data.
	 * @param location The location data.
	 * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
	 */
	public SoundPacket(ILocation location, String sound, float volume, float frequency) {
		this(location.getCoordinates()[0], location.getCoordinates()[1], location.getCoordinates()[2],
				sound, volume, frequency);
	}
    
	@Override
	@SideOnly(Side.CLIENT)
	public void actionClient(World world, EntityPlayer player) {
		CreepMain.proxy.playSound(x, y, z, sound, volume, frequency, mod);
	}
	@Override
	public void actionServer(World world, EntityPlayerMP player) {
		PacketHandler.sendToAllAround(new SoundPacket(x, y, z, sound, volume, frequency, mod),
                new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), x, y, z, RANGE));
	}
	
}