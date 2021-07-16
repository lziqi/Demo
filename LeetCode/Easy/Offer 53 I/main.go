package main

import "fmt"

/*简单遍历*/
func search(nums []int, target int) int {
	res := 0
	for _, num := range nums {
		if num == target {
			res++
		}
	}
	return res
}

/*排序数组，使用二分*/
func search1(nums []int, target int) int {
	res := 0

	left := 0
	right := len(nums) - 1
	for left < right {
		mid := (left + right) / 2
		/*用来找到等于target的值的最左边的值
		  就算nums[mid]等于target，依然将right移动到mid这里，这样有助于找到最左等于target的值
		*/
		if target <= nums[mid] {
			right = mid
		} else {
			left = mid + 1
		}
	}

	for left < len(nums) && nums[left] == target {
		left++
		res++
	}

	return res
}

func main() {
	nums := []int{5, 7, 7, 8, 8, 10}
	fmt.Println(search(nums, 8))
	fmt.Println(search1(nums, 8))
}
