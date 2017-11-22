package vn.com.jvit.musicdemoapp;

/**
 * Created by User on 11/21/2017.
 */

public class Song {
    private String songName;
    private String songPath;
    private boolean flag;

    public Song(String songName, String songPath) {
        this.songName = songName;
        this.songPath = songPath;
        flag = false;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}
