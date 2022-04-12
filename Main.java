
// create an array 8x60 that stores the temps that each thread records
// when any thread writes a value, they potentially store it in a separate area where they store the five highest and lowest values
// use new github link to find five highest and lowest values
// there needs to be a command algorithm to sweep the array and grab the needed values for the output
// a ninth thread is out of the question, but an existing thread could handle this as the "command thread"
// this runs the small risk of writes occuring during reading but this can be solved with a small delay before caluculation after the final minute mark
//
