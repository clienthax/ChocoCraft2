# Tools

## Generate Textures

ImageMagick 6.7.5 is needed to use this tool.

This is currently only set up for adult Chocobos, Chicabos will need to be done manually for now, but I feel we don't need that at this time.

You can run generate texture like this
```$ ./generate_texture.sh```
An output directory will be created, all files in there can be copied into choco folder.

To add another Chocobo color, just create a new Chocobo in the Chocobo folder, when you run generate, it will be generate the appropriate textures for it.

To add another texture overlay, like with saddle bags, create the overlay in the Textures folder.  You can look there to see how it should be made.

To add another variation to a Chocobo, in this case male and female, just create the appropriate texture in the variations folder.

Note: All texture sizes must be the same in order for them to be overlayed properly.

