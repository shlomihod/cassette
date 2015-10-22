package cassette.audiofiles;

import android.content.res.AssetFileDescriptor;

public interface MediaPlayerHandlerCallback {
    public void initPlayer(AssetFileDescriptor fileName);
    public void startPlayer();
    public void stopPlayer();
    public void setLoopingONPlayer();
}
