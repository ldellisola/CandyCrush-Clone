package game.backend.element;

public abstract class Fruit extends Element
{
	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean isDestroyable() {return false;}
}
