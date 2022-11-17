import java.util.Random;
//yazarken bunu kullandim
/*public class Node {
    public Node above;
    public Node below;
    public Node next;
    public Node prev;
    public int key;
    public Node (int key){
        this.key = key;
        this.above = null;
        this.below = null;
        this.next = null;
        this.prev = null;
    }
}
*/
 class skipList {
    private Node head;
    private Node tail;

    private final int NEG_INFINITY = Integer.MIN_VALUE;
    private final int POS_INFINITY = Integer.MAX_VALUE;

    private int height = 0;
    private int size = 0;
    public Random random = new Random();

     public int getHeight() {
         return height;
     }

     public void setHeight(int height) {
         this.height = height;
     }
     public int getSize() {
         return size;
     }

     public void setSize(int size) {
         this.size = size;
     }


    public skipList(){
        head = new Node(NEG_INFINITY);
        tail = new Node(POS_INFINITY);
        head.next = tail;
        tail.prev = head;
    }

    public Node skipSearch(int key){
        Node n = head;
        while (n.below != null){
            n = n.below;
            while (key>= n.next.key){
                n = n.next;
            }
        }
        return n;
    }

    public Node insertNode(int key){
         int size = getSize();
        Node position = skipSearch(key);
        Node q;

        int level = -1;
        int numberHeads = -1;

        if(position.key == key){
            return position;
        }
        do{
            numberHeads++;
            level++;
            increaseLevel(level);
            q = position;
            while (position.above == null){
                position = position.prev;
            }
            position = position.above;
            q = insertAfterAbove(position, q, key);

        }while(random.nextBoolean() == true); //random olarak true geldiginde yap
        size++;
        setSize(size);
        return q;

    }
    public void increaseLevel(int level){ //level i arttirabilir miyiz
         height = getHeight();
        if(level >= height){
            height++;
            addLevel();
            setHeight(height);
        }
    }
    public void addLevel(){ //bos level ekleme
        Node newHead = new Node(NEG_INFINITY);
        Node newTail = new Node(POS_INFINITY);
        newHead.next= newTail;
        newTail.prev=newHead;
        newHead.below = head;
        newTail.below = tail;
        head.above = newHead;
        tail.above = newTail;

        head = newHead;
        tail = newTail;
    }
    public Node insertAfterAbove(Node position, Node q, int key){
        Node newNode = new Node(key);
        Node nodeOld = position.below.below;

        setReferencesBeforeAfter(q, newNode);
        setReferencesAboveBelow(position, key, newNode, nodeOld);
        return newNode;
    }
    public void setReferencesBeforeAfter(Node q, Node newNode){
        newNode.next = q.next;
        newNode.prev = q;
        q.next.prev = newNode;
        q.next = newNode;
    }

    public void setReferencesAboveBelow(Node position, int key, Node newNode, Node nodeOld){
        if(nodeOld != null){
            while(true){
                if(nodeOld.next.key != key){
                    nodeOld = nodeOld.next;
                } else{
                    break;
                }
            }
            newNode.below = nodeOld.next;
            nodeOld.next.above = newNode;

        }
        if (position != null){
            if(position.next.key == key){
                newNode.above = position.next;
            }
        }
    }

    public String toString(){
         //baslangici ve sonu -sonsuz +sonsuz olarak aldigim icin ciktida onlari da bastiriyor
         Node start = head;
         Node highest = start;
         //int level = getHeight();
         while (highest != null){
             System.out.print("\n");
             while(start != null){
                 System.out.print(start.key);
                 if(start.next != null)
                     System.out.print(" ");
                 start = start.next;
             }
             highest = highest.below;
             start = highest;
         }

        return null;
    }
    public int size(){
        return getSize();
    }

    public static void main(String[] args){
         skipList skipList = new skipList();

         skipList.insertNode(6);
        skipList.insertNode(30);
        skipList.insertNode(15);

        skipList.toString();
     }
     /*
-2147483648 2147483647
-2147483648 15 2147483647
-2147483648 15 2147483647
-2147483648 15 2147483647
-2147483648 6 15 30 2147483647
*/
//cikti boyle
 }
