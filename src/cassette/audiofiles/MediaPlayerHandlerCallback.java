package cassette.audiofiles;

import android.content.res.AssetFileDescriptor;

public interface MediaPlayerHandlerCallback {
    public void startPlayer();
    public void stopPlayer();
    public void initPlayer(AssetFileDescriptor fileName);
}
