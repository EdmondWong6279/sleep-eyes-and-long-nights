import hashlib
import string
import itertools
import random

my_dict = {}

for i in range(6):
	ALL = map(''.join, itertools.product(string.ascii_lowercase, repeat=i)) #creates a phat list
	for j in range(len(ALL)): #for each element in the phat list
		word = ALL[j] #take the jth word in the list
		hashed = hashlib.sha1(word)
		nibs = hashed.hexdigest()[0:15] #hash it and only keep the first 15 nibs
		my_dict[nibs] = word
print my_dict

#Random choice is clearly a bit shit here
while (True):
	word = ''.join(random.choice(string.ascii_lowercase + string.ascii_uppercase + string.digits) for _ in range(10))
	hashed = hashlib.sha1(word)
	nibs = hashed.hexdigest()[0:15] #hash it and only keep the first 15 nibs
	for key in my_dict.iterkeys(): #iterate through the dictionary and compare the keys(nibs)
		if key == nibs:
			print 'MATCH!!!'
			print word
			print my_dict[key]
			quit()
	print word + ' ' + nibs
print 'no matches found'