#!/usr/bin/python

import matplotlib.pylab as pyl
import sys
import numpy as np
import re

x = range(0,22)

youngUsed = []
youngAfterGc = []
youngTotal = []
yountTime = []
# pyl.plot(x, y)

# pyl.show()
youngRex = '(\\d+\\w->\\d+\\w)'
youngTimeRex = '\\d{1}\\.\\d+'
youngTotalRex = '\\(\\d+\\w\\)'

with open('log/PGC01.txt', 'rb') as f:
	for line in f:
		lineStr = str(line)
		if(lineStr.find('PSYoungGen',0,len(lineStr))>0):
			lineStrInfo = lineStr.partition('[')[2]
			searchObject = re.search(youngRex,lineStrInfo,0)
			if searchObject:
				matchYoung = searchObject.group()
				youngUsed.append(matchYoung.partition('->')[0].replace('K',''))
				youngAfterGc.append(matchYoung.partition('->')[2].replace('K',''))
				# print('GC前' + matchYoung.partition('->')[0].replace('K',''))
				# print('GC后' + matchYoung.partition('->')[2].replace('K',''))									

			searchObject = re.search(youngTotalRex,lineStrInfo,0)
			if searchObject:
				matchTotal = searchObject.group().replace('K','').replace('(','').replace(')','')
				print('总young' + matchTotal)

			searchObject = re.search(youngTimeRex,lineStrInfo,0)
			if searchObject:
				matchTime = searchObject.group().replace('(','').replace(')','')
				print('GC时间' + matchTime)

# youngUsed,youngAfterGc = np.sin(x), np.cos(x)
pyl.plot(x, youngUsed,label='beforeGC', color ='r')
pyl.plot(x, youngUsed,'ob')
pyl.plot(x, youngAfterGc,label='afterGC', color ='g')
pyl.plot(x, youngAfterGc,'ob')
pyl.legend()
pyl.show()
