# Constraint-Based Scheduling

This was a FinalProject from Artificial Intelligence course in Shahid beheshti university.

The goal is to assign two referees to each lecture and try to minimize the amount of days which is needed to have all lectures held.
Referees <b> must </b> have an expretise in lecture's topic. Each day has 4 different timeframes(time parts) which are allowed to have a lecture held.
You can have multiple lectures held at the same time once you designate free related professors to the every lecture.
Free professor means a professor which is not present at any other lectures (as supervisor or referee) at the same time.
Related professors means a professor which has an expertise in lecture's related topics.

Each lecture has a supervisor which is indicated in the lecture  information and has a specific topic. Also each proffessor has their expertise in a particular feild(s).

<i> <b>
Input
</b> </i> 

You will be given 3 different files namely Lectures.txt , Subjects.txt , Teachers,txt

<i> Lectures.txt </i>

This file has information about all the lectures which should be held. For each lecture, name of the lecture, name of the associated professor, number of topics which are related to the lecture and topic's names are indicated.
Example: <br>
lec1 <br>
Dr.Shamsfard <br>
3 <br>
NLP <br> 
DIP <br>
DM  <br>
lec2 <br>
Dr.Haghighi <br>
2 <br>
SE <br>
Net <br>

The example mentioned above, has two different lectures. First lecture which is lec1, has Dr.Shamsfard as the supervisor and focuses on 3 different topics which are NLP, DIP, DM. 
For the second lecture which is lec2 we have Dr.Haghighi as the supervisor and the lecture is related to 2 topics namely SE and Net.

<i> Subjects.txt </i>  <br>

This file was created to cover all different topics' names. In order to have a full dictionary in terms of topics, you can use this file.

<i> Teachers.txt </i>  <br>
Since you have to assign a related professor for each lecture, you need to have information about proffessor areas.
Every proffessor is accomplished in a specific field(s). In this file, number of expertise each proffesor posses, and name of their epxertise are indicated as the following format:
<br>

Dr.Shamsfard <br>
2 <br>
NLP <br>
AI <br>
Dr.Moayeri <br>
2 <br> 
VLSI <br>
HE <br>

In the example aforementioned, there are two different professors.  First one, Dr.Shamsfard has 2 epxertise such as NLP and AI.
The next professor Dr.Moayeri has also two other expertise namely VLSI and HE.


<b>Remember </b> you are not supposed to cover all related topics to a lecture by desingated referees. The puprose is to assign two referees who are proficent in one of lecture's topic.
<br>
<b> And also remember </b> lecture's supervisor is fixed. You can not assign the supervisor as a referee in another lecture which is held simultaneously; The professor cant be at two different lectures at the same time.


<i> <b>
Output
</b> </i>

When scheduling algorithm finishes, it will print the best plan it has found. For a more detailed output, it will generate an excel file Schedule.xls which contains all the information needed.
The excel file will be like:
