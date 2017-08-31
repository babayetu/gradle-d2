package com.gilt.demo;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.battle.ch2.BadSuspend;

import jline.TerminalSupport;
import jline.console.ConsoleReader;

public class Jline101 {
	private static Logger logger = Logger.getLogger(Jline101.class);
	
	public static void main(String[] args) throws IOException {
		ConsoleReader console = null;
		
		try {
			console = new ConsoleReader(System.in, System.out, new TerminalSupport(true) {});
			console.setPrompt("input>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String line = null;
		if (console == null) {
			logger.info("jline console initialize failed");
		}
		
		do
		{
		    try {
				line = console.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if(line != null)
		    {
		        //TODO
		    }
		}
		while(line!=null && !line.equals("exit"));

	}

}
