#!/usr/bin/env python

from gimpfu import *

def vectors_to_selection(image, vectors):
    pdb.gimp_selection_none(image)
    for vector in vectors:
        vector.to_selection(CHANNEL_OP_ADD)

def vectors_to_stroke(image, layer, vectors, color, width):
    pdb.gimp_context_set_foreground(color)
    for vector in vectors:
        pdb.gimp_edit_stroke_vectors(layer, vector)

def selected_vectors(image):
    vectors = [v for v in image.vectors if v.visible] 
    if vectors:
        return vectors
    if image.vectors:
        return [image.active_vectors]
    return []

def create_layer(image, name, opacity):
    layer = gimp.Layer(image, name, image.width, image.height, RGBA_IMAGE, opacity, NORMAL_MODE)
    image.add_layer(layer)
    return layer

def vertical_gradient(image, layer, y_from, y_to, color_from, color_to):
    pdb.gimp_rect_select(image, 0, y_from, image.width, y_to, CHANNEL_OP_REPLACE, False, 0)
    pdb.gimp_context_set_foreground(color_from)
    pdb.gimp_context_set_background(color_to)
    pdb.gimp_edit_blend(layer, FG_BG_RGB_MODE, NORMAL_MODE, GRADIENT_LINEAR, 100, 0, REPEAT_NONE, False, False, 0,0, True, 0, y_from + 5, 0, y_to - 5)
    pdb.gimp_selection_none(image)

def vectors_to_fill(image, layer, vectors, color):
    pdb.gimp_context_push()
    vectors_to_selection(image, vectors)
    pdb.gimp_context_set_foreground(color)
    pdb.gimp_edit_fill(layer, FOREGROUND_FILL)
    pdb.gimp_selection_none(image)
    pdb.gimp_context_pop()

def create_icons_from_vectors(image, drawable):
    ICON_HEIGHT = 48
    ICON_WIDTH = 48
    
    #collect visible vectors
    vectors = selected_vectors(image)
    
    pdb.gimp_context_push()
    #remove all old layers;
    for layer in image.layers:
        image.remove_layer(layer)
    
    #background
    layer =create_layer(image, "background", 100)
    vertical_gradient(image, layer, 0, ICON_HEIGHT + 10, "#717171", "#313131")
    layer.visible = False
    
    #drop shadow
    layer = create_layer(image, "drop shadow", 30)
    vectors_to_fill(image, layer, vectors, "#000000")
    pdb.plug_in_gauss_rle2(image, layer, 5, 5)
    
    #fill body
    layer = create_layer(image, "body", 100)
    vertical_gradient(image, layer, 0, ICON_HEIGHT, "#FFFFFF", "#979797")
    vertical_gradient(image, layer, ICON_HEIGHT, image.height, "#FFFFFF", "#C0C0C0")
    mask = layer.create_mask(ADD_BLACK_MASK)
    layer.add_mask(mask)
    vectors_to_fill(image, mask, vectors, "#FFFFFF")
    
    #highlight layer
    layer = create_layer(image, "highlights", 50)
    vectors_to_selection(image, vectors)
    for vector in selected_vectors(image):
        pdb.python_fu_vector_to_line_stroke(image, vector, layer, "#FFFFFF", 5, "butt", "miter", 10)
    
    pdb.gimp_selection_none(image)
    
    #icon stroke layer
    layer = create_layer(image, "icon stroke", 100)
    vertical_gradient(image, layer, 0, ICON_HEIGHT, "#464646", "#2D2D2D")
    vertical_gradient(image, layer, ICON_HEIGHT, image.height, "#464646", "#2D2D2D")
    mask = layer.create_mask(ADD_BLACK_MASK)
    layer.add_mask(mask)
    for vector in selected_vectors(image):
        pdb.python_fu_vector_to_line_stroke(image, vector, mask, "#FFFFFF", 2, "butt", "miter", 10)
    
    
    pdb.gimp_context_pop()


register(
    "python_fu_create_icons_from_vectors",    
    "Vector (paths) to nice icons, v0.1",   
    "This script will probably need small improvements for different environments (as in: different colors, sizes). It creates an icon sprite. The image should be 2*ICON_HEIGHT (96) high and a multiple of ICON_WIDTH wide. It will then render the paths with a dropshadow, a nice gradient for border, highlight and fill color.",
    "Reinoud Elhorst", 
    "Claude ICT, released under the same license as the GIMP versions the script works under", 
    "March 2013",
    "<Image>/Claude/Create Icons From Vectors", 
    "RGB*", #no idea whether it will work on other items
    [
    ], 
    [
    ],
    create_icons_from_vectors,
    )

main()
