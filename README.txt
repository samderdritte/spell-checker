@autor: Samuel Spycher
@date: 2019-03-13

- 1. Introduction -

This program has been written as homework assignment no. 4 for the 2019 spring semester of the MCIT Online at University of Pennsylvania.


- 2. About The Program -

The program is a simple spell checker, which you can use to run a spell check over a .txt-file with text in English.

The program is written in Java and consists of four classes:
- FileChecker.java
- WordDictionary.java
- WordRecommender.java
- SpellChecker.java

Additionally, the program comes with a word-list of correctly spelled English words, which are used as a reference dictionary:
- engDictionary.txt

A series of JUnit-test are provided for your use (WordRecommenderTest.java). They cover most of the methods from the WordDictionary and WordRecommender classes.

The FileChecker-class is used for scanning the text-file. Also it contains several methods for creating a new file and manipulating file-extensions.

The WordDictionary-class is used to load the dictionary file. An additional method can be used to check whether a given word is in the dictionary.

The WordRecommender-class contains a series of methods which can be combined to handle the spell checker program.

The SpellChecker-class is the main class of the program. Run this class to run the program. It consists of one main method, which uses the other classes to perform the spell check.


- 3. Comments & Improvements -

The program is a very simple spell check and many ways in which it could be improved.

One flaw is that the dictionary is static and cannot be trained to learn new words. Every time you restart the program, you will reset the dictionary. All the words you have checked in one document, you will have to check again in the next document. This could be changed, if the dictionary were updated with unknown words which are "learned" as correct.

Even within one file, the same words will be checked every single time, if they do not exist in the dictionary. This could also be made better, if the dictionary is updated every time a word is checked.

Furthermore, there are methods in the assignment description, which are not needed for the final implementation of the program. For example the "getWordsWithCommonLetters"-method, which is of no use further in the WordRecommender-class.

Another thing which could be improved, is the design of the loops. With the current design, every word of the text-file is checked against the dictionary. Checking means that we loop over every word in the dictionary. A normal text in the English language has many duplicate words. There is no need to loop the dictionary for every one of those duplicate words. A better way would be to go through the whole text once, and detect duplicate words. Those could then be checked against the dictionary. Thus we would look up duplicate words only once and save unnecessary loops.

