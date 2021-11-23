# SkipLists

#### Name: Arjun Kejriwal

As seen in the table and graph in the attached MS Excel file for
Question 1, as the size of the list increases, so does the running time
of the random permutation generator using both a LinkedSimpleList and a
SkipList. In other words, as the value of n increases, the running time
(in milliseconds) of the random permutation generator in both 
implementations of SimpleList. As seen from the graph, for small values
of n, the SkipList implementation has a slightly higher run time than
the LinkedSimpleList implementation. However, as the values of n rises,
say above 100, the run time of the LinkedSimpleList implementation rises
exponentially, rapidly going above the run time of the SkipList 
implementation, which increases somewhat linearly as n increases. Thus,
overall, the SkipList random permutation generator is much more efficient
than the LinkedSimpleList random permutation generator, especially for 
large values of n. The largest permutation that can be generated using
a LinkedSimpleList in 1 second (1000 milliseconds) has a value of n of
1861. Similarly, the largest permutation that can be generated using a 
SkipList in 1 second (1000 milliseconds) has a value of n of 2262.

I conducted many trials for each of the three sets of students in order
to find the probability that at least one student in each set is 
handed their own exam. After running the program once, these are the 
probabilities I found. For the set of 10 students, there is a 65.000%
chance that some student received their own quiz. For the set of 100 
students, there is a 57.500% chance that some student received their 
own quiz. For the set of 1000 students, there is a 55.000% chance that 
some student received their own quiz. Furthermore, I also conducted many
trials for each of the three sets of students in order
to find the expected value of the number of students in each set who are
handed their own exam. After running the program once, these are the
expected values I found. For the set of 10 students, I expect 1.2 students
to receive their own quiz. For the set of 100 students, I expect
1.15 students to receive their own quiz. For the set of 1000 students, I
expect 1.03333 students to receive their own quiz.  
