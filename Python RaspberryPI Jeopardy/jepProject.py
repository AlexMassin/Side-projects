#gamesPlayed is the first line in the file
#number of players is the second line in the file
#dash indicates that the player was not present    
from tkinter import *   #import the library for the GUI 


#the player class has information about each player and handles assigning points to players
class player:
    def __init__(self,name,buzzer,score,pastScores):
        self.name = name
        self.buzzer = buzzer
        self.score = score
        self.pastScores = pastScores
    def defineBuzzer(self,buzzerNum):
        self.buzzer = buzzerNum
    def givePastScore(self,score, index):
        self.pastScores[index] = score
    def printPastScores(self):
        print(self.pastScores)
    def getScore(self):
        return self.score
    def getName(self):
        return self.name
    def addPoints(self,points):
        self.score+= points
gamesPlayed = 0   
#closes the "Done" button when quizmaster has put in all the names of the players (also destroys the prompt label)
scoreVars = [0]*8
scoreLabels = [0]*8
#Display label based on gamemode being played
#---------------------------------------------------------------------------
def PointSpecial():
    gameTypeLabelText.set("The next question is worth 20 points")
    gameTypeLabel.config(text=gameTypeLabelText.get())
    gameTypeLabel.place(x=300,y=600)
    playerList[1].addPoints(3)
    initializePlayers()
    
def StandardQuestion():
    gameTypeLabelText.set("The next question is a standard question")
    gameTypeLabel.config(text=gameTypeLabelText.get())
    gameTypeLabel.place(x=300,y=600)
    
def WhoOrWhatAmI():
    gameTypeLabelText.set("The next question is a Who/What am I question")
    gameTypeLabel.config(text=gameTypeLabelText.get())
    gameTypeLabel.place(x=300,y=600)
def Shootout():
    gameTypeLabelText.set("The next question is a shootout question")
    gameTypeLabel.config(text=gameTypeLabelText.get())
    gameTypeLabel.place(x=300,y=600)
#---------------------------------------------------------------------------------    
     

    
#closes the "Done" button when quizmaster has put in all the names of the players (also destroys the prompt label)
 

def makeInterface():
    button_done.destroy()
    promptLabel.destroy()
    button_20point= Button(GUI,text= "20 point special",command=PointSpecial)
    button_20point.place(x=200,y=500)
    button_Standard= Button(GUI,text= "Standard Question",command=StandardQuestion)
    button_Standard.place(x=300,y=500)
    button_whoAmI= Button(GUI,text = "Who/What Am I",command=WhoOrWhatAmI)
    button_whoAmI.place(x=415,y=500)
    button_shootout=Button(GUI,text = "Shootout",command=Shootout)
    button_shootout.place(x=515,y=500)
    initializePlayers()
    submitButton.place(x=400,y=300)

    
#INITIALIZEPLAYERS INITIALIZES PLAYERS AND UPDATES SCORES OF PLAYERS
#-------------------------------------------------------------------------------
def initializePlayers(): 
    
    x=75
    y=475
    #new array of stringvars
    for i in range(0,8):
        scoreVars[i] = StringVar(GUI)
        for x in range(0,8):
            try:
                if str(names[i].get()) == str(playerList[x].getName()):
                    scoreVars[i].set(str(playerList[x].getScore()))
                    break
            except IndexError:
                    scoreVars[i].set("Player does not exist")
        
    for i in range(0,len(scoreLabels)):
        scoreLabels[i] = Label(GUI, text=scoreVars[i].get())
    for i in range(0,len(scoreLabels)):
       scoreLabels[i].place(x=x,y=y)
       y-=150
       if i == 3:
        x+= 600
        y=475
    teamOnePlayers = [0]*4
    teamOneTotalLabel = Label(GUI)
    teamOneTotalVar = IntVar(GUI)
    teamOneTotalInt = 0
    teamOneTotalVar.set(0)
    
    #The for loop below makes two teams
    for i in range(0,len(teamOnePlayers)):
        for x in range(0,len(playerList)):
            if str(names[i].get()) ==  str((playerList[x].getName())):
                teamOnePlayers[i] = playerList[x]
                teamOneTotalVar.set(teamOneTotalInt+ teamOnePlayers[i].getScore())
                teamOneTotalInt = teamOneTotalVar.get()
    teamOneTotalLabel.config(text=teamOneTotalVar.get())
    teamOneTotalLabel.place(x=300,y=550)
    

    teamTwoPlayers = [0]*4
    teamTwoTotalLabel = Label(GUI)
    teamTwoTotalVar = IntVar(GUI)
    teamTwoTotalInt=0
    teamTwoTotalVar.set(0)
    
    #The for loop below makes two teams
    for i in range(4,8):
        for x in range(0,len(playerList)):
            if names[i].get() ==  (playerList[x].getName()):
                teamTwoPlayers[i-4] = playerList[x]
                teamTwoTotalVar.set(teamTwoTotalInt+ teamTwoPlayers[i-4].getScore())
                teamTwoTotalInt= teamTwoTotalVar.get()
    teamTwoTotalLabel.config(text=teamTwoTotalVar.get())
    teamTwoTotalLabel.place(x=515,y=550)
    
#--------------------------------------------------------------------------------                            
            
        
    
    
   

    
    
#EXITING THE GAME     
def exitGame(gamesPlayed):
    with open("sampleReachScores2.txt",'w') as reachFile:
        gamesPlayed += 1
        print(str(gamesPlayed),file = reachFile)
        print(str(numPlayers),file = reachFile)
        for i in range(0,len(namesList)):
            print(str(namesList[i]),file = reachFile)
            for x in range(0,gamesPlayed-1):
                print((playerScores[i][x]),file = reachFile)
            print(playerList[i].getScore(), file=reachFile)
                
                
    exit()
#UPDATING THE SCORE LABELS (NOT WORKING)
def updateScoreLabels():
    for i in range(0,len(names)):
        for x in range(0,len(playerList)):
            if names[i].get() == playerList[x].getName():
                print(type(scoreVars[i]))
                scoreVars[i] = playerList[x].getScore()
                print(type(scoreLabels[i]))
                scoreLabels[i].config(text = scoreVars[i])

#-------------------------------------------------------------------------------
#FILE READING BELOW
with open("sampleReachScores2.txt",'r+') as scoresFile:
    gamesPlayed=int(scoresFile.readline())
    numPlayers=int(scoresFile.readline())
    playerScores = [[0 for x in range(gamesPlayed)] for y in range(numPlayers)]
    namesList = [0]*numPlayers

    for i in range(0,len(namesList)):
        namesList[i]= scoresFile.readline().rstrip()
        for score in range (0, gamesPlayed):
            playerScores[i][score] = scoresFile.readline().rstrip()

#-------------------------------------------------------------------------------
#MAKING PLAYERS AND ASSIGNING VALUES TO PARAMETERS
playerList= [0]*numPlayers #list of players
scoreToPass = 0
for index in range(0,len(playerList)):
    playerList[index] = player(namesList[index],0,3,[0]*gamesPlayed)
    for y in range(0,gamesPlayed):
        scoreToPass = playerScores[index][y]
        playerList[index].givePastScore(scoreToPass, y) 

#---------------------------------------------------------------------------    

#INTERFACE DESIGN 
GUI = Tk()
GUI.title("Scoreboard")
GUI.geometry("900x650")
names = [""]*8
optionMenus = [0]*8
menu = Menu(GUI)
GUI.config(menu=menu)
subMenu = Menu(menu)
menu.add_cascade(label="Options", menu=subMenu)
subMenu.add_command(label="Exit", command= lambda: exitGame(gamesPlayed))
#--------------------------------------------------------------------------------
for button in range(0,len(optionMenus)):
    names[button] = StringVar(GUI)
    names[button].set("Buzzer" +" "+ str(button))
    optionMenus[button] = OptionMenu(GUI, names[button], *namesList) 
    

#--------------------------------------------------------------------------------
#DISPLAYING LABELS AND BUTTONS
#initialize the x and y axes
x=75
y=500
#place each button in the appropriate spot (increase the y-value by 150 so each button on the first column goes down 150 units)
for button in range(0,len(optionMenus)):
    optionMenus[button].place(x=x,y=y)
    y-=150
    #after the first 4 buttons, move the horizontal axis over and reset the cycle (set y=0)
    if button==3:
        x+=600
        y=500






button_done= Button(GUI,text= "Done",command=makeInterface)
button_done.place(x=400,y=500)

gameTypeLabelText = StringVar(GUI)
gameTypeLabel = Label(GUI)
        
        
        
    



#------------------------------------------------------------------------------
promptLabel = Label(GUI, text = "Please put the names of the starting lineup in the boxes, hit the button below when you're finished")
promptLabel.place(x=200,y=20)
promptLabel2 = Label(GUI, text = "Enter y or n in the box below")
promptLabel2.place(x=350,y=175)
yesNoBox = Entry(GUI)
yesNoBox.place(x=375,y=200)
submitButton = Button(GUI,text="Submit")

GUI.mainloop()



