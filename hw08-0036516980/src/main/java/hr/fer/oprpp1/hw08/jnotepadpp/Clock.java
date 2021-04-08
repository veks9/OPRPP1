package hr.fer.oprpp1.hw08.jnotepadpp;

import java.util.Date;

import javax.swing.JLabel;

import java.text.SimpleDateFormat;

/**
 * Klasa predstavlja sat koji se prikazuje na satus baru
 * @author vedran
 *
 */
public class Clock implements Runnable {
		Thread t=null;  
		String dateStr = "";  
		JLabel clockLabel;
	
		  
		Clock (JLabel clockLabel){ 
			this.clockLabel = clockLabel;
		    t = new Thread(this);  
		    t.setDaemon(true);
		    t.start();  
		}  
		
		@Override
		public void run() {  
			try {  
				while(true) {  
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
		            long time = date.getTime();  
		            dateStr = formatter.format(time);  
		            clockLabel.setText(dateStr);
		            Thread.sleep(1000); 
		         }  
		      }  
		      catch (Exception e) { }  
		 }
		
				 
	}
