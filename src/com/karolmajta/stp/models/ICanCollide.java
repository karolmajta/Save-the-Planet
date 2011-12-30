package com.karolmajta.stp.models;

/**
 * Interface for an object that can collide with other object
 * 
 * @author Karol
 *
 * @param <With> Concrete class of an object with whom this ICanCollide
 * 		collides
 */
public interface ICanCollide<With> {
	/**
	 * This method is called to indicate that a collision between this and
	 * other occurred. To be exact it indicates that other collided with
	 * this. Given sentence means that implementation of collide should only
	 * affect the state of this, not other. This way this can be collided with
	 * any object (even one that does not implement ICanCollide).
	 * A call to collide should modify the state of this basing on the state
	 * of this and other.
	 * 
	 * Usually the usage of this method will look something like this.
	 * 
	 * <code>
	 * FirstCanCollide a;
	 * SecondCanCollide b;
	 * a.collide(b) // affects only state of a basing on a and b
	 * b.collide(a) // affects only state of b basing on a and b
	 * </code>
	 * 
	 * @param other object that collides with this.
	 */
	public void collide(With other);
	
	/**
	 * Method for checking basing on state of this and other whether current
	 * state of this and other will affect this.
	 * This method should be called before collide and if true is returned it is
	 * safe to call collide. If false is returned calls to collide can give
	 * unexpected (undefined) results.
	 * 
	 * @param other object that collides with this
	 * @return indication if collision occurs and if it's safe to call collide
	 */
	public boolean willAffect(With other);
}
