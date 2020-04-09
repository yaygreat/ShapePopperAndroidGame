# ShapePopperAndroidGame

In this game, the player has to pop shapes before they disappear from the screen.  
The program also keeps track of the top 12 high scores.  
It operates in three modes:  
First, you play the shape popping game.  
Second, you can add a new score through the Add screen.  
Third, you can view up to 12 high scores in the Main screen.  

The score is the reaction time that it takes the player to touch each shape  
so lower scores are better.  
Whenever a shape is touched, whether it's correct or not, the shape will disappear  
from the screen and a new one will take its place.  
If the player fails to touch a correct shape before it disappears from the screen,  
it's lifespan time is added to the score.  
The game ends when the player has touched ten correct shapes.  

The scores are firstly listed from low to high scores and  
secondly listed from most recent time to least recent time.  

Up to 12 scores can be added to the list.  
If a new score to be added is less than the last score in the list,  
it adds it to the list and deletes the last score to maintain the list length at 12.  
If a new score to be added is the same as last score in the list,  
it adds the new score only if its time is more recent than the last score  
and deletes the last score to maintain the list length at 12.  

This project will create a file called Scoresfile.txt in your directory.  
e.g.: /data/user/0/com.example.highscoregame/files/Scoresfile.txt  
This is where your 12 high scores will be saved using tab delimeters:  
Name \t Score \t DateTime  
