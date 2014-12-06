package se.powerslidestudios.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class TypedContactListener<T1, T2>{
	
	private Class<T1> class1;
	private Class<T2> class2;

	public TypedContactListener(Class<T1> class1, Class<T2> class2) {
		this.class1 = class1;
		this.class2 = class2;
	}
	
	protected abstract void onCollision(T1 object1, T2 object2);

	public void handleCollision(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		Object userDataA = fixtureA.getBody().getUserData();
		Object userDataB = fixtureB.getBody().getUserData();
	
		if(userDataA == null || userDataB == null)
			return;
		
		if(userDataA.getClass() == class1 && userDataB.getClass() == class2){
			onCollision((T1)userDataA, (T2)userDataB);
			return;
		}else if(userDataB.getClass() == class1 && userDataA.getClass() == class2){
			onCollision((T1)userDataB, (T2)userDataA);
			return;
		}
	}
}
