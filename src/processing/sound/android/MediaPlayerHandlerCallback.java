package processing.sound.android;

import android.content.res.AssetFileDescriptor;

public interface MediaPlayerHandlerCallback {
    public void startPlayer();
    public void stopPlayer();
    public void initPlayer(AssetFileDescriptor fileName);
}
