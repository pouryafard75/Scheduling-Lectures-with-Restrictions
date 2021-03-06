# Constraint satisfaction Scheduling (Greedy Approach)

There are plenty of lectures and professors. Your mission is to find the best scheduling plan with the minimum amount of time to have all lectures held with a supervisor and referees. But there are some restrictions in terms of choosing referees.
The goal is to assign two referees to each lecture and try to minimize the number of time parts(each day has 4 different time parts) which is needed to have all lectures held. Referees must be experts at least in one of the lecture's topics. Each day has 4 different timeframes(time parts) which are allowed to have a lecture held. You can have multiple lectures held at the same time once you designate free related professors to every lecture. A free professor means a professor who is not present at any other lectures (as supervisor or referee) at the same time. A related professor means a professor who has expertise in lecture's related topics.
Each lecture has a supervisor which is indicated in the lecture information and has a specific topic. Also, each professor has expertise in a particular field(s).
## Output
When the scheduling algorithm finishes, you can get the reuslts in different formats:
### Excel
For a more detailed output, it will generate an excel file which contains all the information needed. The excel file will be like this:
![image](https://user-images.githubusercontent.com/28820932/120908369-df54fd00-c67e-11eb-9873-fb033ef5e17b.png)
### Raw text
Day 1, part 1:  <br>
lec1 | Supervisor : Dr.Shamsfard | Referees : Dr.Ebrahimmoghadam , Dr.Talebpour <br>
lec11 | Supervisor : Dr.Shams | Referees : Dr.Vahidi , Dr.Haghighi <br>
Day 1, part 2: <br> 
lec4 | Supervisor : Dr.Neshati | Referees : Dr.Mahmoudi , Dr.Ebrahimmoghadam <br>
lec5 | Supervisor : Dr.Shekofteh | Referees : Dr.Shamsfard , Dr.Talebpour <br>
lec7 | Supervisor : Dr.Ghasemian | Referees : Dr.Abbaspour , Dr.Moayeri <br>
.....
## Input
You will be given 3 different files namely Lectures.txt, Subjects.txt, Teachers.txt
### Lectures.txt 
This file has information about all the lectures which should be held. For each lecture, the name of the lecture, name of the associated professor, number of topics that are related to the lecture, and topic's names are indicated. Example: 

lec1  <br>
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

The example mentioned above has two different lectures. The first lecture which is lec1 has Dr.Shamsfard as the supervisor and focuses on 3 different topics which are NLP, DIP, DM. For the second lecture which is lec2, we have Dr.Haghighi as the supervisor and the lecture is related to 2 topics namely SE and Net.
### Subjects.txt 
This file was created to cover all different topics' names. In order to have a full dictionary in terms of topics, you can use this file.
### Teachers.txt 
Since you have to assign a related professor for each lecture, you need to have information about professor areas. Every professor is accomplished in a specific field(s). In this file, the number of expertise each professor posses, and the name of their expertise are indicated in the following format: 

Dr.Shamsfard <br>
2 <br>
NLP <br>
AI <br>
Dr.Moayeri <br>
2 <br>
VLSI <br>
HE <br>

In the example aforementioned, there are two different professors. The first one, Dr.Shamsfard has 2 expertise, NLP and AI. The next professor Dr.Moayeri has also two other expertise namely VLSI and HE.
Remember you are not supposed to cover all related topics to a lecture by designated referees. The project goal is to assign two referees who are proficient in one of the lecture's topics. 
And also remember lecture's supervisor is fixed. You can not assign the supervisor as a referee in another lecture that is being held simultaneously; The professor cant be at two different lectures at the same time.
## Algorithm
The algorithms will create and fill a priority queue with teachers called possible teachers. The teacher with less expertise will come at the first of the queue. Then we iterate through lectures. By having this approach, we designate the less accomplished professors at first and while the algorithms continue we have more versatile teachers.(Greedy Approach)
