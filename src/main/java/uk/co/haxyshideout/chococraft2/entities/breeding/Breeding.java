package uk.co.haxyshideout.chococraft2.entities.breeding;

import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;

import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.*;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Breeding {

	public static ChocoboColor getColour(EntityChocobo firstParent, EntityChocobo secondParent) {
		int randColour = RandomHelper.getRandomInt(100);
		boolean bothParentsFedGold = firstParent.fedGoldenGyshal && secondParent.fedGoldenGyshal;
		HashMap<String, List<HashMap<String, String>>> secondParentColourMaps = ChocoCraft2.instance.getConfig().getBreedingInfoHashmap().get(firstParent.getChocoboColor().name());
		for(HashMap.Entry<String, List<HashMap<String, String>>> secondParentColourEntry : secondParentColourMaps.entrySet()) {
			if(secondParentColourEntry.getKey().contains(secondParent.getChocoboColor().name())) {
				List<HashMap<String, String>> breedingInfoList = secondParentColourEntry.getValue();
				boolean flag = false;
				for(HashMap<String, String> breedingInfo : breedingInfoList) {
					String childColour = breedingInfo.get("childColour");
					String conditions = breedingInfo.get("conditions");
					String random = breedingInfo.get("random");
					if(!conditions.equals("none")) {
						if(conditions.equals("bothParentsFedGold")) {
							flag = true;
							if(!bothParentsFedGold) {
								continue;
							}
						}
					} else {
						if(flag && bothParentsFedGold) {
							continue;
						}
					}
					if(!random.equals("none")) {
						String[] parts = random.split(Pattern.quote(" "));
						if(parts[0].equals("above")) {
							if(!(randColour > Integer.parseInt(parts[1])))
								continue;
						} else if(parts[0].equals("under")) {
							if(!(randColour < Integer.parseInt(parts[1])))
								continue;
						}
					}
					if(childColour.equals("secondParent")) {
						return secondParent.getChocoboColor();
					} else {
						return EntityChocobo.ChocoboColor.valueOf(childColour);
					}

				}
			}
		}
		return YELLOW;
	}

}
