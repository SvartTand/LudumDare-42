package svarttand.application.misc;

public enum ParticleType {
	
	HIT("Hit.p",10,false),
	FIRE("Fire.p", 10,false),
	ZFIRE("ZFire.p",10,false),
	HIT2("Hit2.p",10,false),
	PICKUP("pickup.p", 10,false),
	HP_PICKUP("HpPickup.p", 10,false)
	;
	
	private String path;
	private int poolSIze;
	private boolean behind;


	private ParticleType(String path, int poolSIze, boolean behind) {
		this.path = path;
		this.poolSIze = poolSIze;
		this.behind = behind;
	}
	
	public String getPath() {
		return path;
	}


	public int getPoolSIze() {
		return poolSIze;
	}

	public boolean getBehind() {
		return behind;
	}
	
}
