################################################################################
#$                                                                            $#
#$                                May 29th, 2015                              $#
#$                              Alexander Massin 10a                          $#
#$                               Turtle Fractal                               $#
#$                                                                            $#
################################################################################
import turtle
print("What shape do you want? Choose from star, smooth, starfish.")
shape = input()
#There are two fractals to chose from


print("Do you want blue to white, red to white, or green to white?")
colorOrder = input()
#This allows the user to choose from the color patterns available


wn = turtle.Screen()
#Sets the variable wn to being the turtle screen


wn.colormode(255)
#This allows me to use RGB to change the color of the fractal


wn.bgcolor("black")
#Sets wn's background color to black


alex = turtle.Turtle()
#Allows me to set the variable 'alex' to be a turtle that I can command

alex.speed(11)
#Sets the speed of alex to 11 which is the highest it can go

if colorOrder == 'blue to white':
        colors = [(255, 255, 255), (230, 230, 255), (204, 204, 255), (178, 178, 255), (153, 153, 255), (128, 128, 255), (102, 102, 255), (77, 77, 255), (51, 51, 255), (25, 25, 255),(0, 0, 255)]


if colorOrder =='red to white':
        colors = [(255, 255, 255), (255, 230, 230), (255, 204, 204), (255, 178, 178),(255, 153, 153), (255, 128, 128), (255, 102, 102), (255, 77, 77), (255, 51, 51), (255, 25, 25), (255, 0, 0)]

        
if colorOrder =='green to white':
        colors = [(255, 255, 255), (230, 255, 230), (204, 255, 204), (178, 255, 178), (153, 255, 153), (128, 255, 128), (102, 255, 102), (77, 255, 22), (51, 255, 51), (25, 255, 25), (0, 255, 0)]

#These are three lists that allow the user to choose how they want the turtle to look like in the program. 
#This works with the colormode set before the lists

if shape =='star':
        alex.goto(-450,100)


if shape =='smooth':
        alex.goto(-450,330)

#Since the shapes are different, we need to let them start in co-ordinates that allow them to completely take up the screen
for star in range(90,0 ,-1):
        if shape =='star':
                index = int(star/10)             #This is taking the integer of 'star' divided by ten
                color = colors[index]            #Now this makes a variable color, that takes the list of colors above and uses the variable index to go through the list
                alex.color(color)
                alex.forward(star *10)           #Allows the forward number of alex to increase by star*10 each time
                alex.right(144)
        if shape=='starfish':
                index = int(star/10)
                color = colors[index]
                alex.color(color)
                alex.forward(star *10)
                alex.right(166)
#I disabled this shape because it was not considered a fractal
        if shape=='smooth':
                index = int(star/10)
                color = colors[index]
                alex.color(color)
                alex.forward(star *10)
                alex.right(121)
                
wn.exitonclick()
