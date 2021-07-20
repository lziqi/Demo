package main

import "fmt"

var arr = []int{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101}

func groupAnagrams(strs []string) [][]string {
	Map := make(map[int][]string)
	for _, str := range strs {
		num := 1
		for _, s := range str {
			num *= arr[s-97]
		}
		Map[num] = append(Map[num], str)
	}

	res := make([][]string, len(Map))
	i := 0
	for _, v := range Map {
		for _, str := range v {
			res[i] = append(res[i], str)
		}
		i++
	}
	return res
}

func main() {
	//strs := []string{"eat","tea","tan","ate","nat","bat"}
	strs := []string{"rod", "dye"}

	fmt.Println(groupAnagrams(strs))

	fmt.Println('a' - 97)
}
