'''
Author: Matthew Sagen
Date: 10/21/18
CSCI-460: Programming Assignment 2
To run the program, use: python3 sagen_matthew_2.py
Output is written to sagen_matthew_2.txt
'''
import random
import threading
import time
from numpy import savetxt

class Node(object):

    def __init__(self, randomNum, prev, next):
        self.randomNum = randomNum
        self.prev = prev
        self.next = next


class DoublyLinkedList(object):

    head = None
    tail = None
    numNodes = None

    def __init__(self):
        self.numNodes = 0

    def getNodeNum():
        return numNodes

    def append(self,randomNum):
        newNode = Node(randomNum, None, None)
        if self.head is None:
            self.head = self.tail = newNode
            self.numNodes = self.numNodes + 1
        else:
            newNode.prev = self.tail
            newNode.next = None
            self.tail.next = newNode
            self.tail = newNode
            self.numNodes = self.numNodes + 1

    def remove(self, nodeVal):
        curNode = self.head      #get the head of the list
        self.head = curNode.next #set the head of the list to the next node
        curNod = None            #remove the old head from the list


    def show(self, name, f):
        curNode = self.head
        #f = open("sagen_matthew_2.txt", "a")
        while curNode is not None:
            #print (name, '->', curNode.randomNum)
            f.write(name+'->'+str(curNode.randomNum)+'\n')
            curNode = curNode.next
        #print ("*"*50)
        f.write("*"*50+"\n")
        #f.close()

class Producer(threading.Thread):

    def __init__(self, dblList, name):
        super(Producer, self).__init__()
        self.dblList = dblList
        self.name = name

    def generateNode(self,randomNum):
        self.dblList.append(randomNum)

    def generateMsg(self,name,f):
        msg = "Task completed:"
        print(msg, name)
        f.write(msg+' '+name+'\n')

    def getNodeNum(self):
        return self.dblList.numNodes

    def showList(self, name,f):
        self.dblList.show(name,f)

    def run(self):
        while(True):
            maxNodes = 30
            name = threading.currentThread().getName()
            var_lock = threading.Lock()
            try:
                var_lock.acquire()
                f = open("sagen_matthew_2.txt", "a")
                if(name == 'p1'):
                    i = self.getNodeNum()
                    if(i < maxNodes):
                        #print("Before p1:")
                        f.write("Before p1:\n")
                        self.showList(name,f)
                        oddRandomNum = random.randrange(1, 49+1, 2)
                        self.generateNode(oddRandomNum)
                        #print("After p1:")
                        f.write("After p1:\n")
                        self.showList(name,f)
                    else:
                        self.generateMsg(name,f)
                        sys.exit()
                        #break;
                elif(name == 'p2'):
                    i = self.getNodeNum()
                    if(i < maxNodes):
                        #print("Before p2:")
                        f.write("Before p2:\n")
                        self.showList(name,f)
                        evenRandomNum = random.randrange(0, 49+1, 2)
                        self.generateNode(evenRandomNum)
                        #print("After p2:")
                        f.write("After p2:\n")
                        self.showList(name,f)
                    else:
                        self.generateMsg(name,f)
                        sys.exit()
                        #break;
                f.close()
                var_lock.release()
                time.sleep(2)
            except:
                #print(name, '-> Could not acquire Lock')
                f.write(name+': Could not acquire Lock.\n')
                f.close()
                time.sleep(2)
class Consumer(threading.Thread):
    def __init__(self, dblList, name):
        super(Consumer, self).__init__()
        self.dblList = dblList
        self.name = name

    def deleteHead(self, node):
        self.dblList.remove(node)

    def generateMsg(self, name,f):
        msg = "Task completed:"
        print(msg, name)
        f.write(msg+' '+name+'\n')

    def getNodeNum(self):
        return self.dblList.numNodes

    def showList(self,name,f):
        self.dblList.show(name,f)

    def run(self):
        while(True):
            name = threading.currentThread().getName()
            var_lock = threading.Lock()
            try:
                var_lock.acquire()
                f = open("sagen_matthew_2.txt", "a")
                if(name == 'c1'):
                    i = self.getNodeNum()
                    print("I: ", i)
                    if(i > 0 and i % 2 != 0):
                        #print("Before c1:")
                        f.write("Before c1:\n")
                        self.showList(name,f)
                        self.deleteHead(0)
                        #print("After c1:")
                        f.write("After c1:\n")
                        self.showList(name,f)
                    else:
                        self.generateMsg(name,f)
                        sys.exit()
                        #break;
                elif(name == 'c2'):
                    i = self.getNodeNum()
                    if(i > 0 and i % 2 == 0):
                        #print("Before c2:")
                        f.write("Before c2:\n")
                        self.showList(name,f)
                        self.deleteHead(0)
                        #print("After c2:")
                        f.write("After c2:\n")
                        self.showList(name,f)
                    else:
                        self.generateMsg(name,f)
                        sys.exit()
                        #break;
                f.close()
                var_lock.release()
                time.sleep(2)
            except:
                #print(name, '-> Could not acquire Lock.')
                f.write(name+': Could not acquire Lock.\n')
                f.close()
                time.sleep(2)

class Main():

    def main():
     f = open("sagen_matthew_2.txt", "w")
     f.write("Starting threads...\n")
     f.write("*"*50+"\n")
     f.close()
     d = DoublyLinkedList()
     #initialize list with three nodes with a random int between 0 and 50
     for i in range(3):
         randomNum = random.randint(0,49)
         d.append(randomNum)
         i = i + 1
     #d.show('Init')
     counter = 3
     p1 = Producer(d, name='p1')
     p2 = Producer(d, name='p2')
     c1 = Consumer(d, name='c1')
     c2 = Consumer(d, name='c2')
     p1.start()
     p2.start()
     c1.start()
     c2.start()

     #Producer #1: generate a node, add to end of the linked list with odd random int
     #Producer #2: generate a node, add to end of the linked list with even random int
     #When the buffer is full, both should generate a message and wait.

     #Consumer1: delete the first node with odd value from head of list. If the first node has an even value, then wait
     #Consumer2: delete the first node with even value from head of list. If the first node has an odd value, then wait
     #when the buffer is empty, both should generate a message and wait.
    main()
