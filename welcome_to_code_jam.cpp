#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <string>
#include "Main.h"

/*
 *  A solution for Google Code Jam problem
 *  Known as "Welcome to code jam"
 *  http://code.google.com/codejam/contest/90101/dashboard#s=p2
 *  The result for the original input is exactly 400263727, as stated in the problem description.
 *  57% of the users who attempted submitting the code reached the correct result on that input.
 *  I added a few test cases on my own.
 */

using namespace std;

long search_phrase(string &input, string &phrase);

long welcome_count()
{
	string test_input1 = "cwwelceome to to w let cocodee jamm";
	string test_input2 = "welelweellcome to code jam";
	string original_input = "So you've registered. We sent you a welcoming email, to welcome you to code jam. But it's possible that you still don't feel welcomed to code jam. That's why we decided to name a problem welcome to code jam. After solving this problem, we hope that you'll feel very welcome. Very welcome, that is, to code jam.";
	string test_input3 = "welcome to code jam code jam." ;
	string phrase = "welcome to code jam";
	search_phrase(test_input1, phrase);
	search_phrase(test_input2, phrase);
	search_phrase(test_input3, phrase);
	return search_phrase(original_input, phrase);
}


/*
 *  Arguments:
 *  input - the 'haystack' 
 *  phrase - the string to be created using letters from the haystack
 */

long search_phrase(string &input, string &phrase)
{
	map<int, vector<int>> locations;
	int input_idx = 0, phrase_idx = 0, inner_idx = 0;
	long count = 0;

    //First pass over the entire input.
    //increase pharse index each time the letter of that index has been discovered in the haystack.
    //If we reached the end of the haystack and letters of the pharse are yet to be discovered,
    //we could not compose the phrase, not even once, so return 0.
    //For example, the haystack starts with "welcome" with indices 0-6.
    //The 'e' at index 0 could only be used for concating the first 'e' in the phrase (index 1),
    //and then the 'e' at index 6 of the haystack would be used as the second 'e' in the phrase (index 6).
    //However it might also be used as the first 'e', assuming "lcome" letters are available later on.
    //so, candidates for 'first e' are 1&6, candidate for 'second e' is only 6 at the moment.
    //locations[1] = {1,6}, locations[6] = {6}
    
	for(; input_idx <input.length(); input_idx++)
	{
		if(input[input_idx] == phrase[phrase_idx])
			phrase_idx++;
		for(inner_idx = 0; inner_idx < phrase_idx; inner_idx++)
		{
			if(input[input_idx] == phrase[inner_idx])
			{
				locations[inner_idx].push_back(input_idx);
			}
		}
	}
	if (phrase_idx < phrase.length())
		return 0;

    
    //Remove inconsistent locations.
    //lets assume two locations on the phrase, i and j, when i<j.
    //max = max{locations[j] vector elements} this will be the last possible location on the haystack
    //to produce this letter. (by the order of insertion in previous loop, it is always the last element)
    //Now, there is no option for location[i] element to be bigger then max, in order to correctly construct the phrase.
    //so those indices will be erased from locations[i] vector.
    //loop from end of the phrase backwards, each tme get the max, and loop over all saved locations in all previous spots,
    //to remove those cases.
	for(phrase_idx = locations.size()-1; phrase_idx >= 0; phrase_idx--)
	{
		int max = locations[phrase_idx].back();
		for(inner_idx = phrase_idx-1; inner_idx >= 0; inner_idx--)
		{
			vector<int> &curr_vec = locations[inner_idx];
			while(!curr_vec.empty() && curr_vec.back() >= max)
			{
				curr_vec.pop_back();
			}
		}
		
	}
    
    
	map<pair<int,int>,int> mult_by;
	phrase_idx = locations.size()-1;

	for(vector<int>::iterator iter = locations[phrase_idx].begin(); 
		iter != locations[phrase_idx].end(); iter++)
		mult_by[make_pair(*iter,phrase_idx)] = 1;
	for(; phrase_idx > 0; phrase_idx--)
	{
		vector<int> &curr_vec = locations[phrase_idx];
		vector<int> &prev_vec = locations[phrase_idx-1];
		for(vector<int>::iterator prev_iter = prev_vec.begin(); 
			prev_iter != prev_vec.end(); prev_iter++)
		{
			for(vector<int>::iterator curr_iter = curr_vec.begin(); 
				curr_iter != curr_vec.end(); curr_iter++)
			{
				if(*prev_iter < *curr_iter)
					mult_by[make_pair(*prev_iter,phrase_idx-1)] += 
					mult_by[make_pair(*curr_iter,phrase_idx)];
			}
		}

	}
	for(vector<int>::iterator iter = locations[0].begin(); iter != locations[0].end(); iter++)
		count += mult_by[make_pair(*iter,0)];
	return count;
}

int main()
{
	return welcome_count();
}