package game.backend.element;

import game.backend.move.Direction;

import java.util.Objects;

public abstract class Element {
	
	public abstract boolean isMovable();
	
	public abstract String getKey();
	
	public String getFullKey() {
		return getKey();
	}

	public boolean isSolid() {
		return true;
	}
	
	public Direction[] explode() {
		return null;
	}
	
	public long getScore() {
		return 0;
	}

	public boolean isDestoyable() {return true;}

	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return  true;
		if(!(obj instanceof Element))
			return  false;

		Element el = (Element) obj;

		return this.getFullKey().equals(el.getFullKey());
	}
}
