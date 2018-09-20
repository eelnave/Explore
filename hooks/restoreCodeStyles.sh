#!/bin/sh

PROJECTS="Calc360 Collections DateApp Fishing kindnessClient Maintenance Mammals Record Simple"
for proj in ${PROJECTS} ; do
	cp hooks/codeStyles/* ${proj}/.idea/codeStyles
	git add -f ${proj}/.idea/codeStyles/*
done

# git commit -m "Restore code style configuration files."
# git push

exit 0
