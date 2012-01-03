package com.karolmajta.stp.models;

import java.util.ArrayList;
import java.util.List;

public class MenuBallManager extends Tickable {
	
	private ArrayList<MainMenuItemBall> items;
	
	public MenuBallManager() {
		items = new ArrayList<MainMenuItemBall>();
	}
	
	@Override
	protected void onTick(long dt) {
		
	}

	public List<MainMenuItemBall> getItems() {
		return items;
	}

	public void add(MainMenuItemBall b) {
		items.add(b);
	}

	
	public MainMenuItemBall get(int i) {
		// THIS IS SO WRONG! BUT WILL DO FOR SMALL COLLECTIONS
		while(i < 0){
			i += items.size();
		}
		if(i >= items.size()) {
			i = i%items.size();
		}
		return items.get(i);
	}

	public void shiftRight() {
		ArrayList<MainMenuItemBall> shifted = new ArrayList<MainMenuItemBall>();
		shifted.add(items.get(items.size()-1));
		for(int i=0; i < items.size()-1; i++){
			shifted.add(items.get(i));
		}
		items = shifted;
	}

	public void shiftLeft() {
		ArrayList<MainMenuItemBall> shifted = new ArrayList<MainMenuItemBall>();
		for(int i=1; i < items.size(); i++){
			shifted.add(items.get(i));
		}
		shifted.add(items.get(0));
		items = shifted;
	}
}
