package wkeyboard.server.bt115987123050471593055;

import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WirelessKeybordServerActivity extends Activity {
    
	private ServerSocket server;
	private Socket client;
	private BufferedInputStream bis;
	private byte[] shortMessageBuffer;
	private String portData,shortMessage,debug;
	private TextView tv;
	private boolean running=true;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv=(TextView)findViewById(R.id.debug);
        //Intent i=new Intent(this, wkeyboard.server.bt115987123050471593055.WirelessSoftKeyboard.class);
    }
    
    /**
     * Opens the server.
     */
    public void openServer(View view){
    	portData=((EditText)findViewById(R.id.port)).getText().toString();
    	tv.setText("DEBUG started...");
    	Log.e("S-STATE","DEBUG started...");
    	running=true;
    	Thread thr=new Thread(){
    		public void run(){
    			while(running){
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
	    				server.close();
	    				Log.e("S-STATE","Server closed...");
	    				sb.trimToSize();
	    				shortMessage=sb.toString();
	    				debug="MESSAGE RECEIVED "+shortMessage;
	    				Log.e("RECEIVED","MESSAGE RECEIVED: "+shortMessage);
	    			}
	    			catch(Exception e){
	    				debug="S-ERROR "+e.getMessage();
	    				Log.e("S-ERROR",""+e.getMessage());
	    			}
	    			tv.post(new Runnable(){
		    			public void run(){
		    				tv.setText(debug);
		    			}
	    			});
    			}
    		}
    	};
    	thr.start();
    }
    
    
    public void closeServer(View view){
    	boolean exists=true,bound=true,closed=false;
    	try{
    		running=false;
    		exists=(server==null);
    		bound=(server.isBound());
    		closed=(server.isClosed());
    		if(server.isBound()){
    			//client.close();
        		server.close();
        	}
    	}
    	catch(Exception e){
    		debug="S-ERROR: "+e.getMessage();
			Log.e("S-ERROR",""+e.getMessage());
    	}
    	tv.setText("Server state: E-"+exists+", B-"+bound+". C-"+closed+", "+debug);
    }
}