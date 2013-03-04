#!/usr/bin/env python

from gimpfu import *



def vector_to_line_stroke(image, vector, layer, color="#000000", width=1, capstyle="butt", joinstyle="miter", miterlimit=10):
    import re, tempfile
    newelements = {
            'stroke': color,
            'stroke-width': width,
            'stroke-linecap': capstyle,
            'stroke-linejoin': joinstyle,
            'stroke-miterlimit': miterlimit,
            }
    svg = pdb.gimp_vectors_export_to_string(image, vector)
    #fix width and height to be resolution (px/inch)-independent
    svg = re.sub(r'(<svg\s[^>]*\swidth\s*=\s*)\S*"', r'\1"%dpx"' % image.width, svg, flags=re.DOTALL)
    svg = re.sub(r'(<svg\s[^>]*\sheight\s*=\s*)\S*"', r'\1"%dpx"' % image.height, svg, flags=re.DOTALL)
    svg = re.sub(r'(<path\s[^>]*)\sstroke\s*=\s*"black"', r'\1', svg, flags=re.DOTALL)
    svg = re.sub(r'(<path\s[^>]*)\sstroke-width\s*=\s*"1"', r'\1', svg, flags=re.DOTALL)
    svg = re.sub(r'(<path\s)', r'\1' + ''.join([r'%s="%s" ' % i for i in newelements.items()]), svg, flags=re.DOTALL)
    tmpfile = tempfile.NamedTemporaryFile(suffix=".svg")
    tmpfile.write(svg)
    tmpfile.flush();
    newlayer = pdb.gimp_file_load_layer(image, tmpfile.name)
    tmpfile.close()
    image.add_layer(newlayer) #needs to be added to the image to be able to copy from
    copyname = pdb.gimp_edit_named_copy(newlayer, "stroke")
    image.remove_layer(newlayer)
    floating_sel = pdb.gimp_edit_named_paste(layer, copyname, True)
    pdb.gimp_floating_sel_anchor(floating_sel)

register(
    "python_fu_vector_to_line_stroke",    
    "Vector (path) to a line stroke, v0.2",   
    "There is a ui option to go from a vector (path) to a line stroke, but this is not exposed through script-fu. This function provides a work-around for doing that (through saving the path as an SVG and then importing that). Probably because of the tempfile function, it will not work under Windows (but who runs GIMP under Windows anyways right (i.e. if you run GIMP, go to UNIX), developed on OSX, against GIMP 2.8",
    "Reinoud Elhorst", 
    "Claude ICT, released under the same license as the GIMP versions the script works under", 
    "March 2013",
    "<Vectors>/MyScripts/Vector To line Stroke", 
    "RGB*", #no idea whether it will work on other items
    [
      (PF_IMAGE, 'image', 'Image', None),
      (PF_VECTORS, 'vector', 'Vector (path)', None),
      (PF_DRAWABLE, 'layer', 'The target layer for the stroke', None),
      (PF_STRING, 'color', 'Color as string in some way that is SVG comaptible (e.g. "black", or "#FF0000")', "#000000"),
      (PF_INT, 'width', 'Line width', 1),
      (PF_STRING, 'capstyle', 'Some valid SVG cap style (i.e. "butt", "square", or "round")', "butt"),
      (PF_STRING, 'joinstyle', 'Some valid SVG join style (i.e. "miter", "round" or "bevel")', "miter"),
      (PF_INT, 'miterlimit', '''The miterlimit (google thisif you don't understand it)''', 10)
    ], 
    [
    ],
    vector_to_line_stroke,
    )

main()

#usage: Create an image, with at least one vector (one path), and then run:
#pdb.python_fu_vector_to_line_stroke(gimp.image_list()[0], gimp.image_list()[0].vectors[0], gimp.image_list()[0].layers[0], "#000000", 10, "butt", "miter", 10)
