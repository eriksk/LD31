package se.powerslidestudios.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class TypedContactListenerCollection implements ContactListener{

	List<TypedContactListener> listeners;
	
	public TypedContactListenerCollection() {
		listeners = new ArrayList<TypedContactListener>();
	}
	
	public void add(TypedContactListener listener){
		listeners.add(listener);
	}
	
	@Override
	public void beginContact(Contact contact) {
		for (TypedContactListener typedContactListener : listeners) {
			typedContactListener.handleCollision(contact);
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {	
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
