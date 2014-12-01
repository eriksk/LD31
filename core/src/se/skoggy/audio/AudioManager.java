package se.skoggy.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import se.skoggy.content.ContentManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AudioManager implements IAudio{

	protected HashMap<String, Sound> sounds;
	protected HashMap<String, Music> songs;
	protected float soundVolume;
	protected float musicVolume;
	protected boolean muted;

	private List<String> soundNames, songNames;

	public AudioManager(){
		sounds = new HashMap<String, Sound>();
		songs = new HashMap<String, Music>();
		soundNames = new ArrayList<String>();
		songNames = new ArrayList<String>();
		soundVolume = 1f;
		musicVolume = 1f;
		muted = false;
	}

	public void mute() {
		muted = true;
	}

	public void setSfxVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}

	public void registerSound(String name){
		soundNames.add(name);
	}

	public void registerSong(String name){
		songNames.add(name);
	}

	private String getNameWithoutPath(String path){
		String nameWithoutPath = path.substring(0, path.indexOf("."));
		nameWithoutPath = nameWithoutPath.substring(nameWithoutPath.lastIndexOf("/") + 1);
		return nameWithoutPath;
	}

	/**
	 * Loads preregistered audio
	 * @param content
	 */
	public void load(ContentManager content){
		for (String name : soundNames) {
			sounds.put(getNameWithoutPath(name), Gdx.audio.newSound(Gdx.files.internal(name)));
		}
		for (String name : songNames) {
			songs.put(getNameWithoutPath(name), Gdx.audio.newMusic(Gdx.files.internal(name)));
		}
	}

	public void play(String name) {
		if(!muted){
			Sound s = sounds.get(name);
			s.stop();
			s.play(soundVolume);
		}
	}
	public void play(String name, float pitch) {
		if(!muted){
			sounds.get(name).play(soundVolume, pitch, 0f);
		}
	}

	/**
	 * Plays a song and stops any other currently playing, if this is the current song playing it will just continue
	 * @param name
	 * @param loop
	 */
	public void playSong(String name, boolean loop){
		if(!muted){
			stopAllSongs();
			songs.get(name).setVolume(musicVolume);
			songs.get(name).setLooping(loop);
			songs.get(name).play();
		}
	}

	public void pauseSong(String name){
		songs.get(name).pause();
	}

	public void stopSong(String name){
		songs.get(name).stop();;
	}

	public void stopAllSongs(){
		for (Music song : songs.values()) {
			if(song.isPlaying()){
				song.stop();
			}
		}
	}
}
