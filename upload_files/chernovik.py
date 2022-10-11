import csv
from csv import writer
import nltk 
from nltk.sentiment import SentimentIntensityAnalyzer
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from sklearn import svm
import numpy as np
import pandas as pd
import re

X = []
y = []
#comp = neg = pos = neu = []

trainNum = 5000
sia = SentimentIntensityAnalyzer()
f = open("untitled.txt", "w")
important_words = ["risk","bioweapon", "deadly","vaccinated","depopulation", "experimental", "therapy", "untested", "dose", "second", "worry", "rushed", "grateful", "excited"] 

def keywords (w) :
    w = re.sub(r'(https|http)?:\/\/(\w|\.|\/|\?|\=|\&|\%)*\b', '', w, flags=re.MULTILINE)
    lemmatizer = WordNetLemmatizer()
    words = word_tokenize(w)
    final = []
    for word in words :
        word = re.sub(r'^https?:\/\/.*[\r\n]*', '', word, flags=re.MULTILINE)
        word = re.sub(r"[^a-zA-Z ]", "", word)
        word = word.lower()
        if len(word) > 1 : final.append(word)
    stop_words = set(stopwords.words("english"))
    filt_words = [word for word in final
                  if word.casefold() not in stop_words]
    lemmatized_words = [lemmatizer.lemmatize(word) for word in filt_words]

    #bool array construction
    bl = []
    for word in important_words :
        if word in lemmatized_words :
            bl.append(True)
        else : bl.append(False)
    return bl


with open("newfile.csv") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter = ',')
    n = 0
    for row in csv_reader:
        n += 1
        if n == 1: continue
        if n == trainNum: break
        sent = sia.polarity_scores(row[2])
        #FIND KEYWORDS
        #my = (sent["compound"], sent["pos"], sent["neg"], keywords(row[2]))
        
        obj = [sent["compound"], sent["pos"], sent["neg"]]
        keybool = keywords(row[2])
        
        for bl in keybool :
            obj.append(bl)
        X.append(obj)
        y.append(row[4])

clf = svm.SVC()
clf.fit(X, y)

correct = incorrect = 0
tp = tn = fn = fp = 0
file = open('untitled.txt', 'w')

with open("newfile.csv") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter = ',')
    n = 0
    for row in csv_reader:
        n += 1
        if (n < trainNum) : continue
        
        sent = sia.polarity_scores(row[2])
        obj = [sent["compound"], sent["pos"], sent["neg"]]
        keybool = keywords(row[2])
        for bl in keybool :
            obj.append(bl)
        predicted = clf.predict([obj])
        if (predicted != row[4]) :
            file.write("PREDICTED: " + str(predicted) + "   ACTUAL: " + str(row[4]) + "\n")
            file.write(row[2] + "\n\n")

        if int(predicted) == 1 and int(row[4]) == 1 : tp += 1
        elif int(predicted) == 1 and int(row[4]) == 0 : fp += 1
        elif int(predicted) == 0 and int(row[4]) == 1 : fn += 1
        elif int(predicted) == 0 and int(row[4]) == 0 : tn += 1
print(correct)
print(incorrect)
print("tp " + str(tp))
print("fp " + str(fp))
print("tn " + str(tn))
print("fn " + str(fn))
#print(correct/(correct+incorrect))
print((tp + tn) / (tp + fp + tn + fn))
f.close()

#tp 729
#fp 276
#tn 4194
#fn 1651
#0.7186861313868613

#tp 1879
#fp 100
#tn 4370
#fn 501
#0.9122627737226278
