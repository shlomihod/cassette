package cassette.audiofiles;

import java.lang.reflect.Method;

import android.app.Activity;

import processing.core.PApplet;
import processing.core.PConstants;

public abstract class SoundBase implements PConstants {

    protected PApplet parent;

    protected static final boolean DEBUG = true;

    protected Activity activity;

    public static void log(String log) {
        if (DEBUG)
            System.out.println(log);
    }
    
    private Method eventMethod;
    private Object eventHandler;

    public abstract void onResume();
    public abstract void onPause();
    public abstract String getEventName();
    
    public SoundBase(PApplet parent) {
        super();
        this.parent = parent;
        
        parent.registerMethod("pause", this);
        parent.registerMethod("resume", this);
        
        setEventHandlerObject(parent);
        
        activity = parent.getActivity();
    }


    public void pause() {
        onPause();
    }
    
    public void resume() {
        onResume();
    }

    private void setEventHandlerObject(Object obj) {
        eventHandler = obj;

        try {
          eventMethod = obj.getClass().getMethod(getEventName(), getClass());
          return;
        } catch (Exception e) {
          // no such method, or an error.. which is fine, just ignore
          log("No event method");
        }

        // The captureEvent method may be declared as receiving Object, rather
        // than Capture.
        try {
          eventMethod = obj.getClass().getMethod(getEventName(), Object.class);
          return;
        } catch (Exception e) {
          // no such method, or an error.. which is fine, just ignore
        }
    }
    
    private void fireEvent() {
        if (eventMethod != null) {
          try {
            eventMethod.invoke(eventHandler, this);

          } catch (Exception e) {
            System.err.println("error, disabling " + getEventName() + "()");
            e.printStackTrace();
            eventMethod = null;
          }
        }
    }
}
