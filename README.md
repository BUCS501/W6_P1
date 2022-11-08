# W6_P1

This app is the modifyed version of the provided app "Lect6_FragmentTransactions_sse.zip".
The modification is made in the MainActivity.java, in which the question stated for line of codes are answered and the fragment transaction functionalities are fixed.

In detail, the three functions [showFrag1(), showFrag2(), showFrag3()] are modified.
For functions, we made sure that if the appropriate fragment is attached or detached depedning on the button clicked. Also, if the fragment was hidden by the previous function calls, we made sure that the the previous fragment was hidden and the present fragment is shown. Also, besides the Frag1, other fragments were not added to the fragment transaction, so that we added the fragment if they were not added before, and if there were, we just continued the needed fragment transaction to display the appropriate fragment on the screen.
