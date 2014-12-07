package se.powerslidestudios.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class AnyTypeContactListener<T1> extends TypedContactListener<T1, AnyClass>{

	public AnyTypeContactListener(Class<T1> type) {
		super(type, AnyClass.class);
		
	}

	@Override
	public void handleCollision(Contact contact) {
		super.handleCollision(contact);

		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		Object userDataA = fixtureA.getBody().getUserData();
		Object userDataB = fixtureB.getBody().getUserData();
	
		if(userDataA == null && userDataB == null)
			return;
		
		Object typedObject = null;
		
		if(userDataA != null && userDataA.getClass() == class1)
			typedObject = userDataA;
		else if(userDataB != null && userDataB.getClass() == class1)
			typedObject = userDataB;	
		
		if(typedObject != null)
			onCollision((T1)typedObject, null);
				
	}
}
