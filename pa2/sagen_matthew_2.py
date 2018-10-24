'''
Author: Matthew Sagen
Date: 10/21/18
CSCI-460: Programming Assignment 2

To run the program use: python3 sagen_matthew_2.py
Output is written to:   sagen_matthew_2.txt

'''
import random
import threading
import time

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
            self.numNodes = self.numNodes + 1 #increment the number of nodes in the list
        else:
            newNode.prev = self.tail
            newNode.next = None
            self.tail.next = newNode
            self.tail = newNode
            self.numNodes = self.numNodes + 1 #increment the number of nodes in the list

    def removeHead(self, nodeVal):
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
 #Producer #1: generate a node, add to end of the linked list with odd random int
 #Producer #2: generate a node, add to end of the linked list with even random int
 #When the buffer is full, both should generate a message and wait.

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
        bufferSize = 30
        name = threading.currentThread().getName()
        var_lock = threading.Lock()
        while(True):
            try:
                var_lock.acquire()#try to acquire a lock on the thread.
                f = open("sagen_matthew_2.txt", "a")
                nodes = self.getNodeNum()
                if(nodes < bufferSize): #if we haven't reached the max number of nodes...
                    f.write("Before "+name+"\n")
                    self.showList(name,f)
                    if(name == 'p1'):
                        randomNumber = random.randrange(1, 49+1, 2) #add an odd node to the list
                    elif(name == 'p2'):
                        randomNumber = random.randrange(0, 49+1, 2)
                    else:
                        print("Error in class:Producer, method: run")
                    self.generateNode(randomNumber)
                    f.write("After "+name+"\n")
                    self.showList(name,f)
                else:
                    self.generateMsg(name,f) #if the buffer is full, generate a message and wait
                f.close()
                var_lock.release()#release lock
                time.sleep(2)
            except:
                f.write(name+': Could not acquire Lock.\n')
                f.write("*"*50+"\n")
                f.close()
                time.sleep(2)

class Consumer(threading.Thread):
#Consumer1: delete the first node with odd value from head of list. If the first node has an even value, then wait
#Consumer2: delete the first node with even value from head of list. If the first node has an odd value, then wait
#when the buffer is empty, both should generate a message and wait.

    def __init__(self, dblList, name):
        super(Consumer, self).__init__()
        self.dblList = dblList
        self.name = name

    def deleteHead(self, node):
        self.dblList.removeHead(node)

    def generateMsg(self, name,f):
        msg = "Task completed:"
        print(msg, name)
        f.write(msg+' '+name+'\n')

    def getNodeNum(self):
        return self.dblList.numNodes

    def showList(self,name,f):
        self.dblList.show(name,f)

    def run(self):
        name = threading.currentThread().getName()
        var_lock = threading.Lock()
        while(True):
            try:
                var_lock.acquire() #try to acquire a lock on the thread.
                f = open("sagen_matthew_2.txt", "a")
                print(self.dblList.head.randomNum)
                if(name == 'c1'):
                    headVal = self.dblList.head.randomNum
                    nodes = self.getNodeNum()
                    if(nodes > 0 and headVal % 2 != 0): #delete head of list if it's value is odd
                        f.write("Before c1:\n")
                        self.showList(name,f) #print out list before changing it
                        self.deleteHead(0)     #delete the head of the list
                        f.write("After c1:\n")
                        self.showList(name,f) #print out list after changing it
                    elif(nodes > 0 and headVal % 2 == 0): #wait if the head's value is even
                        print("wait")
                    else:
                        self.generateMsg(name,f)

                elif(name == 'c2'):
                    headVal = self.dblList.head.randomNum
                    nodes = self.getNodeNum()
                    if(nodes > 0 and headVal % 2 == 0): #delete head of list if it's value is even
                        f.write("Before c2:\n")
                        self.showList(name,f) #show the list before altering it
                        self.deleteHead(0)     #delete the head of the list
                        f.write("After c2:\n")
                        self.showList(name,f) #show the list after altering it.
                    elif(nodes > 0 and headVal % 2 != 0): #wait if the heads value is odd
                        print("wait")
                    else:
                        self.generateMsg(name,f) #generate a message if there are no node's left.
                        #sys.exit()
                f.close()
                var_lock.release() #release lock
                time.sleep(2)
            except:
                f.write(name+': Could not acquire Lock.\n')
                f.write("*"*50+"\n")
                f.close()
                time.sleep(2)

class Main():

    def main():
     f = open("sagen_matthew_2.txt", "w") #create new output file for each new run of program
     f.write("Starting threads...\n")
     f.write("*"*50+"\n")
     f.close()
     d = DoublyLinkedList()
     #initialize list with three nodes with a random int between 0 and 50
     for i in range(3):
         randomNum = random.randint(0,49)
         d.append(randomNum)
         i = i + 1
     p1 = Producer(d, name='p1') #create producer1 and producer2
     p2 = Producer(d, name='p2')
     c1 = Consumer(d, name='c1') #create Consumer1 and consumer2
     c2 = Consumer(d, name='c2')
     p1.start() #start the four threads
     p2.start()
     c1.start()
     c2.start()

    main()
