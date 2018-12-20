package com.example.youtube;

public class App {

	public static void main(String[] args) {
		Thread t = new Thread(new Frame());
		t.start();
	}

}
