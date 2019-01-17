# My python3 implementation of the LZW decompression algorithm
import math

# For starting and resetting the dictionary
def initial_dict():
	ascii_dict = dict()
	for i in range(0,256):
		ascii_dict[str(i)] = chr(i)
	return ascii_dict

# Formats each input before decoding
def format_input(n):
	input = []
	for i in range (0,int(math.floor(len(n)/3))):
		# Every three bytes are converted into two lots of 12 bits
		A, B, C = n[3*i], n[3*i+1], n[3*i+2]
		D = int(A*16+math.floor(B/16))
		E = ((B%16)*256)+C
		input.append(D)
		input.append(E)
	# This is in case there is an odd number of bytes 
	if len(n)%3==2:
		A, B = n[len(n)-2], n[len(n)-1]
		C = A*256 + B
		input.append(C)
	return input

def decode(i):
	#INPUT
	i_filename = ("compressedfile{}.z".format(i))
	f = open(i_filename,'rb')
	n = f.read()
	#Put it into the correct format
	n = format_input(n)

	#OUTPUT
	o_filename = ("decoded{}.txt".format(i))
	o = open(o_filename,'w')
	charstream = []

	# We start with an initial dictionary
	dict = initial_dict()

	# Then set codeword1 as the first code word
	codeword1 = str(n[0])

	# Output the string corresponding to codeword1
	charstream.append(dict[codeword1])

	for i in range(0,len(n)-1):
		# Dictionary reset once it reaches the limit
		if (len(dict)==4096):
			dict = initial_dict()

		# Let codeword0 be the current codeword
		# Set codeword1 as the next codeword
		codeword0 = codeword1
		codeword1 = str(n[i+1])

		# Is the codeword1 present in the dictionary?
		# We only need to check to see if it lies outside the length of the current dictionary.
		if(int(codeword1)<len(dict)):
			# Output codeword1
			charstream.append(dict[codeword1])
			# Let P be the string corresponding to codeword0
			# Let C be the first character of the string corresponding to codeword1
			P = dict[codeword0]
			C = dict[codeword1][0]
			# Then add P+C to the current dictionary
			dict[str(len(dict))] = P+C
		else:
			# Let P be the string corresponding to codeword0
			# Let C be the first character of the string corresponding to codeword0
			P = dict[codeword0]
			C = dict[codeword0][0]
			# Output the string P+C to the charstream and add it to the current dictionary
			charstream.append(P+C)
			dict[str(len(dict))] = P+C
	for i in range(0,len(charstream)):
		o.write(str(charstream[i]))

def main():
	# Files to be decoded
	for i in range (1, 5):
		decode(i)

if __name__ == "__main__":
	main()