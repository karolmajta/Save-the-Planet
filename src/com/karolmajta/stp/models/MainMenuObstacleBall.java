package com.karolmajta.stp.models;

import java.util.HashSet;

import android.util.Log;
import junit.framework.TestCase;

/**
 * 
 * @author Karol
 *
 */
public class MainMenuObstacleBall extends Tickable {
	
	private final class ItemBallCollider implements ICanCollide<MainMenuItemBall> {
		private HashSet ignoredForCollision;
		
		public ItemBallCollider() {
			ignoredForCollision = new HashSet();
		}
		
		/**
		 * Lol! Need to think about this
		 * 
		 * @param other {@link MainMenuItemBall} instance to collide with this
		 */
		@Override
		public void collide(MainMenuItemBall other) {
			float rx = x - other.getCurrentX();
			float ry = y - other.getCurrentY();
			float r = (float)Math.sqrt(rx*rx + ry*ry);
			float v1 = (float)Math.sqrt(Math.pow(other.getCurrentVX(), 2)+Math.pow(other.getCurrentVY(), 2));
			float v2 = (float)Math.sqrt(vx*vx+vy*vy);
			float dvx = (2/r)*rx*(other.getMass()/(mass+other.getMass()))*(v1+v2);
			float dvy = (2/r)*ry*(other.getMass()/(mass+other.getMass()))*(v1+v2);
			
			vx += dvx;
			vy += dvy;
		}

		/**
		 * Checks collision condition basing on overlapping circles
		 */
		@Override
		public boolean affectedBy(MainMenuItemBall other) {
			float x0 = x;
			float y0 = y;
			float r0 = radius;
			
			float x1 = other.getCurrentX();
			float y1 = other.getCurrentY();
			float r1 = other.getRadius();
			
			boolean result;
			float dist =(float)Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
			if(dist < r0 + r1){
				result = true;
			}else{
				result = false;
			}
			
			if(result){
				if(ignoredForCollision.contains(other)){
					return false;
				}else{
					ignoredForCollision.add(other);
					return true;
				}
			}else{
				ignoredForCollision.remove(other);
				return false;
			}
		}
	}
	
	private final class ObstacleBallCollider implements ICanCollide<MainMenuObstacleBall> {
		private HashSet ignoredForCollision;
		
		public ObstacleBallCollider() {
			ignoredForCollision = new HashSet();
		}
		
		/**
		 * Lol! Need to think about this
		 * 
		 * @param other {@link MainMenuItemBall} instance to collide with this
		 */
		@Override
		public void collide(MainMenuObstacleBall other) {
			// TODO
		}

		/**
		 * Checks collision condition basing on overlapping circles
		 */
		@Override
		public boolean affectedBy(MainMenuObstacleBall other) {
			float x0 = x;
			float y0 = y;
			float r0 = radius;
			
			float x1 = other.getX();
			float y1 = other.getY();
			float r1 = other.getRadius();
			
			boolean result;
			float dist =(float)Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
			if(dist < r0 + r1){
				result = true;
			}else{
				result = false;
			}
			
			if(result){
				if(ignoredForCollision.contains(other)){
					return false;
				}else{
					ignoredForCollision.add(other);
					return true;
				}
			}else{
				ignoredForCollision.remove(other);
				return false;
			}
		}
	}
	
	private ItemBallCollider itemCollider = new ItemBallCollider();
	private ObstacleBallCollider obstacleCollider = new ObstacleBallCollider();
	
	private float impulseX;
	private float impulseY;
	
	private float x; // pixels
	private float y; // pixels
	private float radius; // pixels
	private float vx; // pixels/millisecond
	private float vy; // pixels/millisecond
	
	private float mass;
	
	public MainMenuObstacleBall(float x, float y, float r, float vx, float vy, float mass) {
		
		this.x = x;
		this.y = y;
		this.radius = r;
		this.vx = vx;
		this.vy = vy;
		this.mass = mass;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}



	public float getX() {
		return x;
	}



	public void setX(float x) {
		this.x = x;
	}



	public float getY() {
		return y;
	}



	public void setY(float y) {
		this.y = y;
	}



	public float getVX() {
		return vx;
	}



	public void setVX(float vx) {
		this.vx = vx;
	}



	public float getVY() {
		return vy;
	}



	public void setVY(float vy) {
		this.vy = vy;
	}


	/**
	 * Integrates position based on current speed. Speed does not change
	 */
	@Override
	protected void onTick(long dt) {
		x += vx*dt;
		y += vy*dt;
	}

	public boolean affectedBy(MainMenuItemBall b) {
		return itemCollider.affectedBy(b);
	}
	
	public void collide(MainMenuItemBall b) {
		itemCollider.collide(b);
	}
	
	public boolean affectedBy(MainMenuObstacleBall b) {
		return obstacleCollider.affectedBy(b);
	}
	
	public void collide(MainMenuObstacleBall b) {
		obstacleCollider.collide(b);
	}

	public float getMass() {
		return mass;
	}
}