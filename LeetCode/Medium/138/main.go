package main

import "fmt"

type Node struct {
	Val    int
	Next   *Node
	Random *Node
}

var Map = make(map[*Node]*Node)

func copyRandomList(head *Node) *Node {
	if head == nil {
		return nil
	}
	if Map[head] == nil {
		newNode := &Node{Val: head.Val}
		Map[head] = newNode
		newNode.Next = copyRandomList(head.Next)
		newNode.Random = copyRandomList(head.Random)
	}
	return Map[head]
}

func main() {
	a := make(map[int]*Node)
	fmt.Println(a[3])
}
