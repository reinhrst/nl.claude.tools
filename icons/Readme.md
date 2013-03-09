I'm a programmer. Unfortunatley, my girlfriend is not a graphic designer, and whenever I'm making anything, I have to do most of the designing myself. One aspect of desiging (although I would argue only a small one), is the creation of icons.

Some of the designers I worked with in the past could draw an icon pixel by pixel. They would zoom in to the max, hug each pixel individually, and as soon as they zoom out somehow a nice icon appears. Not me! I actually prefer to draw lines, use whole indexes for points, if the left of an icon is the mirror image of the right, I want it to be exactly that. And, I like the icons to be in some text format. For instance a button, I would prefer to make it 100% by css, if that doesn't work, create it in SVG. And only if that fails go for something PNG-y.

One last note: I create my icons (preferably) in SVG. That doesn't mean that it's a good idea to have the browser load them as SVG. SVG is not supported as background image on all browsers, and may render slow or different on different browsers. So it might be a good idea to (for instance) put the SVG files in your source code, and then at deploytime use something like Imagemagick to render them to PNG.

Unless files in this subdirectory contain a seperate license, they are licensed through the [Creative Commons Attribution-ShareAlike 3.0 Unported License][1], which basically means: have fun with it, but share whatever you do, and somewhere mention that you got the icon from Reinoud Elhorst (https://github.com/reinhrst). Note: I do not claim to have the rights to the images themselves, it's up to everone individually to check that the images themselves (as opposed to the code here that renders them) may be used.



[1]: http://creativecommons.org/licenses/by-sa/3.0/deed.en_US
