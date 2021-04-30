"""
For every pixel in the map,
. expand square from position
. have a priority queue where the most covered map is first

"""

#from PIL import Image,ImageDraw
import numpy as np

def run():
    strworld = [
        "               ",
        "          ###  ",
        "  ######  ###  ",
        "  ###########  ",
        "  #####   ##   ",
        "          ##   ",
        "               "
    ]


    uncovered = 
    
    world = np.array([[0 for x in strworld[0]] for x in strworld])
    for y,line in enumerate(strworld):
        for x,char in enumerate(line):
            if char == "#": 
                world[y][x] = 1

    print(world)


run()