package main

import (
	"fmt"
	"sort"
)

func minPairSum(nums []int) int {
	res := 0

	sort.Ints(nums)
	for i := 0; i < len(nums)/2; i++ {
		if nums[i]+nums[len(nums)-1-i] > res {
			res = nums[i] + nums[len(nums)-1-i]
		}
	}

	return res
}

func main() {
	nums := []int{3, 5, 4, 2, 4, 6}
	fmt.Println(minPairSum(nums))
}
