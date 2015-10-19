package processing.sound.android;

import processing.sound.android.SoundBase;
import processing.sound.android.MediaPlayerHandlerCallback;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import processing.core.PApplet;

public class SoundFile extends SoundBase implements MediaPlayerHandlerCallback {
    
    private MediaPlayerHandler handler;
    private MediaPlayer player;
    
    private boolean looping = false;
	
	public SoundFile(PApplet parent, String fileName) {
	    super(parent);
	    AssetFileDescriptor afd = null;
	    try {
            afd = activity.getAssets().openFd(fileName);
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

	    HandlerThread backgroundThread = new HandlerThread("MediaPlayer");
	    backgroundThread.start();
	    handler = new MediaPlayerHandler(backgroundThread.getLooper());
	    handler.setCallback(this);
	    handler.sendMessage(handler.obtainMessage(MediaPlayerHandler.MSG_INIT_PLAYER, afd));
	}
	
	public void play() {
	    handler.sendMessage(handler.obtainMessage(MediaPlayerHandler.MSG_START_PLAYER));
	}

	public void stop() {
		handler.sendMessage(handler.obtainMessage(MediaPlayerHandler.MSG_STOP_PLAYER));
	}

	public void loop() {
        looping = true;
	}
	
	@Override
	public void onPause() {
	}
	
	@Override
	public void onResume() {
	}
	
	@Override
	public String getEventName() {
	    return "soundFileEvent";
	}
	
	private class MediaPlayerHandler extends Handler {
	    
	    public static final int MSG_INIT_PLAYER = 0;
	    public static final int MSG_START_PLAYER = 1;
		public static final int MSG_STOP_PLAYER = 2;
	    
	    MediaPlayerHandlerCallback callback;
	    
	    public MediaPlayerHandler(Looper looper) {
            super(looper);
        }

        public void setCallback (MediaPlayerHandlerCallback cb) {
	        this.callback = cb;
	    }
	    
	    @Override
	    public void handleMessage(Message msg) {
	        switch (msg.what) {
	        case MSG_INIT_PLAYER:
	            AssetFileDescriptor afd = (AssetFileDescriptor) msg.obj;
	            callback.initPlayer(afd);
	            break;
            case MSG_START_PLAYER:
                callback.startPlayer();
                break;
			case MSG_STOP_PLAYER:
				callback.stopPlayer();
				break;
            default:
                break;
            }
	    }
	}
	
	@Override
	public void initPlayer(AssetFileDescriptor afd) {
	    player = new MediaPlayer();
	    try {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(looping);
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void startPlayer() {
	    player.start();
	}

	@Override
	public void stopPlayer() {
		player.stop();
	}
}
