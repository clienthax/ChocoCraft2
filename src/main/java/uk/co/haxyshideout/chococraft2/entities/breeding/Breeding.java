package uk.co.haxyshideout.chococraft2.entities.breeding;

import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;

import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.*;

public class Breeding {

	public static ChocoboColor getColour(EntityChocobo firstParent, EntityChocobo secondParent) {
		boolean bothParentsFedGold = firstParent.fedGoldenGyshal && secondParent.fedGoldenGyshal;
		int randColour = RandomHelper.getRandomInt(100);
		ChocoboColor childColour = YELLOW;

		//Forgive me for this ugly mess of code :D
		switch (firstParent.getChocoboColor()) {

			case BLACK:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
					case GREEN:
					case BLUE:
						if(randColour < 50) {
							childColour = secondParent.getChocoboColor();
						}
						break;
					case WHITE:
						if(bothParentsFedGold) {
							if(randColour > 50) {
								childColour = GOLD;
							} else if(randColour > 25) {
								childColour = WHITE;
							}
						} else {
							if(randColour > 75) {
								childColour = YELLOW;
							} else if(randColour > 38) {
								childColour = WHITE;
							}
						}
						break;
					case GOLD:
					case PINK:
					case RED:
						if(bothParentsFedGold && randColour > 90) {
							childColour = childColour.GOLD;
						}
						break;
				}
				break;

			case BLUE:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
						if(randColour < 50) {
							childColour = YELLOW;
						}
						break;
					case BLUE:
						if(bothParentsFedGold) {
							if(randColour > 60) {
								childColour = WHITE;
							} else if(randColour > 30) {
								childColour = BLUE;
							}
						} else {
							if(randColour > 80) {
								childColour = WHITE;
							} else if(randColour > 40) {
								childColour = BLUE;
							}
						}
						break;
					case BLACK:
					case WHITE:
						if(bothParentsFedGold) {
							if(randColour > 70) {
								childColour = YELLOW;
							} else if(randColour > 35) {
								childColour = secondParent.getChocoboColor();
							}
						} else {
							if(randColour > 90) {
								childColour = YELLOW;
							} else if(randColour > 45) {
								childColour = secondParent.getChocoboColor();
							}
						}
						break;
				}
				break;

			case GOLD:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
					case GREEN:
					case BLUE:
					case WHITE:
					case BLACK:
					case PURPLE:
						childColour = secondParent.getChocoboColor();
						break;
					case GOLD:
					case PINK:
					case RED:
						if(!bothParentsFedGold || randColour > 20) {
							childColour = YELLOW;
						}
						break;
				}
				break;

			case GREEN:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
						if(randColour < 50) {
							childColour = YELLOW;
						}
						break;
					case BLUE:
						if(bothParentsFedGold) {
							if(randColour > 60) {
								childColour = WHITE;
							} else if(randColour > 30) {
								childColour = BLUE;
							}
						} else {
							if(randColour > 80) {
								childColour = WHITE;
							} else if(randColour > 40) {
								childColour = BLUE;
							}
						}
						break;
					case BLACK:
					case WHITE:
						if(bothParentsFedGold) {
							if(randColour > 70) {
								childColour = YELLOW;
							} else if(randColour > 35) {
								childColour = secondParent.getChocoboColor();
							}
						} else {
							if(randColour > 90) {
								childColour = YELLOW;
							} else if(randColour > 45) {
								childColour = secondParent.getChocoboColor();
							}
						}
						break;
				}
				break;

			case PINK:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
					case GREEN:
					case BLUE:
					case WHITE:
					case BLACK:
					case PURPLE:
						childColour = secondParent.getChocoboColor();
						break;
					case GOLD:
					case PINK:
					case RED:
						if(!bothParentsFedGold || randColour > 20) {
							childColour = YELLOW;
						}
						break;
				}
				break;

			case PURPLE:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
					case GREEN:
					case BLUE:
					case WHITE:
					case BLACK:
						childColour = secondParent.getChocoboColor();
						break;
				}
				break;

			case RED:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
					case GREEN:
					case BLUE:
					case WHITE:
					case BLACK:
					case PURPLE:
						childColour = secondParent.getChocoboColor();
						break;
					case GOLD:
					case PINK:
					case RED:
						if(!bothParentsFedGold || randColour > 20) {
							childColour = YELLOW;
						}
						break;
				}
				break;

			case WHITE:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
						if(bothParentsFedGold) {
							if(randColour > 60) {
								childColour = BLACK;
							} else if(randColour > 30) {
								childColour = YELLOW;
							}
						} else {
							if(randColour > 80) {
								childColour = BLACK;
							} else if(randColour > 40) {
								childColour = YELLOW;
							}
						}
						break;
					case GREEN:
					case BLUE:
						if(bothParentsFedGold) {
							if(randColour > 70) {
								childColour = YELLOW;
							} else if(randColour > 35) {
								childColour = secondParent.getChocoboColor();
							}
						} else {
							if(randColour > 90) {
								childColour = YELLOW;
							} else if(randColour > 45) {
								childColour = secondParent.getChocoboColor();
							}
						}
						break;
					case BLACK:
						if(bothParentsFedGold) {
							if(randColour > 50) {
								childColour = GOLD;
							} else if(randColour > 25) {
								childColour = BLACK;
							}
						} else {
							if(randColour > 75) {
								childColour = YELLOW;
							} else if(randColour > 38) {
								childColour = BLACK;
							}
						}
						break;
					case GOLD:
					case PINK:
					case RED:
						if(bothParentsFedGold && randColour > 70) {
							childColour = GOLD;
						}
				}
				break;

			case YELLOW:
				switch (secondParent.getChocoboColor()) {
					case YELLOW:
						if(bothParentsFedGold) {
							if(randColour > 80) {
								childColour = BLUE;
							} else if(randColour > 60) {
								childColour = GREEN;
							}
						} else {
							if(randColour > 60) {
								childColour = BLUE;
							} else if(randColour > 20) {
								childColour = GREEN;
							}
						}
						break;
					case GREEN:
					case BLUE:
					case BLACK:
						if(randColour > 50) {
							childColour = secondParent.getChocoboColor();
						}
						break;
					case WHITE:
						if(bothParentsFedGold) {
							if(randColour > 60) {
								childColour = BLACK;
							} else if(randColour > 30) {
								childColour = WHITE;
							}
						} else {
							if(randColour > 80) {
								childColour = BLACK;
							} else if(randColour > 40) {
								childColour = WHITE;
							}
						}
						break;
				}
				break;
		}

		return childColour;
	}

}
