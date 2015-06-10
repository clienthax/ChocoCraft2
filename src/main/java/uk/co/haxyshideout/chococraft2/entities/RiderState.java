package uk.co.haxyshideout.chococraft2.entities;

/**
 * Created by clienthax on 8/6/2015.
 */
public class RiderState {

	private boolean jumping;
	private boolean sneaking;
	private boolean changed;

	public RiderState() {
		this.jumping = false;
		this.sneaking = false;
		this.changed = true;
	}

	public boolean hasChanged() {
		return changed;
	}

	public void resetChanged() {
		changed = false;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		if(jumping != this.jumping)
			changed = true;
		this.jumping = jumping;
	}

	public boolean isSneaking() {
		return sneaking;
	}

	public void setSneaking(boolean sneaking) {
		if(sneaking != this.sneaking)
			changed = true;
		this.sneaking = sneaking;
	}

	public void updateState(RiderState riderState) {
		setSneaking(riderState.isSneaking());
		setJumping(riderState.isJumping());
	}
}
