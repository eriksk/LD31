package se.skoggy.audio;

public interface IAudio {
	public void mute();
	public void setSfxVolume(float soundVolume);
	public void setMusicVolume(float musicVolume);
	public void play(String name);
	public void play(String name, float pitch);
	public void playSong(String name, boolean loop);
	public void pauseSong(String name);
	public void stopSong(String name);
	public void stopAllSongs();
}
