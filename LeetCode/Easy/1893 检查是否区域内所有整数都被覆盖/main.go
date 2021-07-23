package main

import "fmt"

func isCovered(ranges [][]int, left int, right int) bool {
	for i := left; i <= right; i++ {
		for _,nums := range ranges{
			if nums[0]<=i && i<=nums[1] {
				goto BREAK
			}
		}
		//i不在任何ranges的范围中
		return false
		BREAK:
	}
	return true
}

func main() {
	ranges := [][]int{{1,2},{3,4},{5,6}}
	fmt.Println(isCovered(ranges,2,5))
}
