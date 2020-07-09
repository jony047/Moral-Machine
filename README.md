# Moral-Machine
The idea of Moral Machines is based on the Trolley Dilemma, a fictional scenario presenting a decision 
maker with a moral dilemma: choosing "the lesser of two evils". The scenario entails an autonomous car
whose brakes fail at a pedestrian crossing. As it is too late to relinquish control to the car’s passengers,
the car needs to make a decision based on the facts available about the situation. Figure 1 shows an
example scenario. In this project, you will create an Ethical Engine, a program designed to explore
different scenarios, build an algorithm to decide between the life of the car’s passengers vs. the life of the
pedestrians, audit your decision-making algorithm through simulations, and allow users of your program
to judge the outcomes themselves.

Program consists of below seven core classes: 
ethicalengine/
|-- Character.java
|-- Person.java
|-- Animal.java
|-- Scenario.java
|__ ScenarioGenerator.java
Audit.java
EthicalEngine.java
welcome.ascii

Execution: 
Show a welcome message, collect user consent for data collection, present 3 scenarios and
have user judge these, show the statistic, and ask for another round of scenarios. The message
provides background information about Moral Machines and walks the user through the program flow.
Next, program collect the user’s consent before saving any results. Explicit consent is crucial
to make sure users are aware of any type of data collection.After the welcome message,  program
 therefore prompt the user with the following method on the command-line:
 Do you consent to have your decisions saved to a file? (yes/no)
 
Only if the user confirms (yes),  program save the user statistic to user.log. If the user selects
no  program function normally but not write any of the users’ decisions to the file (it 
still display the statistic on the command-line though). If the user types in anything other than yes or
no, an InvalidInputException be thrown and the user should be prompted again:

Invalid response. Do you consent to have your decisions saved to a file? (yes/no)

Present Scenarios:
Once the user consented (or not), the scenario judging begins. Therefore, scenarios are either imported
from the config file or (if the config file is not specified) randomly generated. Scenarios are presented one by one using the toString()
method of the Scenario instance and printing its outputs to the command-line. Each scenario should be
followed by a prompt saying

Who should be saved? (passenger(s) [1] or pedestrian(s) [2])

Show the Statistic:
The statistic printed to the command-line using the same method and format as described below. 
If the user previously consented to the data collection, the statistic is saved (i.e., appended)
to the file user.log using the function printToFile(String filepath) of the Audit class. Additionally, the
user prompted to either continue or quit the program as follows:
Would you like to continue? (yes/no)
Should the user choose no the program terminates. If the user decides to continue (yes), the next three
scenarios should be shown. If the config file does not contain any more scenarios, the final statistic should
be shown followed by the following prompt
That’s all. Press Enter to quit.
As soon as the Enter key is pressed, the program should terminate.
======================================
# User Audit
======================================
- % SAVED AFTER 3 RUNS
ceo: 1.0
criminal: 1.0
dog: 1.0
pregnant: 1.0
child: 1.0
athletic: 0.9
red: 0.7

average age: 24.6
Would you like to continue? (yes/no)
Here is an example of a statistic followed by a prompt to continue

Decision Algorithm: 
Decision algorithm is based on below points: 
Person or Animal Factor: 
How many Person or Animal in a Scenario, Person gets preference to be saved. Also, the algorithm is based on to save a bigger number of characters in a scenario. 

You Factor:
Scenario can be presented as myself as pedestrian or passenger. 
Everyone loves to save himself; I would like to be saved 😊 if i would be in a scenario whether as passenger or pedestrian, would be given preference to be saved. 

Legal Crossing Factor: 
Weather Pedestrians are crossing on legal crossing, they would be preferred to be saved. All Pedestrians are following law by crossing on Green signal, they should be given more preference to be saved. 

Infant and Pregnancy Factor: 
If Baby or Child in a scenario, they would be given preference to be saved. Pregnant female has also given preference to be saved in algorithm. 

Profession Factor: 
Doctor, Engineer, CEO, Student are more to give our community as a result these professions has been given preference to be saved.

Pet Factor: 
Animal as pet has lots of emotional values to someone, humans are closer to pet, as a result Pets has been given preference to be saved.  

Based on above factor a score has been calculated, either Passengers or Pedestrian who has higher score to be saved in the scenario. 
If there is a tie at final score, algorithm will check whether pedestrian or passenger has more character, they will be saved. 
If it is still a tie, algorithm will look for more person to be saved. 
If is it still a tie (not likely), algorithm will toss coin and save either pedestrian or passengers. 

