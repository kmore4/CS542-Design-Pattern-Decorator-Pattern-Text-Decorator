# CSX42: Assignment 5

Name:  Kasturi Tarachand More

-----------------------------------------------------------------------

Following are the commands and the instructions to run ANT on your project.


Note: build.xml is present in [textdecorators/src](./textdecorators/src/) folder.

## Instruction to clean:

Assuming you are at textdecorators/ folder.

```commandline
 ant -buildfile src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

## Instructions to compile:

Assuming you are at textdecorators/ folder.

```commandline
 ant -buildfile src/build.xml all 
```
The above command compiles your code and generates .class files inside the BUILD folder.

## Instructions to run:

```commandline
ant -buildfile src/build.xml run -Dinput=input.txt" -Dmisspelled="spell.txt" -Dkeywords="keyw.txt" -Doutput="output.txt" -Ddebug=1


```
Note: Arguments accept the absolute path of the files.


## Description:

Slack Days Used: 3/5

Data structure used in InputDetails : ArrayList of string

Complexity to add each sentence in List: O(n) where n = no. of sentences

Complexity to add each keyword in List: O(n) where n = no of keywords

Complexity to add each misspelled word in List: O(n) where n = no of misspelled words

Complexity to create HashMap of words and its occurrences: O(n)

Complexity of sorting HashMap entries: O(n logn)


## References:

https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/

https://stackoverflow.com/questions/26230225/hashmap-getting-first-key-value

## Assumptions:

As my doubt regarding log.txt has not been solved by instructors,
I have assumed that we have to prefix and suffix the output of the decorators with decorator name for log.txt

## Academic Honesty statement:

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: [08/06/2019]
