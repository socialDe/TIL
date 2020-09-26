## Linked List in Java

* 학습 소스
  * 엔지니어대한민국 Youtube 채널의 Linked List(재생목록)
  * 윤성우의 열혈 자료구조

### 1. 개념

### 2. 구현

#### 2. 1. 단방향 Linked List

```java
class LinkedList{
	Node header;
	
	static class Node{
		int data;
		Node next =null;
	}
	LinkedList(){
		//header: data를 갖지 않고 시작을 알려주는 용도
		header =new Node();
	}
	
	void append(int d){
		Node end = new Node();
		end.data = d;
		Node n = header;
		while(n.next !=null){
			n=n.next;
		}
		n.next = end;
	}
	void delete(int d){
		Node n = header;
		while(n.next!=null){
			if(n.next.data ==d){
				n.next =n.next.next;
			}else{
				n = n.next;
			}
		}
	}
	void retrieve(){
		Node n = header.next; //header는 data를 갖지 않으므로, 다음 것부터 출
		while(n.next!=null){
			System.out.print(n.data+"->");
			n=n.next;
		}
		System.out.println(n.data); // 마지막 data 출력
	}
}
```

기본적인 단방향 Linked List의 구현 소스이다. 

##### 2.1.1. Linked List의 중복값 삭제

```java
class LinkedList_RemoveDups{
	Node header;
	
	static class Node{
		int data;
		Node next =null;
	}
	LinkedList_RemoveDups(){
		//header: data를 갖지 않고 시작을 알려주는 용도
		header =new Node();
	}
	
	void append(int d){
		Node end = new Node();
		end.data = d;
		Node n = header;
		while(n.next !=null){
			n=n.next;
		}
		n.next = end;
	}
	void delete(int d){
		Node n = header;
		while(n.next!=null){
			if(n.next.data ==d){
				n.next =n.next.next;
			}else{
				n = n.next;
			}
		}
	}
	void retrieve(){
		Node n = header.next; //header는 data를 갖지 않으므로, 다음 것부터 출
		while(n.next!=null){
			System.out.print(n.data+"->");
			n=n.next;
		}
		System.out.println(n.data); // 마지막 data 출력
	}
  
//Space_Complexity: O(1)
//Time_Complexity: O(n^2)
	void removeDups(){
		Node n = header;
		while(n!=null && n.next != null) {
			Node r = n;
			while(r.next!=null) {
				if(n.data == r.next.data) { //r은 n은 보지 말고 n 다음부터 
					r.next = r.next.next;
				}else {
					r = r.next;
				}
			}
			n=n.next;
		}
	}
}

public class RemoveDups {

	public static void main(String[] args) {
		LinkedList_RemoveDups ll = new LinkedList_RemoveDups();
		ll.append(2);
		ll.append(1);
		ll.append(2);
		ll.append(3);
		ll.append(4);
		ll.append(4);
		ll.append(2);
		ll.retrieve();
		ll.removeDups();
		ll.retrieve();
	}
}

```

이중 포인터(n, r) 구조로 n은 첫 노드, r은 n노드부터 마지막 노드까지 순회하며 비교를 할 때 r은 자신이 아닌 자신의 다음 노드와 n 노드 데이터를 비교한다. r의 next와 n이 서로 같은 데이터를 가질 경우, r은 자신의 next를 그 다음다음 노드로 붙여준다. (메모리를 효율적으로 쓰려면 아예 지워주는 delete를 사용해야 할 듯) 전체 리스트의 길이만큼 n과 r이 이중으로 순회하므로 n^2의 시간 복잡도를 갖는다. 

> 결과 Console

![스크린샷 2020-09-27 오전 12.02.46]($md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-09-27%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2012.02.46.png)

##### 2.1.2 Linked List의 끝에서부터 n번째 노드 찾기

* 해결방법
  * 1) 전체 노드의 개수를 세고 '전체 노드의 길이 - 찾는 노드의 순서(n) +1'을 찾아 반환
  * 2) 재귀 구조로 반복적으로 호출, 마지막까지 호출했다가 찾는 노드의 순서에서 멈춰 반환
  * 3) 투 포인터(p1, p2)로 접근하여 p1과 p2의 간격을 찾는 노드의 순서(n)만큼 벌려주고, 앞서 나간 포인터를 마지막까지 보내 null값에 도착하면 p2의 위치는 찾는 노드의 순서에 있음. 이를 반환



1) 전체 노드의 개수를 세고 '전체 노드의 길이 - 찾는 노드의 순서(n) +1'을 찾아 반환

```java
package linked_list;

import linked_list.LinkedList.Node;

public class KthToLast_1 {
	public static void main(String[] args) {
		LinkedList ll = new LinkedList(); 
		ll.append(1);
		ll.append(2);
		ll.append(3);
		ll.append(4);
		ll.retrieve();
		int k =3;
		Node kth = KthToLast(ll.header, k);
		System.out.println("Last k("+ k+ ")th data is " +kth.data);
	}
	//first에는 header가 들어와도 되고, 첫 번째 데이터 노드가 들어와도 된다.
	private static Node KthToLast(Node first, int k) {
		Node n =first;
		//total: LinkedList의 총 Node 개수
		int total =1;
		while(n.next!=null) {
			total++;
			n=n.next;
		}
		n = first;
		for(int i=1; i<total -k+1; i++) {
			n = n.next;
		}
		return n;
		
	}

}
```

> 결과 Console

![스크린샷 2020-09-27 오전 12.30.04]($md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-09-27%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2012.30.04.png)



2) 재귀 구조로 반복적으로 호출, 마지막까지 호출했다가 찾는 노드의 순서에서 멈춰 반환

```java
package linked_list;
import linked_list.LinkedList.Node;

public class KthToLast_Recursion {
	//Time Complexity: O(N)
	//Space Complexity: O(N)

	public static void main(String[] args) {
		LinkedList ll = new LinkedList(); 
		ll.append(1);
		ll.append(2);
		ll.append(3);
		ll.append(4);
		ll.retrieve();
		Reference r = new Reference();
		int k=2;
		Node found = KthToLast(ll.header, k, r);
		System.out.println(k+"th found Node is "+found.data);
	}
	
	//count를 저장할 클래스
	static class Reference{
		public int count = 0;
	}
	private static Node KthToLast(Node n, int k, Reference r) {
		if (n == null) {
			return null;
		}
		//재귀적으로 다음 노드를 호출하며 끝에서부터의 번호를 저장
		Node found = KthToLast(n.next, k, r);
		r.count++;
		if(r.count ==k) {
			return n;
		}
		return found;
	}

}
```

> 결과 Console

![스크린샷 2020-09-27 오전 12.31.04]($md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-09-27%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2012.31.04.png)



3) 투 포인터(p1, p2)로 접근하여 p1과 p2의 간격을 찾는 노드의 순서(n)만큼 벌려주고, 앞서 나간 포인터를 마지막까지 보내 null값에 도착하면 p2의 위치는 찾는 노드의 순서에 있음. 이를 반환

```java
package linked_list;

import linked_list.LinkedList.Node;

//Time Complexity: O(N)
//Space Complexity: O(1)
public class KthToLast_TwoPointer {
	public static void main(String[] args) {
		LinkedList ll = new LinkedList(); 
		ll.append(1);
		ll.append(2);
		ll.append(3);
		ll.append(4);
		ll.append(5);
		ll.retrieve();
		int k=2;
		Node found = KthToLast(ll.header, k);
		System.out.println(k+"th found Node is "+found.data);
	}
	private static Node KthToLast(Node first, int k) {
		Node p1 = first;
		Node p2 = first;
		//k만큼 p1과 p2의 간격을 벌려준다.
		for (int i=0; i<k; i++) {
			if(p1==null) return null;
			p1=p1.next;
 		}
		//p1이 null에 도착하면, p2는 뒤에서 k번째 노드 위치에 존재한다.
		while(p1!=null) {
			p1 = p1.next;
			p2 = p2.next;
		}
		return p2;
	}
}
```

> 결과 Console

![스크린샷 2020-09-27 오전 12.31.46]($md-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-09-27%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2012.31.46.png)





#### 2.2. 양방향 Linked List

