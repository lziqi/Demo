package main

import "fmt"

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

func dfs(root *TreeNode,k *int,res *int){
    if root!=nil{
        dfs(root.Left,k,res)
        *k--
        if *k==0 {
            *res=root.Val
        }
        dfs(root.Right,k,res)
    }
}

func kthSmallest(root *TreeNode, k int) int {
    res := -1
    dfs(root,&k,&res)
    return res
}

func main() {
    fmt.Println("1")
}
