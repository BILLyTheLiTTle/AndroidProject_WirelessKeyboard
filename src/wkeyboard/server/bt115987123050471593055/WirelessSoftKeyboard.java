/**
 * 
 */
package wkeyboard.server.bt115987123050471593055;

import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.inputmethodservice.InputMethodService;

import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Example of writing an input method for a soft keyboard.  This code is
 * focused on simplicity over completeness, so it should in no way be considered
 * to be a complete soft keyboard implementation.  Its purpose is to provide
 * a basic example for how you would get started writing an input method, to
 * be fleshed out as appropriate.
 */
public class WirelessSoftKeyboard extends InputMethodService{
    
	static boolean running = false;
    private StringBuilder mComposing = new StringBuilder();
    private int previousCharLength=0;
    private ServerSocket server;
	private Socket client;
	private BufferedInputStream bis;
	private String portData,shortMessage,debug;
    
    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override 
    public void onCreate() {
        super.onCreate();
    }

    /**
     * This is the main point where we do our initialization of the input method
     * to begin operating on an application.  At this point we have been
     * bound to the client, and are now receiving all of the detailed information
     * about the target of our edits.
     */
    @Override 
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        openServer();
        
    }

    /**
     * This is called when the user is done editing a field.  We can use
     * this to reset our state.
     */
    @Override 
    public void onFinishInput() {
        super.onFinishInput();
        
        // Clear current composing text and candidates.
        mComposing.setLength(0);
        
        closeServer();
    }

    @Override 
    public void onInitializeInterface() {
    	openServer();
    }
    
    //@Override 
    //public View onCreateInputView() {
    	//openServer();
    	//return null;
    //}
    
    @Override public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        openServer();
    }
    
    /**
     * Helper function to commit any text being composed in to the editor.
     */
    private void commitTyped(InputConnection inputConnection) {
    	inputConnection=getCurrentInputConnection();
        if (mComposing.length() > 0) {
        	inputConnection.deleteSurroundingText(previousCharLength, 0);
            inputConnection.commitText(mComposing, mComposing.length());
            previousCharLength=mComposing.length();
            mComposing.setLength(0);
        }
    }
    
    public void openServer(){
    	//change it dynamically
    	portData="8895";
    	
    	Log.e("S-STATE","DEBUG started...");
    	Thread thr=new Thread(){
    		public void run(){
    			while(!running){
    				running=true;
	    			try{
	    				Log.e("S-STATE","Setting the port...");
	    				server=new ServerSocket(Integer.parseInt(portData));
	    				Log.e("S-STATE","Opening the server...");
	    				client=server.accept();
	    				Log.e("S-STATE","Server received data...");
	    				bis=new BufferedInputStream(client.getInputStream());
	    				StringBuffer sb=new StringBuffer();
	    				int i=bis.read();
	    				while(i!=-1){
	    					char c=(char)i;
	    					sb.append(c);
	    					i=bis.read();
	    				}
	    				Log.e("S-STATE","Data received...");
	    				bis.close();
	    				client.close();
	    				//server.close();
	    				closeServer();
	    				Log.e("S-STATE","Server closed...");
	    				sb.trimToSize();
	    				shortMessage=sb.toString();
		    			mComposing.setLength(0);
		    			shortMessage=customConversion(shortMessage);
		    			mComposing.append(shortMessage);
		    			commitTyped(null);
	    				debug="MESSAGE RECEIVED "+shortMessage;
	    				Log.e("RECEIVED","MESSAGE RECEIVED: "+shortMessage);
	    			}
	    			catch(Exception e){
	    				debug="S-ERROR "+e.getMessage();
	    				Log.e("S-ERROR",""+e.getMessage());
	    			}
    			}
    		}
    	};
    	thr.start();
    }
    
    
    public void closeServer(){
    	boolean exists=true,bound=true,closed=false;
    	if(running){
	    	try{
	    		running=false;
	    		exists=(server==null);
	    		bound=(server.isBound());
	    		closed=(server.isClosed());
	    		if(server.isBound()){
	        		server.close();
	        	}
	    	}
	    	catch(Exception e){
	    		debug="S-ERROR: "+e.getMessage();
				Log.e("S-ERROR",""+e.getMessage());
	    	}
    	}
    }
    
    public String customConversion(String text){
    	text=text.replace("#*A*#","Α");
    	text=text.replace("#*AA*#","Ά");
    	text=text.replace("#*a*#","α");
    	text=text.replace("#*aa*#","ά");
    	text=text.replace("#*B*#","Β");
    	text=text.replace("#*b*#","β");
    	text=text.replace("#*G*#","Γ");
    	text=text.replace("#*g*#","γ");
    	text=text.replace("#*D*#","Δ");
    	text=text.replace("#*d*#","δ");
    	text=text.replace("#*E*#","Ε");
    	text=text.replace("#*EE*#","Έ");
    	text=text.replace("#*e*#","ε");
    	text=text.replace("#*ee*#","έ");
    	text=text.replace("#*Z*#","Ζ");
    	text=text.replace("#*z*#","ζ");
    	text=text.replace("#*H*#","Η");
    	text=text.replace("#*HH*#","Ή");
    	text=text.replace("#*h*#","η");
    	text=text.replace("#*hh*#","ή");
    	text=text.replace("#*TH*#","Θ");
    	text=text.replace("#*th*#","θ");
    	text=text.replace("#*I*#","Ι");
    	text=text.replace("#*II*#","Ί");
    	text=text.replace("#-I*#","Ϊ");
    	text=text.replace("#*i*#","ι");
    	text=text.replace("#*ii*#","ί");
    	text=text.replace("#*-i*#","ϊ");
    	text=text.replace("#*-ii*#","ΐ");
    	text=text.replace("#*K*#","Κ");
    	text=text.replace("#*k*#","κ");
    	text=text.replace("#*L*#","Λ");
    	text=text.replace("#*l*#","λ");
    	text=text.replace("#*M*#","Μ");
    	text=text.replace("#*m*#","μ");
    	text=text.replace("#*N*#","Ν");
    	text=text.replace("#*n*#","ν");
    	text=text.replace("#*KS*#","Ξ");
    	text=text.replace("#*ks*#","ξ");
    	text=text.replace("#*O*#","Ο");
    	text=text.replace("#*OO*#","Ό");
    	text=text.replace("#*o*#","ο");
    	text=text.replace("#*oo*#","ό");
    	text=text.replace("#*P*#","Π");
    	text=text.replace("#*p*#","π");
    	text=text.replace("#*R*#","Ρ");
    	text=text.replace("#*r*#","ρ");
    	text=text.replace("#*S*#","Σ");
    	text=text.replace("#*s*#","σ");
    	text=text.replace("#*-s*#","ς");
    	text=text.replace("#*T*#","Τ");
    	text=text.replace("#*t*#","τ");
    	text=text.replace("#*Y*#","Υ");
    	text=text.replace("#*YY*#","Ύ");
    	text=text.replace("#*-Y*#","Ϋ");
    	text=text.replace("#*y*#","υ");
    	text=text.replace("#*yy*#","ύ");
    	text=text.replace("#*-y*#","ϋ");
    	text=text.replace("#*-yy*#","ΰ");
    	text=text.replace("#*F*#","Φ");
    	text=text.replace("#*f*#","φ");
    	text=text.replace("#*X*#","Χ");
    	text=text.replace("#*x*#","χ");
    	text=text.replace("#*PS*#","Ψ");
    	text=text.replace("#*ps*#","ψ");
    	text=text.replace("#*W*#","Ω");
    	text=text.replace("#*WW*#","Ώ");
    	text=text.replace("#*w*#","ω");
    	text=text.replace("#*ww*#","ώ");
    	return text;
    }
}
