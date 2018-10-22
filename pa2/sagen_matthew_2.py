'''
Author: Matthew Sagen
Date: 10/21/18
CSCI-460: Programming Assignment 2

'''
import random
import threading


class Node(object):

    def __init__(self, randomNum, prev, next):
        self.randomNum = randomNum
        self.prev = prev
        self.next = next


class DoublyLinkedList(object):

    head = None
    tail = None

    def __init__(self, numNodes):
        self.numNodes = numNodes

    def getNodeNum():
        return numNodes

    def append(self,randomNum):
        numNodes = self.numNodes
        newNode = Node(randomNum, None, None)
        if self.head is None:
            self.head = self.tail = newNode
            numNodes = numNodes + 1
        else:
            newNode.prev = self.tail
            newNode.next = None
            self.tail.next = newNode
            self.tail = newNode
            numNodes = numNodes + 1

    def remove(self, nodeVal):
        curNode = self.head

        while curNode is not None:
            if curNode.randomNum == nodeVal:
                # if it's not the first element
                if curNode.prev is not None:
                    curNode.prev.next = curNode.next
                    curNode.next.prev = curNode.prev
                else:
                    # otherwise we have no prev (it's None), head is the next one, and prev becomes None
                    self.head = curNode.next
                    curNode.next.prev = None

            curNode = curNode.next

    def show(self, name):
        curNode = self.head
        while curNode is not None:
            print (name, '->', curNode.randomNum)
            curNode = curNode.next
        print ("*"*50)

class Producer(threading.Thread):

    def __init__(self, dblList, name):
        super(Producer, self).__init__()
        self.dblList = dblList
        self.name = name

    def generateNode(self,randomNum):
        self.dblList.append(randomNum)

    def generateMsg(self,name):
        msg = "Task completed:"
        print(msg, name)

    def getNodeNum(self):
        return self.dblList.numNodes

    def showList(self, name):
        self.dblList.show(name)

    def run(self):
        #while(True):
        maxNodes = 30
        name = threading.currentThread().getName()
        var_lock = threading.Lock()
        try:
            var_lock.acquire()
            if(name == 'p1'):
                i = self.getNodeNum()
                if(i < maxNodes):
                    print("Before p1:")
                    self.showList(name)
                    oddRandomNum = random.randrange(1, 49+1, 2)
                    self.generateNode(oddRandomNum)
                    print("After p1:")
                    self.showList(name)
                else:
                    self.generateMsg(name)
                    #break;
            elif(name == 'p2'):
                i = self.getNodeNum()
                if(i < maxNodes):
                    print("Before p2:")
                    self.showList(name)
                    var_lock.acquire(True, 2)
                    evenRandomNum = random.randrange(0, 49+1, 2)
                    self.generateNode(evenRandomNum)
                    print("After p2:")
                    self.showList(name)
                else:
                    self.generateMsg(name)
                    #break;
            var_lock.release()
        except:
            print(name, '-> Could not acquire Lock')
            time.sleep(2)
class Consumer(threading.Thread):
    def __init__(self, dblList, name):
        super(Consumer, self).__init__()
        self.dblList = dblList
        self.name = name

    def deleteHead(self):
        dblList.remove()

    def generateMsg(self, name):
        msg = "Task completed:"
        print(msg, name)

    def getNodeNum(self):
        return self.dblList.numNodes

    def showList(self,name):
        self.dblList.show(name)

    def run(self):
        #while(True):
        name = threading.currentThread().getName()
        var_lock = threading.Lock()
        try:
            var_lock.acquire()
            if(name == 'c1'):
                i = self.getNodeNum()
                if(i > 0 and i % 2 != 0):
                    print("Before c1:")
                    self.showList(name)
                    self.deleteHead()
                    print("After c1:")
                    self.showList(name)
                else:
                    self.generateMsg(name)
                    #break;
            elif(name == 'c2'):
                i = self.getNodeNum()
                if(i > 0 and i % 2 == 0):
                    print("Before c2:")
                    self.showList(name)
                    self.deleteHead()
                    print("After c2:")
                    self.showList(name)
                else:
                    self.generateMsg(name)
                    #break;
            var_lock.release()
        except:
            print(name, '-> Could not acquire Lock.')
            time.sleep(2)


class Main():

    def main():
     d = DoublyLinkedList(0)
     #initialize list with three nodes with a random int between 0 and 50
     for i in range(3):
         randomNum = random.randint(0,49)
         d.append(randomNum)
         i = i + 1
     d.show('Init')
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
