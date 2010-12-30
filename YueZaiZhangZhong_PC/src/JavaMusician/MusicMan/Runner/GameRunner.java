package JavaMusician.MusicMan.Runner;

import JavaMusician.MusicMan.Controller.Controller;
public class GameRunner {
	public static void main(String[] args){
		new Thread(new Controller()).start();
	}
}
