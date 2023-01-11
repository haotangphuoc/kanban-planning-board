#!/bin/bash

# Bash script for verifying that a user's signature exists.

# Report the user and file parameters.
echo "Student: "$1
echo "File   : "$2

# Store the parameters in more descriptive variables.
user=$1
file=$2

# Check if the signature file exists:
#   If the signature file exists, confirm it.
#   If the signature file doesn't exist, exit with -1 (255).
if [ -f $file ]; then
	echo "Found signature file."
else
	echo "No signature file found."
	exit -1
fi

# Confirm we're checking for the actual signature now.
echo "Checking for signature contents..."

# Get the contents of the file. It should be the user's CSID.
contents=$( cat $file )

# Print the contents to the screen.
echo "Signature: "$contents

# Check the contents against the username:
#   If the user and signature match, we exit with 0.
#   If the user and signature don't match, we exit with -2 (254).
if [ "$user" == "$contents" ]; then
	echo "Correct signature verified."
	exit 0
else
	echo "Incorrect signature. Try again."
	exit -2
fi
