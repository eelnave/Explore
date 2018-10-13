#!/bin/sh

PROJECTS="Calc360 DateApp Explore Fishing kindnessClient Maintenance Record"
# PROJECTS="BasicJava Classes Collections Console Exceptions FileIO Simple"

for proj in ${PROJECTS} ; do
	cp ../apps/hooks/codeStyles/* ${proj}/.idea/codeStyles
	git add -f ${proj}/.idea/codeStyles/*
done

# git commit -m "Restore code style configuration files."
# git push

exit 0
