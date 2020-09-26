## Linked List in Java

### 1. 개념

### 2. 구현

#### 2. 1. 단방향 Linked List

```java
class Node{
  int data;
  Node next = null;
  Node(int data){ // Constructor
    this.data = d;
  }
  void append(int data){
    Node end = new Node(data);
    Node n = this;
    while(n.next !=null){
      n=n.next;
    }
    n.next = end;
  }
  void delete(int data){
    Node n = this;
    while(n.next!=null){
      if(n.next.data =data){
        n.next =n.next.next;
      }else{
        n = n.next;
      }
    }
  }
  void retrieve(){
    Node n = this;
    while(n.next!=null){
      system.out.print(n.data+"->");
      n=n.next;
    }
    System.out.println(n.data); // 마지막 data 출력
  }
}
```

