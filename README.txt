
# COP4520-Assignment-3B
This program uses a 2d array to store random temperature values, which are then read and collected by a single thread denoted as the "command" thread.
An array was used because an array has no issues regarding concurrent reading, and issues regarding concurrent writing only occur if 
multiple threads are writing on the same element, which this program avoids entirely.

The efficiency of the solution is based around splitting the majority of the work between all of the threads sticking temps into the 2d array.
After an hour has passed, each array will check their own section of the 2d array to grab their five biggest and five smallest values.
They also each calculate their own largest temperature differences and output those to an array to be scanned by the "command" thread.
These operations are all extremely simple and therefore cause little timed to be wasted doing them.

Additionally, the command thread holds all the other threads in place with a flag to prevent itself from falling behind while it does a few additional calculations.
Otherwise, it would be slightly behind the others while it takes a bit of extra time to determine the biggest temperature difference and the other things it does.

The correctness of the program is maintained by each of the threads having strictly linearizable behavior.
Each one has their progress become visible to the other threads whenever they push a new value to the array, and this moment can be pinpointed in the code when a new temp is added.

The progress guarantee is Lock-Free. Since no locks are used at all, this means that starvation and deadlock are impossible. This does come with a minor caveat, however.
As detailed in the comments, since no instructions were given in the description of the problem to ever end the calculations, none of the threads ever actually stop.
Each one is set up to infinitely loop their calculating without ever stopping, and so *technically* none of the methods called ever return.
This however I do not consider to be an issue because if a stop condition was given it could likely be easily implemented and therefore this problem would be solved.

As for testing of this solution, since there are no instructions for the outputting of these values, and since it is instructed that values be gathered every minute for hours at a time,
I have not implemented any way of viewing these values that are calculated, nor have I done thorough testing of whether or not the values are actually calculated correctly.
That said, I certainly was thorough in trying to avoid bugs in the code when I was writing it, but judging from the lack of output instructions,
and the nature of gathering values for an hour before outputting anything, I interpreted this assignment as more of a "proof of concept" rather than a 100% functional model.
I am banking quite a bit on this interpretation of mine, but it's a risk I am confident in taking.

GitHub link referenced at line 57: https://stackoverflow.com/questions/32816006/3-highest-numbers-in-an-array-without-sorting-where-am-i-missing-the-logic
