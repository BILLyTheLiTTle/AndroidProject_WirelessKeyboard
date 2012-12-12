package wkeyboard.client.bt115987123050471593055;

import java.io.BufferedOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class WirelessKeyboardClientActivity extends Activity {
    	
	private BufferedOutputStream bos = null;
    private Socket socket = null;
    private String ipData,portData,shortMessage,previousMessage="Empty Message";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /**
     * Called when the Button is clicked. 
     * Check the main.xml to find out how this is done.
     * @param view
     */
    public void sendText(View view){
    	ipData=((EditText)findViewById(R.id.ip)).getText().toString();
    	portData=((EditText)findViewById(R.id.port)).getText().toString();
    	shortMessage=((EditText)findViewById(R.id.message)).getText().toString();
    	previousMessage=shortMessage;
        Thread thr=new Thread(){
	        public void run(){
	        	try {
	  	          // open a socket connection
	  	          socket = new Socket(ipData, Integer.parseInt(portData));
	  	          // open I/O streams for objects
	  	          bos = new BufferedOutputStream(socket.getOutputStream());
	  	          shortMessage=customConversion(shortMessage);
	  	          bos.write(shortMessage.getBytes());
	  	          bos.flush();
	  	          bos.close();
	  	          socket.close();
	  	          Log.e("SENT","Message \""+shortMessage+
	  	        		  "\" sent to \""+ipData+
	  	        		  " : "+portData+"\"");
	  	        } 
	  	        catch(Exception e) {
	  	          Log.e("C-ERROR",e.getMessage());
	  	        }
	        }
        };
        thr.start();
        EditText shortMessageView=((EditText)findViewById(R.id.message));
        shortMessageView.setText("");
    }
    
    public void recoverText(View view){
    	EditText shortMessageView=((EditText)findViewById(R.id.message));
        shortMessageView.setText(previousMessage);
    }
    
    public String customConversion(String text){
    	text=text.replace("Α","#*A*#");
    	text=text.replace("Ά","#*AA*#");
    	text=text.replace("α","#*a*#");
    	text=text.replace("ά","#*aa*#");
    	text=text.replace("Β","#*B*#");
    	text=text.replace("β","#*b*#");
    	text=text.replace("Γ","#*G*#");
    	text=text.replace("γ","#*g*#");
    	text=text.replace("Δ","#*D*#");
    	text=text.replace("δ","#*d*#");
    	text=text.replace("Ε","#*E*#");
    	text=text.replace("Έ","#*EE*#");
    	text=text.replace("ε","#*e*#");
    	text=text.replace("έ","#*ee*#");
    	text=text.replace("Ζ","#*Z*#");
    	text=text.replace("ζ","#*z*#");
    	text=text.replace("Η","#*H*#");
    	text=text.replace("Ή","#*HH*#");
    	text=text.replace("η","#*h*#");
    	text=text.replace("ή","#*hh*#");
    	text=text.replace("Θ","#*TH*#");
    	text=text.replace("θ","#*th*#");
    	text=text.replace("Ι","#*I*#");
    	text=text.replace("Ί","#*II*#");
    	text=text.replace("Ϊ","#-I*#");
    	text=text.replace("ι","#*i*#");
    	text=text.replace("ί","#*ii*#");
    	text=text.replace("ϊ","#*-i*#");
    	text=text.replace("ΐ","#*-ii*#");
    	text=text.replace("Κ","#*K*#");
    	text=text.replace("κ","#*k*#");
    	text=text.replace("Λ","#*L*#");
    	text=text.replace("λ","#*l*#");
    	text=text.replace("Μ","#*M*#");
    	text=text.replace("μ","#*m*#");
    	text=text.replace("Ν","#*N*#");
    	text=text.replace("ν","#*n*#");
    	text=text.replace("Ξ","#*KS*#");
    	text=text.replace("ξ","#*ks*#");
    	text=text.replace("Ο","#*O*#");
    	text=text.replace("Ό","#*OO*#");
    	text=text.replace("ο","#*o*#");
    	text=text.replace("ό","#*oo*#");
    	text=text.replace("Π","#*P*#");
    	text=text.replace("π","#*p*#");
    	text=text.replace("Ρ","#*R*#");
    	text=text.replace("ρ","#*r*#");
    	text=text.replace("Σ","#*S*#");
    	text=text.replace("σ","#*s*#");
    	text=text.replace("ς","#*-s*#");
    	text=text.replace("Τ","#*T*#");
    	text=text.replace("τ","#*t*#");
    	text=text.replace("Υ","#*Y*#");
    	text=text.replace("Ύ","#*YY*#");
    	text=text.replace("Ϋ","#*-Y*#");
    	text=text.replace("υ","#*y*#");
    	text=text.replace("ύ","#*yy*#");
    	text=text.replace("ϋ","#*-y*#");
    	text=text.replace("ΰ","#*-yy*#");
    	text=text.replace("Φ","#*F*#");
    	text=text.replace("φ","#*f*#");
    	text=text.replace("Χ","#*X*#");
    	text=text.replace("χ","#*x*#");
    	text=text.replace("Ψ","#*PS*#");
    	text=text.replace("ψ","#*ps*#");
    	text=text.replace("Ω","#*W*#");
    	text=text.replace("Ώ","#*WW*#");
    	text=text.replace("ω","#*w*#");
    	text=text.replace("ώ","#*ww*#");
    	return text;
    }
}